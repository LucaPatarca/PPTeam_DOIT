package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;

/**
 * This interface is responsible for managing all projects of the application,
 * it knows how to get every project by it's ID and can perform basic operations on them.
 */
public interface ProjectsManager {
    /**
     * Retrieves the project associated with the given ID and returns an instance of it.
     * @param projectID the ID of the wanted project
     * @return an instance of the project
     */
    Project getProjectInstance(int projectID);

    /**
     * Creates a new empty (with an empty team and no experts) project. The new project is associated
     * with the given {@link Organization}.
     * @param organization the organization responsible for the project creation
     * @param title the title of the new project
     * @param description the text description of the new project
     * @return the id of the newly created project
     */
    int openNewEmptyProject(Organization organization, String title, String description);

    /**
     * Closes the project identified by the given ID. The project is NOT deleted from the system,
     * it will only be marked as "closed" and some useless information might be deleted.
     * @param projectID the ID of the project to close
     * @return true if the project has been deleted, false instead
     * @see ProjectsManager#deleteProject(int)
     */
    boolean closeProject(int projectID);

    /**
     * Deletes a project from the system.
     * @param projectID the ID of the project to delete
     * @return true if the project has been deleted, false instead
     */
    boolean deleteProject(int projectID);

    /**
     * Adds a new member to a project's {@link Team}.
     * @param projectID the project's ID
     * @param user the new member
     * @param skill the skill which the new member is needed for
     */
    void addToTeam(int projectID, User user, Skill skill);

    /**
     * Adds a new {@link Expert} to a project.
     * @param projectID the project's ID
     * @param expert the new expert
     */
    void addExpert(int projectID, Expert expert);

    /**
     * Removes a member from a project's {@link Team}.
     * @param projectID the project's ID
     * @param role the role of the member to be removed
     */
    void removeFromTeam(int projectID, Role role);

    /**
     * Removes an {@link Expert} from a project.
     * @param projectID the project's ID
     * @param expert the expert to be removed
     */
    void removeExpert(int projectID, Expert expert);
}
