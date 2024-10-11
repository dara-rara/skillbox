package com.example.mod2.event;
import com.example.mod2.model.Student;
import org.springframework.context.ApplicationEvent;

public class StudentAddEvent extends ApplicationEvent {
    public StudentAddEvent(Student student) {
        super(student);
    }
    @Override
    public String toString() {
        return "Add student --> " + this.getSource();
    }
}
