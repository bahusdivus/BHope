package ru.bahusdivus.bhope.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.bahusdivus.bhope.entities.User;
import ru.bahusdivus.bhope.repository.UserRepository;
import ru.bahusdivus.bhope.utils.UserDetailsUserImpl;

import javax.transaction.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findUserByLoginIgnoreCase(login);
		if (user == null) {
			throw new UsernameNotFoundException("user " + login + "not found");
		}

		return new UserDetailsUserImpl(user.getLogin(), user.getPassword(), user.getId(), user.isAdmin());
	}
}
