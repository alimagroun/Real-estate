package com.magroun.realestate.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "states")
public class State {

	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String name;
	  
	  
	  
	  
	public State() {
		super();
	}
	public State(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	  
}
