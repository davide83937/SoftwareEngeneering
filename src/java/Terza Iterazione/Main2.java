import java.sql.SQLException;
import java.util.Scanner;

public class Main2 {
    public static final int aula = 22;
    public static final int turno = 1;

    public static void main(String[] args) throws SQLException {
        Sistema sistema = Sistema.getInstance();
        //sistema.load();
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConnection = null;
        dbConnection = new DatabaseConnection();



        while (true){
            System.out.println("Registra la tua presenza, scrivi il codice della tua prenotazione");
            int codice = scanner.nextInt();
            sistema.load_prenotazioni(0, aula, turno);
            sistema.modificaStatoPrenotazione(codice, "IN ESECUZIONE");
        }

    }
}
