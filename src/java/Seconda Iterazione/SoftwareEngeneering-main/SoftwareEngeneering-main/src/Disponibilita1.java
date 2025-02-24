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

    public LinkedList<Disponibilita1> disponibilita;

    public Disponibilita1() throws SQLException {

    }

    public Disponibilita1(int id_postazione, int id_tavolo, int turno) throws SQLException {
        this.id_postazione = id_postazione;
        this.id_tavolo = id_tavolo;
        this.turno = turno;
    }



    public void inserisci_aula_data_nstudenti(String data_scelta, int codAula, int n_studenti) throws SQLException {
        sistema.stringToDate(data_scelta);
        this.load_disponibilita_multipla(codAula, data_scelta, n_studenti);
    }


    /*public void load_disponibilita1(int aulaId, String data) throws SQLException {
        String query = null;
        query = "SELECT " +
                "    p.id AS id_postazione, " +
                "    p.fktavolo, " +
                "    p.fkaula, " +
                "    t.id AS id_turno, " +
                "    t.nome AS nome_turno, " +
                "    t.orario_inizio, " +
                "    t.orario_fine " +
                "FROM postazione1 p " +
                "JOIN turno t " +
                "LEFT JOIN prenotazione2 pr " +
                "    ON pr.fkpostazione = p.id " +
                "    AND pr.fktavolo = p.fktavolo " +
                "    AND pr.fkaula = p.fkaula " +
                "    AND pr.data = ? " +  // Parametro per la data
                "    AND pr.fkturno = t.id " +
                "WHERE p.fkaula = ? " +  // Parametro per l'aula
                "AND pr.id IS NULL";

        PreparedStatement stmt = sistema.dbConnection.conn.prepareStatement(query);
        stmt.setInt(2, aulaId);
        stmt.setDate(1, Date.valueOf(data));

        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            Disponibilita1 d1 = new Disponibilita1(rs.getInt("id_postazione"), rs.getInt("fktavolo"), rs.getInt("id_turno"));
            disponibilita.add(d1);
        }
    }*/

    public void load_disponibilita_multipla(int aulaId, String data, int n_studenti) throws SQLException {
        String query = null;
        query = "WITH tavoli_disponibili AS (" +
                "SELECT " +
                "t.id AS tavolo_id, " +
                "t.fkaula, " +
                "t.numero_postazioni, " +
                "tu.id AS turno_id, " +
                "(t.numero_postazioni - COUNT(CASE WHEN pr.id IS NOT NULL THEN 1 END)) AS posti_liberi " +
                "FROM tavolo1 t " +
                "JOIN postazione1 p ON t.id = p.fktavolo AND t.fkaula = p.fkaula " +
                "CROSS JOIN turno tu " +
                "LEFT JOIN prenotazione2 pr " +
                "ON p.id = pr.fkpostazione " +
                "AND p.fktavolo = pr.fktavolo " +
                "AND p.fkaula = pr.fkaula " +
                "AND pr.data = ? " +
                "AND pr.fkturno = tu.id " +
                "WHERE t.fkaula = ? " +
                "GROUP BY t.id, t.fkaula, t.numero_postazioni, tu.id " +
                "HAVING posti_liberi >= ? " +
                ") " +
                "SELECT " +
                "p.id AS postazione_id, " +
                "p.fktavolo, " +
                "p.fkaula, " +
                "td.turno_id, " +
                "tu.nome AS turno_nome, " +
                "tu.orario_inizio, " +
                "tu.orario_fine " +
                "FROM postazione1 p " +
                "JOIN tavoli_disponibili td ON p.fktavolo = td.tavolo_id AND p.fkaula = td.fkaula " +
                "JOIN turno tu ON td.turno_id = tu.id " +
                "LEFT JOIN prenotazione2 pr " +
                "ON p.id = pr.fkpostazione " +
                "AND p.fktavolo = pr.fktavolo " +
                "AND p.fkaula = pr.fkaula " +
                "AND pr.data = ? " +
                "AND pr.fkturno = tu.id " +
                "WHERE pr.id IS NULL;";

        PreparedStatement stmt = sistema.dbConnection.conn.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(data));
        stmt.setInt(2, aulaId);
        stmt.setInt(3, n_studenti);
        stmt.setDate(4, Date.valueOf(data));

        ResultSet rs = stmt.executeQuery();
        disponibilita = new LinkedList<>();
        while (rs.next()){
            Disponibilita1 d1 = new Disponibilita1(rs.getInt("postazione_id"), rs.getInt("fktavolo"), rs.getInt("turno_id"));
            disponibilita.add(d1);
        }
    }


}
