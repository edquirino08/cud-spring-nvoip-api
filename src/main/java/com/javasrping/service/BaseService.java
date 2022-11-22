package com.javasrping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.model.User;
import com.javasrping.repository.QueryRepository;

@Service
public class BaseService {

	@Autowired
	QueryRepository queryRepository;

	private boolean checkUser(String numbersip) throws Exception {

		if (queryRepository.findBynumbersip(numbersip) == null) {

			throw new Exception("Error! user who made the request not found");
		}

		return true;

	}

	public User getUserByNumberSip(String numbersip) throws Exception {

		if (checkUser(numbersip));

		return new User(queryRepository.findBynumbersip(numbersip));
	}

}
