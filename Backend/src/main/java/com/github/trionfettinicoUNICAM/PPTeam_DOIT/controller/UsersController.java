package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;

import java.util.Set;

public interface UsersController extends EntityController<UserEntity, String> {

    Set<Skill> getUserSkill(String userEmail) throws EntityNotFoundException;

    boolean existSkill(String skill, String userEmail) throws EntityNotFoundException;
}
