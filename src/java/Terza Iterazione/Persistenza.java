import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Persistenza {

    public Persistenza(){

    }

    public void load_tutte_prenotazioni(LinkedList<Prenotazione> prenotazioni, DatabaseConnection dbConnection) throws SQLException {
        String query = null;
        query = "SELECT * FROM `prenotazione2`";
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

            Prenotazione p = new Prenotazione(fkstudente, id, Sistema.getInstance().stringToDate(data), 1, fkaula, fktavolo, fkpostazione, fkturno, stato);
            prenotazioni.add(p);
        }
    }


    public void load_prenotazioni(int matricola, int aula, int turno, LinkedList<Prenotazione> prenotazioni, DatabaseConnection dbConnection) throws SQLException{
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

            Prenotazione p = new Prenotazione(fkstudente, id, Sistema.getInstance().stringToDate(data), 1, fkaula, fktavolo, fkpostazione, fkturno, stato);
            prenotazioni.add(p);
        }
    }


    public void inserisciAulaDatabase(Aula a, DatabaseConnection dbConnection){
        String query;

        // Creazione della query per l'inserimento
        query = "INSERT INTO `aula1` (`fkedificio`, `nome`, `id`, `numerotavoli`, `numeropostazioni`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, a.getEdificio());  // Imposta l'ID del dipartimento
            stmt.setString(2, a.getNome());        // Imposta il nome dell'aula
            stmt.setInt(3, a.getId());
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);

            // Esegui la query di inserimento
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Aula inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {  // Violazione di chiave primaria
                System.err.println("Aula con lo stesso ID gia esistente!");
            } else {
                System.err.println("Errore SQL: " + e.getMessage());
            }
        }
    }


    public void inserisciPrenotazioneDatabase(Prenotazione p, DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO `prenotazione2` (`id`, `fkstudente`, `fkpostazione`, `fktavolo`, `fkaula`, `data`, `fkturno`, `fkmateria`, `stato`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, p.getCodicePrenotazione());
            stmt.setInt(2, p.getId_stud_prenotato());
            stmt.setInt(3, p.getId_postazione());
            stmt.setInt(4, p.getId_tavolo());
            stmt.setInt(5, p.getId_aula());
            stmt.setDate(6, java.sql.Date.valueOf(p.getData()));
            stmt.setInt(7, p.getTurno());
            stmt.setInt(8, p.getMateria());
            stmt.setString(9, p.getStato());

            // Esegui la query di inserimento
            int rowsAffected = stmt.executeUpdate();
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

    public void modificaStatoPrenotazioneDatabase(int id_prenotazione, String nuovo_stato, DatabaseConnection dbConnection) throws SQLException {
        String query = "";
        query = "UPDATE prenotazione2 SET stato = ? WHERE id = "+id_prenotazione;
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        stmt.setString(1, nuovo_stato);
        int rowsAffected = stmt.executeUpdate();
        System.out.println("Prenotazione modificata con successo! (" + rowsAffected + " riga/i inserita/e)");
    }


    public void inserisciBloccoDatabase(Blocco b, DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO `blocco` (`matricola`, `data_inizio`, `data_fine`) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = dbConnection.conn.prepareStatement(query)){
            stmt.setInt(1, b.getMatricola());
            stmt.setDate(2, Date.valueOf(b.getData_inizio()));
            stmt.setDate(3, Date.valueOf(b.getData_fine()));
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Blocco inserito con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePrenotazione(int matricola, DatabaseConnection dbConnection){
        String query;
        System.out.println(matricola);
        query = "DELETE FROM `prenotazione2` WHERE fkstudente = ?";
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)){
            stmt.setInt(1, matricola);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Prenotazione eliminata! (" + rowsAffected + " riga eliminata)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void load(LinkedList<Aula> listaAule, DatabaseConnection dbConnection) throws SQLException {
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

    public void load_blocchi(LinkedList<Blocco> lista_blocchi, DatabaseConnection dbConnection) throws SQLException {
        String query = null;
        query = "SELECT * FROM `blocco`";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id_blocco = rs.getInt("id_blocco");
            int matricola = rs.getInt("matricola");
            String data_fine = rs.getString("data_fine");

            Blocco b = new Blocco(id_blocco, matricola, Sistema.getInstance().stringToDate(data_fine));
            lista_blocchi.add(b);
        }
    }


    public void load_segnalazioni(LinkedList<Segnalazione> lista_segnalazioni, DatabaseConnection dbConnection) throws SQLException {
        String query = null;
        query = "SELECT * FROM `segnalazione`";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id_segnalazione = rs.getInt("id_segnalazione");
            int matricola = rs.getInt("matricola");
            String oggetto = rs.getString("oggetto");
            String testo = rs.getString("testo");
            String stato = rs.getString("stato");

            Segnalazione s = new Segnalazione(id_segnalazione, matricola, oggetto, testo, stato);
            lista_segnalazioni.add(s);
        }
    }

    public void insertSegnalazioneOnDatabase(Segnalazione s, DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO segnalazione (`matricola`, `oggetto`, `testo`, `stato`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement((query))){
            stmt.setInt(1, s.getMatricola());
            stmt.setString(2, s.getOggetto());
            stmt.setString(3, s.getTesto());
            stmt.setString(4, s.getStato());
            int rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificaStatoDatabase(int id, String stato, DatabaseConnection dbConnection) throws SQLException {
        String query = "";
        query = "UPDATE segnalazione SET stato = ? WHERE id_segnalazione = "+id;
        System.out.println(query);
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        stmt.setString(1, stato);
        int rowsAffected = stmt.executeUpdate();
        System.out.println("Segnalazione modificata con successo! (" + rowsAffected + " riga/i inserita/e)");
    }

}
