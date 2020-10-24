package rwa.sara.hikevents.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.RegistrationEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.RegistrationRepository;
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
	
	public Optional<RegistrationEntity> insert(EventEntity eventEntity, UserEntity userEntity) {
		try{
			//registrationEntity.setUser(modelMapper.map(loggedInUser, UserEntity.class));
			RegistrationEntity registrationEntity = new RegistrationEntity(eventEntity, userEntity);
			return Optional.of(registrationRepository.save(registrationEntity));
		} catch (NullPointerException e) {
			return Optional.empty();
		}
	}
	
	@Override
	public List<RegistrationEntity> getAll() {
		return registrationRepository.findAll();
	}

	@Override
	public Optional<RegistrationEntity> get(int id) {
		return registrationRepository.findById(id);
	}

	@Override
	public Optional<RegistrationEntity> update(RegistrationEntity registrationEntity) {
		if (registrationRepository.findById(registrationEntity.getId()).isPresent()) {
			return Optional.of(registrationRepository.save(registrationEntity));
		} else {
			return Optional.empty();
		}
		
	}

	@Override
	public boolean delete(int id) {
		if(!registrationRepository.existsById(id)) {
			return false;
		} else {
			registrationRepository.deleteById(id);
			return true;
		}
	}

}
