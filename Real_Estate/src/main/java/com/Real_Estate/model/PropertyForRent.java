package com.Real_Estate.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "propertyForRent")
public class PropertyForRent {
	
	private Long propertyForRent_id;
	private int price;
	private Date date_available;
	private Property property;
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getPropertyForRent_id() {
		return propertyForRent_id;
	}
	public void setPropertyForRent_id(Long propertyForRent_id) {
		this.propertyForRent_id = propertyForRent_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getDate_available() {
		return date_available;
	}
	public void setDate_available(Date date_available) {
		this.date_available = date_available;
	}
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "property_id")
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	
	

}
