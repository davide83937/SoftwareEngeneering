import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class Sistema {

    DatabaseConnection dbConnection = null;
    private static Sistema sistema;
    private Prenotazione prenotazioneCorrente;
    private Aula aulaCorrente;
    private Tavolo tavoloCorrente;
    private LinkedList<Aula> listaAule;
    public LinkedList<Disponibilita1> disponibilita;
    public  LinkedList<Prenotazione> prenotazioni;


    public Sistema() throws SQLException {
        this.listaAule = new LinkedList<>();
        this.prenotazioni = new LinkedList<>();
        this.disponibilita = new LinkedList<>();
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


    public void load_disponibilita1(int aulaId, String data) throws SQLException{
        String query = null;
        query = "SELECT p.id, p.fktavolo " +
        "FROM postazione1 p " +
                "JOIN tavolo1 t ON p.fktavolo = t.id " +
                "WHERE t.fkaula = ? " +
                "AND p.id NOT IN ( " +
                "    SELECT pren.fkpostazione " +
                "    FROM prenotazione2 pren " +
                "    WHERE pren.data = ? " +
                ")";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        stmt.setInt(1, aulaId);
        stmt.setDate(2, Date.valueOf(data));

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            Disponibilita1 d1 = new Disponibilita1(rs.getInt("id"), rs.getInt("fktavolo"));
            disponibilita.add(d1);
        }
    }



    public static Sistema getInstance() throws SQLException {
        if (sistema == null)
            sistema = new Sistema();
        else
            System.out.println("Istanza già creata");
        return sistema;
    }


    public void inserimentoDatiAula(int id_edificio, String nome_aula, int id_aula){
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

            Aula aula = this.listaAule.stream().filter(a -> a.getId() == tavoloCorrente.getCodiceaula()).findFirst().orElse(null);
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

    public void inserimentoDatiTavolo(int id_tavolo, int id_aula, int n_posti){
        this.tavoloCorrente = new Tavolo(id_tavolo, id_aula, n_posti);
    }

    public void terminaInserimentoTavolo(DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO `tavolo1` (`id`, `fkaula`, `numero_postazioni`) VALUES (?, ?, ?)";

        // Utilizza una query preparata per evitare SQL injection
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            // Imposta il numero di posti
            stmt.setInt(1, this.tavoloCorrente.getId());        // Imposta fk dell'aula
            stmt.setInt(2, this.tavoloCorrente.getCodiceaula());
            stmt.setInt(3, this.tavoloCorrente.getN_postazioni());
            // Esegui la query di inserimento

            int rowsAffected = stmt.executeUpdate();
            String query1 = null;
            query1 = "INSERT INTO `postazione1` (`id`, `fktavolo`, `fkaula`) VALUES (?, ?, ?)";

            for(int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++){
                try (PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query1)) {
                    stmt1.setInt(2, this.tavoloCorrente.getId());
                    stmt1.setInt(3, this.tavoloCorrente.getCodiceaula());
                    stmt1.setInt(1, i);
                    rowsAffected = stmt1.executeUpdate();
                    System.out.println("Postazione inserita, rows affected: " + rowsAffected);
                }
            }
            String query2 = null;
            query2 = "UPDATE aula1 SET numerotavoli = numerotavoli + 1 WHERE id = "+this.tavoloCorrente.getCodiceaula();
            PreparedStatement stmt2 = dbConnection.conn.prepareStatement(query2);
            rowsAffected = stmt2.executeUpdate();

            Aula aula = this.listaAule.stream().filter(a -> a.getId() == tavoloCorrente.getCodiceaula()).findFirst().orElse(null);
            if(aula != null){
                Tavolo tavolo = aula.getLista_tavoli().stream().filter(tavolo1 -> tavolo1.getId() == this.tavoloCorrente.getId()).findFirst().orElse(null);
                if (tavolo == null){
                    for (int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++){
                        Postazione p = new Postazione(i, this.tavoloCorrente.getId(), this.tavoloCorrente.getCodiceaula());
                        this.tavoloCorrente.getLista_postazioni().add(p);
                    }
                }
            }
           System.out.println(this.listaAule);
            System.out.println("Tavolo inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {  // Violazione di chiave primaria
                System.err.println("Aula inserita non esistente o tavolo con lo stesso ID gia esistente!");
            } else {
                System.err.println("Errore SQL: " + e.getMessage());
            }
        }
    }

    public void inserisciDatiPrenotazione(int id_studente_prenotato, int id_aula, int id_tavolo, int postazione, int materia, LocalDate data, int  turno){
        Random rand = new Random();
        int codPrenotazione = rand.nextInt(10000000);
        this.prenotazioneCorrente = new Prenotazione(id_studente_prenotato, codPrenotazione, data, materia, id_aula, id_tavolo, postazione, turno);
        System.out.println(this.prenotazioneCorrente);
    }
    public void terminaPrenotazione(DatabaseConnection dbConnection){

        String query;
        query = "INSERT INTO `prenotazione2` (`id`, `fkstudente`, `fkpostazione`, `fktavolo`, `fkaula`, `data`, `fkturno`, `fkmateria`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, this.prenotazioneCorrente.getCodicePrenotazione());  // Imposta l'ID del dipartimento
            stmt.setInt(2, this.prenotazioneCorrente.getId_stud_prenotato());        // Imposta il nome dell'aula
            stmt.setInt(3, this.prenotazioneCorrente.getId_postazione());
            stmt.setInt(4, this.prenotazioneCorrente.getId_tavolo());
            stmt.setInt(5, this.prenotazioneCorrente.getId_aula());
            stmt.setDate(6, java.sql.Date.valueOf(this.prenotazioneCorrente.getData()));
            stmt.setInt(7, this.prenotazioneCorrente.getTurno());
            stmt.setInt(8, this.prenotazioneCorrente.getMateria());

            // Esegui la query di inserimento
            int rowsAffected = stmt.executeUpdate();

            prenotazioni.add(this.prenotazioneCorrente);
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








