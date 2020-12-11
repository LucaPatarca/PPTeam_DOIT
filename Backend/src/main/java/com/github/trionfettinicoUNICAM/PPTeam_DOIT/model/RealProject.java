package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RealProject implements Project{

    private final Set<Role> candidates;
    private final Team team;
    private boolean isClosed;
    private final Map<Skill, Integer> neededSkills;
    @Id private String name;
    private String description;
    private final String organizationName;
    private final String creatorMail;

    public RealProject(Organization organization, User creator) {
        if(!organization.getMembersMails().contains(creator.getMail()))
            throw new IllegalArgumentException("Creator user must be a member of the organization");

        this.candidates = new HashSet<>();
        this.team = new RealTeam(this.getName());
        this.isClosed = false;
        this.neededSkills = new HashMap<>();
        this.organizationName=organization.getName();
        this.creatorMail=creator.getMail();
    }

    @Override
    public Set<Role> getCandidates() {
        return candidates;
    }

    @Override
    public void acceptCandidate(Role role) {
        if(!team.addRole(role)) throw new RuntimeException("unable to add role to team");
    }

    @Override
    public void rejectCandidate(Role role) {
        candidates.remove(role);
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() {
        isClosed=true;
    }

    @Override
    public Map<Skill, Integer> getNeededSkills() {
        return neededSkills;
    }

    @Override
    public boolean submit(User user, Skill skill) {
        RealRole role = new RealRole(skill, user.getMail(), this.getName());
        if(!candidates.contains(role)) {
            candidates.add(role);
            return true;
        } else return false;
    }

    @Override
    public void removeSubmission(User user) {
        candidates.removeIf(role -> role.getUserMail().equals(user.getMail()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public String getCreatorMail() {
        return creatorMail;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description=description;
    }
}
