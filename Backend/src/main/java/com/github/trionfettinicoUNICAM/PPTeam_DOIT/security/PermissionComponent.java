package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PermissionComponent {

    @Autowired
    private OrganizationRepository organizationRepository;

    public boolean sameMail(Authentication authentication, String userMail){
        System.out.println(authentication.getName());
        System.out.println(userMail);
        return authentication.getName().equals(userMail);
    }

    public boolean isOrganizationCreator(Authentication authentication, String organizationId){
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if (organization.isEmpty()) return false;
        return organization.get().getCreatorMail().equals(authentication.getName());
    }

    public boolean canAddCollaborator(Authentication authentication, String orgId, String userMail, Skill skill){
        // TODO: 14/02/2021 implement
        return true;
    }
}
