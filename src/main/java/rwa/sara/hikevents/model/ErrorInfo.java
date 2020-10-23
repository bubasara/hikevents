package rwa.sara.hikevents.model;

import lombok.Value;

@Value
public class ErrorInfo {

	public enum ErrorCode {
		USER_NOT_FOUND,
		DATABASE_ERROR,
		GENERAL_ERROR
	};
	
	ErrorCode errorCode;
	String message;
}
