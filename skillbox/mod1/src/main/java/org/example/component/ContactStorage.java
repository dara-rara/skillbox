package org.example.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;

@Component
public class ContactStorage {

    @Value("${path}")
    private String initPath;
    private final LinkedHashMap<String, Contact> contacts = new LinkedHashMap<>();

    public Collection<Contact> getAllContact() {
        return contacts.values();
    }

    public static Contact parseContact(String rawContact, String delimiter) throws Exception {
        if (rawContact.isEmpty() && rawContact.isBlank()){
            throw new Exception("Empty data");
        }
        var items = rawContact.split(delimiter);
        if(items.length != 3) {
            throw new Exception("Incorrect amount of data");
        }

        var name = items[0];
        var phone = items[1];
        var email = items[2];

        if(!isValidName(name)){
            throw new Exception("Incorrect name input");
        }
        else if (!isValidEmail(email)){
            throw new Exception("Incorrect email input");
        }
        else if (!isValidPhoneNumber(phone)){
            throw new Exception("Incorrect phone input");
        }

        return new Contact(name, phone, email);
    }

    public void addContact(String contact) throws Exception {
        var currentContact = parseContact(contact, "(\\||;)");
        if (contacts.containsKey(currentContact.email())){
            throw new Exception("Contact already exists");
        }
        contacts.put(currentContact.email(), currentContact);
    }

    public void deleteById(String id) throws Exception {
        if(!contacts.containsKey(id)){
            throw new Exception("Contact does not exist");
        }
        contacts.remove(id);
    }

    @Override
    public String toString() {
        String result = "";
        if (contacts.isEmpty()){
            return result;
        }
        result = getAllContact()
                .stream()
                .map(e -> e.toString())
                .reduce("", (subtotal, item)->{
                    return subtotal.concat(item).concat("\n");
                });

        return result;
    }

    private static boolean isValidEmail(String email){
        return email.matches("([a-z]+[0-9._-]*)+@[a-z]+.[a-z]+");
    }

    private static boolean isValidPhoneNumber(String number){
        return number.matches("^(\\+7|8){0,1}([0-9]{10})");
    }

    private static boolean isValidName(String name){
        return name.matches("[a-zA-ZА-Яа-я ]+");
    }

    public void saveContact(Collection<Contact> values) throws IOException {
        if (!initPath.equals("default"))
            writeCollectionToFile(getAllContact(), initPath);

    }

    private static void writeCollectionToFile(Collection<Contact> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            for (Contact element : collection) {
                writer.write(element.toString());
                writer.newLine();
            }
            System.out.println("Successfully wrote collection to file " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing collection to file " + fileName);
            e.printStackTrace();
        }
    }
}
