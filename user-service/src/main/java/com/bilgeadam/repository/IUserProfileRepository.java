package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.UserProfile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserProfileRepository extends MongoRepository<UserProfile,String> {


    Optional<UserProfile> findOptionalByAuthId(Long authId);


/*    @Query("{$or: [{status:?0},{name:?1}]}")
    List<UserProfile> findAllActiveProfile(String value,String name);*/
    @Query("{status:'ACTIVE'}")
    List<UserProfile> findAllActiveProfile();

}
