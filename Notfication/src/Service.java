import java.io.Serializable;

public class Service implements  Servi ,Serializable {
    public String nom, description;

    public Service(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
    @Override
   public String getNom(){
        return nom;
   }
   @Override
   public String getDescription(){
        return description;
   }

}
