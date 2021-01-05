package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * It's a special type of {@link User} which has special experience with one or more {@link Skill}s.
 * A {@link User} can become an expert in one of 2 ways:
 * <ul>
 *     <li>By addition to an {@link Organization} as an expert, in this case the quality of expert is
 *     valid only inside the organization.</li>
 *     <li>By credit (for instance after a lot of closed projects a user can be considered an expert),
 *     in this case the quality of expert is valid throughout the application.</li>
 * </ul>
 */
@Document(collection = "expert")
public class Expert extends User {

    private Set<Skill> expertSKills;

    public Expert(String mail, String name, Integer age) {
        super(mail, name, age);
        expertSKills = new HashSet<>();
    }

    /**
     * Same as {@link User#addSkill(Skill)} except that this is for skills in which the user is expert.
     * @param skill the skill in which the user is expert
     */
    void addExpertSKill(Skill skill){
        if(expertSKills.contains(skill))
            expertSKills.add(Objects.requireNonNull(skill, "skill is null"));
        //TODO cosa far ritornare se skill non presente ?
    }

    void addExpertSkills(List<Skill> skills){
        Objects.requireNonNull(skills, "Skills is Null");
        for(Skill s: skills)
        {
            if(!expertSKills.contains(s))
                //TODO cosa far ritornare se skill non presente ? P.S non ho ben capito la parte dopo
            Objects.requireNonNull(s, "Skills contain a null skill");
        }
        expertSKills.addAll(skills);
    }

    /**
     * Same as {@link User#removeSkill(Skill)} except that this is for skills in which the user is expert.
     * @param skill the skill in which the user is no more expert
     */
    void removeExpertSkill(Skill skill) throws IllegalArgumentException{
        if(!expertSKills.contains(skill)) throw new IllegalArgumentException("skill not found");
        expertSKills.remove(Objects.requireNonNull(skill, "Skill is Null"));
    }

    /**
     * @return all the skills in which the user is expert.
     */
    Set<Skill> getExpertSkills(){
        return expertSKills;
    }
}
