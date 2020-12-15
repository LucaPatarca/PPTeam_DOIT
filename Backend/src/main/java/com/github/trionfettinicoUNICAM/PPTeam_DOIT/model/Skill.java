package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

/**
 * It's just a label that represents a skill which a {@link User} can know and an {@link Expert} can master.
 * Each {@link Project}s has a set of skills that are needed to complete the project.
 */
public interface Skill {
    // TODO: 15/12/20 far diventare una classe come fatto con project
    String getName();
}
