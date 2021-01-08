package com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
