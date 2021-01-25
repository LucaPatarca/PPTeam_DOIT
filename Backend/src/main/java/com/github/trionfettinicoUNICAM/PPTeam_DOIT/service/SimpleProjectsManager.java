package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.BasicProjectInformation;
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
    public Project getProjectInstance(String projectID) throws EntityNotFoundException {
        return projectRepository.findById(projectID).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'ID: "+projectID));
    }

    @Override
    public Project createNewProject(Project project) throws EntityNotFoundException {
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
        Project toClose = getProjectInstance(projectID);
        toClose.close();
        projectRepository.save(toClose);
        return getProjectInstance(projectID).isClosed();
    }

    @Override
    public boolean deleteProject(String projectID) {
        projectRepository.deleteById(projectID);
        return !exists(projectID);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Page<BasicProjectInformation> getPage(int page, int size) {
        Page<Project> projectPage = projectRepository.findAll(PageRequest.of(page, size));
        List<BasicProjectInformation> basicProjectInformationList = new java.util.ArrayList<>(Collections.emptyList());
        for(Project project : projectPage){
            User creator = userRepository.findById(project.getCreatorMail()).orElseThrow(IllegalArgumentException::new);
            Organization organization = organizationRepository.findById(project.getOrganizationId()).orElseThrow(IllegalArgumentException::new);
            basicProjectInformationList.add(new BasicProjectInformation(project,organization,creator));
        }
        return new PageImpl<>(basicProjectInformationList);
    }

    @Override
    public boolean exists(String projectID) {
        return projectRepository.existsById(projectID);
    }

    @Override
    public boolean existsSignature(String projectSignature) {
        return projectRepository.findAll().stream().anyMatch(it->
                (it.getOrganizationId()+"."+it.getTitle()).equals(projectSignature));
    }

    @Override
    public List<Project> findByOrganizationId(String organizationId) {
        return projectRepository.findByOrganizationId(organizationId);
    }
}
