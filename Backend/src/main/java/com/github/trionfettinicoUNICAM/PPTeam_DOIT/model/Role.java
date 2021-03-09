package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Objects;

/**
 * Describes the role a {@link UserEntity} has inside a {@link Team}. It is responsible for associating a {@link UserEntity}
 * to the {@link Skill} for which it is needed in the {@link Project}.
 */
public class Role{

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)

    private Skill skill;
    private String userMail;
    private boolean asExpert;

    public Role(Skill skill, String userMail, boolean asExpert) throws IllegalArgumentException {
        setSkill(skill);
        setUserMail(userMail);
        setAsExpert(asExpert);
    }

    public Role(){
        this.skill = null;
        this.userMail = "";
        this.asExpert = false;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill= Objects.requireNonNull(skill, "Skill is Null");;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) throws IllegalArgumentException {
        if(userMail.isBlank()) throw new IllegalArgumentException("UserMail is empty");
        this.userMail=userMail;
    }

    public boolean isAsExpert() {
        return asExpert;
    }

    public void setAsExpert(boolean asExpert) {
        this.asExpert = asExpert;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return asExpert == role.asExpert && Objects.equals(skill, role.skill) && Objects.equals(userMail, role.userMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, userMail, asExpert);
    }
}
