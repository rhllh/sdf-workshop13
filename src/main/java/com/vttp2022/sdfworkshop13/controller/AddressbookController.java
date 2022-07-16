package com.vttp2022.sdfworkshop13.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vttp2022.sdfworkshop13.model.Contact;
import com.vttp2022.sdfworkshop13.util.Contacts;

@Controller
@RequestMapping(path="/addressbook")        // each url has unique set of methods
public class AddressbookController {

    private final Logger logger = LoggerFactory.getLogger(AddressbookController.class);

    @Autowired                  // making 'new' objects is not good 
    Contacts contacts;  

    @Autowired
    ApplicationArguments appArgs;

    @GetMapping
    public String showAddressBookForm(Model model) {
        model.addAttribute("contactObj", new Contact());
        return "address";
    }

    @PostMapping
    public String saveContact(@ModelAttribute Contact contact,
                              Model model) {
        logger.info("ID from form: " + contact.getId());    //
        // Contacts contacts = new Contacts();                
        contacts.saveContact(contact, model, appArgs);
        return "showContact";
    }

    @GetMapping("{contactId}")
    public String getContact(@PathVariable("contactId") String id, Model model) {
        // Contacts contacts = new Contacts();                
        contacts.getContactById(model, id, appArgs);
        return "showContact";
    }
}
