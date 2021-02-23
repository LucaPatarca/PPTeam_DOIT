package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserSubmissionInformation;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UsersController extends EntityController<UserEntity, String> {

    Set<Skill> getUserSkill(String userEmail) throws EntityNotFoundException;

    boolean existSkill(String skill, String userEmail) throws EntityNotFoundException;

    List<UserSubmissionInformation> getUserSubmissions(String userMail) throws EntityNotFoundException;

    boolean addNewSkill(String userID, String skillName) throws EntityNotFoundException;

    boolean removeSkill(String userID,Skill skill) throws EntityNotFoundException;

    Page<UserEntity> getPage(int page) throws EntityNotFoundException;
}
