package org.springframework.cloud.dataflow.server.single.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/saml2")
public class SamlCallbackController {

	@GetMapping("/callback")
	public String samlCallback() {
		// Xử lý sau khi xác thực thành công
		return "redirect:dashboard";
	}
}
