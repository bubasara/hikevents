package rwa.sara.hikevents.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rwa.sara.hikevents.exception.ResourceNotFoundException;
import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.RegistrationEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.security.service.LoggedInUser;
import rwa.sara.hikevents.service.impl.EventService;
import rwa.sara.hikevents.service.impl.RegistrationService;

@RestController
@RequestMapping("/registrations")
@Api(value="hikevents", description = "Available operations for registrations.")
public class RegistrationController {

	private final RegistrationService registrationService;
	private final ModelMapper modelMapper;
	private final EventService eventService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService, ModelMapper modelMapper, EventService eventServicer) {
		this.registrationService = registrationService;
		this.modelMapper = modelMapper;
		this.eventService = eventServicer;
	}
	
	@ApiOperation(value="Create new registration.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - registration created!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKER can register for events."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - desired event not found."),
	})
	@PostMapping
	//only hiker itself can register for an event
	@PreAuthorize("hasRole('HIKER')")
	public HttpEntity<RegistrationEntity> create(/*@RequestBody RegistrationDTO registrationDto,*/
			@RequestBody int eventId, @AuthenticationPrincipal LoggedInUser loggedInUser){
		EventEntity event = eventService.get(eventId).get();
		UserEntity user = modelMapper.map(loggedInUser, UserEntity.class);
		Optional<RegistrationEntity> registrationOptional = registrationService.insert(event, user);
		if(registrationOptional.isPresent()) {
			return ResponseEntity.ok(registrationOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@ApiOperation(value="Get all registrations.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - all registrations returned!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as ADMIN can get all registrations."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no registrations found."),
	})
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public HttpEntity<List<RegistrationEntity>> getAll() throws ResponseStatusException {
		try {
			return ResponseEntity.ok(registrationService.getAll());
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No registrations found.", e);
		}
	}
	
	@ApiOperation(value="Get the registration with id", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - registration returned!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKER can get their registrations for events / HIKING CLUB can get registrations for events they host."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - desired registration not found."),
	})
	@GetMapping("/{id}")
	//only hikers can get their own registrations
	//and hiking clubs can get registrations for their own events
	@PreAuthorize("hasRole('HIKINGCLUB') or hasRole('HIKER')")
	public HttpEntity<RegistrationEntity> get(@PathVariable("id") int registrationId, @AuthenticationPrincipal LoggedInUser loggedInUser) throws ResourceNotFoundException{
		RegistrationEntity currentRegistration = registrationService.get(registrationId).get();
		if(currentRegistration.getUser().equals(modelMapper.map(loggedInUser, UserEntity.class))
				|| currentRegistration.getEvent().getHost().equals(modelMapper.map(loggedInUser, UserEntity.class))) {
			Optional<RegistrationEntity> registrationOptional = registrationService.get(registrationId);
			if(registrationOptional.isPresent()) {
				return ResponseEntity.ok(registrationOptional.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@ApiOperation(value="Delete the registration with id", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - registration deleted!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKER can get delete their registrations."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - desired registration not found."),
	})
	@DeleteMapping("/{id}")
	//only hikers can delete their own registrations
	@PreAuthorize("hasRole('HIKER')")
	public HttpEntity<Object> delete(@PathVariable("id") int registrationId, @AuthenticationPrincipal LoggedInUser loggedInUser) throws ResourceNotFoundException{
		RegistrationEntity currentRegistration = registrationService.get(registrationId).get();
		if(currentRegistration.getUser().equals(modelMapper.map(loggedInUser, UserEntity.class))){
			if(registrationService.delete(registrationId)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@ApiOperation(value="Get all participants for an event with id", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - all participants for an event returned!"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - desired event not found."),
	})
	@GetMapping("/participants/{eventId}")
	public HttpEntity<List<RegistrationEntity>> getParticipants(@PathVariable("eventId") int eventId){
		Optional<List<RegistrationEntity>> registrationOptional = registrationService.findByEventId(eventId);
		if(registrationOptional.isPresent()) {
			return ResponseEntity.ok(registrationOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@ApiOperation(value="Get all events which user with id registered for", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - events which user with id registered for returned!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKER can get events which they registered for."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no events or user found."),
	})
	@GetMapping("/usersevents/{userId}")
	//only hiker itself can see their own events
	@PreAuthorize("hasRole('HIKER') AND #userId == #loggedInUser.id")
	public HttpEntity<List<RegistrationEntity>> getUsersEvents(@PathVariable("userId") int userId,
			@AuthenticationPrincipal LoggedInUser loggedInUser){
		Optional<List<RegistrationEntity>> registrationOptional = registrationService.findByUserId(userId);
		if(registrationOptional.isPresent()) {
			return ResponseEntity.ok(registrationOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
}
