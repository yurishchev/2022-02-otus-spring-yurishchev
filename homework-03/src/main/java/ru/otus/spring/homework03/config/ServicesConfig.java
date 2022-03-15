package ru.otus.spring.homework03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework03.dao.QuizDao;
import ru.otus.spring.homework03.dao.impl.QuizDaoCSV;
import ru.otus.spring.homework03.service.IOService;
import ru.otus.spring.homework03.service.Settings;
import ru.otus.spring.homework03.service.impl.IOServiceImpl;

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
        Settings settings = settings();
        return new QuizDaoCSV(settings.getFileName() + "_" + settings.getLocaleTag());
    }
}
