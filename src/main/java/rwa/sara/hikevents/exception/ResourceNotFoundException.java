package rwa.sara.hikevents.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = -1429606651576742807L;
	private ResourceType resourceType;

	public ResourceNotFoundException(ResourceType resourceType, String message) {
		super(message);
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
}