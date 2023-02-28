package com.masai.Repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.masai.Model.UserSession;

public interface UserSessionRepository extends MongoRepository<UserSession, String>{
	public UserSession findByUsername(String username);
	

}
