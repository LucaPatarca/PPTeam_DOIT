package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.ProjectsManager;

import java.util.Map;
import java.util.Set;

/**
 * Represents a group of {@link User}s who works together on a list of {@link Project}s. An organization
 * can be created by any {@link User}.
 */
public interface Organization {
    /**
     * Creates a new {@link Project} with the given parameters. This
     * is done using an instance of {@link ProjectsManager}.
     * @param name the new project's name
     * @param description the new project's descriptions
     * @param creator the {@link User} who wants to crate the project
     * @return the newly created project
     */
    Project createProject(String name, String description, User creator);

    /**
     * Deletes a {@link Project} from the system. This is done using an
     * instance of {@link ProjectsManager}.
     * @param projectName the name of the project to be deleted
     * @throws IllegalArgumentException if the project is not managed by this organization
     */
    void deleteProject(String projectName);

    /**
     * Marks a {@link Project} as closed. This is done using an
     * instance of {@link ProjectsManager}.
     * @param projectName the name of the project to be marked as closed
     * @throws IllegalArgumentException if the project is not managed by this organization
     */
    void closeProject(String projectName);

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
    void removeExpert(User expert);
    void addMember(User user);
    void removeMember(User user);

    /*  i metodi sotto ritornano un Project anche se la classe non avra al suo interno le istanze dei progetti
        la classe pero avra un ProjectManager quindi puo chiamare il metodo getProjectInstance() per ottenere le istanze
     */
    Set<Project> getOpenProjects();
    Set<Project> getCloseProjects();
    Set<Project> getProjects();

    Set<String> getMembersMails();
    Set<String> getExpertsMails();
    String getName();
    String getDescription();
    String getCreatorMail();
    void setName(String name);
    void setDescription(String description);
}
