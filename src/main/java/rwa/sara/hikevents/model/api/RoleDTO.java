package rwa.sara.hikevents.model.api;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RoleDTO {
	
	int id;
	@NotEmpty(message = "name cannot be null")
	String name;
	
	public RoleDTO() {
	}
	
	public RoleDTO(int id, @NotEmpty(message = "name cannot be null") String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
