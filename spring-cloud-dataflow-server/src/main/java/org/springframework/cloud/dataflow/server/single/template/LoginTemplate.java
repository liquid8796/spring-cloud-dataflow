package org.springframework.cloud.dataflow.server.single.template;

public class LoginTemplate {
	public static final String HTML = "<!DOCTYPE html>\n" +
		"<html lang=\"vi\">\n" +
		"<head>\n" +
		"	 <meta charset=\"UTF-8\">" +
		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
		"    <title>Login Page</title>\n" +
		"    <style>\n" +
		"        body {\n" +
		"            font-family: Arial, sans-serif;\n" +
		"            background-color: #f0f2f5;\n" +
		"            display: flex;\n" +
		"            justify-content: center;\n" +
		"            align-items: center;\n" +
		"            height: 100vh;\n" +
		"            margin: 0;\n" +
		"        }\n" +
		"        .login-container {\n" +
		"            background-color: #fff;\n" +
		"            padding: 20px;\n" +
		"            border-radius: 10px;\n" +
		"            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
		"            display: inline-block;\n" +
		"            max-width: 1000px;\n" +
		"            width: 400px;\n" +
		"            padding-right: 40px;\n" +
		"        }\n" +
		"        .login-container h2 {\n" +
		"            margin-bottom: 20px;\n" +
		"            color: #333;\n" +
		"        }\n" +
		"        .login-container label {\n" +
		"            display: block;\n" +
		"            margin-bottom: 5px;\n" +
		"            color: #555;\n" +
		"        }\n" +
		"        .login-container input {\n" +
		"            width: 100%;\n" +
		"            padding: 10px;\n" +
		"            margin-bottom: 10px;\n" +
		"            border: 1px solid #ccc;\n" +
		"            border-radius: 5px;\n" +
		"        }\n" +
		"        .login-container button {\n" +
		"            width: 420px;\n" +
		"            padding: 10px;\n" +
		"            background-color: #007bff;\n" +
		"            color: #fff;\n" +
		"            border: none;\n" +
		"            border-radius: 5px;\n" +
		"            cursor: pointer;\n" +
		"        }\n" +
		"        .login-container button:hover {\n" +
		"            background-color: #0056b3;\n" +
		"        }\n" +
		"        .login-container p {\n" +
		"            font-size: 0.9em;\n" +
		"        }\n" +
		"        #error-message {\n" +
		"           color: red;\n" +
		"		 }\n" +
		"    </style>\n" +
		"</head>\n" +
		"<body>\n" +
		"    <div class=\"login-container\">\n" +
		"        <h2>Login</h2>\n" +
		"        <form action=\"/login\" method=\"post\">\n" +
		"            <label>Username:</label>\n" +
		"            <input type=\"text\" name=\"username\" required/>\n" +
		"            <p id=\"error-message\">${error_username}</p>\n" +
		"            <label>Password:</label>\n" +
		"            <input type=\"password\" name=\"password\" required/>\n" +
		"            <p id=\"error-message\">${error_password}</p>\n" +
		"            <p id=\"error-message\">${error}</p>\n" +
		"            <button type=\"submit\">Login</button>\n" +
		"        </form>\n" +
		"        <p>Need new user? <a href=\"/register\">Register here</a></p>\n" +
		"    </div>\n" +
		"</body>\n" +
		"</html>\n";
}
