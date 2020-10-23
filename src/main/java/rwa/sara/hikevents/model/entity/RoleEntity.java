package rwa.sara.hikevents.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@ApiModelProperty(notes = "The database generated role ID.")
	private int id;

	@Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
	@ApiModelProperty(notes = "Name of the role.")
	private String name;

	@OneToMany//(mappedBy = "roles")
	//@JoinColumn(name = "user_id", insertable = true, updatable = true)
	//@Column(name="users")
	@JsonIgnore
	@ApiModelProperty(notes = "Users with this role.")
	private List<UserEntity> users;
	
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

	public List<UserEntity> getUsers(String roleName) {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
}
