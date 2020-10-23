package rwa.sara.hikevents.model.api;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rwa.sara.hikevents.model.entity.RoleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDTO {

	int id;
	@NotEmpty(message = "name cannot be null")
	String name;
	int year;
	String city;
	@NotEmpty(message = "email cannot be null")
	String email;
	RoleEntity role;
	
	public UserGetDTO() {
		
	}
	
	public UserGetDTO(int id, @NotEmpty(message = "name cannot be null") String name, int year, String city,
			@NotEmpty(message = "email cannot be null") String email, RoleEntity role) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.city = city;
		this.email = email;
		this.role = role;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}	
	
}
