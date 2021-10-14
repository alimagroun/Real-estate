package com.Real_Estate.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "state")
public class State   {
    private long state_id;
    private String state_name;
    
    protected State() {}
    
    
    
    protected State(long state_id, String state_name) {
		super();
		this.state_id = state_id;
		this.state_name = state_name;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getState_id() {
		return state_id;
	}
	public void setState_id(long state_id) {
		this.state_id = state_id;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
    
    
 
    
 
	
    
}
