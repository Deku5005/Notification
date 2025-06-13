

/**
 * Implémentation de interfaces.NotificationObserver qui affiche un message simulant une notification push.
 */
public class PushNotifier implements NotificationObserver {
    @Override
    public void notifierAbonnement(String username, String serviceName) {
        System.out.println("[PUSH] " + username + " s’est abonné au service : " + serviceName);
    }
}

