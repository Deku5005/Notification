import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les services avec notification et persistance.
 */
public class ServiceMananger implements Serializable {
    private final List<Service> services;
    private final List<serviceobserver> observers = new ArrayList<>();

    public ServiceMananger() {
        List<Service> loaded = (List<Service>) sauvegardeFichier.loadObject("services.dat");
        if (loaded != null) {
            this.services = loaded;
        } else {
            this.services = new ArrayList<>();
        }
    }

    public void addService(Service service) {
        services.add(service);
        sauvegardeFichier.saveObject(services, "services.dat");
        notifyObservers(service);
    }

    public List<Service> getServices() {
        return services;
    }

    public void addObserver(serviceobserver observer) {
        observers.add(observer);
    }

    public Service getServiceByName(String name) {
        for (Service service : services) {
            if (service.nom.equalsIgnoreCase(name)) {
                return service;
            }
        }
        return null;
    }

    private void notifyObservers(Service service) {
        for (serviceobserver observer : observers) {
            observer.onserviceCreated(service);
        }
    }
}
