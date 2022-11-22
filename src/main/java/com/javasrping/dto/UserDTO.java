package com.javasrping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String name;
	private String numbersip;
	private String userToken;
	private String telephone;

	public UserDTO(UserDTO dto) {
		this.name = dto.getName();
		this.numbersip = dto.getNumbersip();
		this.userToken = dto.getUserToken();
		this.telephone = dto.getTelephone();
	}

}
