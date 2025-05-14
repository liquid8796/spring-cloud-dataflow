//package org.springframework.cloud.dataflow.server.config.apps;
//
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//
//@Configuration
//public class SSLTrustStoreConfig {
//	@PostConstruct
//	public void setSSLTrustStoreProperties() {
//		System.setProperty("javax.net.ssl.trustStore", getTrustStorePath());
//		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
//		System.setProperty("javax.net.debug", "ssl");
//	}
//
//	private String getTrustStorePath() {
//		return getClass().getClassLoader().getResource("trust-store.jks").getPath();
//	}
//}
