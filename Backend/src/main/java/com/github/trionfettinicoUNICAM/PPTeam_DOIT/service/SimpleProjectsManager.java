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

import java.util.*;
import java.util.stream.Collectors;

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
//        Page<Project> projectPage = projectRepository.findAll(PageRequest.of(page, size));
//        List<String> basicProjectInformationList = new java.util.ArrayList<>(Collections.emptyList());
//        for(Project project : projectPage){
//            basicProjectInformationList.add(getBasicJsonInformation(project));
//        }
//        return new PageImpl<>(basicProjectInformationList);
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

    @Override
    public boolean addNeededSkill(String projectId, String skillName) throws EntityNotFoundException {
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        if(skillName.isBlank()) throw new IllegalArgumentException("Il campo 'skillName' è vuoto");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.addNeededSkill(new Skill(skillName));
        return projectRepository.save(project).getNeededSkills().contains(new Skill(skillName));
    }

    @Override
    public boolean removeNeededSkill(String projectId, String skillName) throws EntityNotFoundException {
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        if(skillName.isBlank()) throw new IllegalArgumentException("Il campo 'skillName' è vuoto");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.removeNeededSkill(new Skill(skillName));
        return !projectRepository.save(project).getNeededSkills().contains(new Skill(skillName));
    }

    @Override
    public boolean submit(String projectId, String userMail, Role role) throws EntityNotFoundException {
        //TODO 09/02/2021 controllare il contenuto di role
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        if(Objects.isNull(role)) throw new IllegalArgumentException("Il campo 'role' è nullo");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        User user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con l'email: "+userMail));
        project.submit(user, role.getSkill(), role.isAsExpert());
        return projectRepository.save(project).getCandidates().contains(role);
    }

    @Override
    public boolean acceptCandidate(String projectId, Role userRole) throws EntityNotFoundException {
        //TODO 09/02/2021 controllare il contenuto di role
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        if(Objects.isNull(userRole)) throw new IllegalArgumentException("Il campo 'userRole' è nullo");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.acceptCandidate(userRole);
        return projectRepository.save(project).getTeam().contains(userRole);
    }

    @Override
    public boolean rejectCandidate(String projectId, Role userRole) throws EntityNotFoundException {
        //TODO 09/02/2021 controllare il contenuto di role
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        if(Objects.isNull(userRole)) throw new IllegalArgumentException("Il campo 'userRole' è nullo");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.rejectCandidate(userRole);
        return projectRepository.save(project).getTeam().contains(userRole);
    }

    @Override
    public List<Role> getUserSubmissions(String userMail) throws EntityNotFoundException {
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con l'email: "+userMail));
        //TODO 09/02/2021 da rifare se possibile con gli stream
        List<Role> userSubmissions = new ArrayList<>();
        for(Project p: projectRepository.findAll())
            for(Role r: p.getCandidates())
                if(r.getUserMail().equals(userMail))
                    userSubmissions.add(r);
        return userSubmissions;
    }
}
