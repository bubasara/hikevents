package rwa.sara.hikevents.model.api;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EventGetDTO extends EventPostDTO {

	private UserGetDTO host;

	public EventGetDTO() {
		super();
	}

	public EventGetDTO(int id, @NotEmpty(message = "title cannot be null") String title, String description,
			@NotEmpty(message = "location cannot be null") String location,
			@NotEmpty(message = "startDate cannot be null") LocalDate startDate,
			@NotEmpty(message = "endDate cannot be null") LocalDate endDate, int price, UserGetDTO host) {
		super(id, title, description, location, startDate, endDate, price);
		this.host = host;
	}

	public UserGetDTO getHost() {
		return host;
	}

	public void setHost(UserGetDTO host) {
		this.host = host;
	}
	
}
