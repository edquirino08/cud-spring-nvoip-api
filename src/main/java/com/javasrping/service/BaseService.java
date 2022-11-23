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
	
	public boolean checkNumber(String number) {

		if (number.length() == 11 || number.length() == 10 && (number.matches("[0-9]+")))
			return true;

		return false;

	}

	public boolean checkNumberSip(String numbersip) {

		if (numbersip.length() == 8 && numbersip.matches("[0-9]+"))
			return true;

		return false;

	}

}
