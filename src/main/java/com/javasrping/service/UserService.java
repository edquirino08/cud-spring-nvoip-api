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

	public User saveUser(UserDTO dto) throws Exception {

		User user = new User();

		if (checkNumber(dto.getTelephone()) && checkNumberSip(dto.getNumbersip())) {

			user = new User(dto.getName(), dto.getNumbersip(), dto.getUserToken(), dto.getTelephone());
			userRepository.save(user);
		}

		return user;
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

	public UserDTO updateUser(UserDTO dto , String numbersip) {

		User editedUser = userRepository.findBynumbersip(numbersip);

		if (dto.getName() != null || dto.getName() != "")
			editedUser.setName(dto.getName());

		if (dto.getUserToken() != null || dto.getUserToken() != "")
			editedUser.setUserToken(dto.getUserToken());

		if (dto.getTelephone() != null || dto.getTelephone() != "")
			editedUser.setTelephone(dto.getTelephone());

		return dto;

	}

	public UserDTO deleteUser(String numbersip) {

		User user = new User(userRepository.findBynumbersip(numbersip));

		UserDTO dto = new UserDTO(user.getName(), user.getNumbersip(), user.getUserToken(), user.getTelephone());

		userRepository.deleteBynumbersip(numbersip);

		return dto;
	}

}
