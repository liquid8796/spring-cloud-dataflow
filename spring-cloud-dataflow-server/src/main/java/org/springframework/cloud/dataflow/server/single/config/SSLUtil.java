package org.springframework.cloud.dataflow.server.single.config;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class SSLUtil {
	public static void disableSSLValidation() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[]{
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
					public void checkClientTrusted(X509Certificate[] certs, String authType) {}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {}
				}
			};

			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
