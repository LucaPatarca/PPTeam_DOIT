package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimpleOrganizationsManager implements OrganizationsManager{

    @Autowired
    private OrganizationRepository repositoryOrg;
    @Autowired
    private UserRepository repositoryUser;
    @Autowired
    private ProjectRepository repositoryProject;

    @Override
    public Organization getOrganizationInstance(String organizationId) {
        return repositoryOrg.findById(organizationId).orElse(null);
    }

    @Override
    public Organization createNewOrganization(Organization organization) {
        if(!organization.getMembersMails().contains(organization.getCreatorMail()))
            organization.addMember(organization.getCreatorMail());
        return repositoryOrg.save(organization);
    }

    @Override
    public boolean deleteOrganization(String organizationId) {
        repositoryOrg.deleteById(organizationId);
        for (Project project:repositoryProject.findByOrganizationId(organizationId)) {
            repositoryProject.deleteById(project.getId());
        }
        return !exists(organizationId);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        //TODO si deve prima cancellare l'instanza vecchia
        repositoryOrg.save(organization);
        return getOrganizationInstance(organization.getId());
    }

    @Override
    public List<User> getUsers(String organizationId) {
        Optional<Organization> org = repositoryOrg.findById(organizationId);
        List<User> users = new ArrayList<>();
        for (String email : org.get().getMembersMails()) {
            users.add(repositoryUser.findById(email).get());
        }
        return users;
    }

    @Override
    public boolean existsName(String organizationName) {
        return repositoryOrg.findAll().stream()
                .anyMatch(it->it.getName().equals(organizationName));
    }

    @Override
    public boolean exists(String organizationId) {
        return repositoryOrg.existsById(organizationId);
    }

    @Override
    public List<Organization> findByUser(String userMail) {
        return repositoryOrg.findByMember(userMail);
    }

    @Override
    public List<Organization> findCreator(String userMail) {
        return repositoryOrg.findByCreatorMail(userMail);
    }


    @Override
    public Page<Organization> getPage(int page, int size) {
        return repositoryOrg.findAll(PageRequest.of(page, size));
    }

    @Override
    public boolean addCollaborator(String organizationId, String userMail, Skill skill) {
        if(repositoryOrg.findById(organizationId).isPresent() && repositoryUser.findById(userMail).isPresent()){
            Organization organization = repositoryOrg.findById(organizationId).get();
            organization.addMember(userMail);
            repositoryOrg.save(organization);
            User user = repositoryUser.findById(userMail).get();
            user.setExpert(skill,organizationId);
            repositoryUser.save(user);
            return true;
        }
        return false;
    }

}
