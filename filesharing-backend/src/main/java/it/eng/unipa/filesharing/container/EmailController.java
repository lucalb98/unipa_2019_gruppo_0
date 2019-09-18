package it.eng.unipa.filesharing.container;

import it.eng.unipa.filesharing.dto.EmailDTO;
import it.eng.unipa.filesharing.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shared")
public class EmailController {

    @Autowired
    private EmailService emailService;
    public EmailController(@Autowired EmailService emailService) {this.emailService = emailService;}

    @PostMapping("")
    public void sendEmail(@RequestBody EmailDTO email){ emailService.sendEmail(email);}

    /*
    @PostMapping("/{email}")
    @ResponseBody
    public void sendOtp(@PathVariable("email")String email){emailService.sendOtpMessage(email);}
    */
}
