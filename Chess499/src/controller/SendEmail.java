package controller;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import chess.ChessGame;

import java.util.Properties;

/**
 * @Author Amadeus
 * Class for sending emails through a temporary email.
 * Utilizing the JavaMail API library
 * The JavaMail API provides a platform-independent and protocol-independent framework to build mail
 * and messaging applications.
 */

public class SendEmail {
    // creates a session for the mail
    private Session session;
    // to and from emails
    private final String to = "ChessGuys499@gmail.com";
    private final String from = "ChessTest499@gmail.com";
    // the subject line
    private String subject;
    // the body message
    private String bodyMessage;
    // constructor
    public SendEmail(String subject, String bodyMessage){
        // setting the properties for the program
        setProperties();
        this.subject = subject;
        this.bodyMessage = bodyMessage;
    }

    /**
     * method for setting the properties
     */
    private void setProperties(){
        // creating email properties
        Properties emailProperties = System.getProperties();
        // sets the port to 587
        // the reason why SMTP port 587 is used is because it is a message submission agent port that requires
        // SMTP authentication and therefore is used instead of port 25
        emailProperties.put("mail.smtp.port", "587");
        // setting the authentication to true
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        session = Session.getDefaultInstance(emailProperties, null);
    }

    /**
     * method for setting the message
     * A MIME (Multipurpose Internet Mail Extension) uses header and separators that tell a user agent
     * how to re create the message
     * @return MimeMessage
     */
    private MimeMessage createMessage(){
        // creates a mime message of the current session
        MimeMessage message = new MimeMessage(session);
        try {
            // adds the to recipient
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // sets the subject
            message.setSubject(subject);
            // sets the body message
            message.setContent(bodyMessage, "text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }


    /**
     * method to send the message
     */
    public void send(){
        // sets gmail as the host
        String emailHost = "smtp.gmail.com";
        // password for the "from"
        String userPass = "ehPX&qnN*!5r";
        Transport transport = null;
        try {
            // creates a transport session
            transport = session.getTransport("smtp");
            // connects to emailHost and logs in
            transport.connect(emailHost, from, userPass);
            // creates a message and then sends it to the recipients
            transport.sendMessage(createMessage(), createMessage().getAllRecipients());
            // closes the transport
            transport.close();
            Logger.getLogger(ChessGame.LOGGER_NAME).log(Level.INFO, "EMAIL HAS BEEN SENT TO THE RECIPIENTS!" );
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
