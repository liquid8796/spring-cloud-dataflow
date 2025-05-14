package org.springframework.cloud.dataflow.server.single.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistrationDTO {

	@NotBlank(message = "Username is required")
	@Size(min = 8, message = "Username length must be at least 8 characters")
	@Size(max = 50, message = "Password length must not exceed 50 characters")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password length must be at least 8 characters")
	@Size(max = 100, message = "Password length must not exceed 100 characters")
	private String password;

	@NotBlank(message = "Creator username is required")
	@Size(min = 8, message = "Creator username length must be at least 8 characters")
	@Size(max = 50, message = "Creator username length must not exceed 50 characters")
	private String usernameCreator;

	@NotBlank(message = "Creator password is required")
	@Size(min = 8, message = "Creator password length must be at least 8 characters")
	@Size(max = 100, message = "Creator password length must not exceed 100 characters")
	private String passwordCreator;

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

	public String getUsernameCreator() {
		return usernameCreator;
	}

	public void setUsernameCreator(String usernameCreator) {
		this.usernameCreator = usernameCreator;
	}

	public String getPasswordCreator() {
		return passwordCreator;
	}

	public void setPasswordCreator(String passwordCreator) {
		this.passwordCreator = passwordCreator;
	}
}

