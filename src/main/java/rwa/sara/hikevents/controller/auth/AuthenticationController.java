package rwa.sara.hikevents.controller.auth;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rwa.sara.hikevents.model.LoginCredentials;
import rwa.sara.hikevents.model.api.UserGetDTO;
import rwa.sara.hikevents.model.api.UserPostDTO;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.security.JwtResponse;
import rwa.sara.hikevents.security.jwt.JwtTokenManager;
import rwa.sara.hikevents.service.impl.RoleService;
import rwa.sara.hikevents.service.impl.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@Api(value="hikevents", description = "Available operations for authentication.")
public class AuthenticationController {

	
	AuthenticationManager authenticationManager;
	UserService userService;
	RoleService roleService;
	PasswordEncoder encoder;
	JwtTokenManager jwtTokenManager;
	private ModelMapper modelMapper;

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
	
	@Autowired
	public void setJwtTokenManager(JwtTokenManager jwtTokenManager) {
		this.jwtTokenManager = jwtTokenManager;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@ApiOperation(value="Authenticate user.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - user authenticated!"),
			@ApiResponse(code = 401, message = "Not authorized - no authentication found."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no user or authentication found."),
	})
	@PostMapping
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginCredentials loginData) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenManager.buildJwtToken(authentication);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}
	
	@ApiOperation(value="Create a new user.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - user created!"),
			@ApiResponse(code = 401, message = "Not authorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found"),
	})
	@PostMapping("/signup")
	public ResponseEntity<UserGetDTO> registerUser(@Valid @RequestBody UserPostDTO user) {
		UserEntity newUser = modelMapper.map(user, UserEntity.class);
		newUser.setPassword(encoder.encode(user.getPassword()));
		Optional<UserEntity> userInserted = userService.insert(newUser);
		if (userInserted.isPresent())
			return ResponseEntity.ok(modelMapper.map(userInserted.get(), UserGetDTO.class));
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
