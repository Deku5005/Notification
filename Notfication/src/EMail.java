import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EMail implements Observer{
    @Override
    public void update(String username, String serviceName) {

    }

    private final subscriptionManager  subscriptionManagers;
    private final UserManager userManager;
    private final ServiceMananger serviceMananger;

    private static final String expéditeur = "dolooumar60@gmail.com";
    private static final String motDePasse = "ggdb nuwc zxli hyhv"; 
    private static final String hoteSMTP = "smtp.example.com"; 
    private static final int portSMTP = 587; // 

    public EMail(subscriptionManager subscriptionManagers, UserManager userManager, ServiceMananger serviceMananger ) {
        this.subscriptionManagers = subscriptionManagers;
        this.userManager = userManager;
        this.serviceMananger = serviceMananger;
    }

    public static void main(String[] args) {

        // Liste des destinataires (sans l'expéditeur)


        List<Observer> destinataires = new ArrayList<>();
        int longueur = destinataires.size();
        // Ou utiliser une liste :
        // List<String> destinatairesList = Arrays.asList("destinataire1@example.com", "destinataire2@example.com");

        // Propriétés de configuration du serveur SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", hoteSMTP);
        properties.put("mail.smtp.port", portSMTP);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Pour les connexions sécurisées
        properties.put("mail.smtp.ssl.trust", hoteSMTP);

        // Authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expéditeur, motDePasse);
            }
        });

        try {
            // Création du message
            MimeMessage message = new MimeMessage(session);

            // Expéditeur
            message.setFrom(new InternetAddress(expéditeur));

            // Destinataires
            InternetAddress[] adressesDestinataires = new InternetAddress[longueur];
            for (int i = 0; i < destinataires.size(); i++) {
                adressesDestinataires[i] = new InternetAddress();
            }
            message.setRecipients(Message.RecipientType.TO, adressesDestinataires);

            // Sujet du message
            message.setSubject("Sujet de l'email");

            // Corps du message (format HTML)
            message.setContent("<html><body><p>Corps du message</p></body></html>", "text/html");

            // Envoi du message
            Transport.send(message);
            System.out.println("Email envoyé avec succès à plusieurs destinataires.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
