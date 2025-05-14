package org.springframework.cloud.dataflow.server.single.template;

public class RegisterTemplate {
	public static final String HTML = "<!DOCTYPE html>\n" +
		"<html lang=\"vi\">\n" +
		"<head>\n" +
		"	 <meta charset=\"UTF-8\">" +
		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
		"    <title>Register</title>\n" +
		"    <!-- Bootstrap CSS -->\n" +
		"    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\">\n" +
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
		"        .register-container {\n" +
		"            background-color: #fff;\n" +
		"            padding: 20px;\n" +
		"            border-radius: 10px;\n" +
		"            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
		"            display: inline-block;\n" +
		"            max-width: 1000px;\n" +
		"            width: 450px;\n" +
		"            padding-right: 40px;\n" +
		"        }\n" +
		"        .register-container h2 {\n" +
		"            margin-bottom: 20px;\n" +
		"            color: #333;\n" +
		"        }\n" +
		"        .register-container label {\n" +
		"            display: block;\n" +
		"            margin-bottom: 5px;\n" +
		"            color: #555;\n" +
		"        }\n" +
		"        .register-container input {\n" +
		"            width: 100%;\n" +
		"            padding: 10px;\n" +
		"            margin-bottom: 10px;\n" +
		"            border: 1px solid #ccc;\n" +
		"            border-radius: 5px;\n" +
		"        }\n" +
		"        .register-container button {\n" +
		"            width: 390px;\n" +
		"            padding: 10px;\n" +
		"            background-color: #007bff;\n" +
		"            color: #fff;\n" +
		"            border: none;\n" +
		"            border-radius: 5px;\n" +
		"            cursor: pointer;\n" +
		"        }\n" +
		"        .register-container button:hover {\n" +
		"            background-color: #0056b3;\n" +
		"        }\n" +
		"        .btn-back {\n" +
		"            margin-top: 10px;\n" +
		"            width: 390px;\n" +
		"            background-color: #6c757d;\n" +
		"            color: #fff;\n" +
		"            text-align: center;\n" +
		"            padding: 10px;\n" +
		"            border-radius: 5px;\n" +
		"            text-decoration: none;\n" +
		"            display: inline-block;\n" +
		"        }\n" +
		"        .btn-back:hover {\n" +
		"            background-color: #5a6268;\n" +
		"        }" +
		"        .register-container p {\n" +
		"            font-size: 0.9em;\n" +
		"        }\n" +
		"        #error-message {\n" +
		"           color: red;\n" +
		"		 }\n" +
		"        #success-message {\n" +
		"           color: green;\n" +
		"		 }\n" +
		"    </style>\n" +
		"</head>\n" +
		"<body>\n" +
		"	<div class=\"register-container\">\n" +
		"        <h2>Register</h2>\n" +
		"        <form action=\"/register\" method=\"post\">\n" +
		"        	<label>Username:</label>\n" +
		"           <input type=\"text\" name=\"username\" required/>\n" +
		"           <p id=\"error-message\">${error_username}</p>\n" +
		"        	<label>Password:</label>\n" +
		"           <input type=\"password\" name=\"password\" required/>\n" +
		"           <p id=\"error-message\">${error_password}</p>\n" +
		"        	<label>Username creator:</label>\n" +
		"           <input type=\"text\" name=\"username_creator\" required/>\n" +
		"           <p id=\"error-message\">${error_username_creator}</p>\n" +
		"        	<label>Password creator:</label>\n" +
		"           <input type=\"password\" name=\"password_creator\" required/>\n" +
		"           <p id=\"error-message\">${error_password_creator}</p>\n" +
		"    	    <p id=\"error-message\">${error}</p>\n" +
		"    	    <p id=\"success-message\">${success}</p>\n" +
		"           <button type=\"submit\">Register</button>\n" +
		"        </form><br>" +
		"        <p><a href=\"/login\">Back to login</a></p>\n" +
		"	</div>\n" +
		"</body>\n" +
		"</html>\n";
}
