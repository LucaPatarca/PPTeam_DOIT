package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimpleProjectsManager implements ProjectsManager{

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Project getInstance(@NotNull @NotBlank String projectID) throws EntityNotFoundException {
        //if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        return projectRepository.findById(projectID).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'ID: "+projectID));
    }

    @Override
    public Project create(Project project) throws EntityNotFoundException {
        checkProject(project);
        return projectRepository.save(project);
    }

    private void checkProject(Project project) throws EntityNotFoundException{
        Organization organization = organizationRepository.findById(project.getOrganizationId()).orElseThrow(()->
                new EntityNotFoundException("L'organizzazione con ID '"+project.getOrganizationId()+"' " +
                        "passata nel progetto con ID '"+project.getId()+"' non esiste")
        );
        if (!organization.getMembersMails().contains(project.getCreatorMail()))
            throw new IllegalArgumentException("Il creatore del progetto non è tra i membri dell'organizzazione del progetto");
    }

    @Override
    public boolean closeProject(String projectID) throws EntityNotFoundException {
        if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        Project toClose = getInstance(projectID);
        if(toClose.isClosed() ) return false;
        toClose.close();
        toClose.getTeam().forEach(role ->{
            UserEntity user = userRepository.findById(role.getUserMail()).get();
            try {
                Skill skill = user.getSkills().stream().filter(it -> role.getSkill().equals(it)).findAny().get();
                skill.levelUp();
                userRepository.save(user);
            }catch (Exception e){}
        });
        projectRepository.save(toClose);
        
        return getInstance(projectID).isClosed();
    }

    @Override
    public boolean delete(String projectID){
        if(projectID.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        if (!exists(projectID)) throw new IllegalArgumentException("projectID non presente");
        projectRepository.deleteById(projectID);
        return !exists(projectID);
    }

    @Override
    public Project update(Project project) throws EntityNotFoundException {
        if (!exists(project.getId())) throw new IllegalArgumentException("projectID non presente");
        checkProject(project);
        return projectRepository.save(project);
    }

    @Override
    public Page<BasicProjectInformation> getPage(int page, int size) throws EntityNotFoundException {
        Page<Project> projectPage = projectRepository.findAll(PageRequest.of(page, size));
        List<BasicProjectInformation> basicProjectInformationList = new java.util.ArrayList<>(Collections.emptyList());
        for(Project project : projectPage){
            Organization organization = organizationRepository.findById(project.getOrganizationId())
                    .orElseThrow(()->new EntityNotFoundException("Nessuna organizzazione con l'ID: "+project.getOrganizationId()));
            UserEntity creator = userRepository.findById(project.getCreatorMail())
                    .orElseThrow(()->new EntityNotFoundException("Nessun utente con la mail: "+project.getCreatorMail()));
            basicProjectInformationList.add(new BasicProjectInformation(project,organization,creator));
        }
        return new PageImpl<>(basicProjectInformationList);
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
    public boolean submit(String projectId, Role role) throws EntityNotFoundException {
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        UserEntity user = userRepository.findById(role.getUserMail()).orElseThrow(()->
                new EntityNotFoundException("Nessun utente con la mail: "+role.getUserMail()));
        if(!user.hasSkill(role.getSkill())) return false;
        if(!project.submit(role)) return false;
        return projectRepository.save(project).getCandidates().contains(role);
    }

    private void checkRole(Role role){
        Objects.requireNonNull(role, "Il campo role è nullo");
        if(role.getUserMail().isBlank()) throw new IllegalStateException("Il campo userMail di role è vuoto");
        Objects.requireNonNull(role.getSkill(), "Il campo Skill di role è nullo");
        if(role.getSkill().getName().isBlank()) throw new IllegalStateException("Il campo name del campo skill  di role è vuoto");
        if(role.getSkill().getLevel() > 10 || role.getSkill().getLevel() <= 0) throw new IllegalStateException("Il campo name del campo skill  di role è vuoto");
    }

    @Override
    public boolean acceptCandidate(String projectId, Role userRole) throws EntityNotFoundException {
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.acceptCandidate(userRole);
        return projectRepository.save(project).getTeam().contains(userRole);
    }

    @Override
    public boolean rejectCandidate(String projectId, Role userRole) throws EntityNotFoundException {
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.rejectCandidate(userRole);
        return !projectRepository.save(project).getCandidates().contains(userRole);
    }

    @Override
    public List<Role> getUserSubmissions(String userMail) throws EntityNotFoundException {
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con l'email: "+userMail));
        List<Role> userSubmissions = new ArrayList<>();
        for(Project p: projectRepository.findAll())
            for(Role r: p.getCandidates())
                if(r.getUserMail().equals(userMail))
                    userSubmissions.add(r);
        return userSubmissions;
    }

    @Override
    public List<Project> getByUser(String userMail) throws EntityNotFoundException {
        return projectRepository.findByCreatorMail(userMail);
    }

    @Override
    public boolean removeTeamMember(String projectId , Role userRole) throws EntityNotFoundException {
        if(projectId.isBlank()) throw new IllegalArgumentException("Il campo 'projectId' è vuoto");
        checkRole(userRole);
        Project project = projectRepository.findById(projectId).orElseThrow(()->
                new EntityNotFoundException("Nessun progetto trovato con l'id: "+projectId));
        project.removeTeamMember(userRole);
        return !projectRepository.save(project).getCandidates().contains(userRole);
    }
}
