package it.eng.unipa.filesharing.service;
import it.eng.unipa.filesharing.context.SecurityContext;
import it.eng.unipa.filesharing.dto.EmailDTO;
import it.eng.unipa.filesharing.dto.OtpDTO;
import it.eng.unipa.filesharing.dto.ResourceDTO;
import it.eng.unipa.filesharing.model.Otp;
import it.eng.unipa.filesharing.model.Team;
import it.eng.unipa.filesharing.repository.OtpRepository;
import it.eng.unipa.filesharing.resource.BucketResource;
import it.eng.unipa.filesharing.resource.ContentResource;
import it.eng.unipa.filesharing.resource.Resource;
import it.eng.unipa.filesharing.resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final long TEMPO_VALIDITA = 5 * 60 * 1000;
    @Autowired
    private JavaMailSender javaMailSender;

    private ConversionService conversionService;


    @Autowired
    TeamService teamService;

    @Autowired
    OtpRepository otpRepository;


    @Autowired
    ResourceRepository resourceRepository;

    @Value("${otpUrl}")
    String otpUrl;


    public void sendEmail(EmailDTO emailDTO) {

        ResourceDTO resourceDTO = teamService.getContent(emailDTO.getUuid(),emailDTO.getBucketName(),emailDTO.getUniqueId());

        if(resourceDTO!=null) {

            Otp otp = new Otp();
            otp.setEmail(emailDTO.getEmail());
            otp.setBucketName(emailDTO.getBucketName());
            otp.setUuid(emailDTO.getUuid());
            otp.setUniqueId(emailDTO.getUniqueId());
            otp.setToken(UUID.randomUUID().toString());

            otpRepository.save(otp);


            String url = this.otpUrl+"/"+otp.getToken()+"/"+emailDTO.getEmail();

            String subject = "L'utente " + SecurityContext.getLongName() + " sta condividendo con te un file!";
            String body = "<html><body>Usa questo link per scaricare il file:<br/><a href="+url+">"+url+"</a></body></html>";
            sendEmail(emailDTO.getEmail(),subject,body);
        }

    }

    @Override
    public void sendOtp(OtpDTO otpDTO) {
        Optional<Otp> otpOpt = otpRepository.myOtp(otpDTO.getEmail(), otpDTO.getToken());
        if(otpOpt.isPresent()){
            Otp otp = otpOpt.get();
            Random rn = new Random();
            int min = 100000;
            int max = 999999;
            int otpNumber = rn.nextInt(max - min + 1) + min;

            otp.setOtp(otpNumber);
            otp.setDataScadenza(new Timestamp(System.currentTimeMillis()+TEMPO_VALIDITA));

            otpRepository.save(otp);
            String subject = "Utilizza questa password per scaricare il tuo file";
            String boby = "La password è <b>"+otpNumber+"</b>, data scadenza"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(otp.getDataScadenza());
            sendEmail(otp.getEmail(),subject,boby);

        }
    }

    private void sendEmail(String email, String subject, String body){
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setSubject(subject);
                helper.setText(body, true);
                helper.setTo(email);
                javaMailSender.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    @Override
    public ResourceDTO getContent(OtpDTO otpDTO){
        Optional<Otp> otpOpt = otpRepository.myOtp(otpDTO.getEmail(), otpDTO.getToken(),otpDTO.getOtp());
        if(otpOpt.isPresent()){
            Otp otp = otpOpt.get();
            if(System.currentTimeMillis()<otp.getDataScadenza().getTime()){
                BucketResource bucketResource = resourceRepository.loadBucket(otp.getUuid(), otp.getBucketName());
                if(bucketResource!=null){
                    Resource contentResource = resourceRepository.read(bucketResource, otp.getUniqueId());
                    if(contentResource!=null){
                        System.out.println("Sono io");
                        return (ResourceDTO)conversionService.convert(contentResource, TypeDescriptor.valueOf(ContentResource.class), TypeDescriptor.valueOf(ResourceDTO.class));
                    }
                }
            }
        }
        return null;
    }

}