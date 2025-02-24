import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Sistema {

    DatabaseConnection dbConnection = null;
    private static Sistema sistema;
    private Prenotazione prenotazioneCorrente;
    private Aula aulaCorrente;
    private LinkedList<Aula> listaAule;
    Disponibilita1 d;
    Aula a;
    public  LinkedList<Prenotazione> prenotazioni;
    private LinkedList<Prenotazione> prenotazioniCorrenti;
    public LinkedList<Studente> studenti;
    Scanner scanner = new Scanner(System.in);

    public Sistema() throws SQLException {
        this.listaAule = new LinkedList<>();
        this.prenotazioni = new LinkedList<>();

        this.studenti = new LinkedList<>();
        dbConnection = new DatabaseConnection();
    }

    public LinkedList<Aula> getListaAule() {
        return listaAule;
    }

    public Prenotazione getPrenotazioneCorrente() {
        return prenotazioneCorrente;
    }

    public void load() throws SQLException {
        String query = null;
        query = "SELECT * FROM `aula1`";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int id_edificio = rs.getInt("fkedificio");
            String nome = rs.getString("nome");
            int n_tavoli = rs.getInt("numerotavoli");
            int n_postazioni = rs.getInt("numeropostazioni");

            Aula a = new Aula(id, id_edificio, nome, n_tavoli, n_postazioni);
            listaAule.add(a);
        }

        String query1 = null;
        query1 = "SELECT * FROM `tavolo1`";
        PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query1);
        ResultSet rs1 = stmt1.executeQuery();

        while (rs1.next()) {
            int id = rs1.getInt("id");
            int n_posti = rs1.getInt("numero_postazioni");
            int id_aula = rs1.getInt("fkaula");
            Tavolo t = new Tavolo(id, id_aula, n_posti);

            String query2 = "SELECT * FROM `postazione1` WHERE `fkaula` ="+id_aula;
            PreparedStatement stmt2 = dbConnection.conn.prepareStatement(query2);
            ResultSet rs2 = stmt2.executeQuery();
            LinkedList<Postazione> postazioni = new LinkedList<Postazione>();
            while (rs2.next()) {
                int id_post = rs2.getInt("id");
                int id_tav = rs2.getInt("fktavolo");
                int id_aula_post = rs2.getInt("fkaula");
                Postazione p = new Postazione(id_post, id_tav, id_aula_post);
                postazioni.add(p);
            }
            t.setLista_postazioni(postazioni);
            for (Aula a: listaAule){
                a.getLista_tavoli().add(t);
            }
        }
    }

    public void load_prenotazioni(int matricola, int aula, int turno) throws SQLException{
        String query = null;
        if(aula == 0 || turno == 0) {
            query = "SELECT id, fkstudente, fkpostazione, fktavolo, fkaula, data, fkturno, stato\n" +
                    "FROM PRENOTAZIONE2\n" +
                    "WHERE fkstudente = " + matricola;
        }
        else {query = "SELECT id, fkstudente, fkpostazione, fktavolo, fkaula, data, fkturno, stato\n" +
                "FROM PRENOTAZIONE2\n" +
                "WHERE fkaula = " + aula+" AND fkturno = "+turno;}

        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int fkstudente = rs.getInt("fkstudente");
            int fkpostazione = rs.getInt("fkpostazione");
            int fktavolo = rs.getInt("fktavolo");
            int fkaula = rs.getInt("fkaula");
            String data = rs.getDate("data").toString();
            int fkturno = rs.getInt("fkturno");
            String stato = rs.getString("stato");

            Prenotazione p = new Prenotazione(fkstudente, id, stringToDate(data), 1, fkaula, fktavolo, fkpostazione, fkturno, stato);
            prenotazioni.add(p);
        }


    }

    public LocalDate stringToDate(String dataa){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = null;
        try {
            data = LocalDate.parse(dataa, formatter);
            System.out.println("Data inserita: " + data);
        } catch (DateTimeParseException e) {
            System.out.println("Formato data non valido. Assicurati che la data sia nel formato yyyy-MM-dd.");
        }
        return data;
    }


    public void inserisci_aula_data_nstudenti(String data_scelta, int codAula, int n_studenti) throws SQLException {
        stringToDate(data_scelta);
        d.inserisci_aula_data_nstudenti(data_scelta, codAula, n_studenti);
    }

    public void loadStudenti() throws SQLException {
        String query = null;
        query = "SELECT * FROM `studente`";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int matricola = rs.getInt("Matricola");
            String nome = rs.getString("Nome");
            String cognome = rs.getString("Cognome");
            Studente s = new Studente(matricola, nome, cognome);
            studenti.add(s);
        }
    }

    public static Sistema getInstance() throws SQLException {
        if (sistema == null)
            sistema = new Sistema();
        else
            System.out.println("Istanza già creata");
        return sistema;
    }


    public void inserimentoDatiAula(int id_edificio, String nome_aula, int id_aula) throws SQLException {
            this.aulaCorrente = new Aula(id_aula, id_edificio, nome_aula);
    }

    public void terminaInserimentoAula(DatabaseConnection dbConnection){

        String query;

        // Creazione della query per l'inserimento
        query = "INSERT INTO `aula1` (`fkedificio`, `nome`, `id`, `numerotavoli`, `numeropostazioni`) VALUES (?, ?, ?, ?, ?)";

        // Utilizza una query preparata per evitare SQL injection
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, this.aulaCorrente.getEdificio());  // Imposta l'ID del dipartimento
            stmt.setString(2, this.aulaCorrente.getNome());        // Imposta il nome dell'aula
            stmt.setInt(3, this.aulaCorrente.getId());
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);

            // Esegui la query di inserimento
            int rowsAffected = stmt.executeUpdate();

            Aula aula = this.listaAule.stream().filter(a -> a.getId() == a.getTavoloCorrente().getCodiceaula()).findFirst().orElse(null);
            if(aula == null){this.listaAule.add(this.aulaCorrente);}

            System.out.println("Aula inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {  // Violazione di chiave primaria
                System.err.println("Aula con lo stesso ID gia esistente!");
            } else {
                System.err.println("Errore SQL: " + e.getMessage());
            }
        }
    }

    public void inserimentoDatiTavolo(int id_tavolo, int id_aula, int n_posti) throws SQLException {
        a = new Aula();
        Aula aula = listaAule.stream().filter(a->a.getId()==id_aula).findFirst().orElse(null);
        if (aula != null){
              a.inserimentoDatiTavolo(id_tavolo, aula.getId(), n_posti);
        }else {
            System.out.println("L'aula non esiste");
        }
    }

    public void terminaInserimentoTavolo(DatabaseConnection dbConnection) throws SQLException {
        a = new Aula();
        a.terminaInserimentoTavolo(dbConnection);
    }


    public void inserisciDatiPrenotazione(LinkedList<Integer> studenti, int id_aula, String data) throws SQLException {
        d = new Disponibilita1();
        int count = 1;
        if(studenti.size()>1) {
            count = 0;
            for (Integer s : studenti) {
                System.out.println("DENTRO");
                for (Studente st : sistema.studenti) {
                    if (s == st.getMatricola()) {
                        count++;
                        System.out.println(count);
                        break;
                    }
                }
            }
        }
            System.out.println(count);
            System.out.println(studenti.size());
        if (count == studenti.size()){

            inserisci_aula_data_nstudenti(data, id_aula, studenti.size());
            for (Disponibilita1 d1: d.disponibilita){
                System.out.println("Posto -> "+d1.id_postazione+", Tavolo "+d1.id_tavolo+", Turno "+d1.turno);
            }

            System.out.println("Seleziona il tavolo, inserisci il codice");
            int tavolo_scelto = scanner.nextInt();
            LinkedList<Integer> postazioniScelte = new LinkedList<>();

            for (int i = 0; i<studenti.size(); i++) {
                System.out.println("Seleziona il posto, inserisci il codice");
                int postazione_scelta = scanner.nextInt();
                postazioniScelte.add(postazione_scelta);
            }

            System.out.println("Selezione la turno, digita 1 per la mattina, 2 per il pomeriggio, 3 per la sera");
            int fascia_scelta = scanner.nextInt();

            Random rand = new Random();
            String stato="CONFERMATO";
            prenotazioniCorrenti = new LinkedList<>();
            for (Integer studente: studenti){
                int codPrenotazione = rand.nextInt(10000000);
                this.prenotazioneCorrente = new Prenotazione(studente, codPrenotazione, stringToDate(data), 1, id_aula, tavolo_scelto, postazioniScelte.get(studenti.indexOf(studente)), fascia_scelta, stato);
                this.prenotazioniCorrenti.add(prenotazioneCorrente);
                stato = "IN ATTESA";
            }
        }else{
            System.out.println("Qualche studente non è stato trovato");
        }
    }


    public void terminaPrenotazione(DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO `prenotazione2` (`id`, `fkstudente`, `fkpostazione`, `fktavolo`, `fkaula`, `data`, `fkturno`, `fkmateria`, `stato`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        for(Prenotazione p: this.prenotazioniCorrenti){
            try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
                // Imposta i parametri della query
                stmt.setInt(1, p.getCodicePrenotazione());  // Imposta l'ID del dipartimento
                stmt.setInt(2, p.getId_stud_prenotato());        // Imposta il nome dell'aula
                stmt.setInt(3, p.getId_postazione());
                stmt.setInt(4, p.getId_tavolo());
                stmt.setInt(5, p.getId_aula());
                stmt.setDate(6, java.sql.Date.valueOf(p.getData()));
                stmt.setInt(7, p.getTurno());
                stmt.setInt(8, p.getMateria());
                stmt.setString(9, p.getStato());

                // Esegui la query di inserimento
                int rowsAffected = stmt.executeUpdate();

                prenotazioni.add(p);
                System.out.println("Prenotazione inserita con successo! (" + rowsAffected + " riga/i inserita/e)");

            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {  // Codice di errore MySQL per violazione UNIQUE KEY
                    System.out.println("Questa prenotazione esista già");
                } else {
                    // Rilancia l'eccezione se non è una violazione della UNIQUE KEY
                    e.printStackTrace();
                }
            }
        }
    }





    public void modificaStatoPrenotazione(int id_prenotazione, String nuovo_stato) throws SQLException {
        String query = "";
        query = "UPDATE prenotazione2 SET stato = ? WHERE id = "+id_prenotazione;
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        stmt.setString(1, nuovo_stato);
        int rowsAffected = stmt.executeUpdate();
        System.out.println("Prenotazione modificata con successo! (" + rowsAffected + " riga/i inserita/e)");
    }


}








