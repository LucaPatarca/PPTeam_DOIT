package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.EntityNotFoundException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.exception.IdConflictException;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Skill;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.service.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/users")
public class UsersRestController implements UsersController {

    @Autowired
    private UsersManager manager;

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/{userID}")
    public User getInstance(@PathVariable String userID) throws EntityNotFoundException { return manager.getInstance(userID); }

    @Override
    @PreAuthorize("permitAll")
    @PostMapping(value = "/createNew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody User user) throws EntityNotFoundException, IdConflictException { return manager.create(user); }

    @Override
    @PreAuthorize("permitAll")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User update(@RequestBody User user) throws EntityNotFoundException { return manager.update(user); }

    @Override
    @PreAuthorize("permitAll")
    @DeleteMapping(value = "/{userID}")
    public boolean delete(@PathVariable String userID) throws EntityNotFoundException { return manager.delete(userID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/exist/{userID}")
    public boolean exists(@PathVariable String userID) { return manager.exists(userID); }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/list/{page}")
    public Page<String> getPage(@PathVariable int page) {
        // TODO: 08/02/2021 implementare
        return null;
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/getUserSkills/{userEmail}")
    public Set<Skill> getUserSkill(@PathVariable String userEmail) throws EntityNotFoundException {
        return manager.getInstance(userEmail).getSkills();
    }

    @Override
    @PreAuthorize("permitAll")
    @GetMapping("/existSkill/{skill}/{userEmail}")
    public boolean existSkill(@PathVariable String skill, @PathVariable String userEmail) throws EntityNotFoundException {
        return manager.existSkill(new Skill(skill),userEmail);
    }

}
