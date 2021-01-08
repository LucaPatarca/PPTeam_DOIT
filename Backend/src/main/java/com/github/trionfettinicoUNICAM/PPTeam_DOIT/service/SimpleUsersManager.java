package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
