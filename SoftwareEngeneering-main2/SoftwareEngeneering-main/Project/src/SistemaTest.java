import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class SistemaTest {

    DatabaseConnection dbConnection = null;
    Sistema sistema = Sistema.getInstance();

    SistemaTest() throws SQLException {
    }

    @Test
    void inserisciDatiPrenotazione() {
        sistema.inserisciDatiPrenotazione(100000, 22, 2, 1, 2, LocalDate.of(2025, 2, 14), Time.valueOf("08:00:00"), Time.valueOf("13:00:00"));
        assertNotNull(sistema.getPrenotazioneCorrente());
    }

    @Test
    void terminaPrenotazione() throws SQLException, InterruptedException {
        dbConnection = new DatabaseConnection();
        assertNotNull(dbConnection);
        int count = 0;
        int countAfter = 0;
        String query = "SELECT COUNT(*) FROM prenotazione";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            count = rs.getInt(1);
        }
        sistema.terminaPrenotazione(dbConnection, 1);

        //Thread.sleep(1000);
        PreparedStatement stmt1 = dbConnection.conn.prepareStatement(query);
        ResultSet rs1 = stmt1.executeQuery();
        if(rs1.next()){
            countAfter = rs1.getInt(1);
        }
        assertEquals(count +1, countAfter);
    }

    @Test
    void getListaAule() {
    }

    @Test
    void load() {
    }

    @Test
    void load_disponibilita() {
    }

    @Test
    void getInstance() {
    }

    @Test
    void inserimentoDatiAula() {
    }

    @Test
    void terminaInserimentoAula() {
    }

    @Test
    void inserimentoDatiTavolo() {
    }

    @Test
    void terminaInserimentoTavolo() {
    }


}