package rwa.sara.hikevents.model.api;

import lombok.Data;

@Data
public class EventAddDTO {

	int id;

	public EventAddDTO(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
