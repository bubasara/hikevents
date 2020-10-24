package rwa.sara.hikevents.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "events")
public class EventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@ApiModelProperty(notes = "The database generated event ID.")
	private int id;
	
	@Column(name = "title", columnDefinition = "varchar(250)", nullable = false, unique = true)
	@ApiModelProperty(notes = "Title of the event.")
	private String title;
	
	@Column(name = "description", columnDefinition = "TEXT", nullable = true, unique = false)
	@ApiModelProperty(notes = "Description of the event.")
	private String description;
	
	@Column(name = "location", columnDefinition = "varchar(250)", nullable = false, unique = false)
	@ApiModelProperty(notes = "Location of the event.")
	private String location;
	
	@Column(name = "start_date", columnDefinition = "DATE", nullable = false, unique = false)
	@ApiModelProperty(notes = "The event starts on this date.")
	private LocalDate startDate;
	
	@Column(name = "end_date", columnDefinition = "DATE", nullable = false, unique = false)
	@ApiModelProperty(notes = "The event ends on this date.")
	private LocalDate endDate;
	
	@Column(name = "price", columnDefinition = "int", nullable = true, unique = false)
	@ApiModelProperty(notes = "Participation price for the event.")
	private int price;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "host")
	@JsonIgnore
	@ApiModelProperty(notes = "Host of the event.")
	private UserEntity host;
	
	@OneToMany(mappedBy = "event")
	@JsonIgnore
	@ApiModelProperty(notes = "Hikers who registered for the event.")
	List<RegistrationEntity> eventsUsers;
	
	public EventEntity() {
	}

	public EventEntity(int id, String title, String description, String location, LocalDate startDate,
			LocalDate endDate, int price, UserEntity host, List<RegistrationEntity> eventsUsers) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.host = host;
		this.eventsUsers = eventsUsers;
	}

	public UserEntity getHost() {
		return host;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
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

	public List<RegistrationEntity> getEventsUsers() {
		return eventsUsers;
	}

	public void setEventsUsers(List<RegistrationEntity> eventsUsers) {
		this.eventsUsers = eventsUsers;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHost(UserEntity host) {
		this.host = host;
	}

}
