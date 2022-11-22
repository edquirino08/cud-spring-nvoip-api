package com.javasrping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.model.Error;
import com.javasrping.model.User;
import com.javasrping.repository.ErrorRepository;
import com.javasrping.repository.QueryRepository;

@Service
public class ErrorService extends BaseService {

	@Autowired
	private ErrorRepository errorRepository;

	@Autowired
	private QueryRepository queryRepository;

	public void addError(String message, User user) {

		Error error = new Error();

		if (message.equals("save_new_user"))
			error.setErrorMessage("Foi gerado um erro ao tentar criar uma conta com o nome: " + user.getName()
					+ " ,e numbersip: " + user.getNumbersip());

		else
			error.setErrorMessage("O usuário da conta "
					+ queryRepository.findBynumbersip(user.getNumbersip() + ", teve o erro :" + message));

		errorRepository.save(error);

	}

}
