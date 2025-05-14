//package org.springframework.cloud.dataflow.server.single.config;
//
//import io.buji.pac4j.realm.Pac4jRealm;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.pac4j.saml.client.SAML2Client;
//import org.pac4j.saml.config.SAML2Configuration;
//import org.springframework.cloud.dataflow.server.single.handler.filter.SamlSecurityFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//	// Cấu hình SAML2Client để làm IdP
//	@Bean
//	public SAML2Client saml2Client() {
//		SAML2Configuration config = new SAML2Configuration();
//		config.setKeystorePath("classpath:samlKeystore.jks");
//		config.setKeystorePassword("scdf-adfs");
//		config.setPrivateKeyPassword("scdf-adfs");
//		config.setIdentityProviderMetadataPath("classpath:idp-metadata.xml");
//		config.setServiceProviderEntityId("https://scdf-pmc.apps.uat-cloud.sacombank.local:443/service-provider-metadata/scdf-adfs");
//		config.setSingleSignOutServiceUrl("https://scdf-pmc.apps.uat-cloud.sacombank.local:443/saml2/sso/singleLogout");
//		config.setCallbackUrl("https://scdf-pmc.apps.uat-cloud.sacombank.local:443/dashboard");
//		config.setMaximumAuthenticationLifetime(3600);
//		config.setForceAuth(false);
//		config.setPassive(false);
//		config.setWantsAssertionsSigned(true);
//
//		SAML2Client saml2Client = new SAML2Client(config);
//		saml2Client.init();
//
//		return saml2Client;
//	}
//
//	@Bean
//	public Pac4jRealm pac4jRealm() {
//		Pac4jRealm pac4jRealm = new Pac4jRealm();
//		pac4jRealm.setAuthenticationCachingEnabled(false);
//
//		return pac4jRealm;
//	}
//
//	@Bean
//	public SecurityManager securityManager(Pac4jRealm pac4jRealm) {
//		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//		securityManager.setRealm(pac4jRealm);
//		securityManager.setSessionManager(sessionManager());
//		return securityManager;
//	}
//
//	@Bean
//	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, SAML2Client saml2Client) {
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//		// Cấu hình các filter tùy chỉnh
//		Map<String, javax.servlet.Filter> filters = new LinkedHashMap<>();
//		String saml2ClientName = saml2Client.getName();
//		filters.put("saml2", new SamlSecurityFilter(saml2ClientName));
//		shiroFilterFactoryBean.setFilters(filters);
//
//		// Cấu hình các quy tắc filter
//		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//		filterChainDefinitionMap.put("/login", "anon");
//		filterChainDefinitionMap.put("/register", "anon");
//		filterChainDefinitionMap.put("/logout", "logout");
//		filterChainDefinitionMap.put("/dashboard/**", "authc");
//		filterChainDefinitionMap.put("/apps/**", "anon");
//		filterChainDefinitionMap.put("/tasks/definitions/**", "anon");
//		filterChainDefinitionMap.put("/saml2/callback", "saml2"); // Xử lý callback từ IdP
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//
//		return shiroFilterFactoryBean;
//	}
//
//	@Bean
//	public DefaultWebSessionManager sessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		sessionManager.setGlobalSessionTimeout(24 * 60 * 60 * 1000);
//
//		return sessionManager;
//	}
//}