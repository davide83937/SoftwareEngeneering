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
    private  LinkedList<Tavolo> listaTavoli;
    public LinkedList<Postazione> postazioni;
    public LinkedList<LocalDate> date;
    public LinkedList<Disponibilita> disponibilita;


    public Sistema() throws SQLException {
        this.listaAule = new LinkedList<>();
        this.listaTavoli = new LinkedList<>();
        this.postazioni = new LinkedList<>();
        this.disponibilita = new LinkedList<>();
        dbConnection = new DatabaseConnection();
        this.date = new LinkedList<>();
        LocalDate start = LocalDate.of(2025, 2, 13);
        LocalDate end = LocalDate.of(2025, 2, 28);

        for (LocalDate data = start; !data.isAfter(end); data = data.plusDays(1)) {
            date.add(data);
        }
    }

    public LinkedList<Aula> getListaAule() {
        return listaAule;
    }

    public void load() throws SQLException {
        String query = null;
        query = "SELECT * FROM `aula`";
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
        query1 = "SELECT * FROM `tavolo`";
        PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query1);
        ResultSet rs1 = stmt1.executeQuery();

        while (rs1.next()) {
            int id = rs1.getInt("id");
            int n_posti = rs1.getInt("numero posti");
            int id_aula = rs1.getInt("fkaula");
            Tavolo t = new Tavolo(id, id_aula, n_posti);
            String query2 = "SELECT * FROM `postazione` WHERE `id_aula` ="+id_aula+" AND occupato = 0";
            PreparedStatement stmt2 = dbConnection.conn.prepareStatement(query2);
            ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next()) {
                int id_post = rs2.getInt("id");
                int id_tav = rs2.getInt("id_tavolo");
                int id_aula_post = rs2.getInt("id_aula");
                boolean occupato = rs2.getBoolean("occupato");
                Postazione p = new Postazione(id_post, id_tav, id_aula_post, occupato);
                postazioni.add(p);
            }
            t.setLista_postazioni(postazioni);
            listaTavoli.add(t);
        }
    }

    public void load_disponibilita() throws SQLException{
        String query = null;
        query = "SELECT * FROM disponibilità";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int id_tavolo = rs.getInt("id_tavolo");
            int id_aula = rs.getInt("id_aula");
            Date data = rs.getDate("data_disponibile");
            LocalDate data_disponibile = data.toLocalDate();

            Time orario_inizio_1 = rs.getTime("orario_inizio_1");
            Time orario_fine_1 = rs.getTime("orario_fine_1");
            Time orario_inizio_2 = rs.getTime("orario_inizio_2");
            Time orario_fine_2 = rs.getTime("orario_fine_2");
            Boolean occupato_1 = rs.getBoolean("occupato_1");
            Boolean occupato_2 = rs.getBoolean("occupato_2");

            Disponibilita d = new Disponibilita(id, id_tavolo, id_aula, data_disponibile, orario_inizio_1, orario_fine_1, orario_inizio_2, orario_fine_2, occupato_1, occupato_2);
            disponibilita.add(d);
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
        query = "INSERT INTO `aula` (`fkedificio`, `nome`, `id`, `numerotavoli`, `numeropostazioni`) VALUES (?, ?, ?, ?, ?)";

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
            this.listaAule.add(this.aulaCorrente);
            System.out.println("Aula inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserimentoDatiTavolo(int id_tavolo, int id_aula, int n_posti){
        this.tavoloCorrente = new Tavolo(id_tavolo, id_aula, n_posti);
    }

    public void terminaInserimentoTavolo(DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO `tavolo` (`id`, `fkaula`, `numero posti`) VALUES (?, ?, ?)";

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
            query1 = "INSERT INTO `postazione` (`id`, `id_tavolo`, `id_aula`, `occupato`) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query1);
            for(int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++){
                stmt1.setInt(2, this.tavoloCorrente.getId());        // Imposta fk dell'aula
                stmt1.setInt(3, this.tavoloCorrente.getCodiceaula());
                stmt1.setInt(1, i);
                stmt1.setBoolean(4, false);
                // Esegui la query di inserimento
                rowsAffected = stmt1.executeUpdate();
            }

            String query2 = null;
            query2 = "UPDATE aula SET numerotavoli = numerotavoli + 1 WHERE id = "+this.tavoloCorrente.getCodiceaula();
            PreparedStatement stmt2 = dbConnection.conn.prepareStatement(query2);
            rowsAffected = stmt2.executeUpdate();

            String query3 = null;
            query3 = "INSERT INTO disponibilità (id, id_tavolo, id_aula, data_disponibile, orario_inizio_1, orario_fine_1, orario_inizio_2, orario_fine_2, occupato_1, occupato_2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt3 = dbConnection.conn.prepareStatement(query3);
            for(int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++){
                for(LocalDate ld: date) {
                    stmt3.setInt(1, i);
                    stmt3.setInt(2, this.tavoloCorrente.getId());        // Imposta fk dell'aula
                    stmt3.setInt(3, this.tavoloCorrente.getCodiceaula());
                    stmt3.setDate(4, Date.valueOf(ld));
                    stmt3.setTime(5, Time.valueOf("08:00:00"));
                    stmt3.setTime(6, Time.valueOf("13:00:00"));
                    stmt3.setTime(7, Time.valueOf("13:00:00"));
                    stmt3.setTime(8, Time.valueOf("19:00:00"));
                    stmt3.setBoolean(9, false);
                    stmt3.setBoolean(10, false);
                    // Esegui la query di inserimento
                    rowsAffected = stmt3.executeUpdate();
                }
            }

            this.listaTavoli.add(this.tavoloCorrente);
            System.out.println("Tavolo inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserisciDatiPrenotazione(int id_studente_prenotato, int id_aula, int id_tavolo, int postazione, int materia, LocalDate data, Time ora_inizio, Time ora_fine){
        Random rand = new Random();
        int codPrenotazione = rand.nextInt(10000000);
        this.prenotazioneCorrente = new Prenotazione(id_studente_prenotato, codPrenotazione, data, ora_inizio, ora_fine, materia, id_aula, id_tavolo, postazione);
        System.out.println(this.prenotazioneCorrente);
    }
    public void terminaPrenotazione(DatabaseConnection dbConnection, int fascia){

        String query;
        query = "INSERT INTO `prenotazione` (`id`, `fkstudente`, `fktavolo`, `data`, `orainizio`, `orafine`, `fkmateria`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, this.prenotazioneCorrente.getCodicePrenotazione());  // Imposta l'ID del dipartimento
            stmt.setInt(2, this.prenotazioneCorrente.getId_stud_prenotato());        // Imposta il nome dell'aula
            stmt.setInt(3, this.prenotazioneCorrente.getId_tavolo());
            stmt.setDate(4, java.sql.Date.valueOf(this.prenotazioneCorrente.getData()));
            stmt.setTime(5, this.prenotazioneCorrente.getOrario_inizio());
            stmt.setTime(6, this.prenotazioneCorrente.getOrario_fine());
            stmt.setInt(7, this.prenotazioneCorrente.getMateria());

            // Esegui la query di inserimento
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Prenotazione inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
            String f = "occupato_"+fascia;
            query = "UPDATE disponibilità SET "+f+"= true" +" WHERE id ="+this.prenotazioneCorrente.getId_postazione()+ " and id_tavolo ="+ this.prenotazioneCorrente.getId_tavolo()+ " and id_aula ="+this.prenotazioneCorrente.getId_aula()+ " and data_disponibile = ?";
            PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query);
            stmt1.setDate(1, java.sql.Date.valueOf(this.prenotazioneCorrente.getData()));
            rowsAffected = stmt1.executeUpdate();
            System.out.println("Disponibilità aggiornata! (" + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


        }








