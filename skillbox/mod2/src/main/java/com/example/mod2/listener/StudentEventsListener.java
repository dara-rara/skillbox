package com.example.mod2.listener;

import com.example.mod2.event.StudentAddEvent;
import com.example.mod2.event.StudentDeleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentEventsListener {

    @EventListener({StudentAddEvent.class, StudentDeleteEvent.class})
    void handleStudentEvents(ApplicationEvent event) {
        log.debug(event.toString());
    }
}
