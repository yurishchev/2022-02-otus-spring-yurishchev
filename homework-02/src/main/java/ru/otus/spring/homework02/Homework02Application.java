package ru.otus.spring.homework02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.homework02.service.QuizRunner;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Homework02Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Homework02Application.class);

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.run();
    }

}
