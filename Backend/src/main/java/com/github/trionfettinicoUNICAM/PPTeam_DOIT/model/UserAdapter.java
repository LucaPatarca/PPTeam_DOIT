package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class UserAdapter extends User {
    public UserAdapter(UserEntity user) {
        super(user.getMail(), user.getSecret(), new ArrayList<>());
    }
}
