package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;


public interface UsersManager {
    User getUserInstance(String userMail) throws EntityNotFoundException;
    User createUser(User user) throws IdConflictException;
    boolean deleteUser(String mail) throws EntityNotFoundException;
    User updateUser(User user) throws EntityNotFoundException;
    boolean exists(String userMail);
    boolean existSkill(Skill skill, String userMail) throws EntityNotFoundException;
    boolean hasSkillExpertFor(Skill skill, String userMail, String organizationId) throws EntityNotFoundException;
}
