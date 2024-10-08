package com.ansysan.register_of_characteristics;

import org.springframework.boot.SpringApplication;

public class TestRegisterOfCharacteristicsApplication {

    public static void main(String[] args) {
        SpringApplication.from(RegisterOfCharacteristicsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
