package com.masai.Model;

import java.time.LocalTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "userDetails")
@Data
public class UserDetails {
	
	private String username; 
	private String lattitude;
	private String longitude;
	private LocalTime loginTime;
	private String osName;
	private String browserName;
	private LocalTime sessionTime;
	

}
