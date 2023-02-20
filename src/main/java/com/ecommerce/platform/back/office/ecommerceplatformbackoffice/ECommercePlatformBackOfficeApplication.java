package com.ecommerce.platform.back.office.ecommerceplatformbackoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ECommercePlatformBackOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommercePlatformBackOfficeApplication.class, args);
    }

}
