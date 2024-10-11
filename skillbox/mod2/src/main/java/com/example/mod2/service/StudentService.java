package com.example.mod2.service;

import com.example.mod2.event.StudentAddEvent;
import com.example.mod2.event.StudentDeleteEvent;
import com.example.mod2.model.StudentStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class StudentService implements ApplicationEventPublisherAware {

    @Value("${spring.profiles.active}")
    private String profile;
    private ApplicationEventPublisher applicationEventPublisher;
    private final StudentStorage studentStorage;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    //Example: add --f first name --l last name --a age
    @ShellMethod(key = "add")
    public void addStudent(
            @ShellOption("f") String firstName,
            @ShellOption("l") String lastName,
            @ShellOption("a") String age
    ) {
        var student = studentStorage.create(firstName, lastName, age);
        studentStorage.save(student);
        applicationEventPublisher.publishEvent(new StudentAddEvent(student));
    }

    //Example: delete --id student id
    @ShellMethod(key = "delete")
    public void deleteStudent(Integer id) {
        studentStorage.delete(id);
        applicationEventPublisher.publishEvent(new StudentDeleteEvent(id));
    }

    @ShellMethod(key = "d all")
    public void deleteAll() {
        log.debug(studentStorage.deleteAll());
    }

    //Example: get --id student id
    @ShellMethod(key = "get")
    public String getStudent(Integer id) {
        return studentStorage.get(id);
    }

    @ShellMethod(key = "g all")
    public String getAllStudents() {
        var sb = new StringBuilder();
        studentStorage.getAll().forEach(student -> sb.append(student.toString()).append("\n"));
        return sb.isEmpty() ? "The list of students is empty" : sb.toString();
    }
}
