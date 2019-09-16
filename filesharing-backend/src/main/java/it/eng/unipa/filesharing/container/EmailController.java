package it.eng.unipa.filesharing.container;

import it.eng.unipa.filesharing.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EmailController {

    private EmailService emailService;
    public EmailController(@Autowired EmailService emailService) {this.emailService = emailService;}

    @RequestMapping("/{email}")
    @ResponseBody
    public void run(@PathVariable("email") String email){
        emailService.sendEmail();
    }
}