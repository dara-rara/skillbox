package org.example;

import org.example.component.ContactStorage;
import org.example.config.DefaultConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DefaultConfig.class);
        var storage = context.getBean(ContactStorage.class);
        out.println("\nThe following commands are accepted for work:");
        boolean goahead = true;
        printMessage();
        while (goahead){
            var scn = new Scanner(System.in);
            var command = Integer.parseInt(scn.nextLine());
            switch (command) {
                case 1 -> {
                    try {
                        out.println(storage);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                }
                case 2 -> {
                    try {
                        storage.addContact(scn.nextLine());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        storage.deleteById(scn.nextLine());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        goahead = false;
                        storage.saveContact(storage.getAllContact());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                default -> out.println("Incorrect input");
            }
        }
    }

    public static void printMessage(){
        out.println("   1  -->   Output contact list");
        out.println("   2  -->   Entering a new contact");
        out.println("   3  -->   Delete user by email");
        out.println("   4  -->   Exit and save the current contact list");
    }

}