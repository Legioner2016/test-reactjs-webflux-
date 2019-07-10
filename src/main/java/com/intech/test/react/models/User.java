package com.intech.test.react.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 
 * 
 * @author a.palkin
 *
 */
@Entity
@Data
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String login;
	private String name;
	private String surname;
	private UserRole role;
	
	
}
