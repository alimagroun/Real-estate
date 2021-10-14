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
@Table(name = "picture")
public class Picture {
 private Long picture_id;
 private String picture;
 
 @ManyToOne()
 @JoinColumn(name = "property_id")
 private Property property;
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
public Long getPicture_id() {
	return picture_id;
}

public void setPicture_id(Long picture_id) {
	this.picture_id = picture_id;
}

public String getPicture() {
	return picture;
}

public void setPicture(String picture) {
	this.picture = picture;
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
