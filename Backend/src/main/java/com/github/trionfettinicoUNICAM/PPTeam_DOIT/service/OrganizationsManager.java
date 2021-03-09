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

    List<UserEntity> getUsers(String organizationId) throws EntityNotFoundException;

    boolean existsName(String organizationName);

    List<Organization> findByUser(String userMail);

    List<Organization> findByCreator(String userMail);

    Page<BasicOrganizationInformation> getPage(int page, int i) throws EntityNotFoundException;

    void addCollaborator(String organizationId, String userMail, Skill skill) throws EntityNotFoundException;

    boolean removeMember(String organizationId, String memberMail, Boolean removeProjects) throws EntityNotFoundException;

    boolean addMember(String organizationId, String memberMail) throws EntityNotFoundException;

}
