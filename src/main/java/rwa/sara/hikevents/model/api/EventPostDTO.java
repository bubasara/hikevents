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
public class EventPostDTO {

	int id;
	@NotEmpty(message = "title cannot be null")
	String title;
	String description;
	@NotEmpty(message = "location cannot be null")
	String location;
	@NotEmpty(message = "startDate cannot be null")
	LocalDate startDate;
	@NotEmpty(message = "endDate cannot be null")
	LocalDate endDate;
	int price;
	
	public EventPostDTO() {
	}
	public EventPostDTO(int id, @NotEmpty(message = "title cannot be null") String title, String description,
			@NotEmpty(message = "location cannot be null") String location,
			@NotEmpty(message = "startDate cannot be null") LocalDate startDate,
			@NotEmpty(message = "endDate cannot be null") LocalDate endDate, int price) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
