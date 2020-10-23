package rwa.sara.hikevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ComponentScan({"rwa.sara.hikevents.controller", "rwa.sara.hikevents.controller.auth",
//	"rwa.sara.hikevents.service", "rwa.sara.hikevents.service.impl", "rwa.sara.hikevents.security.service"})
public class HikeventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikeventsApplication.class, args);
	}

}
