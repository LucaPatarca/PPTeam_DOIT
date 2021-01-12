package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a registered user inside the application. It has the ability to join a project's team
 * and to do so, it needs a list of {@link Skill}s.
 */
@Document(collection = "user")
public class User {

    // TODO: 10/12/20 scrivere il javadoc di questi metodi (lasciati indietro perche comunque si spiegano gia bene da soli)

    // TODO: 09/01/21 rimuovere roles e submissions (e metodi associati)

    @Id
    private String mail;
    private String name;
    private Set<Skill> skills;

    public User(String mail, String name) throws IllegalArgumentException {
        setMail(mail);
        setName(name);
        skills = new HashSet<>();
    }

    /**
     * Adds a skill to the user's list.
     * @param skill the skill to add
     */
    public void addSkill(Skill skill) {
        //TODO aggiunger controllo se skill è gia presente
        skills.add(Objects.requireNonNull(skill, "skill is null"));
    }

    public void addSkills(Set<Skill> skills) {
        Objects.requireNonNull(skills, "Skills is Null");
        for(Skill s: skills) Objects.requireNonNull(s, "Skills contain a null skill");
        //TODO aggiungere controllo se una delle skill è già presente
        this.skills.addAll(skills);
    }

    /**
     * Removes a skill from the user's list.
     * @param skill the skill to remove
     */
    public void removeSkill(Skill skill) throws IllegalArgumentException{
        if(!skills.contains(skill)) throw new IllegalArgumentException("skill not found");
        skills.remove(Objects.requireNonNull(skill, "Skill is Null"));
    }

    /**
     * @return the set of skills this user has.
     */
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        Objects.requireNonNull(skills, "Skills is Null");
        for(Skill s: skills) Objects.requireNonNull(s, "Skills contain a null skill");
        this.skills = skills;
    }

    /**
     * The {@link User} mail is unique for each user so a user can be identified by it's mail.
     * @return the user's mail
     */
    @Id
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws IllegalArgumentException {
        if(mail.length() == 0) throw new IllegalArgumentException("Mail is empty");
        this.mail = mail;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if(name.length() == 0) throw new IllegalArgumentException("Name is empty");
        this.name = name;
    }

    public void setGloballyExpert(Skill skill){
        Optional<Skill> optionalSkill = this.getSkills().stream().filter(it->it.equals(skill)).findAny();
        if(optionalSkill.isPresent()){
            optionalSkill.get().setGloballyExpert(true);
        } else{
            skill.setGloballyExpert(true);
            this.addSkill(skill);
        }
    }

    public void setExpert(Skill skill, String organizationName){
        Optional<Skill> optionalSkill = this.getSkills().stream().filter(it->it.equals(skill)).findAny();
        if(optionalSkill.isPresent()){
            optionalSkill.get().getExpertInOrganization().add(organizationName);
        } else{
            skill.getExpertInOrganization().add(organizationName);
            this.addSkill(skill);
        }
    }
}
