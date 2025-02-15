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
        //sistema.load();

        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConnection = null;
        Studente sessioneU=null;
        Amministratore sessioneA = null; //per ora classe amministratore non utilizzata da implementare
        String nome,cognome,codiceFiscale,email,nascita,corsoDiStudio,query;
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
                            sistema.load_disponibilita();
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
                            if(conferma == 1){
                                sistema.terminaInserimentoAula(dbConnection);
                            }else {
                                break;
                            }
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
                            System.out.println("Seleziona una data formato yyyy-MM-dd");
                            /*LinkedList<LocalDate> date = new LinkedList<>();
                            for(Disponibilita d: Sistema.getInstance().disponibilita){
                                if (!d.isOccupato1() || !d.isOccupato2()){
                                    date.add(d.getData_disponibile());
                                }
                            }
                            // Creiamo un HashSet per rimuovere i duplicati
                            Set<LocalDate> dSenzaDuplicati = new HashSet<>(date);
                            // Convertiamo di nuovo in una lista
                            LinkedList<LocalDate> dateSenzaDuplicati = new LinkedList<>(dSenzaDuplicati);
                            for (LocalDate ld: dateSenzaDuplicati){
                                System.out.println(ld);
                            }*/
                            String data_scelta = scanner.nextLine();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate data = null;
                            try {
                                // Converti la stringa in un oggetto LocalDate
                                data = LocalDate.parse(data_scelta, formatter);

                                // Stampa la data per conferma
                                System.out.println("Data inserita: " + data);
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato data non valido. Assicurati che la data sia nel formato yyyy-MM-dd.");
                            }
                            /*
                            LinkedList<Integer> id_aule_disponibili = new LinkedList<>();*/
                            System.out.println("Seleziona un aula, scrivi il codice indicato");

                            for (Aula a: sistema.getListaAule()){
                                System.out.println("Aula -> "+a.getId()+", Nome -> "+a.getNome()+", Edificio -> "+a.getEdificio());
                            }
                            /*for(Disponibilita d: sistema.disponibilita){
                                if(d.getData_disponibile().equals(data) && (!d.isOccupato1()||!d.isOccupato2())){
                                    id_aule_disponibili.add(d.getId_aula());
                                }
                            }
                            Set<Integer> id_senza_duplicati = new HashSet<>(id_aule_disponibili);
                            // Convertiamo di nuovo in una lista
                            LinkedList<Integer> idSenzaDuplicati = new LinkedList<>(id_senza_duplicati);

                            for (Integer idaula: idSenzaDuplicati){
                                for(Aula a: Sistema.getInstance().getListaAule()){
                                    if(idaula == a.getId()){
                                        System.out.println("Aula -> "+a.getNome()+", Codice -> "+idaula+", Edificio -> "+a.getEdificio());
                                    }
                                }
                            }*/

                            int aula_scelta = scanner.nextInt();
                            /*for(Disponibilita d: sistema.disponibilita){
                                if(d.getId_aula() == aula_scelta && d.getData_disponibile().equals(data)){
                                    System.out.println("Postazione -> "+d.getId_postazione()+", Tavolo "+d.getId_tavolo());
                                    if(!d.isOccupato1()){
                                        System.out.println("Disponibile dalle "+ d.getOrario_inizio_1()+" alle "+d.getOrario_fine_1());
                                    }
                                    if(!d.isOccupato2()){
                                        System.out.println("Disponibile dalle "+ d.getOrario_inizio_2()+ " alle "+ d.getOrario_fine_2());
                                    }
                                }
                            }*/
                            sistema.load_disponibilita1(aula_scelta, data_scelta);

                            for (Disponibilita1 d1: sistema.disponibilita1){
                                System.out.println("Posto -> "+d1.id_postazione);
                            }

                            System.out.println("Seleziona il tavolo, inserisci il codice");
                            int tavolo_scelto = scanner.nextInt();
                            System.out.println("Seleziona il posto, inserisci il codice");
                            int postazione_scelta = scanner.nextInt();
                            System.out.println("Selezione la fascia oraria, digita 1 per la mattina, 2 per il pomeriggio");
                            int fascia_scelta = scanner.nextInt();
                            Time ora_di_inizio = null;
                            Time ora_di_fine = null;


                            for(Disponibilita d: sistema.disponibilita){
                                if(d.getId_tavolo() == tavolo_scelto && d.getId_postazione() == postazione_scelta){
                                    if(fascia_scelta == 1){
                                        ora_di_inizio = d.getOrario_inizio_1();
                                        ora_di_fine = d.getOrario_fine_1();
                                    }
                                    if(fascia_scelta == 2){
                                        ora_di_inizio = d.getOrario_inizio_2();
                                        ora_di_fine = d.getOrario_fine_2();
                                    }
                                }
                            }

                            sistema.inserisciDatiPrenotazione(sessioneU.getMatricola(),aula_scelta, tavolo_scelto, postazione_scelta, 1, data, ora_di_inizio, ora_di_fine);

                            System.out.println("Confermi la prenotazione?");
                            System.out.println("1) Si");
                            System.out.println("2) No");
                            conferma = scanner.nextInt();
                            if(conferma == 1){
                                sistema.terminaPrenotazione(dbConnection, fascia_scelta);
                            }
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
