package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;

public interface UserManager {
    User getUserInstance(String userMail);
    User createUser(String mail, String name, Integer age);
    boolean deleteUser(String mail);
    boolean updateUser(User user);
    boolean exists(String userMail);
}
