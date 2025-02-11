import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Sistema {

    DatabaseConnection dbConnection = null;
    private static Sistema sistema;
    private Prenotazione prenotazioneCorrente;
    private Aula aulaCorrente;
    private Tavolo tavoloCorrente;
    private LinkedList<Aula> listaAule;
    private  LinkedList<Tavolo> listaTavoli;


    private Sistema() throws SQLException {

        this.listaAule = new LinkedList<>();
        this.listaTavoli = new LinkedList<>();
        dbConnection = new DatabaseConnection();

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

            Aula a = new Aula(id, id_edificio, nome);
            listaAule.add(a);
        }

        String query1 = null;
        query = "SELECT * FROM `tavolo`";
        PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query1);
        ResultSet rs1 = stmt1.executeQuery();

        while (rs1.next()) {
            int id = rs1.getInt("id");
            int n_posti = rs1.getInt("numero posti");
            int id_aula = rs1.getInt("fkaula");

            Tavolo t = new Tavolo(id, id_aula, n_posti);
            listaTavoli.add(t);
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
        query = "INSERT INTO `aula` (`fkedificio`, `nome`, `id`) VALUES (?, ?, ?)";

        // Utilizza una query preparata per evitare SQL injection
        try (PreparedStatement stmt = dbConnection.conn.prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, this.aulaCorrente.getEdificio());  // Imposta l'ID del dipartimento
            stmt.setString(2, this.aulaCorrente.getNome());        // Imposta il nome dell'aula
            stmt.setInt(3, this.aulaCorrente.getId());


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
            this.listaTavoli.add(this.tavoloCorrente);
            System.out.println("Tavolo inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserisciDatiPrenotazione(int id_aula, int id_tavolo, int postazione, int materia, LocalDateTime data_ora_inizio, LocalDateTime data_ora_fine){
        if(prenotazioneCorrente != null){
            for (Aula a: listaAule) {
               if(a.getId() == id_aula){
                   Aula aula = a;
                   for(Tavolo t: aula.getLista_tavoli()){
                       if(t.getId() == id_tavolo){
                           Tavolo tavolo = t;
                           for(Integer p: tavolo.getLista_postazioni()){
                               if (p == id_tavolo){
                                   this.prenotazioneCorrente = new Prenotazione(123456789, data_ora_inizio, data_ora_fine, materia, aula.getId(), tavolo.getId(), p);
                               }
                           }
                       }
                   }
               }

            }
            }
            }
    public void terminaPrenotazione(){

    }


        }








