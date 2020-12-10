package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

public class RealRole implements Role{

    private Skill skill;
    private User user;
    private Project project;

    public RealRole(Skill skill, User user, Project project){
        this.skill=skill;
        this.user=user;
        this.project=project;
    }

    @Override
    public Skill getSkill() {
        return skill;
    }

    @Override
    public void setSkill(Skill skill) {
        this.skill=skill;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user=user;
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public void setProject(Project project) {
        this.project=project;
    }
}
