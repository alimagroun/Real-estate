package com.Real_Estate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Real_Estate.model.Property;
import com.Real_Estate.repository.PropertyRepository;



@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class PropertyController {
	
    @Autowired
    private PropertyRepository propertyRepository;

    
    @GetMapping("/properties")
    public List<Property> getAllEproperties() {
        return propertyRepository.findAll();
    }
    
    
}
