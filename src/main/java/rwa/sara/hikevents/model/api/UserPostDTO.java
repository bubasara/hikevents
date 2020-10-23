package rwa.sara.hikevents.model.api;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rwa.sara.hikevents.model.entity.RoleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDTO extends UserGetDTO {

	@NotEmpty(message = "password cannot be null")
	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public UserPostDTO() {
		
	}

	public UserPostDTO(int id, @NotEmpty(message = "name cannot be null") String name, int year, String city,
			@NotEmpty(message = "email cannot be null") String email, RoleEntity role, String password) {
		super(id, name, year, city, email, role);
		this.password = password;
	}

}
