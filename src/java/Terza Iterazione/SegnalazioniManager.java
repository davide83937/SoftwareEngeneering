import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

public class SegnalazioniManager {

    Scanner scanner = new Scanner(System.in);
    private static SegnalazioniManager sm;
    private LinkedList<Segnalazione> lista_segnalazioni;
    DatabaseConnection dbConnection = Sistema.getInstance().dbConnection;
    Persistenza persistenza;


    public SegnalazioniManager() throws SQLException {
        lista_segnalazioni = new LinkedList<>();
        persistenza = new Persistenza();
    }


    public static SegnalazioniManager getInstance() throws SQLException {
        if (sm == null)
            sm = new SegnalazioniManager();
        else
            System.out.println("Istanza già creata");
        return sm;
    }


    public void load_segnalazioni() throws SQLException {
        persistenza.load_segnalazioni(lista_segnalazioni, dbConnection);
    }



    public void creaSegnalazione(int matricola, String oggetto, String testo){
        Segnalazione s = new Segnalazione(matricola, oggetto, testo, "IN ATTESA");
        lista_segnalazioni.add(s);
        persistenza.insertSegnalazioneOnDatabase(s, dbConnection);
    }



    public void modificaStato(String stato) throws SQLException {
        System.out.println("Inserisci l'id della prenotazione");
        int id_segnalazione = scanner.nextInt();
        for (Segnalazione s: lista_segnalazioni){
            if (s.getId_segnalazione() == id_segnalazione){
                s.setStato(stato);
                persistenza.modificaStatoDatabase(id_segnalazione, stato, dbConnection);
            }
        }
    }

    public void vedi_segnalazioni(){
        for (Segnalazione s: lista_segnalazioni){
            System.out.println("Segnalazione "+s.getId_segnalazione());
            System.out.println("OGGETTO: "+s.getOggetto());
            System.out.println(s.getTesto());
            System.out.println("Stato: "+s.getStato());
        }
    }






}
