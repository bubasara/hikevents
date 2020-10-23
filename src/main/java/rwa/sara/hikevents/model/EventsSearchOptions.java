package rwa.sara.hikevents.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
@Getter
@Setter
public class EventsSearchOptions {

	String titleFilter;
	String locationFilter;
	
	public EventsSearchOptions(String titleFilter, String locationFilter) {
		this.titleFilter = titleFilter;
		this.locationFilter = locationFilter;
	}

	private EventsSearchOptions() {
	}

	public String getTitleFilter() {
		return titleFilter;
	}

	public String getLocationFilter() {
		return locationFilter;
	}

	public static EventsSearchOptions builder() {
		return new EventsSearchOptions();
	}

	public EventsSearchOptions setTitleFilter(String titleFilter) {
		this.titleFilter = titleFilter;
		return this;
	}
	
	public EventsSearchOptions setLocationFilter(String locationFilter) {
		this.locationFilter = locationFilter;
		return this;
	}

	public EventsSearchOptions build() {
		return new EventsSearchOptions(titleFilter, locationFilter);
	}
}
