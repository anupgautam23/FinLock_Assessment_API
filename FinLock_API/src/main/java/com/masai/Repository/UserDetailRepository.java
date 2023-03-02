package com.masai.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masai.Model.UserDetails;

public interface UserDetailRepository extends MongoRepository<UserDetails, String>{
  
 public	List<UserDetails> findByUsername(String username);

}
