package it.eng.unipa.filesharing.container;

import it.eng.unipa.filesharing.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class EmailController {

    @Autowired
    private EmailService emailService;
    public EmailController(@Autowired EmailService emailService) {this.emailService = emailService;}

    @GetMapping("/{email}")
    @ResponseBody
    public void sendEmail(@PathVariable("email") String email){ emailService.sendEmail();}

    @PostMapping("/{email}")
    @ResponseBody
    public void sendOtp(@PathVariable("email")String email){emailService.sendOtpMessage();}

}