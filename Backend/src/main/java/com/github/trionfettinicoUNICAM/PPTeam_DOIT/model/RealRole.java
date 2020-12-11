package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

public class RealRole implements Role{

    private Skill skill;
    private String userMail;
    private String projectName;

    public RealRole(Skill skill, String userMail, String projectName) throws IllegalArgumentException {
        setSkill(skill);
        setUserMail(userMail);
        setProjectName(projectName);
    }

    @Override
    public Skill getSkill() {
        return skill;
    }

    @Override
    public void setSkill(Skill skill) {
        this.skill=Objects.requireNonNull(skill, "Skill is Null");;
    }

    @Override
    public String getUserMail() {
        return userMail;
    }

    @Override
    public void setUserMail(String userMail) throws IllegalArgumentException {
        if(userMail.length() == 0) throw new IllegalArgumentException("UserMail is empty");
        this.userMail=userMail;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) throws IllegalArgumentException {
        if(projectName.length() == 0) throw new IllegalArgumentException("ProjectName is empty");
        this.projectName=projectName;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        RealRole realRole = (RealRole) object;
        return userMail.equals(realRole.userMail) && projectName.equals(realRole.projectName);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), userMail, projectName);
    }
}
