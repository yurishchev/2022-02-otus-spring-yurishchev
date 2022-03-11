package ru.otus.spring.homework02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework02.dao.QuizDao;
import ru.otus.spring.homework02.dao.impl.QuizDaoCSV;
import ru.otus.spring.homework02.service.IOService;
import ru.otus.spring.homework02.service.impl.IOServiceImpl;
import ru.otus.spring.homework02.service.Settings;

@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    public Settings settings() {
        return new Settings();
    }

    @Bean
    public QuizDao daoService() {
        return new QuizDaoCSV(settings().getQuizFileName());
    }
}
