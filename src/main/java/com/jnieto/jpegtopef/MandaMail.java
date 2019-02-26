package com.jnieto.jpegtopef;



import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MandaMail {

    private final Properties properties = new Properties();

    private Session session;

    private void init() {
        properties.put("mail.smtp.host", Constantes.MIALHOST);
        properties.put("mail.smtp.starttls.enable", Constantes.MIALSTARTTLS);
        properties.put("mail.smtp.port", Constantes.MIALPORT);
        properties.put("mail.smtp.mail.sender", Constantes.MAILEMISOR);
        properties.put("mail.smtp.user", Constantes.MAILUSER);
        properties.put("mail.smtp.auth", Constantes.MAILAUTH);
        session = Session.getDefaultInstance(properties);
    }

    public void sendEmail(String destinatario, String asunto, String contenido) throws AddressException, MessagingException, Exception {
        init();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject(asunto);
        message.setText(contenido);
        Transport t = session.getTransport("smtp");
        t.connect((String) properties.get("mail.smtp.user"), Constantes.MAILPASSW);
        t.sendMessage(message, message.getAllRecipients());
        t.close();
    }

}
