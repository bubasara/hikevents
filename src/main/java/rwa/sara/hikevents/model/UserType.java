package rwa.sara.hikevents.model;

public enum UserType {
	
	HIKER("ROLE_HIKER"), HIKINGCLUB("ROLE_HIKINGCLUB"), ADMIN("ROLE_ADMIN");

	private String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
