package com.example.mod3.controller;

import com.example.mod3.domain.ContactEntity;
import com.example.mod3.domain.dto;
import com.example.mod3.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        model.addAttribute("contact", new ContactEntity());
        return "index";
    }

    @PostMapping("/contact/update")
    public String update(@ModelAttribute ContactEntity contactUp) {
        contactService.update(contactUp);
        return "redirect:/";
    }

    @PostMapping("/contact/save")
    public String editContact(@ModelAttribute ContactEntity contact) {
        contactService.save(contact);
        return "redirect:/";
    }

//    @PostMapping("/contact/delete")
//    public String deleteContact(@ModelAttribute ContactEntity contact) {
//        contactService.deleteById(contact.getId());
//        return "redirect:/";
//    }
    @PostMapping("/contact/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteById(id);
        return "redirect:/";
    }

}
