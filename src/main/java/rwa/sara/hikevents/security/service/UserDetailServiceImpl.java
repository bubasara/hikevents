package rwa.sara.hikevents.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rwa.sara.hikevents.exception.EmailNotFoundException;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) {
		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new EmailNotFoundException("User with this email cannot be found: ", email));
		return LoggedInUser.build(userEntity);
	}

}
