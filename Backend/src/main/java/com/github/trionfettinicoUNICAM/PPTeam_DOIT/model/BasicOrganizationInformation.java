package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

public class BasicOrganizationInformation {
    private final String id;
    private final String name;
    private final String description;
    private final String creatorMail;
    private final String creatorName;

    public BasicOrganizationInformation(Organization organization, UserEntity creator){
        if(!organization.getMembersMails().contains(creator.getMail())) throw new IllegalArgumentException("Il creatore non fa parte dell'organizzazione");
        this.id = organization.getId();
        this.name = organization.getName();
        this.description = organization.getDescription();
        this.creatorMail = creator.getMail();
        this.creatorName = creator.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatorMail() {
        return creatorMail;
    }

    public String getCreatorName() {
        return creatorName;
    }
}
