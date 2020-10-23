package rwa.sara.hikevents.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.Getter;

@Getter
public class EmailNotFoundException extends UsernameNotFoundException {

	private static final long serialVersionUID = 8628220089606799250L;

	private String email;
	
	public EmailNotFoundException(String message, String email) {
		super(message);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
