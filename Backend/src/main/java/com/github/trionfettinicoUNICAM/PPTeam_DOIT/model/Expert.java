package com.github.trionfettinicoUNICAM.PPTeam_DOIT.model;

import java.util.List;
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
public interface Expert extends User{
    // TODO: 15/12/20 far diventare una classe come fatto con project
    /**
     * Same as {@link User#addSkill(Skill)} except that this is for skills in which the user is expert.
     * @param skill the skill in which the user is expert
     */
    void addExpertSKill(Skill skill);

    void addExpertSkills(List<Skill> skills);

    /**
     * Same as {@link User#removeSkill(Skill)} except that this is for skills in which the user is expert.
     * @param skill the skill in which the user is no more expert
     */
    void removeExpertSkill(Skill skill);

    /**
     * @return all the skills in which the user is expert.
     */
    Set<Skill> getExpertSkills();
}
