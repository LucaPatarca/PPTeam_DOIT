package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class SimpleUserManager implements UserManager{
    //TODO applicare controlli e condizioni sui metodi

    @Autowired
    private UserRepository repository;

    @Override
    public User getUserInstance(String userMail) {
        return repository.findById(userMail).orElse(null);
    }

    @Override
    public User createUser(String mail, String name, Integer age) {
        User user = new User(mail, name, age);
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
        return repository.existsById(userMail);
    }
}
