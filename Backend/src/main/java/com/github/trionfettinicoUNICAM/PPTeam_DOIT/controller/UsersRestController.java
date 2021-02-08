package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UsersRestController {

    @Autowired
    private UsersManager manager;

    @PreAuthorize("permitAll")
    @GetMapping("/{userEmail}")
    public User getUser(@PathVariable String userEmail) throws EntityNotFoundException {
        return manager.getInstance(userEmail);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/getUserSkills/{userEmail}")
    public Set<Skill> getUserSkill(@PathVariable String userEmail) throws EntityNotFoundException {
        return manager.getInstance(userEmail).getSkills();
    }

    @PreAuthorize("permitAll")
    @GetMapping("/existSkill/{skill}/{userEmail}")
    public boolean existSkill(@PathVariable String skill, @PathVariable String userEmail) throws EntityNotFoundException {
        return manager.existSkill(new Skill(skill),userEmail);
    }

    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createNewUser(@RequestBody User user) throws IdConflictException {
        return manager.create(user);
    }

    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{userMail}")
    public boolean deleteUser(@PathVariable String userMail) throws EntityNotFoundException {
        return manager.delete(userMail);
    }

    @PreAuthorize("permitAll")
    @PutMapping(value = "/modify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User modifyUser(@RequestBody User user) throws EntityNotFoundException {
        return manager.update(user);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/exist/{userMail}")
    public boolean existsUser(@PathVariable String userMail){
        return manager.exists(userMail);
    }

}
