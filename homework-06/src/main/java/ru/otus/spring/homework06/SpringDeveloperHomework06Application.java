package ru.otus.spring.homework06;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class SpringDeveloperHomework06Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringDeveloperHomework06Application.class, args);
        Console.main(args);
    }

}
