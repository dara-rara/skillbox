package com.example.mod3.service;

import com.example.mod3.domain.ContactEntity;
import com.example.mod3.repository.ContactRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContactService {

    private final ContactRepository repository;
    public List<ContactEntity> findAll() {
        return repository.findAll();
    }

    public ContactEntity getById(Long id) {
        return repository.getReferenceById(id);
    }

    public ContactEntity save(ContactEntity contact) {
        return repository.save(contact);
    }

    public ContactEntity update(ContactEntity contactUp) {
        var contact = getById(contactUp.getId());
        contact.setEmail(contactUp.getEmail());
        contact.setFirstName(contactUp.getFirstName());
        contact.setLastName(contactUp.getLastName());
        contact.setPhone(contactUp.getPhone());
        return repository.save(contact);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
