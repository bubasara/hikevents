package rwa.sara.hikevents.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import rwa.sara.hikevents.model.entity.UserEntity;

@Data
public class LoggedInUser implements UserDetails {

	private static final long serialVersionUID = -830177788637822855L;
	
	private int id;
	private String name;
	private String email;
	//@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public LoggedInUser(Integer id, String name, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static LoggedInUser build(UserEntity userEntity) {
		List<GrantedAuthority> authoritiesGranted = new ArrayList<>();
		authoritiesGranted.add(new SimpleGrantedAuthority(userEntity.getRole().getName()));

		return new LoggedInUser(userEntity.getId(), userEntity.getName(),
				userEntity.getEmail(), userEntity.getPassword(), authoritiesGranted);
	}
	
	public int getId() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
