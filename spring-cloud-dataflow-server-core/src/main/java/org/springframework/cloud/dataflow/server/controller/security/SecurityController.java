/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.dataflow.server.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.common.security.support.SecurityStateBean;
import org.springframework.cloud.dataflow.rest.resource.security.SecurityInfoResource;
import org.springframework.cloud.dataflow.server.utils.ObjectUtil;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Provides security-related meta information. Provides one REST endpoint at present time
 * {@code /security/info} that provides information such as whether security is enabled
 * and if so what is the username of the currently logged in user etc.
 *
 * @author Gunnar Hillert
 * @author Ilayaperumal Gopinathan
 * @since 1.0
 */
@RestController
@RequestMapping("/security/info")
@ExposesResourceFor(SecurityInfoResource.class)
public class SecurityController {

	private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private final SecurityStateBean securityStateBean;

	@Value("${security.oauth2.client.client-id:#{null}}")
	private String oauthClientId;

	public SecurityController(SecurityStateBean securityStateBean) {
		this.securityStateBean = securityStateBean;
	}

	/**
	 * Return security information. E.g. is security enabled? Which user do you represent?
	 *
	 * @return the security info
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public SecurityInfoResource getSecurityInfo(Principal principal) {

		final boolean authenticationEnabled = securityStateBean.isAuthenticationEnabled();

		final SecurityInfoResource securityInfo = new SecurityInfoResource();
		securityInfo.setAuthenticationEnabled(authenticationEnabled);
		securityInfo.add(linkTo(SecurityController.class).withSelfRel());

		if (authenticationEnabled && SecurityContextHolder.getContext() != null) {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				securityInfo.setAuthenticated(authentication.isAuthenticated());

				Object uniqueName = ObjectUtil.getValue(principal, "principal['attributes']['username']");

				assert uniqueName != null;
				securityInfo.setUsername(uniqueName.toString());

				for (GrantedAuthority authority : authentication.getAuthorities()) {
					securityInfo.addRole(authority.getAuthority());
				}
			}
		}

		return securityInfo;
	}

}
