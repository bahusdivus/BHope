package ru.bahusdivus.bhope.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(login);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		if (user.isAdmin()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
		} else {
			grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		}

		return new org.springframework.security.core.userdetails.User(user.getLogin(),
																	  user.getPassword(),
																	  grantedAuthorities);
	}
}