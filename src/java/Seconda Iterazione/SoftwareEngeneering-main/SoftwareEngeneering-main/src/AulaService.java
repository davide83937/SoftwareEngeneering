import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AulaService {

    private Aula aulaCorrente;
    private List<Aula> listaAule = new ArrayList<>();

    private Tavolo tavoloCorrente;
    private Disponibilita1 d;
    Prenotazione prenotazioneCorrente;
    LinkedList<Prenotazione> prenotazioniCorrenti;





    public void inserimentoDatiAula(int id_edificio, String nome_aula, int id_aula) throws SQLException {
        this.aulaCorrente = new Aula(id_aula, id_edificio, nome_aula);
    }

    // Metodo per terminare l'inserimento dell'aula nel database
    public void terminaInserimentoAula(DatabaseConnection dbConnection) {
        String query = "INSERT INTO `aula1` (`fkedificio`, `nome`, `id`, `numerotavoli`, `numeropostazioni`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
            // Imposta i parametri della query
            stmt.setInt(1, this.aulaCorrente.getEdificio());
            stmt.setString(2, this.aulaCorrente.getNome());
            stmt.setInt(3, this.aulaCorrente.getId());
            stmt.setInt(4, 0); // Numerotavoli
            stmt.setInt(5, 0); // Numeropostazioni

            // Esegui la query di inserimento
            int rowsAffected = stmt.executeUpdate();

            // Aggiungi l'aula alla lista se non è già presente
            boolean aulaEsistente = this.listaAule.stream()
                    .anyMatch(aula -> aula.getId() == this.aulaCorrente.getId());

            if (!aulaEsistente) {
                this.listaAule.add(this.aulaCorrente);
            }

            System.out.println("Aula inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {  // Violazione di chiave primaria
                System.err.println("Aula con lo stesso ID già esistente!");
            } else {
                // Log dell'errore completo
                e.printStackTrace();
                System.err.println("Errore SQL: " + e.getMessage());
            }
        }
    }

    // Getter per ottenere l'aula corrente
    public Aula getAulaCorrente() {
        return aulaCorrente;
    }

    // Setter per l'aula corrente
    public void setAulaCorrente(Aula aulaCorrente) {
        this.aulaCorrente = aulaCorrente;
    }

    // Getter per ottenere la lista delle aule
    public List<Aula> getListaAule() {
        return listaAule;
    }

    public Tavolo getTavoloCorrente() {
        return tavoloCorrente;
    }

    public void inserimentoDatiTavolo(int id_tavolo, int id_aula, int n_posti) {
        this.tavoloCorrente = new Tavolo(id_tavolo, id_aula, n_posti);
    }

    // Metodo per terminare l'inserimento del tavolo
    public void terminaInserimentoTavolo(DatabaseConnection dbConnection) {
        String query = "INSERT INTO `tavolo1` (`id`, `fkaula`, `numero_postazioni`) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, this.tavoloCorrente.getId());
            stmt.setInt(2, this.tavoloCorrente.getCodiceaula());
            stmt.setInt(3, this.tavoloCorrente.getN_postazioni());
            int rowsAffected = stmt.executeUpdate();

            String query1 = "INSERT INTO `postazione1` (`id`, `fktavolo`, `fkaula`) VALUES (?, ?, ?)";
            for (int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++) {
                try (PreparedStatement stmt1 = dbConnection.getConnection().prepareStatement(query1)) {
                    stmt1.setInt(2, this.tavoloCorrente.getId());
                    stmt1.setInt(3, this.tavoloCorrente.getCodiceaula());
                    stmt1.setInt(1, i);
                    rowsAffected = stmt1.executeUpdate();
                    System.out.println("Postazione inserita, rows affected: " + rowsAffected);
                }
            }

            String query2 = "UPDATE aula1 SET numerotavoli = numerotavoli + 1 WHERE id = " + this.tavoloCorrente.getCodiceaula();
            PreparedStatement stmt2 = dbConnection.getConnection().prepareStatement(query2);
            rowsAffected = stmt2.executeUpdate();

            for (int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++) {
                Postazione p = new Postazione(i, this.tavoloCorrente.getId(), this.tavoloCorrente.getCodiceaula());
                this.tavoloCorrente.getLista_postazioni().add(p);
            }

            listaAule.stream()
                    .filter(aula -> aula.getId() == this.tavoloCorrente.getCodiceaula())
                    .findFirst().ifPresent(aula -> aula.getLista_tavoli().add(this.tavoloCorrente));

            System.out.println("Tavolo inserito con successo! (" + rowsAffected + " riga/i inserita/e)");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                System.err.println("Aula inserita non esistente o tavolo con lo stesso ID gia esistente!");
            } else {
                System.err.println("Errore SQL: " + e.getMessage());
            }
        }
    }




}