package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

@Service
public class SimpleOrganizationsManager implements OrganizationsManager{

    @Autowired
    private OrganizationRepository repositoryOrg;
    @Autowired
    private UserRepository repositoryUser;

    @Override
    public Organization getOrganizationInstance(String organizationName) {
        return repositoryOrg.findById(organizationName).orElse(null);
    }

    @Override
    public Organization createNewOrganization(Organization organization) {
        if(!organization.getMembersMails().contains(organization.getCreatorMail()))
            organization.addMember(organization.getCreatorMail());
        return repositoryOrg.save(organization);
    }

    @Override
    public boolean deleteOrganization(String organizationName) {
        repositoryOrg.deleteById(organizationName);
        return !exists(organizationName);
    }

    @Override
    public boolean updateOrganization(Organization organization) {
        repositoryOrg.save(organization);
        return true;
    }

    @Override
    public List<User> getUsers(String organizationName) {
        Optional<Organization> org = repositoryOrg.findById(organizationName);
        List<User> users = new ArrayList<>();
        for (String email : org.get().getMembersMails()) {
            users.add(repositoryUser.findById(email).get());
        }
        return users;
    }

    @Override
    public boolean exists(String organizationName) {
        return repositoryOrg.existsById(organizationName);
    }

    @Override
    public List<Organization> findByUser(String userMail) {
        return repositoryOrg.findByMember(userMail);
    }

    @Override
    public Page<Organization> getPage(int page, int size) {
        return repositoryOrg.findAll(PageRequest.of(page, size));
    }

    @Override
    public boolean addCollaborator(String organizationName, String userMail, Skill skill) {
        if(repositoryOrg.findById(organizationName).isPresent() && repositoryUser.findById(userMail).isPresent()){
            Organization organization = repositoryOrg.findById(organizationName).get();
            organization.addMember(userMail);
            repositoryOrg.save(organization);
            User user = repositoryUser.findById(userMail).get();
            Optional<Skill> optionalSkill = user.getSkills().stream().filter(it->it.equals(skill)).findAny();
            if(optionalSkill.isPresent()){
                optionalSkill.get().getExpertInOrganization().add(organizationName);
            } else{
                skill.getExpertInOrganization().add(organizationName);
                user.addSkill(skill);
            }
            repositoryUser.save(user);
            return true;
        }
        return false;
    }

}
