package rwa.sara.hikevents.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rwa.sara.hikevents.exception.ResourceNotFoundException;
import rwa.sara.hikevents.model.api.UserGetDTO;
import rwa.sara.hikevents.model.api.UserPostDTO;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.security.service.LoggedInUser;
import rwa.sara.hikevents.service.impl.UserService;

@RestController
@RequestMapping("/users")
@Api(value="hikevents", description = "Available operations for users.")
public class UserController {

	private UserService userService;
	private ModelMapper modelMapper;
	
	@Autowired
	public UserController(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@ApiOperation(value="Get all users.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - all users returned!"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no users found."),
	})
	@GetMapping
	public HttpEntity<List<UserGetDTO>> getAll(){ //without passwords
		List<UserGetDTO> users = new ArrayList<>();
		for(UserEntity userEntity : userService.getAll()) {
			users.add(modelMapper.map(userEntity, UserGetDTO.class));
		}
		return ResponseEntity.ok(users);
	}
	
	@ApiOperation(value="Get the user with id.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - user with that id returned!"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no user with that id found."),
	})
	@GetMapping("/{id}")
	public HttpEntity<UserGetDTO> get(@PathVariable("id") int userId) throws ResourceNotFoundException{
		Optional<UserEntity> userOptional = userService.get(userId);
		if(userOptional.isPresent()) {
			return ResponseEntity.ok(modelMapper.map(userOptional, UserGetDTO.class));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ApiOperation(value="Delete the user with id.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - user deleted!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKER/HIKINGCLUB can delete their own accounts / ADMIN can ban users."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no user with that id found."),
	})
	@DeleteMapping("/{id}")
	//only user itself can delete its account or an admin can ban the user
	@PreAuthorize("hasRole('ADMIN') OR #id == #loggedInUser.id")
	public HttpEntity<Object> delete(@PathVariable("id") int userId, @AuthenticationPrincipal LoggedInUser loggedInUser) throws ResourceNotFoundException{
		if(userService.delete(userId)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ApiOperation(value="Update the user with id.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - user account updated!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKER/HIKINGCLUB can update their own accounts."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no user with that id found."),
	})
	@PutMapping("/{id}")
	//only user itself can update its info
	@PreAuthorize("#id == #loggedInUser.id")
	public HttpEntity<UserGetDTO> update(@RequestBody UserPostDTO userDto, @AuthenticationPrincipal LoggedInUser loggedInUser) throws ResourceNotFoundException{
		Optional<UserEntity> userOptional = userService.update(modelMapper.map(userDto, UserEntity.class));
		if(userOptional.isPresent()) {
			return ResponseEntity.ok(modelMapper.map(userDto, UserGetDTO.class));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
