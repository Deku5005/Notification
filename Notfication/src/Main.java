
/**
 * Point d'entrée principal.
 * Initialise les gestionnaires et les observateurs.
 */
public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        ServiceMananger serviceMananger = new ServiceMananger();
        subscriptionManager subManager = new subscriptionManager();

        serviceMananger.addObserver(new loggerObserver());
        serviceMananger.addObserver(new AuditObserver());
        EmailNotifier notifier = new EmailNotifier(userManager, serviceMananger, subManager);
        subManager.addObserver(notifier);

        EMail notifie = new EMail(subManager, userManager,serviceMananger);



        ConsoleUi ui = new ConsoleUi(userManager, serviceMananger, subManager, notifier);
        ui.run();
    }
}

