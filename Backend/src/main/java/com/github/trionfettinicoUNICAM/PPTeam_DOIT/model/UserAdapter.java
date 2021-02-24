package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Set;

public class UserAdapter extends User {

    private String name;
    private Set<Skill> skills;

    public UserAdapter(UserEntity user) {
        super(user.getMail(), user.getSecret(), new ArrayList<>());
        this.name = user.getName();
        this.skills = user.getSkills();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public String getMail(){
        return this.getUsername();
    }
}
