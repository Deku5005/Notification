public class loggerObserver implements serviceobserver {
    @Override
    public void onserviceCreated(Service service) {
        System.out.println("service ajouter:" + service.nom);
    }
}
