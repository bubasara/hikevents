package rwa.sara.hikevents.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rwa.sara.hikevents.exception.DuplicateResourceException;
import rwa.sara.hikevents.exception.EmailNotFoundException;
import rwa.sara.hikevents.exception.ResourceNotFoundException;
import rwa.sara.hikevents.exception.ResourceType;
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
	
	public Optional<UserEntity> findByName(String name){
		return userRepository.findByName(name);
	}
	
	public List<UserEntity> findByRole(UserType userType){
		return userRepository.findByRole(userType);
	}


	public Optional<UserEntity> findByEmail(String email) throws EmailNotFoundException {
		return Optional.of(userRepository.findByEmail(email)).orElseThrow(
				() -> new EmailNotFoundException("User with this email not found: ", email));
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public Optional<UserEntity> insert(UserEntity userEntity) throws DuplicateResourceException {
		if(userRepository.existsByEmail(userEntity.getEmail())) {
			throw new DuplicateResourceException(ResourceType.USER, "User with email: " + userEntity.getEmail() + " already exists.");
		} else {
			return Optional.of(userRepository.save(userEntity));
		}
	}
	
	@Override
	public List<UserEntity> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserEntity> get(int id) throws ResourceNotFoundException {
		return Optional.of(userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.USER, "User with id: " + id + " not found.")));
	}

	@Override
	public Optional<UserEntity> update(UserEntity userEntity) throws ResourceNotFoundException {
		Optional.of(userRepository.findById(userEntity.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.USER, "User with id: " + userEntity.getId() + " not found.")));
		return Optional.of(userRepository.save(userEntity));
	}

	@Override
	public boolean delete(int id) throws ResourceNotFoundException {
		if(!userRepository.existsById(id)) {
			throw new ResourceNotFoundException(ResourceType.USER, "User with id: " + id + " not found.");
		} else {
			userRepository.deleteById(id);
			return true;
		}
	}

}
