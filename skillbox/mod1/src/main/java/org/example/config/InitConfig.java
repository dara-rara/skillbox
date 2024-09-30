package org.example.config;

import org.example.component.ContactStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitConfig {

    @Value("${path}")
    private String initPath;

    @Autowired
    private ContactStorage storage;

    @PostConstruct
    public void initContactStorage(){
        List<String> contactsValue;

        try {
            contactsValue = Files.readAllLines(Path.of(initPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String item: contactsValue) {
            try {
                storage.addContact(item);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
