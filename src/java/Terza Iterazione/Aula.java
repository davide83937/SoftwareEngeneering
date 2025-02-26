import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class Aula {

    // Attributi
    private int id;               // ID dell'aula
    private int edificio;   // FK per il dipartimento, presumibilmente un intero che rappresenta il dipartimento
    private String nome;          // Nome dell'aula
    private int numerotavoli;     // Numero di tavoli nell'aula
    private int numeropostazioni; // Numero di postazioni nell'aula
    private LinkedList<Tavolo> lista_tavoli;
    private Tavolo tavoloCorrente;
    Sistema sistema = Sistema.getInstance();


    // Costruttore

    public Aula() throws SQLException {}

    public Aula(int id, int edificio, String nome) throws SQLException {
        this.id = id;
        this.edificio = edificio;
        this.nome = nome;
        this.numerotavoli = 0;
        lista_tavoli = new LinkedList<>();

    }

    public Aula(int id, int edificio, String nome, int numerotavoli, int numeropostazioni) throws SQLException {
        this.id = id;
        this.edificio = edificio;
        this.nome = nome;
        this.numerotavoli = numerotavoli;
        this.numeropostazioni = numeropostazioni;
        lista_tavoli = new LinkedList<>();
    }

    public Tavolo getTavoloCorrente() {
        return tavoloCorrente;
    }

    public void setTavoloCorrente(Tavolo tavoloCorrente) {
        this.tavoloCorrente = tavoloCorrente;
    }

    public LinkedList<Tavolo> getLista_tavoli() {
        return lista_tavoli;
    }

    public void setLista_tavoli(LinkedList<Tavolo> lista_tavoli) {
        this.lista_tavoli = lista_tavoli;
    }

    // Getter e Setter per id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter per fkdipartimento


    public void setEdificio(int edificio) {
        this.edificio = edificio;
    }

    public int getEdificio() {
        return edificio;
    }

    // Getter e Setter per nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter per numerotavoli
    public int getNumerotavoli() {
        return numerotavoli;
    }

    public void setNumerotavoli(int numerotavoli) {
        this.numerotavoli = numerotavoli;
    }

    // Getter e Setter per numeropostazioni
    public int getNumeropostazioni() {
        return numeropostazioni;
    }

    public void setNumeropostazioni(int numeropostazioni) {
        this.numeropostazioni = numeropostazioni;
    }

    // Metodo per rappresentare l'oggetto in formato stringa
    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", fkdipartimento=" + edificio +
                ", nome='" + nome + '\'' +
                ", numerotavoli=" + numerotavoli +
                ", numeropostazioni=" + numeropostazioni +
                '}';
    }

    public void inserimentoDatiTavolo(int id_tavolo, int id_aula, int n_posti){
        this.tavoloCorrente = new Tavolo(id_tavolo, id_aula, n_posti);
    }

    public void inserisci_tavolo_database(Tavolo t, DatabaseConnection dbConnection){
        String query;
        query = "INSERT INTO `tavolo1` (`id`, `fkaula`, `numero_postazioni`) VALUES (?, ?, ?)";

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


            System.out.println("Tavolo inserita con successo! (" + rowsAffected + " riga/i inserita/e)");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {  // Violazione di chiave primaria
                System.err.println("Aula inserita non esistente o tavolo con lo stesso ID gia esistente!");
            } else {
                System.err.println("Errore SQL: " + e.getMessage());
            }
        }

    }

    public void terminaInserimentoTavolo(DatabaseConnection dbConnection){
        for (int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++){
            Postazione p = new Postazione(i, this.tavoloCorrente.getId(), this.tavoloCorrente.getCodiceaula());
            this.tavoloCorrente.getLista_postazioni().add(p);
        }
        sistema.getListaAule().stream().filter(aula -> aula.getId() == this.tavoloCorrente.getCodiceaula()).findFirst().ifPresent(aula -> aula.getLista_tavoli().add(this.tavoloCorrente));
        inserisci_tavolo_database(this.tavoloCorrente , dbConnection);
        }
    }

