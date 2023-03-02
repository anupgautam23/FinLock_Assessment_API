package com.masai.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Document(collection = "userSession")
@Data
public class UserSession {
	
	@Id
	private String username;
	private String password ;  
	
}
