package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller.OrganizationsManager;

import java.util.Set;

/**
 * Represents a registered user inside the application. It has the ability to join a project's team
 * and to do so, it needs a list of {@link Skill}s.
 */
public interface User {
    /**
     * Adds a skill to the user's list.
     * @param skill the skill to add
     */
    void addSkill(Skill skill);

    void addSkills(Set<Skill> skills);

    /**
     * Removes a skill from the user's list.
     * @param skill the skill to remove
     */
    void removeSkill(Skill skill);

    /**
     * @return the set of skills this user has.
     */
    Set<Skill> getSkills();

    /**
     * Creates a new empty {@link Organization}, this user will be part of that organization.
     * This is done using a {@link OrganizationsManager} included in this user.
     * @param title the new organization's title
     * @param description the new organization's description
     */
    void createOrganization(String title, String description);

    /**
     * Deletes an {@link Organization} from the system, this is possible only if this user if part of
     * that organization.
     * This is done using a {@link OrganizationsManager} included in this user.
     * @param organization the organization to be deleted
     */
    void deleteOrganization(Organization organization);

    /**
     * When a {@link User} apply to a {@link Project} he is put on a list
     * waiting to be accepted (or rejected) by the project's {@link Organization}.
     * @param project the project to which the user wants to apply
     * @param skill the skill for which the user propose itself
     * @throws UnsupportedOperationException if this project is closed
     * @throws IllegalArgumentException if the user doesn't have the skill or if the skill
     * is not needed for the project
     */
    void applyToProject(Project project, Skill skill);

    /**
     * @return the list of {@link Role}s this user had submitted for.
     */
    Set<Role> getSubmissions();

    /**
     * @return the set of {@link Role}s for which this user had been accepted.
     */
    Set<Role> getRoles();

    /**
     * The {@link User} mail is unique for each user so a user can be identified by it's mail.
     * @return the user's mail
     */
    String getMail();
}
