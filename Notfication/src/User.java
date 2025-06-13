import java.io.Serializable;



public class User implements  utilis , Serializable {
   public String nom, email,role, password;

    User(String nom, String email, String role, String password) {
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.password = password;
    }
    @Override
    public String getNom() {
        return nom;
    }
    @Override
    public String getEmail() {
        return email;
    }

}
