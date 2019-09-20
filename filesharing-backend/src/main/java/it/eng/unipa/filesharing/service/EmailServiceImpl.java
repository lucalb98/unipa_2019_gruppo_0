package it.eng.unipa.filesharing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.io.IOException;

@SpringBootApplication
public class EmailServiceImpl implements CommandLineRunner, EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    //#########################
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceImpl.class, args);
    }

    @Override
    public void run(String... args) throws MessagingException,IOException{

        System.out.println("Sending Email...");


        sendEmail();
        sendOtpMessage();
        /*try {

            //sendEmailWithAttachment();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        System.out.println("Done");

    }
    //##############################


    public void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("alessandra.comparetto@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("hello world \n Spring Boot Email");

        javaMailSender.send(msg);

    }

    public void sendOtpMessage(){
        SimpleMailMessage msg = new SimpleMailMessage();
        String codex=new String();
        msg.setTo("alessandra.comparetto@gmail.com");

        msg.setSubject("Testing Otp message");
        OtpService otp= new OtpService();
        msg.setText("This is your OTP : \n\n"+codex.valueOf(otp.generateOTP("alessandra.comparetto@gmail.com")));

        javaMailSender.send(msg);


    }

   /* void sendEmailWithAttachment() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("1@gmail.com");

        helper.setSubject("Testing from Spring Boot");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();

        //ResourceUtils.getFile("classpath:android.png");

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }*/
}