package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.auth0.jwt.JWT;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SimpleUsersManager implements UsersManager {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserEntity getInstance(String mail) throws EntityNotFoundException {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        return userRepository.findById(mail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+mail));
    }

    @Override
    public UserEntity create(UserEntity user) throws IdConflictException {
        checkUser(user);
        if(userRepository.existsById(user.getMail()))
            throw new IdConflictException("Esiste già un utente con questa mail");
        user.setSecret(passwordEncoder.encode(user.getSecret()));
        return userRepository.save(user);
    }

    private void checkUser(UserEntity user){
        if(user.getMail().isBlank()) throw new IllegalStateException("Il campo mail è vuoto");
        if(user.getName().isBlank()) throw new IllegalStateException("Il campo name è vuoto");
    }

    @Override
    public boolean delete(String mail) {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        if(!exists(mail)) return false;
        userRepository.deleteById(mail);
        return !exists(mail);
    }

    @Override
    public UserEntity update(UserEntity user) throws EntityNotFoundException {
        checkUser(user);
        if(!exists(user.getMail()))
            throw new EntityNotFoundException("Nessun utente trovato con la mail: "+user.getMail());
        return userRepository.save(user);
    }

    @Override
    public boolean exists(String mail) {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        if(this.userRepository.count()==0)
            return false;
        return userRepository.existsById(mail);
    }

    @Override
    public boolean existSkill(Skill newSkill, String mail) throws EntityNotFoundException {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        checkSkill(newSkill);
        UserEntity user = userRepository.findById(mail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+mail));
        for (Skill skill : user.getSkills()) {
            if(skill.equals(newSkill))
                return true;
        }
        return false;
    }

    private void checkSkill(Skill skill){
        Objects.requireNonNull(skill, "Il campo skill è nullo");
        if(skill.getName().isBlank()) throw new IllegalStateException("Il campo name del campo skill è vuoto");
        if(skill.getLevel() > 10 || skill.getLevel() <= 0) throw new IllegalStateException("Il campo name del campo skill è vuoto");
    }

    @Override
    public boolean hasSkillExpertFor(Skill newSkill, String mail, String organizationId) throws EntityNotFoundException {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        checkSkill(newSkill);
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'organizationId' è vuoto");
        UserEntity user = userRepository.findById(mail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+mail));
        Skill userSkill = null;
        for (Skill skill : user.getSkills()) {
            if(skill.equals(newSkill))
                userSkill = skill;
        }
        return userSkill != null && userSkill.isExpertFor(organizationId);
    }

    @Override
    public List<UserSubmissionInformation> getUserSubmissions(String userMail) throws EntityNotFoundException {
        List<UserSubmissionInformation> result = new ArrayList<>();
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo mail è vuoto");

        List<Project> projects = projectRepository.findAll().stream().filter(project ->
                project.getCandidates().stream().anyMatch(role -> role.getUserMail().equals(userMail)))
                .collect(Collectors.toList());

        for(Project project: projects){
            Organization organization = organizationRepository.findById(project.getOrganizationId())
                    .orElseThrow(()->new EntityNotFoundException("Nessuna organizzazione"));

            List<Role> roles = project.getCandidates().stream().filter(role -> role.getUserMail().equals(userMail))
                    .collect(Collectors.toList());

            result.add(new UserSubmissionInformation(organization,project,roles));
        }
        return result;
    }

    @Override
    public Page<UserEntity> getPage(int page, int size) throws EntityNotFoundException {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<UserEntity> getExpertPage(int page, int size) {
        Page<UserEntity> userPage = userRepository.findAll(PageRequest.of(page, size));
        List<UserEntity> basicUserInformationList = new java.util.ArrayList<>(Collections.emptyList());
        for(UserEntity user : userPage) {
            for (Skill userSkill: user.getSkills()) {
                if(userSkill.isGloballyExpert()) {
                    basicUserInformationList.add(user);
                    break;
                }
            }
        }
        return new PageImpl<>(basicUserInformationList);
    }

    public boolean addNewSkill(String skillName,String userMail) throws EntityNotFoundException,IllegalArgumentException {
        if(skillName.isBlank()) throw new IllegalArgumentException("Nome skill non valida");
        if(userMail.isBlank()) throw new IllegalArgumentException("userMail non valida");
        UserEntity user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        if (user.getSkills().stream().anyMatch(s -> s.getName().equals(skillName))) {
            return false;
        }
        user.addSkill(new Skill(skillName));
        userRepository.save(user);
        return true;
    }

    public boolean removeSkill(Skill skill,String userMail) throws EntityNotFoundException{
        if(skill==null)
            throw new IllegalArgumentException("skill non valida");
        UserEntity user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        user.removeSkill(skill);
        userRepository.save(user);
        return true;
    }

    public String refreshToken(String token){
        return tokenService.refresh(token);
    }

    public boolean validateToken(String token){
        return tokenService.isValid(token);
    }
}
