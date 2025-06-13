/**
 * Observateur simulant un audit.
 */

public class AuditObserver implements serviceobserver {
    @Override
    public void onserviceCreated(Service service) {
        System.out.println("service enregistrer:" +service.nom);
    }
}
