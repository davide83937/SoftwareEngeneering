import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnection {
    private String dbUrl = "jdbc:mysql://localhost:3328/ing_soft";
    private String dbUser = "root";
    private String dbPassword = "";
    Connection conn;

    // Costruttore per la connessione
    public DatabaseConnection() throws SQLException {
        // Connessione al database
        conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // Metodo getter per ottenere la connessione
    public Connection getConnection() {
        return conn;
    }

    // Funzione per eseguire una query e restituire i risultati
    public List<Map<String, Object>> eseguiQuery(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> rows = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Estrazione dei dati
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                rows.add(row);
            }
        } finally {
            // Chiusura delle risorse
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return rows;
    }

    // Metodo per chiudere la connessione
    public void chiudiConnessione() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
