package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * This interface is responsible for managing all projects of the application,
 * it knows how to get every project by it's ID and can perform basic operations on them.
 */
public interface ProjectsManager extends EntityManager<Project, String> {

    /**
     * Closes the project identified by the given ID. The project is NOT deleted from the system,
     * it will only be marked as "closed" and some useless information might be deleted.
     * @param projectID the unique name of the project to close
     * @return true if the project has been deleted, false instead
     * @see ProjectsManager#deleteProject(String)
     */
    boolean closeProject(String projectID) throws EntityNotFoundException;

    Page<BasicProjectInformation> getPage(int page, int size) throws EntityNotFoundException;

    boolean existsSignature(String projectSignature);

    List<Project> findByOrganizationId(String organizationId);

    boolean submit(String projectId, Role role) throws EntityNotFoundException;

    boolean acceptCandidate(String projectId, Role userRole) throws EntityNotFoundException;

    boolean rejectCandidate(String projectId, Role userRole) throws EntityNotFoundException;

    boolean removeTeamMember(String projectId, Role userRole) throws EntityNotFoundException;

    List<Role> getUserSubmissions(String userMail) throws EntityNotFoundException;

    List<Project> getByUser(String userMail) throws EntityNotFoundException;
}
