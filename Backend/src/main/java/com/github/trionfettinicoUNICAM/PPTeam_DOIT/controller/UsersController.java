package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;

import java.util.Set;

public interface UsersController extends EntityController<User, String> {

    Set<Skill> getUserSkill(String userEmail) throws EntityNotFoundException;

    boolean existSkill(String skill, String userEmail) throws EntityNotFoundException;

    boolean addCollaborator(String organizationId, String userMail, String skillName) throws EntityNotFoundException;

}
