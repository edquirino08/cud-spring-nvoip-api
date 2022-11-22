package com.javasrping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.model.Log;
import com.javasrping.model.User;
import com.javasrping.repository.LogRepository;

@Service
public class LogService extends BaseService {

	@Autowired
	LogRepository logRepository;


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
}
