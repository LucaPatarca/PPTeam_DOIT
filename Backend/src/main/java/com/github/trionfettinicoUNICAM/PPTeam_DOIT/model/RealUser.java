package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class RealUser implements User{

    @Id
    private final String mail;

    public RealUser(String mail){
        this.mail = mail;
    }

    @Override
    public void addSkill(Skill skill) {

    }

    @Override
    public void addSkills(Set<Skill> skills) {

    }

    @Override
    public void removeSkill(Skill skill) {

    }

    @Override
    public Set<Skill> getSkills() {
        return null;
    }

    @Override
    public void createOrganization(String title, String description) {

    }

    @Override
    public void deleteOrganization(Organization organization) {

    }

    @Override
    public void applyToProject(Project project, Skill skill) {

    }

    @Override
    public Set<Role> getSubmissions() {
        return null;
    }

    @Override
    public Set<Role> getRoles() {
        return null;
    }

    @Override
    public String getMail() {
        return mail;
    }
}
