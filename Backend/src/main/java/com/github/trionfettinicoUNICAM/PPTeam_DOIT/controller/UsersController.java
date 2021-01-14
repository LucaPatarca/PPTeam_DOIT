package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    private UsersManager usersManager;

    @PreAuthorize("permitAll")
    @GetMapping("/{userEmail}")
    public User getUser(@PathVariable String userEmail){
        return usersManager.getUserInstance(userEmail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/existSkill/{skill}/{userEmail}")
    public boolean existSkill( @PathVariable String skill,@PathVariable String userEmail){
        return usersManager.existSkill(skill,userEmail);
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/addSkillCollaborator/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addCollaborator(@PathVariable String userEmail, @RequestBody Skill skill){
        if(usersManager.existSkill(skill.getName(),userEmail))
            return false;
        usersManager.addCollaborator(userEmail,skill);
        return true;
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createNewUser(@RequestBody User user){
        return usersManager.createUser(user.getMail(),user.getName());
    }

    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{userMail}")
    public boolean deleteUser(@PathVariable String userMail){
        return usersManager.deleteUser(userMail);
    }


    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean modifyUser(@RequestBody User user){
        return usersManager.updateUser(user);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/exist/")
    public boolean existsUser(@RequestBody String userEmail){
        return usersManager.exists(userEmail);
    }

}
