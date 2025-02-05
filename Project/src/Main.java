import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new Prova()); //-> se in futuro vogliamo mettere interfaccia grafica
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Crea e visualizza la finestra */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConnection = null;

        try {
            // Instanzia l'oggetto DatabaseConnection
            dbConnection = new DatabaseConnection();
            boolean exit = false;
            //caricare tutti i dati
            //utenti
            //amministratori
            //aule
            //tavoli

            while (!exit) {
                System.out.println("1. test query");
                System.out.println("2. inserici aula");
                System.out.println("3. inserisci tavolo");
                System.out.println("4. Esci");
                System.out.print("Seleziona un'opzione: ");
                int scelta = scanner.nextInt();
                scanner.nextLine();  // Consumiamo la newline lasciata da nextInt()
                String query;
                switch (scelta) {
                    case 1:
                        // Esegui una query
                        System.out.print("Inserisci la query: ");
                        query = scanner.nextLine();

                        // Chiamata al metodo eseguiQuery dell'oggetto dbConnection
                        List<Map<String, Object>> results = dbConnection.eseguiQuery(query);

                        // Stampa i risultati
                        for (Map<String, Object> row : results) {
                            System.out.println(row);
                        }
                        break;
                    case 2:
                        // Esegui la query per ottenere i dipartimenti
                        query = "SELECT * FROM `dipartimento`";
                        List<Map<String, Object>> results2 = dbConnection.eseguiQuery(query);

                        // Stampa i risultati dei dipartimenti
                        for (Map<String, Object> row : results2) {
                            System.out.println(row);
                        }

                        // Chiedi l'ID del dipartimento
                        System.out.print("Inserisci id del dipartimento: ");
                        String iddipartimento = scanner.nextLine();

                        // Chiedi il nome dell'aula
                        System.out.print("Inserisci nome dell'aula: ");
                        String nomeaula = scanner.nextLine();

                        // Creazione della query per l'inserimento
                        query = "INSERT INTO `aula` (`fkdipartimento`, `nome`) VALUES (?, ?)";

                        // Utilizza una query preparata per evitare SQL injection
                        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
                            // Imposta i parametri della query
                            stmt.setString(1, iddipartimento);  // Imposta l'ID del dipartimento
                            stmt.setString(2, nomeaula);        // Imposta il nome dell'aula

                            // Esegui la query di inserimento
                            int rowsAffected = stmt.executeUpdate();
                            System.out.println("Aula inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 3:
                        // Esegui la query per ottenere i dipartimenti
                        query = "SELECT * FROM `aula`";
                        List<Map<String, Object>> results3 = dbConnection.eseguiQuery(query);

                        // Stampa i risultati dei dipartimenti
                        for (Map<String, Object> row : results3) {
                            System.out.println(row);
                        }

                        // Chiedi l'ID del dipartimento
                        System.out.print("Inserisci id dell'aula: ");
                        String idaula = scanner.nextLine();

                        // Chiedi il nome dell'aula
                        System.out.print("Inserisci numeroposti: ");
                        String numeroposti = scanner.nextLine();

                        // Creazione della query per l'inserimento
                        query = "INSERT INTO `tavolo` (`numeroposti`, `fkaula`) VALUES (?, ?)";

                        // Utilizza una query preparata per evitare SQL injection
                        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
                            // Imposta i parametri della query
                            stmt.setString(1, numeroposti);  // Imposta l'ID del dipartimento
                            stmt.setString(2, idaula);        // Imposta il nome dell'aula

                            // Esegui la query di inserimento
                            int rowsAffected = stmt.executeUpdate();
                            System.out.println("Tavolo inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        // Esci dal ciclo
                        exit = true;
                        break;
                    default:
                        System.out.println("Opzione non valida!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Chiusura della connessione
            try {
                if (dbConnection != null) {
                    dbConnection.chiudiConnessione();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
