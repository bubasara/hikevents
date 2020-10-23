package rwa.sara.hikevents.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends Exception {

	private static final long serialVersionUID = -2880878278811292356L;
	private final ResourceType resourceType;

	public DuplicateResourceException(ResourceType resourceType, String message) {
		super(message);
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
}