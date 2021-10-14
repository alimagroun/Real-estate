package com.Real_Estate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Real_Estate.model.City;
import com.Real_Estate.repository.CityRepository;

@Service
@Transactional
public class CityService {
	@Autowired
	private CityRepository repo;

	
	public City get(long id) {
		return repo.findById(id).get();
	}
	public List<City> listAll() {
		return repo.findAll();
	}	












}
