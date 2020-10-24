package rwa.sara.hikevents.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "registrations")
public class RegistrationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@ApiModelProperty(notes = "The database generated registration ID.")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	@JsonIgnore
	@ApiModelProperty(notes = "Hiker who registered for the event.")
	UserEntity user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id", insertable = false, updatable = false)
	@JsonIgnore
	@ApiModelProperty(notes = "Event which hiker registered for.")
	EventEntity event;
	
	public RegistrationEntity() {
	}

	public RegistrationEntity(int id, EventEntity event, UserEntity user) {
		this.id = id;
		this.event = event;
		this.user = user;
	}

	public RegistrationEntity(EventEntity event, UserEntity user) {
		this.event = event;
		this.user = user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}

	public void setUser(UserEntity userEntity) {
		this.user = userEntity;		
	}
	
	public UserEntity getUser() {
		return this.user;
	}
	
	public EventEntity getEvent() {
		return this.event;
	}

	public int getId() {
		return id;
	}
}
