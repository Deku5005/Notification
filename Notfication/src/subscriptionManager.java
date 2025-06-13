import java.io.Serializable;
import java.util.*;

/**
 * Gère les abonnements des employés à des services.
 * Applique SRP (une seule responsabilité) et OCP (extensible).
 * Implémente le design pattern interfaces.Observer (avec interface personnalisée).
 */
public class subscriptionManager implements Serializable {

    private final Map<String, Set<String>> subscriptions; // utilisateur → liste de services
    private static final List<Observer> observers = new ArrayList<>(); // Liste des observateurs
    private static final String FILE = "subscriptions.dat"; // Fichier de sauvegarde

    public subscriptionManager() {
        Map<String, Set<String>> loaded =
                (Map<String, Set<String>>) sauvegardeFichier.loadObject(FILE);
        this.subscriptions = (loaded != null) ? loaded : new HashMap<>();
    }

    /**
     * Abonne un utilisateur à un service et notifie les observateurs.
     */
    public void subscribe(String username, String serviceName, String email) {
        subscriptions
                .computeIfAbsent(username, k -> new HashSet<>())
                .add(serviceName);
        sauvegardeFichier.saveObject(subscriptions, FILE);
        notifyObservers(username, serviceName, email); // notification
    }

    /**
     * Désabonne un utilisateur d’un service.
     */
    public void unsubscribe(String username, String serviceName) {
        if (subscriptions.containsKey(username)) {
            subscriptions.get(username).remove(serviceName);
            sauvegardeFichier.saveObject(subscriptions, FILE);
        }
    }

    /**
     * Retourne les services auxquels un utilisateur est abonné.
     */
    public Set<String> getSubscriptions(String username) {
        return subscriptions.getOrDefault(username, Collections.emptySet());
    }

    public void addObserver(EmailNotifier notifier) {
    }

    public void subscribe(String nom, String serviceName) {
    }

    /**
     * Ajoute un observateur.
     */
    public interface Observer {
        void update(String username, String serviceName, String email);
    }

    /**
     * Notifie tous les observateurs.
     *
     *
     */
    static String notifyObservers(String username, String serviceName, String email) {
        for (Observer obs : observers) {
            obs.update(username, serviceName, email);
        }
        return email;
    }
}
