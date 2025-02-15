import javax.swing.*;

public class Prova {

    public Prova() {
        // Crea il frame principale
        JFrame frame = new JFrame("Prova2 Form");

        // Crea l'oggetto Prova2 e ottieni il pannello dal form
        Prova2 provaForm = new Prova2(); // Prova2 è la classe generata da IntelliJ per il form

        // Controlla se il pannello è correttamente inizializzato
        if (provaForm.getMainPanel() != null) {
            // Imposta il pannello come contenuto del frame
            frame.setContentPane(provaForm.getMainPanel());
        } else {
            // Se il pannello è null, mostra un errore
            System.out.println("Errore: il pannello non è stato inizializzato correttamente.");
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Esegui l'interfaccia utente in modo thread-safe
        SwingUtilities.invokeLater(() -> new Prova());
    }
}
