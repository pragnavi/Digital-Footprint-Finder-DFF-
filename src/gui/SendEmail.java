package gui;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.IOException;
import java.util.Properties;

public class SendEmail {
	public static void sendEmail(String recipientEmail) throws IOException {
        // Gmail SMTP server configuration
        String host = "smtp.gmail.com";
        int port = 587;
        String username = "<enter-your-email>";
        String password = "<enter-your-password>";
        
        
        String subject = "Test Email";
        String body = "This is a test email sent using JavaMail API.";

        // Email properties
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
        	// Create MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // Set sender
            message.setFrom(new InternetAddress(username));
            // Set recipient
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            // Set subject
            message.setSubject("Alert from Digital Footprint Finder");

            // Create MimeBodyPart for text
            BodyPart textPart = new MimeBodyPart();
            textPart.setText("Dear User,\n\n" +
                    "We have found sensitive information related to you on the internet. " +
                    "Please find the attached JSON file containing details of your digital footprint.\n\n" +
                    "Thank you,\n" +
                    "Digital Footprint Finder Team");

            // Create MimeBodyPart for attachment
            BodyPart attachmentPart = new MimeBodyPart();
            String filename = "./scans/output".concat(recipientEmail.split("@")[0]).concat(".json");
            ((MimeBodyPart) attachmentPart).attachFile(filename);

            // Create Multipart and add parts to it
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Set content of the message
            message.setContent(multipart);

            // Send message
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
