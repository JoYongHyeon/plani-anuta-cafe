package io.plani.cafe.planicafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PlaniCafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaniCafeApplication.class, args);
    }

}
