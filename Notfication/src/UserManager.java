import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les utilisateurs avec persistance.
 */
public class UserManager implements Serializable {
    private final List<User> users;

    public UserManager() {
        List<User> loaded = (List<User>) sauvegardeFichier.loadObject("users.dat");
        if (loaded != null) {
            this.users = loaded;
        } else {
            this.users = new ArrayList<>();
        }
    }

    public void addUser(User user) {
        users.add(user);
        sauvegardeFichier.saveObject(users, "users.dat");
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.nom.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.nom.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }
}
