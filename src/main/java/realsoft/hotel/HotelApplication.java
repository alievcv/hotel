package realsoft.hotel;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;



@SpringBootApplication
@EnableScheduling
@ConditionalOnProperty(value = "schedule.enabled",matchIfMissing = true)

public class HotelApplication  {

    public static void main(String[] args) {

        SpringApplication.run(HotelApplication.class, args);
    }

}
