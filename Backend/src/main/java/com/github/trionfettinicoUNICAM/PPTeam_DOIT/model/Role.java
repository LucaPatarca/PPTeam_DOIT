package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

/**
 * Describes the role a {@link User} has inside a {@link Team}. It is responsible for associating a {@link User}
 * to the {@link Skill} for which it is needed in the {@link Project}.
 */
public interface Role {

    Skill getSkill();

    void setSkill(Skill skill);

    int getUserId();

    void setUserId();

}
