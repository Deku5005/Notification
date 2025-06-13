import java.io.Serializable;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailNotifier implements Observer, Serializable {

    private final UserManager userManager;
    private final ServiceMananger serviceMananger;
    private final subscriptionManager subscriptionManager;


    private final String fromEmail = "dolooumar60@gmail.com";
    private final String fromPassword = "ggdb nuwc zxli hyhv";


    public EmailNotifier(UserManager userManager, ServiceMananger serviceMananger, subscriptionManager subscriptionManager) {
        this.userManager = userManager;
        this.serviceMananger = serviceMananger;
        this.subscriptionManager = subscriptionManager;
    }

    @Override
    public void update(String username, String serviceName) {
        notifierAbonnement(username, serviceName);
    }

    public void notifierAbonnement(String username, String serviceName) {
        User user = userManager.getUserByUsername(username);
        Service service = serviceMananger.getServiceByName(serviceName);

        if (user != null && service != null) {
            sendEmail(user.getEmail(), username, service.getNom(), service.getDescription(),user.getEmail());
        }
    }

    private void sendEmail(String subs, String username, String serviceName, String description, String email) {
        String subject = "Abonnement au service : " + serviceName;
        String body = "Bonjour " + username + ",\n"
                + "Vous êtes désormais abonné au service : " + serviceName + ".\n\n"
                + "Description : " + description + "\n"
                + "Expéditeur : "+username;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Système Java"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(subscriptionManager.notifyObservers( username,  serviceName, email)));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("[EMAIL] Email envoyé à " + subscriptionManager.notifyObservers( username,  serviceName, email));
        } catch (Exception e) {
            System.out.println("[ERREUR EMAIL] Échec d’envoi : " + e.getMessage());
        }
    }
}
