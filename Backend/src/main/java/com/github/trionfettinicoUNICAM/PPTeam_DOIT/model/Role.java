package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

/**
 * Describes the role a {@link User} has inside a {@link Team}. It is responsible for associating a {@link User}
 * to the {@link Skill} for which it is needed in the {@link Project}.
 */
public interface Role {
    // TODO: 15/12/20 far diventare una classe come fatto con project

    RealSkill getSkill();

    void setSkill(RealSkill skill);

    String getUserMail();

    void setUserMail(String userMail);

    String getProjectName();

    void setProjectName(String projectName);

}
