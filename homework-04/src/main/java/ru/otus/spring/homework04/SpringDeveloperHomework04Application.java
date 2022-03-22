package ru.otus.spring.homework04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.homework04.service.QuizRunner;

@SpringBootApplication
public class SpringDeveloperHomework04Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringDeveloperHomework04Application.class, args);

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.run();
    }

}
