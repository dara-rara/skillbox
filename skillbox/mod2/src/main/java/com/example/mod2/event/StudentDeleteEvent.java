package com.example.mod2.event;

import org.springframework.context.ApplicationEvent;

public class StudentDeleteEvent extends ApplicationEvent {
    public StudentDeleteEvent(Integer id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Delete student --> " + this.getSource();
    }
}
