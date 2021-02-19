package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicOrganizationInformation;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * This interface is responsible for managing all organizations of the application,
 * it knows how to get every organization by it's ID and can can create new organizations or 
 * delete old ones.
 */
public interface OrganizationsManager extends EntityManager<Organization, String>{

    /**
     * Retrieves the organization with the given ID and returns an instance of it.
     * @return an instance of the organization
     */
    Organization getInstance(@Valid @NotNull @NotBlank String organizationId) throws EntityNotFoundException;

    /**
     * Creates a new {@link Organization} with the given parameters
     * @param  organization the organization
     * @return the newly created organization
     */
    Organization create(Organization organization) throws EntityNotFoundException, IdConflictException;

    /**
     * Removes an {@link Organization} from the system.
     * @return true if the organization is successfully removed, false instead
     */
    boolean delete(String organizationId);

    /**
     * Updates the {@link Organization} passed as parameter and saves it.
     * @param organization the organization to be saved
     * @return true if the organization is stored successfully, false instead.
     */
    Organization update(Organization organization) throws EntityNotFoundException;

    List<UserEntity> getUsers(String organizationId) throws EntityNotFoundException;

    boolean existsName(String organizationName);

    boolean exists(String organizationId);

    List<Organization> findByUser(String userMail);

    List<Organization> findByCreator(String userMail);

    Page<BasicOrganizationInformation> getPage(int page, int i) throws EntityNotFoundException;

    void addCollaborator(String organizationId, String userMail, Skill skill) throws EntityNotFoundException;

    void addExpert(String organizationId, String userMail, Skill skill) throws EntityNotFoundException;

    boolean removeMember(String organizationId, String memberMail) throws EntityNotFoundException;

    boolean addMember(String organizationId, String memberMail) throws EntityNotFoundException;

}
