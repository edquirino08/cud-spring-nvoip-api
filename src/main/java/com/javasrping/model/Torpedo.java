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
@Table(name = "torpedo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Torpedo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "message")
	private String message;

	@Column(name = "sender")
	private String sender;

	@Column(name = "destiny")
	private String destiny;

	public Torpedo(String message, String sender, String destiny) {
		this.message = message;
		this.sender = sender;
		this.destiny = destiny;
	}

}
