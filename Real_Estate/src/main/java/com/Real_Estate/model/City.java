package com.Real_Estate.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City {

	private long city_id;
	private String city_name;
	
	
	  private State state;

	  
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getCity_id() {
		return city_id;
	}

	public void setCity_id(long city_id) {
		this.city_id = city_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "state_id")
	public State getState() {
		return state;
	}

	

	public void setState(State state) {
		this.state = state;
	}


	  
	
}
