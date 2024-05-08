package gui;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.util.Properties;

public class SendEmail {
	public static void sendEmail(String recipientEmail) throws IOException {
        String host = "smtp.gmail.com";
        int port = 587;
        String username = "ag9489@nyu.edu";
        String password = "fmlc ttsu sqfv edqx";
        
        
        String subject = "Test Email";
        String body = "This is a test email sent using JavaMail API.";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Alert from Digital Footprint Finder");

            BodyPart textPart = new MimeBodyPart();
            textPart.setText("Dear User,\n\n" +
                    "We have found sensitive information related to you on the internet. " +
                    "Please find the attached JSON file containing details of your digital footprint.\n\n" +
                    "Thank you,\n" +
                    "Digital Footprint Finder Team");

            BodyPart attachmentPart = new MimeBodyPart();
            String filename = "./scans/output".concat(recipientEmail.split("@")[0]).concat(".json");
            ((MimeBodyPart) attachmentPart).attachFile(filename);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public static void main(String[] args) throws IOException {
        String recipientEmail = "gargabhay1999@gmail.com";
        sendEmail(recipientEmail);
    }
}
