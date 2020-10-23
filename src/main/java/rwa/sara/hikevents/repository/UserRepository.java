package rwa.sara.hikevents.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rwa.sara.hikevents.model.UserType;
import rwa.sara.hikevents.model.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>,
										JpaSpecificationExecutor<UserEntity>{
	
	public List<UserEntity> findAll();
	public Optional<UserEntity> findByName(String name);
	public List<UserEntity> findByRole(UserType userType);
	public Optional<UserEntity> findByEmail(String email);
	public Boolean existsByEmail(String email);
	
}
