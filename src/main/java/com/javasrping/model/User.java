package com.javasrping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name")
	private String name;

	@Column(name = "sip_number")
	private String numbersip;

	@Column(name = "user_token")
	private String userToken;

	@Column(name = "telephone")
	private String telephone;

	public User(User user) {
		this.name = user.getName();
		this.numbersip = user.getNumbersip();
		this.userToken = user.getUserToken();
		this.telephone = user.getTelephone();
	}

	public User(String name, String numbersip, String userToken, String telephone) {
		this.name = name;
		this.numbersip = numbersip;
		this.userToken = userToken;
		this.telephone = telephone;
	}

}
