package com.vttp2022.sdfworkshop13.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.vttp2022.sdfworkshop13.model.Contact;

@Component("contacts")  //
public class Contacts {
    private static final Logger logger = LoggerFactory.getLogger(Contacts.class);

    public void saveContact(Contact contact, Model model,
                            ApplicationArguments appArgs) {
        logger.info("saved id: " + contact.getId());
        String dataFileName = contact.getId();
        String dataDir = getDataDir(appArgs);

        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(dataDir + File.separator + dataFileName);
            pw = new PrintWriter(fw);
            pw.println(contact.getName());
            pw.println(contact.getEmail());
            pw.println(contact.getPhoneNumber());
        } catch (IOException e) {
            logger.error("Error: " + e);
        } finally {
            pw.close();
        }

        //model.addAttribute("contact", new Contact(contact.getId(), contact.getName(), 
        //                            contact.getEmail(), contact.getPhoneNumber())); 
        model.addAttribute("contact", contact);
        
    }

    public void getContactById(Model model, String id, ApplicationArguments appArgs) {
        Contact contact = new Contact();
        try {
            Path filePath = new File(getDataDir(appArgs) + File.separator + id).toPath();
            Charset charset = Charset.forName("UTF-8");
            List<String> lines = Files.readAllLines(filePath, charset);
            contact.setId(id);
            contact.setName(lines.get(0));
            contact.setEmail(lines.get(1));
            contact.setPhoneNumber(Integer.parseInt(lines.get(2)));
        } catch (IOException e) {
            logger.error("Error > " + e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact info not found");
        }

        model.addAttribute("contact", contact);
    }

    public String getDataDir(ApplicationArguments appArgs) {
        Set<String> optNames = appArgs.getOptionNames();
        String[] optNamesArr = optNames.toArray(new String[optNames.size()]);

        List<String> optValues = appArgs.getOptionValues(optNamesArr[0]);
        String[] optValuesArr = optValues.toArray(new String[optValues.size()]);

        return optValuesArr[0];
    }
}
