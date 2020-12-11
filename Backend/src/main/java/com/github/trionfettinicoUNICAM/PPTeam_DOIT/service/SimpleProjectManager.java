package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Project;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.RealProject;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleProjectManager implements ProjectsManager{

    @Autowired
    private ProjectRepository repository;

    @Override
    public Project getProjectInstance(String projectName) {
        return repository.findById(projectName).orElse(null);
    }

    @Override
    public Project openNewEmptyProject(Organization organization, String title, String description, User creator) {
        Project project = new RealProject(organization.getName(), creator.getMail() , title, description);
        return repository.save(project);
    }

    @Override
    public boolean closeProject(Project project) {
        return false;
    }

    @Override
    public boolean deleteProject(Project project) {
        repository.delete(project);
        return true;
    }

    @Override
    public boolean updateProject(Project project) {
        return false;
    }

    @Override
    public Page<Project> getPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}
