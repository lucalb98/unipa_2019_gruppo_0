package it.eng.unipa.filesharing.container;

import it.eng.unipa.filesharing.context.SecurityContext;
import it.eng.unipa.filesharing.dto.OtpDTO;
import it.eng.unipa.filesharing.dto.ResourceDTO;
import it.eng.unipa.filesharing.model.Otp;
import it.eng.unipa.filesharing.resource.ContentResource;
import it.eng.unipa.filesharing.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.eng.unipa.filesharing.service.EmailService;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    OtpController otpController;



    @Autowired
    EmailService emailService;

    @PostMapping("/sendOtp")
    public void sendOtp(@RequestBody OtpDTO otpDTO){
        emailService.sendOtp(otpDTO);
    }


    @PostMapping("/download")
    public ResponseEntity<Resource> download(@RequestBody OtpDTO otpDTO) {
        ResourceDTO resourceDTO = emailService.getContent(otpDTO);
        return resourceDTO!=null ? getResponseEntityResource(resourceDTO.getName(), resourceDTO.getContent()) : new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Resource> getResponseEntityResource(String name, byte[] body) {
        return ResponseEntity.ok()
                //.contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                .body(new InputStreamResource(new ByteArrayInputStream(body)));
    }

}