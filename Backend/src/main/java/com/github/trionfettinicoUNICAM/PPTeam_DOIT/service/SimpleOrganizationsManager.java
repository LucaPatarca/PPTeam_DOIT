package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.*;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.OrganizationRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Validated
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
    public Organization getInstance(@Valid @NotNull @NotBlank String organizationId) throws EntityNotFoundException {
        //if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        return organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessuna organizzazione trovata con l'ID: "+organizationId));
    }

    @Override
    public Organization create(Organization organization) throws EntityNotFoundException, IdConflictException {
        // TODO: 11/02/2021 estrarre questi controlli e metterli anche nel metodo update
        if(!userRepository.existsById(organization.getCreatorMail()))
            throw new EntityNotFoundException("Nessun utente trovato con la mail: "+organization.getCreatorMail());
        for (String memberMail : organization.getMembersMails()) {
            if (!userRepository.existsById(memberMail))
                throw new EntityNotFoundException("Nessun utente con la mail: "+memberMail);
        }
        if(existsName(organization.getName()))
            throw new IdConflictException("Esiste gia un organizzazione con questo nome");
        if(!organization.getMembersMails().contains(organization.getCreatorMail()))
            organization.addMember(organization.getCreatorMail());
        // TODO: 14/02/2021 indagare perche non ritorna l'id
        return organizationRepository.save(organization);
    }

    @Override
    public boolean delete(String organizationId) {
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        if(!exists(organizationId)) return false;
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        organization.ifPresent(value -> value.getMembersMails().forEach(email -> {
            try {
                removeMember(organizationId, email, false);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        }));
        projectRepository.findByOrganizationId(organizationId).forEach(projectRepository::delete);
        organizationRepository.deleteById(organizationId);
        return !exists(organizationId);
    }

    @Override
    public Organization update(Organization organization) throws EntityNotFoundException {
        if(!exists(organization.getId()))
            throw new EntityNotFoundException("Nessuna organizzazione con id: "+ organization.getId());
        return organizationRepository.save(organization);
    }

    @Override
    public List<UserEntity> getUsers(String organizationId) throws EntityNotFoundException {
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        Organization org = organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessuna organizzazione trovata con l'ID: "+organizationId));
        List<UserEntity> users = new ArrayList<>();
        for (String email : org.getMembersMails()) {
            users.add(userRepository.findById(email).orElseThrow(()->
                    new EntityNotFoundException("Nessun utente trovato con la mail: "+email)));
        }
        return users;
    }

    @Override
    public boolean existsName(String organizationName) {
        if(organizationName.isBlank()) throw new IllegalArgumentException("Il campo 'name' è vuoto");
        return organizationRepository.findAll().stream()
                .anyMatch(it->it.getName().equals(organizationName));
    }

    @Override
    public boolean exists(String organizationId) {
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        return organizationRepository.existsById(organizationId);
    }

    @Override
    public List<Organization> findByUser(String userMail) {
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        return organizationRepository.findByMember(userMail);
    }

    @Override
    public List<Organization> findByCreator(String userMail) {
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        return organizationRepository.findByCreatorMail(userMail);
    }

    public Page<BasicOrganizationInformation> getPage(int page, int size) throws EntityNotFoundException {
        Page<Organization> projectPage = organizationRepository.findAll(PageRequest.of(page, size));
        List<BasicOrganizationInformation> basicOrganizationInformation = new java.util.ArrayList<>(Collections.emptyList());
        for(Organization organization : projectPage){
            UserEntity creator = userRepository.findById(organization.getCreatorMail())
                    .orElseThrow(()->
                            new EntityNotFoundException("Nessun utente trovato con la mail: "+organization.getCreatorMail()));
            basicOrganizationInformation.add(new BasicOrganizationInformation(organization,creator));
        }
        return new PageImpl<>(basicOrganizationInformation);
    }

    @Override
    public void addCollaborator(String organizationId, String userMail, Skill skill) throws EntityNotFoundException {
        if(Objects.isNull(organizationId) || organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        // TODO: 11/02/2021 controllare se la skill è nulla
        // TODO: 11/02/2021 controllare se lo user è il creatore dell'organizzazione e in caso ritorna errore
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessun organizzazione trovata con l'ID: "+organizationId));
        UserEntity user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        if(organization.getMembersMails().contains(userMail)) {
            user.setExpert(skill, organizationId);
            userRepository.save(user);
        }
    }

    @Override
    public void addExpert(String organizationId, String userMail, Skill skill) throws EntityNotFoundException {
        if(Objects.isNull(organizationId) || organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        if(userMail.isBlank()) throw new IllegalArgumentException("Il campo 'userMail' è vuoto");
        // TODO: 11/02/2021 controllare se la skill è nulla
        // TODO: 11/02/2021 controllare se lo user è il creatore dell'organizzazione e in caso ritorna errore
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(()->
                new EntityNotFoundException("Nessun organizzazione trovata con l'ID: "+organizationId));
        UserEntity user = userRepository.findById(userMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+userMail));
        organization.addMember(userMail);
        organizationRepository.save(organization);
        user.setExpert(skill,organizationId);
        userRepository.save(user);
    }

    @Override
    public boolean removeMember(String organizationId, String memberMail, Boolean removeProjects) throws EntityNotFoundException {
        // TODO: 24/02/2021 implementare removeProjects
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        if(memberMail.isBlank()) throw new IllegalArgumentException("Il campo 'memberMail' è vuoto");
        Organization organization = getInstance(organizationId);
        UserEntity user = userRepository.findById(memberMail).orElseThrow(()->
                new EntityNotFoundException("Nessun utente trovato con la mail: "+memberMail));
        //se l'utente non è membro dell'organizzazione ritorna false
        if(!organization.getMembersMails().contains(memberMail)) return false;
        organization.removeMember(memberMail);
        //rimuove le eventuali skill da collaboratore
        user.getSkills().forEach(skill -> skill.getExpertInOrganization().remove(organizationId));
        UserEntity savedUser = userRepository.save(user);
        Organization savedOrg = organizationRepository.save(organization);
        return organization.equals(savedOrg) && user.equals(savedUser);
    }

    @Override
    public boolean addMember(String organizationId, String memberMail) throws EntityNotFoundException {
        if(organizationId.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        if(memberMail.isBlank()) throw new IllegalArgumentException("Il campo 'memberMail' è vuoto");
        Organization organization = getInstance(organizationId);
        if(!userRepository.existsById(memberMail))
            throw new EntityNotFoundException("Nessun utente trovato con la mail: "+memberMail);
        if(organization.getMembersMails().contains(memberMail)) return false;
        organization.addMember(memberMail);
        Organization savedOrg = organizationRepository.save(organization);
        return organization.equals(savedOrg);
    }
}
