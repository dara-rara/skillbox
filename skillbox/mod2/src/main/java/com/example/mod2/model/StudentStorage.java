package com.example.mod2.model;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentStorage {
    private final Map<Integer, Student> students = new HashMap<>();

    public void save(Student student) {
        students.put(student.id(), student);
    }

    public Student create(String firstName, String lastName, String age) {
        return new Student(generateId(), firstName, lastName, Integer.parseInt(age));
    }

    public String get(Integer id) {
        return students.containsKey(id) ?
                students.get(id).toString() :
                "Student with id=" + id + " not found";
    }

    public Collection<Student> getAll() {
        return students.values();
    }

    public void delete(Integer id) {
        students.remove(id);
    }

    public String deleteAll() {
        students.clear();
        return "The list of students has been removed";
    }

    private Integer generateId() {
        var id = 0;
        while (students.keySet().contains(id)) {
            id++;
        }
        return id;
    }
}
