package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SimpleUsersManager implements UsersManager {
    //TODO applicare controlli e condizioni sui metodi

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserEntity getInstance(String mail) throws EntityNotFoundException {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        return userRepository.findById(mail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+mail));
    }

    @Override
    public UserEntity create(UserEntity user) throws IdConflictException {
        if(userRepository.existsById(user.getMail()))
            throw new IdConflictException("Esiste già un utente con questa mail");
        user.setSecret(passwordEncoder.encode(user.getSecret()));
        return userRepository.save(user);
    }

    @Override
    public boolean delete(String mail) throws EntityNotFoundException {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
        if(!exists(mail))
            throw new EntityNotFoundException("Nessun utente con la mail: "+mail);
        userRepository.deleteById(mail);
        return !userRepository.existsById(mail);
    }

    @Override
    public UserEntity update(UserEntity user) throws EntityNotFoundException {
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
        UserEntity user = userRepository.findById(mail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+mail));
        for (Skill skill : user.getSkills()) {
            if(skill.equals(newSkill))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasSkillExpertFor(Skill newSkill, String mail, String organizationId) throws EntityNotFoundException {
        if(mail.isBlank()) throw new IllegalArgumentException("Il campo 'mail' è vuoto");
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
}
