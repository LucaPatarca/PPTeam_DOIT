package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import org.bson.types.ObjectId;

import java.util.UUID;


public interface UsersManager {
    User getUserInstance(String userMail);
    User createUser(String mail, String name);
    boolean deleteUser(String mail);
    boolean updateUser(User user);
    boolean exists(String userMail);
    boolean existSkill(Skill skill, String userMail);
    boolean hasSkillExpertFor(Skill skill, String userMail, String organizationId);
    boolean addCollaborator(String userEmail,Skill skill);

}
