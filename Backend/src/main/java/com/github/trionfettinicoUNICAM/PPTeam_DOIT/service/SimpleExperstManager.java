package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Expert;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ExpertRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class SimpleExperstManager implements ExpertsManager{
    //TODO applicare controlli e condizioni sui metodi

    @Autowired
    private ExpertRepository repository;

    @Override
    public Expert getExpertInstance(String userMail) {
        return repository.findById(userMail).orElse(null);
    }

    @Override
    public Expert createExpert(User user) {
        Expert expert = new Expert(user.getMail(),user.getName()) ;
        return repository.save(expert);
    }

    @Override
    public boolean deleteExpert(String mail) {
        repository.deleteById(mail);
        return true;
    }

    @Override
    public boolean removeSkill(String mail, Skill skill) {
        Optional<Expert> expert = repository.findById(mail);
        expert.get().removeSkill(skill);
        repository.save(expert.get());
        return true;
    }

    @Override
    public boolean addSkill(String mail, Skill skill) {
        Optional<Expert> expert = repository.findById(mail);
        expert.get().addSkill(skill);
        repository.save(expert.get());
        return true;
    }

    @Override
    public boolean exists(String userMail) {
        return repository.existsById(userMail);
    }
}
