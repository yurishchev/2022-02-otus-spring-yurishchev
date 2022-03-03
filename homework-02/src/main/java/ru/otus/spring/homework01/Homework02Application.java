package ru.otus.spring.homework01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Homework02Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Homework02Application.class);

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        quizRunner.run();
    }

}
