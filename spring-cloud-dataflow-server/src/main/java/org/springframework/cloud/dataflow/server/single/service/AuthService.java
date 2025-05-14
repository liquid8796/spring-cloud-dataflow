package org.springframework.cloud.dataflow.server.single.service;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AuthService {
	String registerUser(String username, String password, String username_creator, String password_creator, RedirectAttributes redirectAttributes);

	String doLogin(String username, String password, RedirectAttributes redirectAttributes);
}
