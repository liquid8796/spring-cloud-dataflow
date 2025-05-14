package org.springframework.cloud.dataflow.server.single.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.dataflow.server.single.service.AuthService;
import org.springframework.cloud.dataflow.server.single.template.LoginTemplate;
import org.springframework.cloud.dataflow.server.single.template.RegisterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthController {
	@Autowired
	private AuthService authService;

	@GetMapping("/login")
	public void getLoginPage(HttpServletResponse response, @ModelAttribute("error") String error,
							 @ModelAttribute("error_username") String error_username,
							 @ModelAttribute("error_password") String error_password) throws IOException {
		response.setContentType("text/html");
		String loginHtml = LoginTemplate.HTML.replace("${error}", error != null ? error : "")
			.replace("${error_username}", error_username != null ? error_username : "")
			.replace("${error_password}", error_password != null ? error_password : "");

		response.getWriter().write(loginHtml);
	}

	@PostMapping("/login")
	public String doLogin(String username, String password, RedirectAttributes redirectAttributes) {
		return authService.doLogin(username, password, redirectAttributes);
	}

	@GetMapping("/register")
	public void register(HttpServletResponse response, @ModelAttribute("error") String error, @ModelAttribute("success") String success,
						 @ModelAttribute("error_username") String error_username,
						 @ModelAttribute("error_password") String error_password,
						 @ModelAttribute("error_username_creator") String error_username_creator,
						 @ModelAttribute("error_password_creator") String error_password_creator) throws IOException {
		response.setContentType("text/html");
		String registerHtml = RegisterTemplate.HTML.replace("${error}", error != null ? error : "")
			.replace("${success}", success != null ? success : "")
			.replace("${error_username}", error_username != null ? error_username : "")
			.replace("${error_password}", error_password != null ? error_password : "")
			.replace("${error_username_creator}", error_username_creator != null ? error_username_creator : "")
			.replace("${error_password_creator}", error_password_creator != null ? error_password_creator : "");

		response.getWriter().write(registerHtml);
	}

	@PostMapping("/register")
	public String handleRegister(String username, String password, String username_creator, String password_creator, RedirectAttributes redirectAttributes) {
		return authService.registerUser(username, password, username_creator, password_creator, redirectAttributes);
	}

	@GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:dashboard";
    }

    @GetMapping("/403")
//    public void getUnauthorizedPage(HttpServletResponse response) throws IOException {
	public String getUnauthorizedPage(){
//		response.setContentType("text/html");
//		response.getWriter().write(UnauthorizedTemplate.HTML);
		return "unauthorized";
    }
}