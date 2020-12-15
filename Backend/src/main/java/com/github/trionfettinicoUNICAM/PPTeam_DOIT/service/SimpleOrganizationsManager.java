package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleOrganizationsManager implements OrganizationsManager{

    @Autowired
    private OrganizationRepository repository;

    @Override
    public Organization getOrganizationInstance(String organizationName) {
        return repository.findById(organizationName).orElse(null);
    }

    @Override
    public Organization createNewOrganization(Organization organization) {
        if(!organization.getMembersMails().contains(organization.getCreatorMail()))
            organization.addMember(organization.getCreatorMail());
        return repository.save(organization);
    }

    @Override
    public boolean deleteOrganization(String organizationName) {
        repository.deleteById(organizationName);
        return !exists(organizationName);
    }

    @Override
    public boolean updateOrganization(Organization organization) {
        repository.save(organization);
        return true;
    }

    @Override
    public boolean exists(String organizationName) {
        return repository.existsById(organizationName);
    }

    @Override
    public List<Organization> findByUser(String userMail) {
        return repository.findAll().stream().filter(
                organization -> organization.getMembersMails().contains(userMail)
        ).collect(Collectors.toList());
    }

    @Override
    public Page<Organization> getPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}
