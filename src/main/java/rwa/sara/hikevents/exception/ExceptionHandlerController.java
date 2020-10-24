package rwa.sara.hikevents.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import rwa.sara.hikevents.model.ErrorInfo;
import rwa.sara.hikevents.model.ErrorInfo.ErrorCode;


@ControllerAdvice
@ResponseBody
public class ExceptionHandlerController {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorInfo handlePersistenceException(ResourceNotFoundException e) {
		return new ErrorInfo(ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorInfo handlePersistenceException(DuplicateResourceException e) {
		return new ErrorInfo(ErrorCode.DUPLICATE_RESOURCE_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorInfo handlePersistenceException(EmailNotFoundException e) {
		return new ErrorInfo(ErrorCode.EMAIL_NOT_FOUND, e.getMessage());
	}
}
