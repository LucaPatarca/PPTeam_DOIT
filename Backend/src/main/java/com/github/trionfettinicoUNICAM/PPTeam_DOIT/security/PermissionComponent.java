package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Role;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
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

    public boolean sameMail(Authentication authentication, String userMail){
        return authentication.isAuthenticated() && authentication.getName().equals(userMail);
    }

    public boolean isFounder(Authentication authentication, String organizationId){
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if (organization.isEmpty()) return false;
        return organization.get().getCreatorMail().equals(authentication.getName());
    }

    public boolean isProjectCreator(Authentication authentication, String projectId){
        // TODO: 14/02/2021 implement
        return true;
    }

    public boolean isExpert(Authentication authentication, String orgId, Skill skill){
        // TODO: 14/02/2021 implement
        if(Objects.isNull(skill)) return false;
        return true;
    }

    public boolean isMember(Authentication authentication, String orgId){
        // TODO: 14/02/2021 implement
        return true;
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
