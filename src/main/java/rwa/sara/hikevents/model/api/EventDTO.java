package rwa.sara.hikevents.model.api;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rwa.sara.hikevents.model.entity.UserEntity;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EventDTO {

	int id;
	@NotEmpty(message = "title cannot be null")
	String title;
	String description;
	@NotEmpty(message = "location cannot be null")
	String location;
	@NotEmpty(message = "startDate cannot be null")
	Date startDate;
	@NotEmpty(message = "endDate cannot be null")
	Date endDate;
	int price;
	UserEntity host;
	
}
