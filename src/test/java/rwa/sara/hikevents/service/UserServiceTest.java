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

import rwa.sara.hikevents.model.UserType;
import rwa.sara.hikevents.model.entity.RegistrationEntity;
import rwa.sara.hikevents.model.entity.RoleEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.RoleRepository;
import rwa.sara.hikevents.repository.UserRepository;
import rwa.sara.hikevents.security.service.UserDetailServiceImpl;
import rwa.sara.hikevents.service.impl.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private UserDetailServiceImpl userDetailService;
	
	@InjectMocks
	private UserService userService;
	
	private UserEntity userAdmin;
	private UserEntity userHikingClub;
	private UserEntity userHiker;
	//private PasswordEncoder encoder;
	private RoleEntity roleAdmin;
	private RoleEntity roleHikingClub;
	private RoleEntity roleHiker;
	private List<RegistrationEntity> registrations;
	
	@Before
	public void init() {
		roleAdmin = new RoleEntity(1, "ADMIN");
		roleHikingClub = new RoleEntity(2, "HIKINGCLUB");
		roleHiker = new RoleEntity(3, "HIKER");
		
		userAdmin = new UserEntity();
		userAdmin.setId(1);
		userAdmin.setName("Sara");
		userAdmin.setCity("Nis");
		userAdmin.setEmail("sara.stankovic1@pmf.edu.rs");
		userAdmin.setYear(1996);
		//user.setPassword(encoder.encode("password"));
		userAdmin.setPassword("password");
		userAdmin.setRole(roleAdmin);
		userHikingClub = new UserEntity(2, "PD Nis", 2020, "Nis", "pdnis@gmail.com", "password", roleHikingClub, null, null);
		userHiker = new UserEntity(3, "Hiker", 2000, "Nis", "hiker@gmail.com", "password", roleHiker, null, null);
		userHiker.setRegistrations(registrations);
		userHiker.setUsersEvents(registrations);
	}
	
	@Test
	public void insertTestShouldReturnInsertedUser() {
		when(userRepository.save(userAdmin)).thenReturn(userAdmin);
		Optional<UserEntity> actualUser = userService.insert(userAdmin);
		assertEquals("Failed because user was not created correctly.", userAdmin, actualUser.get());
	}
	
	@Test
	public void insertTestShouldReturnOptionalEmpty() {
		when(userRepository.existsByEmail(userAdmin.getEmail())).thenReturn(true);
		Optional<UserEntity> inserted = userService.insert(userAdmin);
		assertEquals("Failed because user with the same email was inserted.", Optional.empty(), inserted);
	}
	
	@Test
	public void getByIdTestShouldReturnUserWithId() {
		when(userRepository.findById(userAdmin.getId())).thenReturn(Optional.of(userAdmin));
		Optional<UserEntity> actualUser = userService.get(userAdmin.getId());
		assertEquals("Failed because user was not found by given id.", userAdmin, actualUser.get());
	}
	
	@Test
	public void getAllTestShouldReturnAllUsers() {
		List<UserEntity> expectedUsers = new ArrayList<>();
		expectedUsers.add(userAdmin);
		when(userRepository.findAll()).thenReturn(expectedUsers);
		List<UserEntity> actualUsers = userService.getAll();
		assertEquals("Failed because getAll is not working correctly.", expectedUsers, actualUsers);
	}
	
	@Test
	public void updateTestShouldReturnUpdatedUser() {
		userAdmin.setName("Sara St");
		when(userRepository.save(userAdmin)).thenReturn(userAdmin);
		Optional<UserEntity> actualUser = userService.update(userAdmin);
		assertEquals("Failed because user was not updated correctly.", userAdmin, actualUser.get());
	}
	
	@Test
	public void existsByEmailTestShouldReturnTrueIfExists() {
		Boolean expected = true;
		when(userRepository.existsByEmail(userAdmin.getEmail())).thenReturn(expected);
		Boolean actual = userService.existsByEmail(userAdmin.getEmail());
		assertEquals("Failed because email was not found.", expected, actual);
	}
	
	@Test
	public void getByEmailTestShouldReturnUserWithEmail() {
		when(userRepository.findByEmail(userAdmin.getEmail())).thenReturn(Optional.of(userAdmin));
		Optional<UserEntity> actualUser = userService.findByEmail(userAdmin.getEmail());
		assertEquals("Failed because user was not found by given email.", userAdmin, actualUser.get());
	}
	
	@Test
	public void getByNameTestShouldReturnUserWithName() {
		when(userRepository.findByName(userAdmin.getName())).thenReturn(Optional.of(userAdmin));
		Optional<UserEntity> actualUser = userService.findByName(userAdmin.getName());
		assertEquals("Failed because user was not found by given name.", userAdmin, actualUser.get());
		
	}
	
	public UserType getRole(UserEntity userEntity) {
		UserType role = null;
		switch (userAdmin.getRole().getName()) {
		case "ADMIN":
			role = UserType.ADMIN;
			break;
		case "HIKINGCLUB":
			role = UserType.HIKINGCLUB;
			break;
		case "HIKER":
			role = UserType.HIKER;
			break;
		}
		return role;
	}
	
	@Test
	public void getByRoleTestShouldReturnUserWithRoleAdmin() {
		List<UserEntity> expectedUsers = new ArrayList<UserEntity>(); expectedUsers.add(userAdmin);		
		when(userRepository.findByRole(getRole(userAdmin))).thenReturn(expectedUsers);
		List<UserEntity> actualUsers = userService.findByRole(UserType.ADMIN);
		assertEquals("Failed because users were not found by role.", expectedUsers, actualUsers);
		
	}
	
	@Test
	public void getByRoleTestShouldReturnUserWithRoleHikingClub() {
		userAdmin.setRole(roleHikingClub);
		List<UserEntity> expectedUsers = new ArrayList<UserEntity>(); expectedUsers.add(userAdmin);		
		when(userRepository.findByRole(getRole(userAdmin))).thenReturn(expectedUsers);
		List<UserEntity> actualUsers = userService.findByRole(UserType.HIKINGCLUB);
		assertEquals("Failed because users were not found by role.", expectedUsers, actualUsers);
		
	}
	
	@Test
	public void getByRoleTestShouldReturnUserWithRoleHiker() {
		userAdmin.setRole(roleHiker);
		List<UserEntity> expectedUsers = new ArrayList<UserEntity>(); expectedUsers.add(userAdmin);		
		when(userRepository.findByRole(getRole(userAdmin))).thenReturn(expectedUsers);
		List<UserEntity> actualUsers = userService.findByRole(UserType.HIKER);
		assertEquals("Failed because users were not found by role.", expectedUsers, actualUsers);
		
	}
	
	@Test
	public void deleteTestShouldReturnTrueIfDeleted() {
		when(userRepository.existsById(2)).thenReturn(true);
		Boolean deleted = userService.delete(2);
		assertTrue("Failed because user was not deleted.", deleted);
	}

	@Test
	public void deleteTestShouldReturnFalseIfNotDeleted() {
		when(!userRepository.existsById(4)).thenReturn(false);
		Boolean deleted = userService.delete(4);
		assertFalse("Failed because user was not deleted.", deleted);
	}
}
