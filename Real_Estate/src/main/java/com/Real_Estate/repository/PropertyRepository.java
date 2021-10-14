package com.Real_Estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Real_Estate.model.Property;

public interface PropertyRepository  extends JpaRepository <Property, Long> {

}
