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
@Table(name = "propertyForSale")
public class PropertyForSale {

	private Long propertyForSale_id;
	private int price;
	private String propertyType;
	private Property property;
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getPropertyForSale_id() {
		return propertyForSale_id;
	}
	public void setPropertyForSale_id(Long propertyForSale_id) {
		this.propertyForSale_id = propertyForSale_id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
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
