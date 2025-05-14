package org.springframework.cloud.dataflow.server.single.handler.filter;

import org.pac4j.jee.filter.SecurityFilter;

public class SamlSecurityFilter extends SecurityFilter {
	public SamlSecurityFilter(String clientName) {
		super();
		setClients(clientName);
	}
}