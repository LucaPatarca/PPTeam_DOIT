package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.Map;
import java.util.Set;

/**
 * Represents a project, it has a {@link Team}, a list of candidates, and a set of {@link Skill}s
 * that are needed to complete this project's task.
 */
public interface Project {
    /**
     * When a {@link User} apply to a {@link Project} he is put on a list
     * waiting to be accepted (or rejected) by the project's {@link Organization}.
     * This method returns the set of candidates.
     * @return the set of candidates.
     */
    Set<Role> getCandidates();

    /**
     * Accepts the {@link Role} of a {@link User} who applied for this project.
     * After this operation the {@link Role} becomes part of the project's {@link Team}.
     * The {@link User} should be notified for this operation.
     * @param role the role to accept
     */
    void acceptCandidate(Role role);

    /**
     * Rejects the {@link Role} of a {@link User} who applied for this project.
     * @param role the role to be rejected
     */
    void rejectCandidate(Role role);

    /**
     * @return true if the project is closed, false instead
     */
    boolean isClosed();

    /**
     * Marks this project as closed. After this operation no one can apply to this project
     */
    void close();

    /**
     * To be successful each project needs a set of {@link Skill}s, {@link User}s can than use
     * their skills to work for the project.
     * @return all the needed skills for this project grouped by name, each one associated
     * with it's count.
     */
    Map<Skill, Integer> getNeededSkills();
}
