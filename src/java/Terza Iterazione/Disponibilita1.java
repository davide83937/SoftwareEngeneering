import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Disponibilita1 {

    int id_postazione;
    int id_tavolo;
    int turno;
    Sistema sistema = Sistema.getInstance();
    Persistenza persistenza = sistema.persistenza;

    public LinkedList<Disponibilita1> disponibilita;


    public Disponibilita1(int id_postazione, int id_tavolo, int turno) throws SQLException {
        this.id_postazione = id_postazione;
        this.id_tavolo = id_tavolo;
        this.turno = turno;
    }

    public Disponibilita1() throws SQLException {

    }


    public void inserisci_aula_data_nstudenti(String data_scelta, int codAula, int n_studenti) throws SQLException {
        sistema.stringToDate(data_scelta);
        this.load_disponibilita_multipla(codAula, data_scelta, n_studenti);
    }


    public void load_disponibilita_multipla(int aulaId, String data, int n_studenti) throws SQLException {
        persistenza.load_disponibilita_multipla(aulaId, data, n_studenti, disponibilita, sistema.dbConnection);
    }


}
