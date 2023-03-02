package com.masai.Model;

import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Document(collection = "users")
@Data
public class User {
	
	private String email;	
	private String password ; 
	
	
}
