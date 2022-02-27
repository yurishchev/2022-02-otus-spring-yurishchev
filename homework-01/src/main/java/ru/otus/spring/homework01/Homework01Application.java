package ru.otus.spring.homework01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.homework01.domain.Quiz;
import ru.otus.spring.homework01.service.QuizService;

public class Homework01Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuizService quizService = context.getBean(QuizService.class);
        Quiz quiz = quizService.loadQuiz();

        System.out.println(quiz);
    }

}
