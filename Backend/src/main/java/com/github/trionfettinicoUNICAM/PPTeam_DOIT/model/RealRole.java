package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

public class RealRole implements Role{

    private Skill skill;
    private String userMail;
    private String projectName;

    public RealRole(Skill skill, String userMail, String projectName){
        this.skill=skill;
        this.userMail=userMail;
        this.projectName=projectName;
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
    public String getUserMail() {
        return userMail;
    }

    @Override
    public void setUserMail(String userMail) {
        this.userMail=userMail;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName=projectName;
    }
}
