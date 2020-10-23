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
	
	public void setUser(UserEntity loggedInUser) {
		this.user = loggedInUser;		
	}
		
}
