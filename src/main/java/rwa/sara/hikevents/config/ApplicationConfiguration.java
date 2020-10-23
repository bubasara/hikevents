package rwa.sara.hikevents.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import rwa.sara.hikevents.security.jwt.JwtAuthenticationEntryPoint;
import rwa.sara.hikevents.security.jwt.JwtAuthenticationTokenFilter;
import rwa.sara.hikevents.security.jwt.JwtTokenManager;
import rwa.sara.hikevents.security.service.UserDetailServiceImpl;

@Configuration
@EnableJpaRepositories(basePackages = {
		"rwa.sara.hikevents.repository"
}
)
public class ApplicationConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public UserDetailServiceImpl userDetailServiceImpl() {
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

	@Bean
	public JwtTokenManager jwtTokenManager() {
		return new JwtTokenManager();
	}
}
