package com.example.login;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    public static void main(String[] args) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("stevekamga18@gmail.com", "ebndtinquknsqxtc");
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject("Test Mail Java fx");
        message.setContent("Yo","text/html");

        Address address = new InternetAddress("stevekamga18@gmail.com");
        message.setRecipient(Message.RecipientType.TO, address);

        Transport.send(message);
    }
}
