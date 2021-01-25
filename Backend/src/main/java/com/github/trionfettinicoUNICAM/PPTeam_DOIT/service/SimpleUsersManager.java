package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleUsersManager implements UsersManager {
    //TODO applicare controlli e condizioni sui metodi

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public User getUserInstance(String userMail) throws EntityNotFoundException {
        return userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
    }

    @Override
    public User createUser(User user) throws IdConflictException {
        if(userRepository.existsById(user.getMail()))
            throw new IdConflictException("Esiste già un utente con questa mail");
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(String mail) throws EntityNotFoundException {
        if(!exists(mail))
            throw new EntityNotFoundException("Nessun utente trovato con la mail: "+mail);
        userRepository.deleteById(mail);
        return !userRepository.existsById(mail);
    }

    @Override
    public User updateUser(User user) throws EntityNotFoundException {
        if(!exists(user.getMail()))
            throw new EntityNotFoundException("Nessun utente trovato con la mail: "+user.getMail());
        return userRepository.save(user);
    }

    @Override
    public boolean exists(String userMail) {
        if(this.userRepository.count()==0)
            return false;
        return userRepository.existsById(userMail);
    }

    @Override
    public boolean existSkill(Skill newSkill, String userMail) throws EntityNotFoundException {
        User user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        for (Skill skill : user.getSkills()) {
            if(skill.equals(newSkill))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasSkillExpertFor(Skill newSkill, String userMail, String organizationId) throws EntityNotFoundException {
        User user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        Skill userSkill = null;
        for (Skill skill : user.getSkills()) {
            if(skill.equals(newSkill))
                userSkill = skill;
        }
        return userSkill != null && userSkill.isExpertFor(organizationId);
    }
}
