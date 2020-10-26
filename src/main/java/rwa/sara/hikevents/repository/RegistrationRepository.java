package rwa.sara.hikevents.repository;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;

import rwa.sara.hikevents.model.entity.RegistrationEntity;

@Repository
public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Integer>{
	public List<RegistrationEntity> findAll();
	public Optional<List<RegistrationEntity>> findByUserId(int userId);
	public Optional<List<RegistrationEntity>> findByEventId(int eventId);
//	@Modifying
//	@Transactional
//	@Query(value = "INSERT INTO `db_hikevents`.`registrations` (`event_id`, `user_id`) VALUES (:user_id, :event_id);", nativeQuery = true)
//	public int save(@Param("user_id") int userId, @Param("event_id") int eventId);
}
