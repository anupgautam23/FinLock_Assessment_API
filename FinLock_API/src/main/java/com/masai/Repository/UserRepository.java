package com.masai.Repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.masai.Model.User;

public interface UserRepository extends MongoRepository<User, Integer>{
	
	public Optional<User> findByEmail(String email);

}
