package rwa.sara.hikevents.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import rwa.sara.hikevents.exception.ResourceNotFoundException;
import rwa.sara.hikevents.exception.ResourceType;
import rwa.sara.hikevents.model.entity.RegistrationEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.RegistrationRepository;
import rwa.sara.hikevents.security.service.LoggedInUser;
import rwa.sara.hikevents.service.IService;

@Service
public class RegistrationService implements IService<RegistrationEntity>{

	public final RegistrationRepository registrationRepository;
	private final ModelMapper modelMapper;
	
	public RegistrationService(RegistrationRepository registrationRepository, ModelMapper modelMapper) {
		this.registrationRepository = registrationRepository;
		this.modelMapper = modelMapper;
	}

	public Optional<List<RegistrationEntity>> findByUserId(int userId){
		return registrationRepository.findByUserId(userId);
	}
	
	public Optional<List<RegistrationEntity>> findByEventId(int eventId){
		return registrationRepository.findByEventId(eventId);
	}
	
	public Optional<RegistrationEntity> insert(RegistrationEntity registrationEntity, LoggedInUser loggedInUser) {
		try{
			registrationEntity.setUser(modelMapper.map(loggedInUser, UserEntity.class));
			return Optional.of(this.registrationRepository.save(registrationEntity));
		} catch (NullPointerException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public List<RegistrationEntity> getAll() {
		return registrationRepository.findAll();
	}

	@Override
	public Optional<RegistrationEntity> get(int id) throws ResourceNotFoundException {
		return Optional.of(registrationRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.REGISTRATION, "Registration with id: " + id + " not found.")));
	}

	@Override
	public Optional<RegistrationEntity> update(RegistrationEntity registrationEntity) throws ResourceNotFoundException {
		Optional.of(registrationRepository.findById(registrationEntity.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.EVENT, "Registration with id: " + registrationEntity.getId() + " not found.")));
		return Optional.of(registrationRepository.save(registrationEntity));
	}

	@Override
	public boolean delete(int id) throws ResourceNotFoundException {
		if(!registrationRepository.existsById(id)) {
			throw new ResourceNotFoundException(ResourceType.REGISTRATION, "Registration with id: " + id + " not found.");
		} else {
			registrationRepository.deleteById(id);
			return true;
		}
	}

}
