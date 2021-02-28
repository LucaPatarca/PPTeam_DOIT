package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class PermissionComponent {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean sameMail(Authentication authentication, String userMail){
        return authentication.isAuthenticated() && authentication.getName().equals(userMail);
    }

    public boolean isFounder(Authentication authentication, String organizationId){
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if (organization.isEmpty()) return false;
        return organization.get().getCreatorMail().equals(authentication.getName());
    }

    public boolean isProjectCreator(Authentication authentication, String projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) return false;
        Optional<UserEntity> user = userRepository.findById(authentication.getName());
        if (user.isEmpty()) return false;
        return project.get().getCreatorMail().equals(authentication.getName());
    }

    public boolean isExpert(Authentication authentication, String orgId, Skill skill){
        if(Objects.isNull(skill)) return false;
        Optional<Organization> organization = organizationRepository.findById(orgId);
        if (organization.isEmpty()) return false;
        Optional<UserEntity> user = userRepository.findById(authentication.getName());
        if (user.isEmpty()) return false;
        return isMember(authentication,orgId) && skill.isExpertFor(orgId) && user.get().getSkills().contains(skill);
    }

    public boolean isMember(Authentication authentication, String orgId){
        Optional<Organization> organization = organizationRepository.findById(orgId);
        if (organization.isEmpty()) return false;
        Optional<UserEntity> user = userRepository.findById(authentication.getName());
        if (user.isEmpty()) return false;
        return organization.get().getMembersMails().contains(authentication.getName());
    }

    public boolean isProjectManager(Authentication authentication, String projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) return false;
        return isFounder(authentication, project.get().getOrganizationId()) || isProjectCreator(authentication, projectId);
    }

    public boolean isTeamManager(Authentication authentication, String projectId, Skill skill){
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) return false;
        return isProjectManager(authentication, projectId) ||
                isExpert(authentication,project.get().getOrganizationId(),skill);
    }
}
