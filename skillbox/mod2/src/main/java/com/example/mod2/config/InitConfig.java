package com.example.mod2.config;

import com.example.mod2.model.StudentStorage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Configuration
@PropertySource("classpath:application-init.yaml")
@Profile("init")
public class InitConfig {

    @Value("${app.path}")
    private String initPath;

    @Autowired
    private StudentStorage studentStorage;

    @PostConstruct
    public void initContactStorage(){
        try {
            Files.readAllLines(Paths.get(initPath), Charset.defaultCharset())
                    .forEach(str -> {
                        var data = str.split(";");
                        studentStorage.save(studentStorage.create(data[0], data[1], data[2]));
                    });
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
