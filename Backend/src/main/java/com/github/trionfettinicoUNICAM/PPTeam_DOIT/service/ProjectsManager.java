package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * This interface is responsible for managing all projects of the application,
 * it knows how to get every project by it's ID and can perform basic operations on them.
 */
public interface ProjectsManager {
    /**
     * Retrieves the project associated with the given ID and returns an instance of it.
     * @param projectID the unique name of the wanted project
     * @return an instance of the project
     */
    Project getProjectInstance(String projectID);

    /**
     * Creates a new empty (with an empty team and no experts) project. The new project is associated
     * with the given {@link Organization}.
     * @param  project the project
     * @return the newly created Project
     * @throws IllegalArgumentException if the {@link User} is not part of the organization
     */
    Project createNewProject(Project project);

    /**
     * Closes the project identified by the given ID. The project is NOT deleted from the system,
     * it will only be marked as "closed" and some useless information might be deleted.
     * @param projectID the unique name of the project to close
     * @return true if the project has been deleted, false instead
     * @see ProjectsManager#deleteProject(String)
     */
    boolean closeProject(String projectID);

    /**
     * Deletes a project from the system.
     * @param projectID the unique name of the project to delete
     * @return true if the project has been deleted, false instead
     */
    boolean deleteProject(String projectID);

    /**
     * Updates the {@link Project} passed as parameter and saves it.
     * @param project the project to be saved
     * @return true if the project is stored successfully, false instead.
     */
    Project updateProject(Project project);

    Page<Project> getPage(int page, int size);

    boolean exists(String projectID);

    List<Project> findByOrganizationName(String organizationName);
}
