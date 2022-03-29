package ru.otus.spring.homework05.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "library")
@Component
public class Settings {
}
