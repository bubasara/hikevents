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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rwa.sara.hikevents.model.EventsSearchOptions;
import rwa.sara.hikevents.model.api.EventGetDTO;
import rwa.sara.hikevents.model.api.EventPostDTO;
import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.security.service.LoggedInUser;
import rwa.sara.hikevents.service.impl.EventService;

@RestController
@RequestMapping("/events")
@Api(value="hikevents", description = "Available operations for events.")
public class EventController {

	private final EventService eventService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public EventController(EventService eventService, ModelMapper modelMapper) {
		this.eventService = eventService;
		this.modelMapper = modelMapper;
	}
	
	@ApiOperation(value="Create an event.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - event created!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKINGCLUB can create an event."),
			@ApiResponse(code = 403, message = "Forbidden")
	})
	@PostMapping
	//only hiking clubs can create new events
	@PreAuthorize("hasRole('HIKINGCLUB')")
	public HttpEntity<EventGetDTO> create(@RequestBody EventPostDTO eventDto,
											@AuthenticationPrincipal LoggedInUser loggedInUser) {
		//eventDto.setHost(modelMapper.map(loggedInUser, UserEntity.class));
		EventEntity event = modelMapper.map(eventDto, EventEntity.class);
		event.setHost(modelMapper.map(loggedInUser, UserEntity.class));
		Optional<EventEntity> eventOptional = eventService.insert(event);
		if(eventOptional.isPresent()) {
			return ResponseEntity.ok(modelMapper.map(eventOptional.get(), EventGetDTO.class));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@ApiOperation(value="Get all events.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - all events returned!"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no events found."),
	})
	@GetMapping
	public HttpEntity<List<EventGetDTO>> getAll() throws ResponseStatusException {
		try {
			List<EventGetDTO> eventsGetDTO = new ArrayList<>();
			for (EventEntity event : eventService.getAll()) {
				eventsGetDTO.add(modelMapper.map(event, EventGetDTO.class));
			}
			return ResponseEntity.ok(eventsGetDTO);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No events found.", e);
		}
	}
	
	@ApiOperation(value="Get the event with id.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - event returned!"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no event with that id found."),
	})
	@GetMapping("/{id}")
	public HttpEntity<EventEntity> get(@PathVariable("id") int eventId) {
		Optional<EventEntity> eventOptional = eventService.get(eventId);
		if (eventOptional.isPresent()) {
			return ResponseEntity.ok(eventOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ApiOperation(value="Search events.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - filtered events returned!"),
			@ApiResponse(code = 401, message = "Not authorized"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no filtered events found."),
	})
	@GetMapping("/searchEvents")
	public List<EventEntity> searchEvents(@RequestParam(name = "title", required = false) String titleFilter,
			@RequestParam(name = "location", required = false) String locationFilter){
		final EventsSearchOptions eventSearchOptions = EventsSearchOptions.builder().setTitleFilter(titleFilter).setLocationFilter(locationFilter).build();
		
		return eventService.searchEvents(eventSearchOptions);
	}
	
	@ApiOperation(value="Delete the event with id", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - event deleted!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKINGCLUB can delete their own events."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no event with that id found."),
	})
	@DeleteMapping("/{id}")
	//only hiking clubs can delete their own events
	@PreAuthorize("hasRole('HIKINGCLUB')")
	public HttpEntity<Object> delete(@PathVariable("id") int eventId, @AuthenticationPrincipal LoggedInUser loggedInUser) {
		EventEntity currentEvent = eventService.get(eventId).get();
		UserEntity checkIfHost = modelMapper.map(loggedInUser, UserEntity.class);
		if(checkIfHost.getId() != currentEvent.getHost().getId()) {
			if(eventService.delete(eventId)) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@ApiOperation(value="Update the event with id", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - event updated!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as HIKINGCLUB can update their own events."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no event with that id found."),
	})
	@PutMapping("/{id}")
	//only hiking clubs can update their own events
	@PreAuthorize("hasRole('HIKINGCLUB')")
	public HttpEntity<EventEntity> update(@RequestBody EventGetDTO eventDto, @AuthenticationPrincipal LoggedInUser loggedInUser) {
		if (loggedInUser.getId() != eventDto.getHost().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			Optional<EventEntity> eventOptional = eventService.update(modelMapper.map(eventDto, EventEntity.class));
			if(eventOptional.isPresent()) {
				return ResponseEntity.ok(eventOptional.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
	}
}