# Homework 6

Описание.
----------

Создать приложение хранящее информацию о книгах в библиотеке.
Предусмотреть таблицы авторов, книг и жанров. 
Предполагается отношение многие-к-одному (у книги один автор и жанр). Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров).
Добавить сущность "комментария к книге", реализовать CRUD для новой сущности.
Интерфейс выполняется на Spring Shell (CRUD книги и комментария обязателен, операции с авторами и жанрами - как будет удобно).

Цель.
----------

Полноценно работать с JPA + Hibernate для подключения к реляционным БД посредством ORM-фреймворка.

Результат: высокоуровневое приложение с JPA-маппингом сущностей.

### Prerequisites
 - JDK 11+
 - Maven 3.*

### Installation
 > mvn clean install

### Execution
 > java -jar target/homework-06-0.0.1-SNAPSHOT.jar