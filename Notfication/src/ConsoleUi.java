import java.util.Scanner;
import java.util.*;

/**
 * Gère l'affichage en console.
 * SRP : cette classe ne s'occupe que de l'interface utilisateur.
 */
public class ConsoleUi {
    private final UserManager userManager;
    private final ServiceMananger serviceMananger;
    private final subscriptionManager subManager;
    private final EmailNotifier emailNotifier;// ← nouveau
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUi(UserManager um, ServiceMananger sm, subscriptionManager sub, EmailNotifier notifier) {
        this.userManager    = um;
        this.serviceMananger = sm;
        this.subManager     = sub;
        this.emailNotifier = notifier;
    }

    // Démarre le menu principal
    public void run() {
        if (userManager.isEmpty()) {
            // Admin par défaut
            userManager.addUser(new User("admin", "dolooumar60@gmail.com", "admin", "admin123"));
        }

        while (true) {
            System.out.println("\n1. Connexion");
            System.out.println("2. Quitter");
            System.out.print("Choix : ");
            int choix = lireInt();

            switch (choix) {
                case 1 -> connecter();
                case 2 -> {
                    System.out.println("Fermeture...");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // Menu de connexion
    private void connecter() {
        System.out.print("Nom d'utilisateur : ");
        String nom = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        User user = userManager.authenticate(nom, password);
        if (user == null) {
            System.out.println("Échec de connexion.");
            return;
        }

        System.out.println("Bienvenue " + user.nom + " (" + user.role + ")");

        if (user.role.equals("admin")) adminMenu();
        else employeMenu(user);
    }

    // Menu administrateur
    private void adminMenu() {
        while (true) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Créer un service");
            System.out.println("2. Créer un utilisateur");
            System.out.println("3. Voir les services");
            System.out.println("4. Déconnexion");
            System.out.print("Choix : ");
            int choix = lireInt();

            switch (choix) {
                case 1 -> {
                    System.out.print("Nom du service : ");
                    String nom = scanner.nextLine();
                    System.out.print("description du service : ");
                    String description = scanner.nextLine();
                    serviceMananger.addService(new Service(nom, description));
                }
                case 2 -> {
                    System.out.print("Nom d'utilisateur : ");
                    String nom = scanner.nextLine();
                    System.out.print("email : ");
                    String email = scanner.nextLine();
                    System.out.print("Rôle (admin/employe) : ");
                    String role = scanner.nextLine();
                    System.out.print("Entrer un mot de passe : ");
                    String password = scanner.nextLine();
                    userManager.addUser(new User(nom,email,role,password));
                    System.out.println("Utilisateur enregistrer avec succes");
                }
                case 3 -> afficherServices();
                case 4 -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // Menu employé
    private void employeMenu(User employe) {
        while (true) {
            System.out.println("\n--- Menu Employé ---");
            System.out.println("1. Voir tous les services");
            System.out.println("2. S’abonner à un service");
            System.out.println("3. Voir mes abonnements");
            System.out.println("4. Se désabonner d’un service");
            System.out.println("5. Envoye des notifications");
            System.out.println("6. Déconnexion");
            System.out.print("Choix : ");
            int choix = lireInt();

            switch (choix) {
                case 1  -> afficherServices();
                case 2  -> choisirServicePourAbonnement(employe);
                case 3  -> afficherMesAbonnements(employe);
                case 4  -> choisirServicePourDesabonnement(employe);
                case 5  -> envoyerNotification(employe);
                case 6  -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }


    // Affiche tous les services
    private void afficherServices() {
        System.out.println("Services disponibles :");
        for (Service s : serviceMananger.getServices()) {
            System.out.println("- " + s.nom);
        }
    }

    // Lecture sécurisée d'un entier
    private int lireInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Nombre attendu : ");
        }
        int n = scanner.nextInt();
        scanner.nextLine(); // vider le buffer
        return n;
    }

    /* ----------- Abonnement ----------- */
    private void choisirServicePourAbonnement(User employe) {
        List<Service> services = serviceMananger.getServices();
        if (services.isEmpty()) {
            System.out.println("Aucun service disponible.");
            return;
        }
        System.out.println("Choisissez le numéro du service à ajouter :");
        for (int i = 0; i < services.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, services.get(i).nom);
        }
        int choix = lireInt();
        if (choix < 1 || choix > services.size()) {
            System.out.println("Numéro invalide.");
        } else {
            String serviceName = services.get(choix - 1).nom;
            subManager.subscribe(employe.nom, serviceName);
            System.out.println("→ Abonné à " + serviceName);
        }
    }

    private void afficherMesAbonnements(User employe) {
        Set<String> subs = subManager.getSubscriptions(employe.nom);
        if (subs.isEmpty()) {
            System.out.println("Vous n’êtes abonné à aucun service.");
        } else {
            System.out.println("Vos abonnements :");
            subs.forEach(s -> System.out.println("• " + s));
        }
    }

    private void choisirServicePourDesabonnement(User employe) {
        Set<String> subs = subManager.getSubscriptions(employe.nom);
        if (subs.isEmpty()) {
            System.out.println("Aucun abonnement à supprimer.");
            return;
        }
        List<String> liste = new ArrayList<>(subs);
        for (int i = 0; i < liste.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, liste.get(i));
        }
        int choix = lireInt();
        if (choix < 1 || choix > liste.size()) {
            System.out.println("Numéro invalide.");
        } else {
            String serviceName = liste.get(choix - 1);
            subManager.unsubscribe(employe.nom, serviceName);
            System.out.println("→ Désabonné de " + serviceName);
        }
    }

    private void envoyerNotification(User employe) {
        Set<String> abonnements = subManager.getSubscriptions(employe.nom);

        if (abonnements.isEmpty()) {
            System.out.println("Vous n’êtes abonné à aucun service.");
            return;
        }

        System.out.println("Choisissez un service à notifier :");
        List<String> liste = new ArrayList<>(abonnements);
        for (int i = 0; i < liste.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, liste.get(i));
        }

        int choix = lireInt();
        if (choix < 1 || choix > liste.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        String serviceName = liste.get(choix - 1);
        emailNotifier.notifierAbonnement(employe.nom, serviceName);
    }


}

