package com.masai.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "users")
@Data
public class User {

	
	
	private String email;
	
	
	private String password ; 
	
	
}
