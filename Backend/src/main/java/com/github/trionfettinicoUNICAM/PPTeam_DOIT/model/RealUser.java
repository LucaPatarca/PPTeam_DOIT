package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class RealUser implements User{

    @Id
    private String mail;
    private String name;
    private int age;
    private Set<Skill> skills;
    private Set<Role> roles;
    private Set<Role> submissions;

    public RealUser(String mail, String name, Integer age){
        this.mail = mail;
        this.name = name;
        this.age = age;
    }

    @Override
    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    @Override
    public void addSkills(Set<Skill> skills) {
        this.skills.addAll(skills);
    }

    @Override
    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    @Override
    public Set<Skill> getSkills() {
        return skills;
    }

    @Override
    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public Set<Role> getSubmissions() {
        return submissions;
    }

    @Override
    public void setSubmissions(Set<Role> submissions) {
        this.submissions = submissions;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }
}
