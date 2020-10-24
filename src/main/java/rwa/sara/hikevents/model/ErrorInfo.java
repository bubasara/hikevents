package rwa.sara.hikevents.model;

import lombok.Value;

@Value
public class ErrorInfo {

	public ErrorInfo(ErrorCode userNotFound, String message) {
		this.errorCode = userNotFound;
		this.message = message;
	}
	public enum ErrorCode {
		RESOURCE_NOT_FOUND,
		DUPLICATE_RESOURCE_FOUND,
		EMAIL_NOT_FOUND
	};
	
	ErrorCode errorCode;
	String message;
}
