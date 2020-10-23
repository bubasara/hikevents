package rwa.sara.hikevents.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rwa.sara.hikevents.model.entity.RegistrationEntity;

@Repository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Integer>{
	public List<RegistrationEntity> findAll();
	public Optional<List<RegistrationEntity>> findByUserId(int userId);
	public Optional<List<RegistrationEntity>> findByEventId(int eventId);
}
