package com.javasrping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javasrping.dto.SmsDTO;
import com.javasrping.dto.TorpedoDTO;
import com.javasrping.dto.UserDTO;
import com.javasrping.model.User;
import com.javasrping.service.NvoipApiService;
import com.javasrping.service.UserService;

@RestController
@RequestMapping(value = "/")
public class Controller {

	@Autowired
	private UserService userService;

	@Autowired
	private NvoipApiService nvoipApiService;

	@PostMapping
	public ResponseEntity<?> saveUser(@RequestBody UserDTO dto) throws Exception {

		User requestUser = new User();
		try {
			requestUser = this.userService.saveUser(dto);
			this.userService.addLog("save_new_user", requestUser);
			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			userService.addError("save_new_user", requestUser);
			return ResponseEntity.badRequest().body("Error to create user: " + dto.getName() + " ," + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> listAllUsers(@RequestParam String numbersip) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));

		try {

			List<UserDTO> users = this.userService.listAllUsers();
			this.userService.addLog("list_all_users", requestUser);
			return ResponseEntity.ok().body(users);
		} catch (Exception e) {
			userService.addError("list_all_users", requestUser);
			return ResponseEntity.badRequest().body("Error to list all users: " + e.getMessage());
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> listUserById(@PathVariable Long id, @RequestParam String numbersip) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));

		try {

			UserDTO dto = this.userService.listUserById(id);
			this.userService.addLog("list_user_by_id", requestUser);
			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			userService.addError("list_user_by_id", requestUser);
			return ResponseEntity.badRequest().body("Error to list user by id:  " + e.getMessage());

		}

	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestParam String numbersip, @RequestBody UserDTO userDto, @RequestHeader String numberSip) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));

		try {
			UserDTO dto = this.userService.updateUser(userDto,numberSip);
			this.userService.addLog("update_user", requestUser);
			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			userService.addError("update_user", requestUser);
			return ResponseEntity.badRequest()
					.body("Error to update user: " + userDto.getNumbersip() + " , " + e.getMessage());

		}

	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestParam String numbersip, @RequestBody String deleteNumbersip)
			throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));

		try {

			UserDTO dto = this.userService.deleteUser(deleteNumbersip);
			this.userService.addLog("delete_user", requestUser);
			return ResponseEntity.ok().body("Deleted user: " + dto);
		} catch (Exception e) {

			userService.addError("delete_user", requestUser);
			return ResponseEntity.badRequest().body("Error to delete users" + ", " + e.getMessage());
		}

	}

	@PostMapping("/sms")
	public ResponseEntity<?> sendSMS(@RequestParam String numbersip, @RequestBody SmsDTO dto) throws Exception {

		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));
		try {

			this.nvoipApiService.sendSMS(dto);
			this.userService.addLog("send_sms", requestUser);
			return ResponseEntity.ok().body("SMS sended: " + dto);
		} catch (Exception e) {
			userService.addError("send_sms", requestUser);
			return ResponseEntity.badRequest().body("Error to send sms" + ", " + e.getMessage());
		}

	}
	
	@PostMapping("/torpedo")
	public ResponseEntity<?> sendTorpedo (@RequestParam String numbersip, @RequestBody TorpedoDTO dto) throws Exception{
		
		User requestUser = new User(this.userService.getUserByNumberSip(numbersip));
		
		try {
			this.nvoipApiService.sendTorpedo(dto);
			this.userService.addLog("send_torpedo", requestUser);
			return ResponseEntity.ok().body("Torpedo sended : "+dto);
		}catch (Exception e) {
			userService.addError("send_torpedo", requestUser);
			return ResponseEntity.badRequest().body("Error to send torpedo" + ", " + e.getMessage());
		}
	}

}
