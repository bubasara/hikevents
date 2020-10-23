package rwa.sara.hikevents.model.api;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rwa.sara.hikevents.model.entity.UserEntity;


@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RoleDTO {
	
	int id;
	@NotEmpty(message = "name cannot be null")
	String name;
	List<UserEntity> users;
	
}
