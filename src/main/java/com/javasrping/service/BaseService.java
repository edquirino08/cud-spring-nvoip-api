package com.javasrping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.model.User;
import com.javasrping.repository.UserRepository;

@Service
public class BaseService {

	@Autowired
	UserRepository userRepository;

	private boolean checkUser(String numbersip) throws Exception {

		if (userRepository.findBynumbersip(numbersip) == null) {

			throw new Exception("Error! user who made the request not found");
		}

		return true;

	}

	public User getUserByNumberSip(String numbersip) throws Exception {

		if (checkUser(numbersip));

		return new User(userRepository.findBynumbersip(numbersip));
	}
	
	public boolean checkNumber(String number) throws Exception {

		if (number.length() == 11 || number.length() == 10 && (number.matches("[0-9]+")))
			return true;

		throw new Exception("Error! invalid number");

	}

	public boolean checkNumberSip(String numbersip) throws Exception {

		if (numbersip.length() == 8 && numbersip.matches("[0-9]+"))
			return true;

		throw new Exception("Error! invalid number");

	}

}
