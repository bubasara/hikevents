package rwa.sara.hikevents.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rwa.sara.hikevents.model.entity.RoleEntity;
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

	public Optional<RoleEntity> insert(RoleEntity roleEntity) {
		return Optional.of(roleRepository.save(roleEntity));
	}
	
	@Override
	public List<RoleEntity> getAll() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<RoleEntity> get(int roleId) {
		return roleRepository.findById(roleId);
	}

	@Override
	public Optional<RoleEntity> update(RoleEntity roleEntity) {
		if (roleRepository.findById(roleEntity.getId()).isPresent()){
			return Optional.of(roleRepository.save(roleEntity));
		} else {
			return Optional.empty();
		}
		
	}

	@Override
	public boolean delete(int id) {
		if(!roleRepository.existsById(id)) {
			return false;
		} else {
			roleRepository.deleteById(id);
			return true;
		}
	}

}
