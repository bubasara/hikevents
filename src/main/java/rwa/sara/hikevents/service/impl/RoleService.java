package rwa.sara.hikevents.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rwa.sara.hikevents.exception.DuplicateResourceException;
import rwa.sara.hikevents.exception.ResourceNotFoundException;
import rwa.sara.hikevents.exception.ResourceType;
import rwa.sara.hikevents.model.entity.RoleEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.RoleRepository;
import rwa.sara.hikevents.service.IService;

@Service
public class RoleService implements IService<RoleEntity>{

	private final RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Optional<RoleEntity> findByName(String roleName){
		return roleRepository.findByName(roleName);
	}

	public Optional<RoleEntity> insert(RoleEntity roleEntity) throws DuplicateResourceException {
		if(!roleRepository.existsByName(roleEntity.getName())) {
			throw new DuplicateResourceException(ResourceType.ROLE, "Role with id: " + roleEntity.getId() + " already exists.");
		} else {
			return Optional.of(roleRepository.save(roleEntity));
		}
	}
	
	public boolean deleteByName(String roleName) {
		try {
			roleRepository.deleteByName(roleName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Optional<List<UserEntity>> getUsers(String roleName) {
		return getUsers(roleName);
	}
	
	@Override
	public List<RoleEntity> getAll() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<RoleEntity> get(int roleId) throws ResourceNotFoundException {
		return Optional.of(roleRepository.findById(roleId).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.ROLE, "Role with id: " + roleId + " not found.")));
	}

	@Override
	public Optional<RoleEntity> update(RoleEntity roleEntity) throws ResourceNotFoundException {
		Optional.of(roleRepository.findById(roleEntity.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.ROLE, "Role with id: " + roleEntity.getId() + " not found.")));
		return Optional.of(roleRepository.save(roleEntity));
	}

	@Override
	public boolean delete(int id) throws ResourceNotFoundException {
		if(!roleRepository.existsById(id)) {
			throw new ResourceNotFoundException(ResourceType.ROLE, "Role with id: " + id + " not found.");
		} else {
			roleRepository.deleteById(id);
			return true;
		}
	}

}
