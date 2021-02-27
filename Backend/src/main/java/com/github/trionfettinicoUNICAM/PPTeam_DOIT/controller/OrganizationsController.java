package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrganizationsController extends EntityController<Organization, String>{

    List<Organization> getByUser(String userMail);

    List<UserEntity> getUsers(String organizationId) throws EntityNotFoundException;

    void addCollaborator(String organizationId, String userMail, Skill skill) throws EntityNotFoundException;

    boolean addMember(String organizationId, String userMail) throws EntityNotFoundException;

    boolean removeMember(String organizationId, String userMail, Boolean removeProjects) throws EntityNotFoundException;

    List<Organization> findByCreator(String userMail);

    Page<BasicOrganizationInformation> getPage(int page) throws EntityNotFoundException;

}
