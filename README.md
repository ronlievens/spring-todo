# spring-todo

This small todo project is my playground to experiment with [Spring Boot](https://spring.io/projects/spring-boot)

# Setup - Mariadb

To create Mariadb database with user, run:

```sql
DROP DATABASE IF EXISTS todo_db;
CREATE DATABASE todo_db CHARACTER SET = 'utf8' COLLATE = 'utf8_general_ci';

DROP USER IF EXISTS 'todo_db'@'%';
DROP USER IF EXISTS 'todo_db'@'localhost';
CREATE USER 'todo_usr'@'localhost' IDENTIFIED BY 'todo_pwd';
CREATE USER 'todo_usr'@'%' IDENTIFIED BY 'todo_pwd';

GRANT ALL ON todo_db.* TO 'todo_usr'@'localhost';
GRANT ALL ON todo_db.* TO 'todo_usr'@'%';

FLUSH PRIVILEGES;
```

# Q & A

- why remove tomcat and use undertow
  Undertow calculate the number of connections based on the cpu and memory, so we get auto-tuning during start.

# Dashboards

- Open [OpenAPI Definition](http://127.0.0.1:8080/api/v3/swagger-ui.html)
- Open [Spring Actuator](http://127.0.0.1:8080/api/actuator)
- Open [Spring Actuator Prometheus scraper page](http://127.0.0.1:8080/api/actuator/prometheus)

# Generate RSA key set

In the directory assets run the command:

```
openssl genpkey -out private.pem -algorithm RSA -pkeyopt rsa_keygen_bits:4096
openssl rsa -in private.pem -outform PEM -pubout -out public.pem
```
