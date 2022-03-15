package ru.otus.spring.homework03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.homework03.service.QuizRunner;

@SpringBootApplication
public class SpringDeveloperHomework03Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringDeveloperHomework03Application.class, args);

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.run();
    }

}
