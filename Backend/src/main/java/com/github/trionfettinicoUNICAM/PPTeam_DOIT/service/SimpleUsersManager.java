package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleUsersManager implements UsersManager {
    //TODO applicare controlli e condizioni sui metodi

    @Autowired
    private UserRepository repository;

    @Override
    public User getUserInstance(String userMail) {
        return repository.findById(userMail).orElse(null);
    }

    @Override
    public User createUser(String mail, String name) {
        User user = new User(mail, name);
        return repository.save(user);
    }

    @Override
    public boolean deleteUser(String mail) {
        repository.deleteById(mail);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        repository.save(user);
        return true;
    }

    @Override
    public boolean exists(String userMail) {
        if(this.repository.count()==0)
            return false;
        return repository.existsById(userMail);
    }

    @Override
    public boolean existSkill(Skill newSkill, String userMail) {
        for (Skill skill : repository.findById(userMail).get().getSkills()) {
            if(skill.equals(newSkill))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasSkillExpertFor(Skill newSkill, String userMail, String organizationName) {
        Optional<User> user = repository.findById(userMail);
        Skill userSkill = null;
        if(user.isPresent()){
            for (Skill skill : user.get().getSkills()) {
                if(skill.equals(newSkill))
                    userSkill = skill;
            }
            return userSkill != null && userSkill.isExpertFor(organizationName);
        }
        return false;
    }


    @Override
    public boolean addCollaborator(String userEmail,Skill skill) {
        Optional<User> user = repository.findById(userEmail);
        if(user.isPresent()) {
            user.get().addSkill(skill);
            repository.save(user.get());
            return true;
        }
        return false;
    }
}
