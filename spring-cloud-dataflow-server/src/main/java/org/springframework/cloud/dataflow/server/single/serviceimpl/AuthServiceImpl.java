package org.springframework.cloud.dataflow.server.single.serviceimpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.dataflow.server.single.dto.LoginDTO;
import org.springframework.cloud.dataflow.server.single.dto.RegistrationDTO;
import org.springframework.cloud.dataflow.server.single.entity.User;
import org.springframework.cloud.dataflow.server.single.handler.validation.ValidationService;
import org.springframework.cloud.dataflow.server.single.repository.UserRepository;
import org.springframework.cloud.dataflow.server.single.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
	private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

//	private BCryptPasswordEncoder passwordEncoder;

	private final ValidationService validationService = new ValidationService();

	@Override
	public String registerUser(String username, String password, String username_creator, String password_creator, RedirectAttributes redirectAttributes) {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setUsername(username);
		registrationDTO.setPassword(password);
		registrationDTO.setUsernameCreator(username_creator);
		registrationDTO.setPasswordCreator(password_creator);

		Set<ConstraintViolation<RegistrationDTO>> violations = validationService.validate(registrationDTO);

		if (!violations.isEmpty()) {
			for (ConstraintViolation<RegistrationDTO> violation : violations) {
				String message = violation.getMessage();
				switch (violation.getPropertyPath().toString()) {
					case "username":
						redirectAttributes.addFlashAttribute("error_username", message);
						break;
					case "password":
						redirectAttributes.addFlashAttribute("error_password", message);
						break;
					case "usernameCreator":
						redirectAttributes.addFlashAttribute("error_username_creator", message);
						break;
					case "passwordCreator":
						redirectAttributes.addFlashAttribute("error_password_creator", message);
						break;
				}
			}
			return "redirect:register";
		}

		Optional<User> user = userRepository.findByUsername(username);

		if (user.isPresent()) {
			logger.error("User already exists with username: {}", username);
			redirectAttributes.addFlashAttribute("error", "Username is already taken!");
		} else {
			Optional<User> creator = userRepository.findUserByUsernameAndPassword(username_creator, password_creator);

			if(creator.isEmpty()) {
				logger.error("Invalid creator username or password!");
				redirectAttributes.addFlashAttribute("error", "Invalid creator username or password!");
			} else {
				//			String encodedPassword = passwordEncoder.encode(password);

				User newUser = new User();
				newUser.setUsername(username);
				newUser.setPassword(password);
				newUser.setCreated_by(creator.get().getId());
				userRepository.save(newUser);

				logger.error("User registered successfully with username: {}", username);
				redirectAttributes.addFlashAttribute("success", "User registered successfully!");
			}
		}

		return "redirect:register";
	}

	@Override
	public String doLogin(String username, String password, RedirectAttributes redirectAttributes) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(username);
		loginDTO.setPassword(password);

		Set<ConstraintViolation<LoginDTO>> violations = validationService.validate(loginDTO);

		if (!violations.isEmpty()) {
			for (ConstraintViolation<LoginDTO> violation : violations) {
				String message = violation.getMessage();
				if (violation.getPropertyPath().toString().equals("username")) {
					redirectAttributes.addFlashAttribute("error_username", message);
				}
				if (violation.getPropertyPath().toString().equals("password")) {
					redirectAttributes.addFlashAttribute("error_password", message);
				}
			}
			return "redirect:login";
		}

		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(new UsernamePasswordToken(username, password));
			return "redirect:dashboard";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Invalid username or password!");
			return "redirect:login";
		}
	}
}
