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
@Table(name = "property")
public class Property {

	private Long property_id;
	private Date property_date = new Date();
	private int numberOfBedrooms;
	private int numberOfBathrooms;
	private int numberOfPartialBathrooms;
	private int numberofGarages;
	private int squareMeters;
	private String description;
	private int yearBuilt;
	
	
	
	  private User user;
	

	  private City city;
	
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getProperty_id() {
		return property_id;
	}
	public void setProperty_id(Long property_id) {
		this.property_id = property_id;
	}
	public Date getProperty_date() {
		return property_date;
	}
	public void setProperty_date(Date property_date) {
		this.property_date = property_date;
	}
	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}
	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}
	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}
	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}
	public int getNumberOfPartialBathrooms() {
		return numberOfPartialBathrooms;
	}
	public void setNumberOfPartialBathrooms(int numberOfPartialBathrooms) {
		this.numberOfPartialBathrooms = numberOfPartialBathrooms;
	}
	public int getNumberofGarages() {
		return numberofGarages;
	}
	public void setNumberofGarages(int numberofGarages) {
		this.numberofGarages = numberofGarages;
	}
	public int getSquareMeters() {
		return squareMeters;
	}
	public void setSquareMeters(int squareMeters) {
		this.squareMeters = squareMeters;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	  public int getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "id_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "city_id")
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	

	
	
	
	
}
