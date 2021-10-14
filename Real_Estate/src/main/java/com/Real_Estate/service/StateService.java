package com.Real_Estate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Real_Estate.model.State;
import com.Real_Estate.repository.StateRepository;


@Service
@Transactional
public class StateService {
	
	
	@Autowired
	private StateRepository repo;
	
	
	public State get(long id) {
		return repo.findById(id).get();
	}
	public List<State> listAll() {
		return repo.findAll();
	}	

}
