import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Sistema {


    DatabaseConnection dbConnection = null;
    private static Sistema sistema;
    private Prenotazione prenotazioneCorrente;
    private Aula aulaCorrente;
    private LinkedList<Aula> listaAule;
    Disponibilita1 d;
    Aula a;
    Persistenza persistenza;
    public  LinkedList<Prenotazione> prenotazioni;
    LinkedList<Prenotazione> prenotazioniCorrenti;
    public LinkedList<Studente> studenti;
    public LinkedList<Blocco> lista_blocchi;
    Scanner scanner = new Scanner(System.in);

    public Sistema() throws SQLException {
        this.listaAule = new LinkedList<>();
        this.prenotazioni = new LinkedList<>();
        this.lista_blocchi = new LinkedList<>();
        this.studenti = new LinkedList<>();
        dbConnection = new DatabaseConnection();
        persistenza = new Persistenza();
    }




    public LinkedList<Aula> getListaAule() {
        return listaAule;
    }

    public Prenotazione getPrenotazioneCorrente() {
        return prenotazioneCorrente;
    }


    public void load() throws SQLException {
        persistenza.load(listaAule, dbConnection);
    }



    public void load_prenotazioni(int matricola, int aula, int turno) throws SQLException{
        persistenza.load_prenotazioni(matricola, aula, turno, prenotazioni, dbConnection);
    }



    public void load_tutte_prenotazioni() throws SQLException {
        persistenza.load_tutte_prenotazioni(prenotazioni, dbConnection);
    }



    public void load_blocchi() throws SQLException {
        persistenza.load_blocchi(lista_blocchi, dbConnection);
    }



    public LocalDate stringToDate(String dataa){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = null;
        try {
            data = LocalDate.parse(dataa, formatter);

        } catch (DateTimeParseException e) {
            System.out.println("Formato data non valido. Assicurati che la data sia nel formato yyyy-MM-dd.");
        }
        return data;
    }




    public void inserisci_aula_data_nstudenti(String data_scelta, int codAula, int n_studenti) throws SQLException {
        stringToDate(data_scelta);
        d.inserisci_aula_data_nstudenti(data_scelta, codAula, n_studenti);
    }




    public void loadStudenti() throws SQLException {
        String query = null;
        query = "SELECT * FROM `studente`";
        PreparedStatement stmt = dbConnection.conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int matricola = rs.getInt("Matricola");
            String nome = rs.getString("Nome");
            String cognome = rs.getString("Cognome");
            Studente s = new Studente(matricola, nome, cognome);
            studenti.add(s);
        }
    }




    public static Sistema getInstance() throws SQLException {
        if (sistema == null)
            sistema = new Sistema();
        else
            System.out.println("Istanza già creata");
        return sistema;
    }




    public void inserimentoDatiAula(int id_edificio, String nome_aula, int id_aula) throws SQLException {
            this.aulaCorrente = new Aula(id_aula, id_edificio, nome_aula);
    }



    public void terminaInserimentoAula(){
            Aula aula = this.listaAule.stream().filter(a -> a.getId() == a.getTavoloCorrente().getCodiceaula()).findFirst().orElse(null);
            if(aula == null){
                this.listaAule.add(this.aulaCorrente);
                persistenza.inserisciAulaDatabase(this.aulaCorrente, dbConnection);
            }
    }



    public void inserimentoDatiTavolo(int id_tavolo, int id_aula, int n_posti) throws SQLException {
        a = new Aula();
        Aula aula = listaAule.stream().filter(a->a.getId()==id_aula).findFirst().orElse(null);
        if (aula != null){
              a.inserimentoDatiTavolo(id_tavolo, aula.getId(), n_posti);
        }else {
            System.out.println("L'aula non esiste");
        }
    }




    public void terminaInserimentoTavolo(DatabaseConnection dbConnection) throws SQLException {
        a = new Aula();
        a.terminaInserimentoTavolo(dbConnection);
    }




    public boolean inserisciDatiPrenotazione(LinkedList<Integer> studenti, int id_aula, String data) throws SQLException {
        for (Blocco b: lista_blocchi){
            if(b.getMatricola() == studenti.getFirst() || LocalDate.now().isBefore(b.getData_fine())){
                System.out.println("Sei stato bloccato per comportamento illecito, non potrai effettuare prenotazioni fino a data "+b.getData_fine());
                return false;
            }
        }

        d = new Disponibilita1();
        int count = 1;
        if(studenti.size()>1) {
            count = 0;
            for (Integer s : studenti) {
                System.out.println("DENTRO");
                for (Studente st : sistema.studenti) {
                    if (s == st.getMatricola()) {
                        count++;
                        System.out.println(count);
                        break;
                    }
                }
            }
        }
            System.out.println(count);
            System.out.println(studenti.size());
        if (count == studenti.size()){

            inserisci_aula_data_nstudenti(data, id_aula, studenti.size());
            for (Disponibilita1 d1: d.disponibilita){
                System.out.println("Posto -> "+d1.id_postazione+", Tavolo "+d1.id_tavolo+", Turno "+d1.turno);
            }

            System.out.println("Seleziona il tavolo, inserisci il codice");
            int tavolo_scelto = scanner.nextInt();
            LinkedList<Integer> postazioniScelte = new LinkedList<>();

            for (int i = 0; i<studenti.size(); i++) {
                System.out.println("Seleziona il posto, inserisci il codice");
                int postazione_scelta = scanner.nextInt();
                postazioniScelte.add(postazione_scelta);
            }

            System.out.println("Selezione la turno, digita 1 per la mattina, 2 per il pomeriggio, 3 per la sera");
            int fascia_scelta = scanner.nextInt();

            Random rand = new Random();
            String stato="CONFERMATO";
            prenotazioniCorrenti = new LinkedList<>();
            for (Integer studente: studenti){
                int codPrenotazione = rand.nextInt(10000000);
                this.prenotazioneCorrente = new Prenotazione(studente, codPrenotazione, stringToDate(data), 1, id_aula, tavolo_scelto, postazioniScelte.get(studenti.indexOf(studente)), fascia_scelta, stato);
                this.prenotazioniCorrenti.add(prenotazioneCorrente);
                stato = "IN ATTESA";
            }
        }else{
            System.out.println("Qualche studente non è stato trovato");
        }
        return true;
    }




    public void terminaPrenotazione(){
        for(Prenotazione p: this.prenotazioniCorrenti){
              prenotazioni.add(p);
              persistenza.inserisciPrenotazioneDatabase(p, dbConnection);
            }

    }



    public void modificaStatoPrenotazione(int id_prenotazione, String nuovo_stato) throws SQLException {
        for (Prenotazione p: prenotazioni){
            if(p.getCodicePrenotazione() == id_prenotazione){
                p.setStato(nuovo_stato);
                persistenza.modificaStatoPrenotazioneDatabase(p.getCodicePrenotazione(), nuovo_stato, dbConnection);
            }
        }
    }




    public void bloccaStudente(int matricola, LocalDate data_fine_blocco){
        for (Studente s: studenti){
            if(s.getMatricola() == matricola){
                Blocco b = new Blocco(matricola, LocalDate.now(), data_fine_blocco);
                lista_blocchi.add(b);
                persistenza.inserisciBloccoDatabase(b, dbConnection);

                Iterator<Prenotazione> iterator = prenotazioni.iterator();
                while (iterator.hasNext()) {
                    Prenotazione p = iterator.next();
                    if (p.getId_stud_prenotato() == b.getMatricola()) {
                        iterator.remove();
                        persistenza.deletePrenotazione(p.getId_stud_prenotato(), dbConnection);
                    }
                }
            }
        }
    }




}








