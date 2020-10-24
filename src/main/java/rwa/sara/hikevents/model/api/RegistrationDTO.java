package rwa.sara.hikevents.model.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.UserEntity;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RegistrationDTO {

	int id;
	UserEntity user;
	EventEntity event;
		
	@SuppressWarnings("unused")
	private RegistrationDTO() {
	}

	public RegistrationDTO(int id, UserEntity user, EventEntity event) {
		this.id = id;
		this.user = user;
		this.event = event;
	}

	public void setUser(UserEntity loggedInUser) {
		this.user = loggedInUser;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventEntity getEvent() {
		return event;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}

	public UserEntity getUser() {
		return user;
	}
		
}
