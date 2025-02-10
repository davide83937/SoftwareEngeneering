import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater(() -> new Prova()); -> se in futuro vogliamo mettere interfaccia grafica
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConnection = null;
        Studente sessioneU=null ,sessioneA = null; //per ora classe amministratore non utilizzata da implementare
        String nome,cognome,codiceFiscale,email,nascita,corsoDiStudio,query,idaula,numeroposti ;
        int matricola, scelta;
        try {
            // Instanzia l'oggetto DatabaseConnection
            dbConnection = new DatabaseConnection();
            //prossimi passi
            //caricamento liste
            //aule
            //tavoli

            boolean exit = false;
            boolean loggato = false;
            while (!loggato) {
                System.out.println("1. Login Amministratore");
                System.out.println("2. Login Studente");
                System.out.println("3. Esci");
                System.out.print("Seleziona un'opzione: ");
                scelta = scanner.nextInt();
                scanner.nextLine();
                switch (scelta) {
                    case 1:
                        // Esegui una query
                        System.out.print("Inserisci nome: ");
                        nome = scanner.nextLine();

                        System.out.print("Inserisci password: ");
                        String password = scanner.nextLine();

                        query = "SELECT * FROM studente, corsidistudio WHERE studente.Fkcorsodistudio=corsidistudio.id and studente.Nome = \"" + nome + "\" AND studente.Password = \"" + password + "\"";

                        List<Map<String, Object>> amministratore = dbConnection.eseguiQuery(query);

                        if (amministratore != null && !amministratore.isEmpty()) {
                            Map<String, Object> primaRiga = amministratore.get(0);

                            // Estrarre i valori correttamente
                            nome = (String) primaRiga.get("Nome");
                            cognome = (String) primaRiga.get("Cognome");
                            codiceFiscale = (String) primaRiga.get("Codicefiscale");
                            email = (String) primaRiga.get("Email");
                            nascita = (String) primaRiga.get("Email");
                            // Matricola potrebbe essere Integer, Long o un altro tipo numerico
                            matricola = ((Number) primaRiga.get("Matricola")).intValue();

                            corsoDiStudio = (String) primaRiga.get("corsodistudio");

                            // Creazione dell'oggetto Studente con i dati recuperati
                            sessioneA = new Studente(nome, cognome, nascita, codiceFiscale, email, matricola, corsoDiStudio);

                            System.out.println("Login effettuato con successo per: " + sessioneA.getNome() + " " + sessioneA.getCognome());
                            loggato = true;
                        } else {
                            System.out.println("Nome o password errati.");
                        }
                        break;
                    case 2:
                        // Esegui una query
                        System.out.print("Inserisci nome: ");
                        nome = scanner.nextLine();

                        System.out.print("Inserisci password: ");
                        password = scanner.nextLine();

                        query = "SELECT * FROM studente, corsidistudio WHERE studente.Fkcorsodistudio=corsidistudio.id and studente.Nome = \"" + nome + "\" AND studente.Password = \"" + password + "\"";

                        List<Map<String, Object>> utente = dbConnection.eseguiQuery(query);

                        if (utente != null && !utente.isEmpty()) {
                            Map<String, Object> primaRiga = utente.get(0);

                            // Estrarre i valori correttamente
                            nome = (String) primaRiga.get("Nome");
                            cognome = (String) primaRiga.get("Cognome");
                            codiceFiscale = (String) primaRiga.get("Codicefiscale");
                            email = (String) primaRiga.get("Email");
                            nascita = (String) primaRiga.get("Email");
                            // Matricola potrebbe essere Integer, Long o un altro tipo numerico
                            matricola = ((Number) primaRiga.get("Matricola")).intValue();

                            corsoDiStudio = (String) primaRiga.get("corsodistudio");

                            // Creazione dell'oggetto Studente con i dati recuperati
                            sessioneU = new Studente(nome, cognome, nascita, codiceFiscale, email, matricola, corsoDiStudio);

                            System.out.println("Login effettuato con successo per: " + sessioneU.getNome() + " " + sessioneU.getCognome());
                            loggato = true;
                        } else {
                            System.out.println("Nome o password errati.");
                        }
                        break;
                    case 3:
                        // Esci dal ciclo
                        loggato = true;
                        exit = true;
                        break;
                    default:
                        System.out.println("Opzione non valida!");
                }
            }


            while (!exit) {
                if (sessioneA != null) {
                    System.out.println("1. test query");
                    System.out.println("2. Inserici Aula");
                    System.out.println("3. Inserisci Tavolo");
                    System.out.println("4. Esci");
                    System.out.print("Seleziona un'opzione: ");
                    scelta = scanner.nextInt();
                    scanner.nextLine();  // Consumiamo la newline lasciata da nextInt()
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
                            query = "SELECT * FROM `edificio`";
                            List<Map<String, Object>> results3 = dbConnection.eseguiQuery(query);

                            // Stampa i risultati dei dipartimenti
                            for (Map<String, Object> row : results3) {
                                System.out.println(row);
                            }

                            // Chiedi l'ID del dipartimento
                            System.out.print("Inserisci id dell'edificio: ");
                            String idedificio = scanner.nextLine();

                            // Chiedi il nome dell'aula
                            System.out.print("Inserisci nome dell'aula: ");
                            String nomeaula = scanner.nextLine();

                            // Creazione della query per l'inserimento
                            query = "INSERT INTO `aula` (`fkedificio`, `nome`) VALUES (?, ?)";

                            // Utilizza una query preparata per evitare SQL injection
                            try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
                                // Imposta i parametri della query
                                stmt.setString(1, idedificio);  // Imposta l'ID del dipartimento
                                stmt.setString(2, nomeaula);        // Imposta il nome dell'aula

                                // Esegui la query di inserimento
                                int rowsAffected = stmt.executeUpdate();
                                System.out.println("Aula inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            // Esegui la query per ottenere le aule
                            query = "SELECT * FROM `aula`";
                            List<Map<String, Object>> results4 = dbConnection.eseguiQuery(query);

                            // Stampa i risultati delle aule
                            for (Map<String, Object> row : results4) {
                                System.out.println(row);
                            }

                            // Chiedi l'ID dell'aula
                            System.out.print("Inserisci id dell'aula: ");
                             idaula = scanner.nextLine();

                            // Chiedi il numero di posti del tevolo
                            System.out.print("Inserisci il numero di posti: ");
                             numeroposti = scanner.nextLine();

                            // Creazione della query per l'inserimento
                            query = "INSERT INTO `tavolo` (`numeroposti`, `fkaula`) VALUES (?, ?)";

                            // Utilizza una query preparata per evitare SQL injection
                            try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
                                // Imposta i parametri della query
                                stmt.setString(1, numeroposti);  // Imposta il numero di posti
                                stmt.setString(2, idaula);        // Imposta fk dell'aula

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


                } else {
                    //menu studente
                    System.out.println("1. inserisci prenotazione");
                    System.out.println("2. Esci");
                    System.out.print("Seleziona un'opzione: ");
                    scelta = scanner.nextInt();
                    scanner.nextLine();  // Consumiamo la newline lasciata da nextInt()
                    switch (scelta) {
                        case 1:
                            //fare
                            break;
                        case 2:
                            // Esci dal ciclo
                            exit = true;
                            break;
                        default:
                            System.out.println("Opzione non valida!");
                    }
                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
