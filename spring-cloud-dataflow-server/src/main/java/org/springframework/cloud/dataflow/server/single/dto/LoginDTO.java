package org.springframework.cloud.dataflow.server.single.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotBlank(message = "Username is required")
	@Size(min = 8, message = "Username length must be at least 8 characters")
	@Size(max = 50, message = "Password length must not exceed 50 characters")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password length must be at least 8 characters")
	@Size(max = 100, message = "Password length must not exceed 100 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

