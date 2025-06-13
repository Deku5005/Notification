import java.io.Serializable;

/**
 * Interface pour tout observateur de notifications.
 * SRP : définit uniquement le contrat pour recevoir une notification.
 * OCP : on peut ajouter de nouveaux types de notifications sans modifier le code existant.
 */
public interface NotificationObserver extends Serializable {
    void notifierAbonnement(String username, String serviceName);
}
