package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller.ProjectsManager;

import java.util.Map;
import java.util.Set;

/**
 * Represents a group of {@link User}s who work together on a list of {@link Project}s. An organization
 * can be created by any {@link User}.
 */
public interface Organization {
    /**
     * Creates a new {@link Project} with the given parameters. This
     * is done using an instance of {@link ProjectsManager}.
     * @param name the new project's name
     * @param description the new project's descriptions
     * @return the newly created project
     */
    Project createProject(String name, String description);

    /**
     * Deletes a {@link Project} from the system. This is done using an
     * instance of {@link ProjectsManager}.
     * @param project the project to be deleted
     */
    void deleteProject(Project project);

    /**
     * Marks a {@link Project} as closed. This is done using an
     * instance of {@link ProjectsManager}.
     * @param project the project to be marked as closed
     */
    void closeProject(Project project);

    /**
     * When a {@link User} apply to a {@link Project} he is put on a list
     * waiting to be accepted (or rejected) by the project's {@link Organization}.
     * This method returns the list of candidates for each organization's project,
     * each one associated with its project.
     * @return the list of candidates for each organization's project
     */
    Map<Role, Project> getProjectsCandidates();

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli
    void addExpert(User user, Role role);
    void removeExpert(Expert expert);
    void addMember(User user);
    void removeMember(User user);
    Set<Project> getOpenProjects();
    Set<Project> getCloseProjects();
    Set<Project> getProjects();
    Set<User> getMembers();
    Set<Expert> getExperts();
    String getName();
    String getDescription();
    User getCreator();
    void setName(String name);
    void setDescription(String description);
}
