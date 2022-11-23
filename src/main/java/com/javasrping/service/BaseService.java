package com.javasrping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.model.Error;
import com.javasrping.model.Log;
import com.javasrping.model.User;
import com.javasrping.repository.ErrorRepository;
import com.javasrping.repository.LogRepository;
import com.javasrping.repository.UserRepository;

@Service
public class BaseService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LogRepository logRepository;
	
	@Autowired
	private ErrorRepository errorRepository;

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
	
	public void addLog(String message, User user) throws Exception {

		Log log = new Log();

		if (!message.equals("delete_user"))
			log.setChanges("O usuário da conta " + user.getId() + " ,"
					+ user.getNumbersip()+ " realizou a alteração: "
					+ message);

		else
			log.setChanges("O usuário da conta " + user.getNumbersip() + " realizou a alteração " + message);

		logRepository.save(log);

	}
	
	public void addError(String message, User user) {

		Error error = new Error();

		if (message.equals("save_new_user"))
			error.setErrorMessage("Foi gerado um erro ao tentar criar uma conta com o nome: " + user.getName()
					+ " ,e numbersip: " + user.getNumbersip());

		else
			error.setErrorMessage("O usuário da conta "
					+ userRepository.findBynumbersip(user.getNumbersip() + ", teve o erro :" + message));

		errorRepository.save(error);

	}

}
