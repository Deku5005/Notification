import java.io.*; // Import de toutes les classes d’E/S nécessaires


public class sauvegardeFichier {

    public static void saveObject(Object obj, String filename) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj); //  Écriture binaire de l’objet dans le fichier

        } catch (IOException e) {
            System.out.println("Erreur de sauvegarde : " + e.getMessage());
        }
    }

    public static Object loadObject(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject(); // Retourne l’objet lu
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
