package rwa.sara.hikevents.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rwa.sara.hikevents.model.api.RoleDTO;
import rwa.sara.hikevents.model.entity.RoleEntity;
import rwa.sara.hikevents.service.impl.RoleService;

@RestController
@RequestMapping("/roles")
@Api(value="hikevents", description = "Available operations for roles.")
public class RoleController {
	
	private final RoleService roleService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public RoleController(RoleService roleService, ModelMapper modelMapper) {
		this.roleService = roleService;
		this.modelMapper = modelMapper;
	}
	
	@ApiOperation(value="Create new role", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - role created!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as ADMIN can create new roles."),
			@ApiResponse(code = 403, message = "Forbidden")
	})
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public HttpEntity<RoleEntity> create(@RequestBody RoleDTO roleDto) {
		Optional<RoleEntity> roleOptional = roleService.insert(modelMapper.map(roleDto, RoleEntity.class));
		if(roleOptional.isPresent()) {
			return ResponseEntity.ok(roleOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@ApiOperation(value="Get all roles.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - roles returned!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as ADMIN can get all roles."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no roles found."),
	})
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public HttpEntity<List<RoleEntity>> getAll() throws ResponseStatusException {
		try {
			return ResponseEntity.ok(roleService.getAll());
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No roles found.", e);
		}
	}
	
	@ApiOperation(value="Get the role with certain name.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - role returned!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as ADMIN can get roles."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no role with that name found."),
	})
	@GetMapping("/{name}")
	@PreAuthorize("hasRole('ADMIN')")
	public HttpEntity<RoleEntity> get(@PathVariable("name") String roleName){
		Optional<RoleEntity> roleOptional = roleService.findByName(roleName);
		if(roleOptional.isPresent()) {
			return ResponseEntity.ok(roleOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ApiOperation(value="Delete the role with id", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - role deleted!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as ADMIN can delete roles."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no role with that name found."),
	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public HttpEntity<Object> delete(@PathVariable("id") int roleId){
		if(roleService.deleteById(roleId)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ApiOperation(value="Update the role with certain name.", response = Iterable.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Success - role updated!"),
			@ApiResponse(code = 401, message = "Not authorized - only users registered as ADMIN can update roles."),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not found - no role with that id found or has users."),
	})
	@PutMapping("/{name}")
	@PreAuthorize("hasRole('ADMIN')")
	public HttpEntity<RoleEntity> update(@RequestBody RoleDTO roleDto) {
		Optional<RoleEntity> roleOptional = roleService.update(modelMapper.map(roleDto, RoleEntity.class));
		if(roleOptional.isPresent()) {
			return ResponseEntity.ok(roleOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
