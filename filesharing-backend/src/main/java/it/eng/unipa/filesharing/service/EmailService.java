package it.eng.unipa.filesharing.service;

import it.eng.unipa.filesharing.dto.EmailDTO;
import it.eng.unipa.filesharing.dto.OtpDTO;
import it.eng.unipa.filesharing.dto.ResourceDTO;

import java.util.UUID;

public interface EmailService {

    public void  sendEmail (EmailDTO email);
    public void sendOtp(OtpDTO otpDTO);
    public ResourceDTO getContent(OtpDTO otpDTO);

}
