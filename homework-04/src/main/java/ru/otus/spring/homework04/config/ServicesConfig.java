package ru.otus.spring.homework04.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework04.dao.QuizDao;
import ru.otus.spring.homework04.dao.impl.QuizDaoCSV;
import ru.otus.spring.homework04.service.IOService;
import ru.otus.spring.homework04.service.Localizer;
import ru.otus.spring.homework04.service.Settings;
import ru.otus.spring.homework04.service.impl.IOServiceImpl;
import ru.otus.spring.homework04.service.impl.LocalizerImpl;

@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    public QuizDao daoService(Settings settings) {
        return new QuizDaoCSV(settings.getFileName());
    }

    @Bean
    public Localizer localizer(MessageSource messageSource, Settings settings) {
        return new LocalizerImpl(messageSource, settings.getLocaleTag());
    }
}
