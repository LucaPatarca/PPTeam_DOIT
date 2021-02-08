package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SimpleProjectsManager implements ProjectsManager{
    //TODO migliorare i controlli e le condizioni sui metodi

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Project getInstance(String projectID) throws EntityNotFoundException {
        if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        return projectRepository.findById(projectID).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'ID: "+projectID));
    }

    @Override
    public Project create(Project project) throws EntityNotFoundException {
        Organization organization = organizationRepository.findById(project.getOrganizationId()).orElseThrow(()->
                new EntityNotFoundException("L'organizzazione con ID '"+project.getOrganizationId()+"' " +
                        "passata nel progetto con ID '"+project.getId()+"' non esiste")
        );
        if (!organization.getMembersMails().contains(project.getCreatorMail()))
            throw new IllegalArgumentException("Il creatore del progetto non è tra i membri dell'organizzazione del progetto");
        return projectRepository.save(project);
    }

    @Override
    public boolean closeProject(String projectID) throws EntityNotFoundException {
        if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        Project toClose = getInstance(projectID);
        toClose.close();
        projectRepository.save(toClose);
        return getInstance(projectID).isClosed();
    }

    @Override
    public boolean delete(String projectID) {
        if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        // TODO: 04/02/2021 se il progetto non esiste lancio illegal argument
        projectRepository.deleteById(projectID);
        return !exists(projectID);
    }

    @Override
    public Project update(Project project) {
        // TODO: 04/02/2021 se il progetto non esiste lancio illegal argument
        return projectRepository.save(project);
    }

    @Override
    public Page<String> getPage(int page, int size) {
        Page<Project> projectPage = projectRepository.findAll(PageRequest.of(page, size));
        List<String> basicProjectInformationList = new java.util.ArrayList<>(Collections.emptyList());
        for(Project project : projectPage){
            basicProjectInformationList.add(getBasicJsonInformation(project));
        }
        return new PageImpl<>(basicProjectInformationList);
    }

    @Override
    public String getBasicJsonInformation(Project object) {
        return null;
    }

    @Override
    public boolean exists(String projectID) {
        if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        return projectRepository.existsById(projectID);
    }

    @Override
    public boolean existsSignature(String projectSignature) {
        if(projectSignature.isBlank()) throw new IllegalArgumentException("Il campo 'projectSignature' è vuoto");
        return projectRepository.findAll().stream().anyMatch(it->
                (it.getOrganizationId()+"."+it.getTitle()).equals(projectSignature));
    }

    @Override
    public List<Project> findByOrganizationId(String organizationId) {
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'organizationID' è vuoto");
        return projectRepository.findByOrganizationId(organizationId);
    }
}
