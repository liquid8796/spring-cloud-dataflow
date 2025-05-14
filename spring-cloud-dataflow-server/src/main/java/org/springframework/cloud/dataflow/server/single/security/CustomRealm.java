package org.springframework.cloud.dataflow.server.single.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.dataflow.server.single.entity.User;
import org.springframework.cloud.dataflow.server.single.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class CustomRealm extends AuthorizingRealm {
	private final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public CustomRealm(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		Optional<User> user = userRepository.findByUsername(username);

		if (user.isEmpty()) {
			logger.error("User not found with username: {}", username);
			throw new UnknownAccountException("User not found!");
		}

//		System.out.println(">>>>>>>>>>>>>>>>>>raw=" + new String((char[]) token.getCredentials()));
//		System.out.println(">>>>>>>>>>>>>>>>>>encoded=" + user.getPassword());
//		if (!passwordEncoder.matches(new String((char[]) token.getCredentials()), user.getPassword())) {
//		if (!passwordEncoder.matches(token.getCredentials().toString(), user.getPassword())) {
//			throw new IncorrectCredentialsException("Invalid password!");
//		}

		return new SimpleAuthenticationInfo(
			user.get().getUsername(),
			user.get().getPassword(),
			getName()
		);
	}
}