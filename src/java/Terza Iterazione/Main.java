import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Sistema sistema = Sistema.getInstance();
        SegnalazioniManager sm = SegnalazioniManager.getInstance();
        //sistema.load();
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConnection = null;
        Studente sessioneU=null;
        Amministratore sessioneA = null; //per ora classe amministratore non utilizzata da implementare
        String nome,cognome,codiceFiscale,email,nascita,corsoDiStudio, stato, query;
        int numeroposti, conferma, id_tavolo;
        int matricola, scelta;
        try {
            // Instanzia l'oggetto DatabaseConnection
            dbConnection = new DatabaseConnection();

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
                        query = "SELECT * FROM amministratore WHERE amministratore.nome = \"" + nome + "\" AND amministratore.password = \"" + password + "\"";
                        List<Map<String, Object>> amministratore = dbConnection.eseguiQuery(query);

                        if (amministratore != null && !amministratore.isEmpty()) {
                            Map<String, Object> primaRiga = amministratore.get(0);
                            nome = (String) primaRiga.get("Nome");
                            cognome = (String) primaRiga.get("Cognome");
                            codiceFiscale = (String) primaRiga.get("Codicefiscale");
                            email = (String) primaRiga.get("Email");
                            nascita = (String) primaRiga.get("Email");
                            sessioneA = new Amministratore(nome, cognome, nascita, codiceFiscale, email);
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
                            sistema.load();
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
                    System.out.println("2. Inserisci Aula");
                    System.out.println("3. Inserisci Tavolo");
                    System.out.println("4. Gestisci Segnalazioni");
                    System.out.println("5. Blocca uno studente");
                    System.out.println("6. Esci");
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
                            query = "SELECT * FROM `dipartimento`";
                            List<Map<String, Object>> results3 = dbConnection.eseguiQuery(query);
                            // Stampa i risultati dei dipartimenti
                            for (Map<String, Object> row : results3) {
                                System.out.println(row);
                            }
                            // Chiedi l'ID del dipartimento
                            System.out.print("Inserisci id dell'edificio: ");
                            int idedificio = Integer.parseInt(scanner.nextLine());

                            // Chiedi il nome dell'aula
                            System.out.print("Inserisci nome dell'aula: ");
                            String nomeaula = scanner.nextLine();

                            System.out.print("Inserisci id dell'aula: ");
                            int id_aula = Integer.parseInt(scanner.nextLine());

                            Sistema.getInstance().inserimentoDatiAula(idedificio, nomeaula, id_aula);

                            System.out.println("Confermi l'inserimento?");
                            System.out.println("1) Si");
                            System.out.println("2) No");
                            conferma = Integer.parseInt(scanner.nextLine());
                            if(conferma == 1){sistema.terminaInserimentoAula();}
                            break;

                        case 3:
                            // Chiedi l'ID dell'aula
                            System.out.print("Inserisci id dell'aula: ");
                            id_aula = Integer.parseInt(scanner.nextLine());
                            // Chiedi il numero di posti del tevolo
                            System.out.print("Inserisci id del tavolo: ");
                            id_tavolo = Integer.parseInt(scanner.nextLine());
                            System.out.println("Inserisci il numero di posti");
                            numeroposti = scanner.nextInt();
                            sistema.inserimentoDatiTavolo(id_tavolo, id_aula, numeroposti);

                            System.out.println("Confermi l'inserimento?");
                            System.out.println("1) Si");
                            System.out.println("2) No");
                            conferma = scanner.nextInt();
                            if(conferma == 1){
                                sistema.terminaInserimentoTavolo(dbConnection);
                            }
                            break;
                        case 4:

                            sm.load_segnalazioni();
                            sm.vedi_segnalazioni();
                            System.out.println("Inserisci 1 per prendere in carico una segnalazione");
                            System.out.println("Inserisci 2 per accettare una segnalazione");
                            System.out.println("Inserisci 3 per rifiutare una segnalazione");
                            System.out.println("Inserisci 4 per uscire");
                            int scelta1 = scanner.nextInt();
                            switch (scelta1) {
                                case 1:
                                    sm.modificaStato("PRESA IN CARICO");
                                    break;
                                case 2:
                                    sm.modificaStato("ACCETTATA");
                                    break;
                                case 3:
                                    sm.modificaStato("RIFIUTATA");
                                    break;
                                case 4:
                                    break;
                            }
                            break;
                        case 5:
                            sistema.load_tutte_prenotazioni();
                            sistema.loadStudenti();
                            System.out.println("Inserisci la matricola dello studente che vuoi bloccare");
                            int matricola1 = scanner.nextInt();
                            System.out.println("Inserisci la data di fine blocco xxxx-yy-zz");
                            scanner.nextLine();
                            String data1 = scanner.nextLine();
                            sistema.bloccaStudente(matricola1, sistema.stringToDate(data1));
                            break;
                        case 6:
                            // Esci dal ciclo
                            exit = true;
                            break;
                        default:
                            System.out.println("Opzione non valida!");
                    }

                } else {
                    //menu studente
                    LinkedList<Integer> studenti;
                    sistema.loadStudenti();
                    sistema.load_blocchi();
                    System.out.println("1. inserisci prenotazione");
                    System.out.println("2. inserisci prenotazione multipla");
                    System.out.println("3. visualizza le mie prenotazioni ");
                    System.out.println("4. invia una segnalazione");
                    System.out.println("5. Esci");
                    System.out.print("Seleziona un'opzione: ");
                    scelta = scanner.nextInt();
                    scanner.nextLine();  // Consumiamo la newline lasciata da nextInt()
                    switch (scelta) {
                        case 1:

                            System.out.println("Seleziona una data formato yyyy-MM-dd");
                            String data_scelta = scanner.nextLine();

                            for (Aula a: sistema.getListaAule()){
                                System.out.println("Aula -> "+a.getId()+", Nome -> "+a.getNome()+", Edificio -> "+a.getEdificio());
                            }

                            System.out.println("Inserisci un aula");
                            int aula_scelta = scanner.nextInt();
                            studenti = new LinkedList<>();
                            studenti.addFirst(sessioneU.getMatricola());
                            boolean ok = sistema.inserisciDatiPrenotazione(studenti, aula_scelta, data_scelta);

                            if(ok) {
                                System.out.println("Confermi la prenotazione?");
                                System.out.println("1) Si");
                                System.out.println("2) No");
                                conferma = scanner.nextInt();
                                if (conferma == 1) {
                                    sistema.terminaPrenotazione();
                                }
                            }
                            break;

                        case 2:

                            System.out.println("Seleziona numero studenti oltre te");
                            int n_studenti = scanner.nextInt();
                            studenti = new LinkedList<>();
                            for (int i = 0; i < n_studenti; i++){
                                System.out.println("Scrivi la matricola del tuo amichetto");
                                int matricola_scelta = scanner.nextInt();
                                studenti.add(matricola_scelta);
                            }

                            scanner.nextLine();
                            System.out.println("Seleziona una data formato yyyy-MM-dd");
                            String data_scelta1 = scanner.nextLine();

                            for (Aula a: sistema.getListaAule()){
                                System.out.println("Aula -> "+a.getId()+", Nome -> "+a.getNome()+", Edificio -> "+a.getEdificio());
                            }

                            System.out.println("Inserisci un aula");
                            int aula_scelta1 = scanner.nextInt();

                            studenti.addFirst(sessioneU.getMatricola());
                            boolean ok1 = sistema.inserisciDatiPrenotazione(studenti, aula_scelta1, data_scelta1);
                            if(ok1) {
                                System.out.println("Confermi la prenotazione?");
                                System.out.println("1) Si");
                                System.out.println("2) No");
                                conferma = scanner.nextInt();
                                if (conferma == 1) {
                                    sistema.terminaPrenotazione();
                                }
                            }
                            break;

                        case 3:
                             sistema.load_prenotazioni(sessioneU.getMatricola(), 0, 0);
                             int count = 0;
                             for (Prenotazione p: sistema.prenotazioni){
                                 if(p.getStato().equals("IN ATTESA")) {
                                     System.out.println(p);
                                     System.out.println(p.getStato());
                                     count++;
                                 }
                             }

                             System.out.println("Premi");
                            System.out.println("1. Se vuoi accettare una prenotazione");
                            System.out.println("2. Se vuoi rifiutare una prenotazione");
                            System.out.println("3. Esci");
                            int id_prenotazione = 0;
                             int scelta1 =scanner.nextInt();
                             switch (scelta1){
                                 case 1:
                                     System.out.println("Inserisci il codice della prenotazione che vuoi accettare");
                                     id_prenotazione = scanner.nextInt();
                                     sistema.modificaStatoPrenotazione(id_prenotazione, "CONFERMATO");
                                     break;
                                 case 2:
                                     System.out.println("Inserisci il codice della prenotazione che vuoi rifiutare");
                                     id_prenotazione = scanner.nextInt();
                                     sistema.modificaStatoPrenotazione(id_prenotazione, "RIFIUTATO");
                                     break;
                                 case 3:
                                     break;

                             }


                            break;
                        case 4:
                            System.out.println("Inserisci oggetto segnalazione");
                            String oggetto = scanner.nextLine();
                            System.out.println("Inserisci messaggio");
                            String messaggio = scanner.nextLine();
                            sm.creaSegnalazione(sessioneU.getMatricola(), oggetto, messaggio);
                            break;
                        case 5:
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
