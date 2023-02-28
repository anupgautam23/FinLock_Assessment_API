package com.masai.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masai.Model.UserDetails;

public interface UserDetailRepository extends MongoRepository<UserDetails, String>{

}
