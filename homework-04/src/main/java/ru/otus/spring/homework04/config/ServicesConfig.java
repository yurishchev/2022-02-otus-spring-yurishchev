package ru.otus.spring.homework04.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework04.service.IOService;
import ru.otus.spring.homework04.service.impl.IOServiceImpl;

@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

}
