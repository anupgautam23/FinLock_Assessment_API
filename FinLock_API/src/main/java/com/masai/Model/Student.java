package com.masai.Model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "students")
@Data
public class Student {
	
	private Integer roll;
	private String Name;
	private String address;
	private Integer marks  ; 
	
	
}
