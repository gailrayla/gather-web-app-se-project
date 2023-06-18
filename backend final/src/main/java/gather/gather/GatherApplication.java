package gather.gather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GatherApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		ConfigurableApplicationContext run;
		run = SpringApplication.run(GatherApplication.class, args);
	}

}