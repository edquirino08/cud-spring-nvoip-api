package com.javasrping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasrping.dto.UserDTO;
import com.javasrping.model.User;
import com.javasrping.service.ErrorService;
import com.javasrping.service.LogService;
import com.javasrping.service.UserService;

@RestController
@RequestMapping(value = "/")
public class Controller {

	@Autowired
	private UserService userService;
	@Autowired
	private LogService logService;
	@Autowired
	private ErrorService errorService;

	@PostMapping
	public ResponseEntity<?> saveUser(@RequestBody UserDTO dto) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(dto.getNumbersip()));
		try {
			this.userService.saveUser(dto);
			this.logService.addLog("save_new_user", requestUser);
			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			errorService.addError("save_new_user", requestUser);
			return ResponseEntity.badRequest().body("Error to create user: " + dto.getName() + " ," + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> listAllUsers(@RequestBody String numbersip) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));

		try {

			List<UserDTO> users = this.userService.listAllUsers();
			this.logService.addLog("list_all_users", requestUser);
			return ResponseEntity.ok().body(users);
		} catch (Exception e) {
			errorService.addError("list_all_users", requestUser);
			return ResponseEntity.badRequest().body("Error to list all users: " + e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listUserById(@PathVariable Long id, @RequestBody String numbersip) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));

		try {

			UserDTO dto = this.userService.listUserById(id);
			this.logService.addLog("list_user_by_id", requestUser);
			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			errorService.addError("list_user_by_id", requestUser);
			return ResponseEntity.badRequest().body("Error to list user by id:  " + e.getMessage());

		}

	}

}
