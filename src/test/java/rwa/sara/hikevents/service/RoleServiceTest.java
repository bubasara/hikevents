package rwa.sara.hikevents.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rwa.sara.hikevents.model.entity.RoleEntity;
import rwa.sara.hikevents.repository.RoleRepository;
import rwa.sara.hikevents.service.impl.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoleServiceTest {

	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private RoleService roleService;
	
	private RoleEntity role;
	
	@Before
	public void init() {
		role = new RoleEntity();
		role.setId(1);
		role.setName("ADMIN");
	}
	
	@Test
	public void insertTestShouldReturnInsertedRole() {
		when(roleRepository.save(role)).thenReturn(role);
		Optional<RoleEntity> actualRole = roleService.insert(role);
		assertEquals("Failed because role was not created.", role, actualRole.get()); 
	}
	
	@Test
	public void getByIdTestShouldReturnRoleWithId() {
		when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
		Optional<RoleEntity> actualRole = roleService.get(role.getId());
		assertEquals("Failed because role was not found by given id.", role, actualRole.get());
	}
	
	@Test
	public void getAllTestShouldReturnAllRoles() {
		List<RoleEntity> expectedRoles = new ArrayList<>();
		expectedRoles.add(role);
		when(roleRepository.findAll()).thenReturn(expectedRoles);
		List<RoleEntity> actualRoles = roleService.getAll();
		assertEquals("Failed because getAll is not working correctly.", expectedRoles, actualRoles);
	}
	
	@Test
	public void updateTestShouldReturnUpdatedRole() {
		role.setName("HIKER");
		when(roleRepository.save(role)).thenReturn(role);
		when(roleRepository.findById(1)).thenReturn(Optional.of(role));
		Optional<RoleEntity> actualRole = roleService.update(role);
		assertEquals("Failed because role was not updated correctly.", role, actualRole.get());
	}
	
	@Test
	public void updateTestShouldReturnOptionalEmptyIfNotUpdated() {
		role.setName("HIKER");
		when(roleRepository.save(role)).thenReturn(role);
		when(roleRepository.findById(2)).thenReturn(Optional.of(role));
		Optional<RoleEntity> actualRole = roleService.update(role);
		assertEquals("Failed because role was updated.", Optional.empty(), actualRole);
	}
	
	@Test
	public void getByNameTestShouldReturnRoleWithName() {
		when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
		Optional<RoleEntity> actualRole = roleService.findByName(role.getName());
		assertEquals("Failed because role was not found by given name.", role, actualRole.get());
		
	}
	
	@Test
	public void deleteTestShouldReturnTrueIfDeleted() {
		Boolean deleted = false;
		when(roleRepository.existsById(1)).thenReturn(true);
		roleService.delete(role.getId());
		deleted = roleService.delete(role.getId());
		assertTrue("Failed because user was not deleted.", deleted);
	}
	
	@Test
	public void deleteTestShouldReturnFalseIfNotDeleted() {
		Boolean deleted = true;
		when(roleRepository.existsById(1)).thenReturn(true);
		when(!roleRepository.existsById(1)).thenReturn(false);
		roleService.delete(role.getId());
		deleted = roleService.delete(role.getId());
		assertFalse("Failed because user was deleted.", deleted);
	}
}
