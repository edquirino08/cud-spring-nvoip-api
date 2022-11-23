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
@Table(name = "sms")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message")
	private String message;

	@Column(name = "sender")
	private String sender;

	@Column(name = "destiny")
	private String destiny;

	public Sms(String message, String sender, String destiny) {
		this.message = message;
		this.sender = sender;
		this.destiny = destiny;
	}

}
