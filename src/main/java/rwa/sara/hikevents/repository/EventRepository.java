package rwa.sara.hikevents.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.UserEntity;

@Repository
public interface EventRepository extends CrudRepository<EventEntity, Integer>, JpaSpecificationExecutor<EventEntity>{

	public List<EventEntity> findAll();
	public Optional<EventEntity> findByTitle(String title);
	public Optional<List<EventEntity>> findByLocation(String location);
	public Optional<List<EventEntity>> findByStartDate(Date startDate);
	public List<EventEntity> findByHost(UserEntity host);
	public Optional<List<EventEntity>> findByPriceLessThan(int price);
}
