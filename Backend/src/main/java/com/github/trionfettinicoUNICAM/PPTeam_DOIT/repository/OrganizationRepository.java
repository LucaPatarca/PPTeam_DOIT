package com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {

    List<Organization> findByCreatorMail(String creatorMail);

    default List<Organization> findByMember(String userMail){
        return findAll().stream().filter(organization ->
                organization.getMembersMails().contains(userMail)
        ).collect(Collectors.toList());
    }
}
