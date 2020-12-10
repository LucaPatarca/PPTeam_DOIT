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
    @Id private String title;
    private String description;
    private final Organization organization;
    private final User creator;

    public RealProject(Organization organization, User creator) {
        if(!organization.getMembers().contains(creator))
            throw new IllegalArgumentException("Creator user must be a member of the organization");

        this.candidates = new HashSet<>();
        this.team = new RealTeam(this);
        this.isClosed = false;
        this.neededSkills = new HashMap<>();
        this.organization=organization;
        this.creator=creator;
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
        RealRole role = new RealRole(skill, user, this);
        if(!candidates.contains(role)) {
            candidates.add(role);
            return true;
        } else return false;
    }

    @Override
    public void removeSubmission(User user) {
        candidates.removeIf(role -> role.getUser().equals(user));
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public User getCreator() {
        return creator;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public void setDescription(String description) {
        this.description=description;
    }
}
