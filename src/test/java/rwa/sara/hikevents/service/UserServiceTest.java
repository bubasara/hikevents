package rwa.sara.hikevents.service;

import static org.junit.Assert.assertEquals;
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
	
	private UserEntity user;
	//private PasswordEncoder encoder;
	private RoleEntity role;
	
	@Before
	public void init() {
		role = new RoleEntity(1, "ADMIN");
		user = new UserEntity();
		user.setId(1);
		user.setName("Sara");
		user.setCity("Nis");
		user.setEmail("sara.stankovic1@pmf.edu.rs");
		user.setYear(1996);
		//user.setPassword(encoder.encode("password"));
		user.setPassword("password");
		user.setRole(role);
			
	}
	
	@Test
	public void insertTestShouldReturnInsertedUser() {
		when(userRepository.save(user)).thenReturn(user);
		Optional<UserEntity> actualUser = userService.insert(user);
		assertEquals("Failed because user was not created.", user, actualUser.get());
	}
	
	@Test
	public void getByIdTestShouldReturnUserWithId() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		Optional<UserEntity> actualUser = userService.get(user.getId());
		assertEquals("Failed because user was not found by given id.", user, actualUser.get());
	}
	
	@Test
	public void getAllTestShouldReturnAllUsers() {
		List<UserEntity> expectedUsers = new ArrayList<>();
		expectedUsers.add(user);
		when(userRepository.findAll()).thenReturn(expectedUsers);
		List<UserEntity> actualUsers = userService.getAll();
		assertEquals("Get all not working", expectedUsers, actualUsers);
	}
	
	@Test
	public void updateTestShouldReturnUpdatedUser() {
		user.setName("Sara St");
		when(userRepository.save(user)).thenReturn(user);
		Optional<UserEntity> actualUser = userService.update(user);
		assertEquals("Failed because user was not updated.", user, actualUser.get());
	}
	
	@Test
	public void existsByEmailTestShouldReturnTrueIfExists() {
		Boolean expected = true;
		when(userRepository.existsByEmail(user.getEmail())).thenReturn(expected);

		Boolean actual = userService.existsByEmail(user.getEmail());
		assertEquals("Failed because email was not found.", expected, actual);
	}
	
	@Test
	public void getByEmailTestShouldReturnUserWithEmail() {
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		Optional<UserEntity> actualUser = userService.findByEmail(user.getEmail());
		assertEquals("Failed because user was not found by email.", user, actualUser.get());
	}
	
	@Test
	public void getByNameTestShouldReturnUserWithName() {
		when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));
		Optional<UserEntity> actualUser = userService.findByName(user.getName());
		assertEquals("Failed because user was not found by name.", user, actualUser.get());
		
	}
	
	public UserType getRole(UserEntity userEntity) {
		UserType role;
		switch (user.getRole().getName()) {
		case "ADMIN":
			role = UserType.ADMIN;
			break;
		case "HIKINGCLUB":
			role = UserType.HIKINGCLUB;
		case "HIKER":
				role = UserType.HIKER;
				break;
		default:
			role = UserType.HIKER;
			break;
		}
		return role;
	}
	
	@Test
	public void getByRoleTestShouldReturnUserWithRole() {
		List<UserEntity> expectedUsers = new ArrayList<UserEntity>(); expectedUsers.add(user);		
		when(userRepository.findByRole(getRole(user))).thenReturn(expectedUsers);
		List<UserEntity> actualUsers = userService.findByRole(UserType.ADMIN);
		assertEquals("Failed because users were not found by role.", expectedUsers, actualUsers);
		
	}

}
