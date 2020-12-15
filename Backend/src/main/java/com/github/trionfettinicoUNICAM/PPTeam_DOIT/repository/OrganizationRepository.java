package com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization,String> {
//    default List<Organization> findByMember(String userMail){
//        return findAll().stream().filter(organization ->
//                organization.getMembersMails().contains(userMail)
//        ).collect(Collectors.toList());
//    }
}
