package rwa.sara.hikevents.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@ApiModelProperty(notes = "The database generated user ID.")
	private int id;

	@Column(name = "name", columnDefinition = "varchar(35)", nullable = false, unique = false)
	@ApiModelProperty(notes = "Hiker's / hiking club's name.")
	private String name;
	
	@Column(name = "year", columnDefinition = "int", nullable = true, unique = false)
	@ApiModelProperty(notes = "Hiker's year of birth / hiking club's founding year.")
	private int year;
	
	@Column(name = "city", columnDefinition = "varchar(50)", nullable = true, unique = false)
	@ApiModelProperty(notes = "Hiker's / hiking club's city.")
	private String city;
	
	@Column(name = "email", columnDefinition = "varchar(50)", nullable = false, unique = true)
	@Email(message = "Please provide a valid e-mail address.")
	@ApiModelProperty(notes = "Hiker's / hiking club's email.")
	private String email;
	
	@Column(name = "password", columnDefinition = "varchar(250)", nullable = false, unique = false)
	@ApiModelProperty(notes = "User's password.")
	private String password;
	
	@ManyToOne
	@ApiModelProperty(notes = "User's role.")
	private RoleEntity role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	@ApiModelProperty(notes = "Hiker's registrations.")
	private List<RegistrationEntity> registrations;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	@ApiModelProperty(notes = "Hiking club's events.")
	List<RegistrationEntity> usersEvents;
	
	public UserEntity() {
	}

	public UserEntity(int id, String name, int year, String city,
			@Email(message = "Please provide a valid e-mail address.") String email, String password, RoleEntity role,
			List<RegistrationEntity> registrations, List<RegistrationEntity> usersEvents) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.city = city;
		this.email = email;
		this.password = password;
		this.role = role;
		this.registrations = registrations;
		this.usersEvents = usersEvents;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setPassword(String password) {
		this.password = password;		
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

	public List<RegistrationEntity> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<RegistrationEntity> registrations) {
		this.registrations = registrations;
	}

	public List<RegistrationEntity> getUsersEvents() {
		return usersEvents;
	}

	public void setUsersEvents(List<RegistrationEntity> usersEvents) {
		this.usersEvents = usersEvents;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
