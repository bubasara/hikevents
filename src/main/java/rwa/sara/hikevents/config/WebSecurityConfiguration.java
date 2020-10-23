package rwa.sara.hikevents.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import rwa.sara.hikevents.security.jwt.JwtAuthenticationEntryPoint;
import rwa.sara.hikevents.security.jwt.JwtAuthenticationTokenFilter;
import rwa.sara.hikevents.security.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	private UserDetailServiceImpl userDetailService;
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
    public JwtAuthenticationTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
	
	@Autowired
	public WebSecurityConfiguration(UserDetailServiceImpl userDetailService,
			JwtAuthenticationEntryPoint unauthorizedHandler) {
		this.userDetailService = userDetailService;
		this.unauthorizedHandler = unauthorizedHandler;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("*.html").permitAll()
                .antMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/configuration/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/auth**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/roles/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/events/**").hasRole("HIKINGCLUB")
                .antMatchers(HttpMethod.PUT, "/events/**").hasRole("HIKINGCLUB")
                .antMatchers(HttpMethod.DELETE, "/events/**").hasRole("HIKINGCLUB")
                .antMatchers(HttpMethod.GET, "/events/**").permitAll()
                .antMatchers(HttpMethod.POST, "/registrations/**").hasRole("HIKER")
                .antMatchers(HttpMethod.DELETE, "/registrations/**").hasRole("HIKER")
                .antMatchers(HttpMethod.GET, "/registrations/**").permitAll()
                .antMatchers(HttpMethod.GET, "/searchEvents/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
