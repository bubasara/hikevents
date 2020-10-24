package rwa.sara.hikevents.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rwa.sara.hikevents.model.UserType;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.UserRepository;
import rwa.sara.hikevents.service.IService;

@Service
public class UserService implements IService<UserEntity>{

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Optional<UserEntity> findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public List<UserEntity> findByRole(UserType userType) {
		return userRepository.findByRole(userType);
	}


	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public Optional<UserEntity> insert(UserEntity userEntity) {
		if(userRepository.existsByEmail(userEntity.getEmail())) {
			return Optional.empty();
		} else {
			return Optional.of(userRepository.save(userEntity));
		}
	}
	
	@Override
	public List<UserEntity> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserEntity> get(int id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<UserEntity> update(UserEntity userEntity) {
		return Optional.of(userRepository.save(userEntity));
	}

	@Override
	public boolean delete(int id) {
		if(!userRepository.existsById(id)) {
			return false;
		} else {
			userRepository.deleteById(id);
			return true;
		}
	}

}
