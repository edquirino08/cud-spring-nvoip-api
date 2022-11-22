package com.javasrping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasrping.dto.UserDTO;
import com.javasrping.model.User;
import com.javasrping.repository.UserRepository;

@Service
public class UserService extends BaseService {

	@Autowired
	UserRepository userRepository;

	public UserDTO saveUser(UserDTO dto) {

		if (checkNumber(dto.getTelephone()) && checkNumberSip(dto.getNumbersip())) {

			User user = new User(dto.getName(), dto.getNumbersip(), dto.getUserToken(), dto.getTelephone());
			userRepository.save(user);
		}

		return dto;
	}

	private boolean checkNumber(String number) {

		if (number.length() == 11 || number.length() == 10 && (number.matches("[0-9]+")))
			return true;

		return false;

	}

	private boolean checkNumberSip(String numbersip) {

		if (numbersip.length() == 8 && numbersip.matches("[0-9]+"))
			return true;

		return false;

	}

	public List<UserDTO> listAllUsers() {

		List<UserDTO> usersDTO = new ArrayList<>();
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();

		for (User user : users) {

			UserDTO dto = new UserDTO(user.getName(), user.getNumbersip(), user.getUserToken(), user.getTelephone());
			usersDTO.add(dto);

		}

		return usersDTO;
	}

	public UserDTO listUserById(Long id) {

		User user = new User(userRepository.findById(id).get());

		return new UserDTO(user.getName(), user.getNumbersip(), user.getUserToken(), user.getTelephone());

	}

}
