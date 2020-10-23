package rwa.sara.hikevents.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rwa.sara.hikevents.model.entity.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer>, JpaSpecificationExecutor<RoleRepository> {
	
	public List<RoleEntity> findAll();
	public Optional<RoleEntity> findByName(String roleName);
	public Optional<RoleEntity> deleteByName(String roleName);
	//public Optional<List<UserEntity>> getUsers(String roleName);
	public boolean existsByName(String roleName);
	
}
