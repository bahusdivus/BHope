package ru.bahusdivus.bhope.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationManager authenticationManager;

	public SecurityServiceImpl(UserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String findLoggedInLogin() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	@Override
	public void autoLogin(String login, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(login);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
		 = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			log.debug(String.format("Auto login %s successfully!", password));
		}
	}
}
