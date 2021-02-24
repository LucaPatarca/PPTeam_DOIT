package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

public class BasicProjectInformation {
    private final String id;
    private final String title;
    private final String description;
    private final String organizationId;
    private final String organizationName;
    private final String creatorMail;
    private final String creatorName;
    private final boolean closed;

    public BasicProjectInformation(Project project, Organization organization, UserEntity creator){
        if(!project.getOrganizationId().equals(organization.getId())) throw new IllegalArgumentException("l'organizzazione non corrisponde con il progetto");
        if(!project.getCreatorMail().equals(creator.getMail())) throw new IllegalArgumentException("l'utente non corrisponde con il creatore del progetto");
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.organizationId = project.getOrganizationId();
        this.organizationName = organization.getName();
        this.creatorMail = project.getCreatorMail();
        this.creatorName = creator.getName();
        this.closed = project.isClosed();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public boolean isClosed() {
        return closed;
    }
}
