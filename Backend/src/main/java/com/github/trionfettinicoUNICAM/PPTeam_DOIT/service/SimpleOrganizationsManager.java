package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
// TODO: 22/01/2021 controllare annotazioni perche forse service non e un singleton
public class SimpleOrganizationsManager implements OrganizationsManager{

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Organization getOrganizationInstance(String organizationId) throws EntityNotFoundException {
        return organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessuna organizzazione trovata con l'ID: "+organizationId));
    }

    @Override
    public Organization createNewOrganization(Organization organization) {
        if(!organization.getMembersMails().contains(organization.getCreatorMail()))
            organization.addMember(organization.getCreatorMail());
        return organizationRepository.save(organization);
    }

    @Override
    public boolean deleteOrganization(String organizationId) {
        organizationRepository.deleteById(organizationId);
        for (Project project: projectRepository.findByOrganizationId(organizationId)) {
            projectRepository.deleteById(project.getId());
        }
        return !exists(organizationId);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public List<User> getUsers(String organizationId) throws EntityNotFoundException {
        Organization org = organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessuna organizzazione trovata con l'ID: "+organizationId));
        List<User> users = new ArrayList<>();
        for (String email : org.getMembersMails()) {
            users.add(userRepository.findById(email).orElseThrow(()->
                    new EntityNotFoundException("Nessun utente trovato con la mail: "+email)));
        }
        return users;
    }

    @Override
    public boolean existsName(String organizationName) {
        return organizationRepository.findAll().stream()
                .anyMatch(it->it.getName().equals(organizationName));
    }

    @Override
    public boolean exists(String organizationId) {
        return organizationRepository.existsById(organizationId);
    }

    @Override
    public List<Organization> findByUser(String userMail) {
        return organizationRepository.findByMember(userMail);
    }

    @Override
    public List<Organization> findByCreator(String userMail) {
        return organizationRepository.findByCreatorMail(userMail);
    }


    @Override
    public Page<BasicOrganizationInformation> getPage(int page, int size) {
        Page<Organization> projectPage = organizationRepository.findAll(PageRequest.of(page, size));
        List<BasicOrganizationInformation> basicOrganizationInformation = new java.util.ArrayList<>(Collections.emptyList());
        for(Organization organization : projectPage){
            User creator = userRepository.findById(organization.getCreatorMail()).orElseThrow(IllegalArgumentException::new);
            basicOrganizationInformation.add(new BasicOrganizationInformation(organization,creator));
        }
        return new PageImpl<>(basicOrganizationInformation);
    }

    @Override
    public void addCollaborator(String organizationId, String userMail, Skill skill) throws EntityNotFoundException {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessun organizzazione trovata con l'ID: "+organizationId));
        organization.addMember(userMail);
        organizationRepository.save(organization);
        User user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        user.setExpert(skill,organizationId);
        userRepository.save(user);
    }

}
