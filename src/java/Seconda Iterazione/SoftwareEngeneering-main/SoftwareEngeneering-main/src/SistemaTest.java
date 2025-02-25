import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {

    public LinkedList<Disponibilita1> disponibilita;
    public LinkedList<Studente> studenti;
    public LinkedList<Aula> lista_aule = new LinkedList<>();
    public Aula aulaCorrente;
    public Tavolo tavoloCorrente = new Tavolo(1,1, 1);
    public Prenotazione prenotazioneCorrente = new Prenotazione(1, 1, LocalDate.of(2024, 11, 1), 1, 1,1,1,1, "");
    public LinkedList<Prenotazione> prenotazioniCorrenti = new LinkedList<>();
    public LinkedList<Prenotazione> prenotazioni = new LinkedList<>();


    public void load_studenti(){
        this.studenti = new LinkedList<>();
        Studente s = new Studente(1111, "Davide", "Panto");
        Studente s1 = new Studente(1112, "Jacopo", "Schembri");
        Studente s2 = new Studente(1113, "Elon", "Musk");
        studenti.add(s);
        studenti.add(s1);
        studenti.add(s2);
    }


    public void load_disponibilita() throws SQLException {
        this.disponibilita = new LinkedList();
        Disponibilita1 d1 = new Disponibilita1(1, 1, 1);
        Disponibilita1 d2 = new Disponibilita1(2, 1, 1);
        Disponibilita1 d3 = new Disponibilita1(3, 1, 1);
        Disponibilita1 d4 = new Disponibilita1(1, 1, 1);
        Disponibilita1 d5 = new Disponibilita1(2, 2, 1);
        Disponibilita1 d6 = new Disponibilita1(3, 3, 1);
        this.disponibilita.add(d1);
        this.disponibilita.add(d2);
        this.disponibilita.add(d3);
        this.disponibilita.add(d4);
        this.disponibilita.add(d5);
        this.disponibilita.add(d6);
    }



    @Test
    public void inserimentoDatiAula() throws SQLException {
        this.aulaCorrente = new Aula(1, 8, "Davide");
        assertNotNull(this.aulaCorrente);
    }

    @Test
    public void terminaInserimentoAula() {
    Aula aula = lista_aule.stream().filter(a -> a.getId() == a.getTavoloCorrente().getCodiceaula()).findFirst().orElse(null);
    if (aula == null) {
        lista_aule.add(this.aulaCorrente);
    }
    assertNotNull(lista_aule.stream().filter(aula1 -> aula1.getId() == aulaCorrente.getId()));
    }

    @Test
    public void inserimentoDatiTavolo(){
        this.tavoloCorrente = new Tavolo(1, 1,8);
        assertNotNull(this.tavoloCorrente);
    }

    @Test
    public void terminaInserimentoTavolo(){
        for (int i = 0; i < this.tavoloCorrente.getN_postazioni(); i++){
            Postazione p = new Postazione(i, this.tavoloCorrente.getId(), this.tavoloCorrente.getCodiceaula());
            this.tavoloCorrente.getLista_postazioni().add(p);
        }
        lista_aule.stream().filter(aula -> aula.getId() == this.tavoloCorrente.getCodiceaula()).findFirst().ifPresent(aula -> aula.getLista_tavoli().add(this.tavoloCorrente));
        Optional<Aula> a = lista_aule.stream().filter(aula -> aula.getId() == tavoloCorrente.getId()).findFirst();
        assertNotNull(a);
    }

    @Test
    public void inserisciDatiPrenotazione() throws SQLException {

        load_studenti();
        load_disponibilita();
        int count = 1;
        if (studenti.size() > 1) {
            count = 0;
            for (Studente s : studenti) {

                for (Studente st : studenti) {
                    if (s.getMatricola() == st.getMatricola()) {
                        count++;
                        System.out.println(count);
                        break;
                    }
                }
            }
        }
        System.out.println(count);
        System.out.println(studenti.size());
        if (count == studenti.size()) {


            LinkedList<Integer> postazioni = new LinkedList<>();
            postazioni.add(disponibilita.get(1).id_postazione);
            postazioni.add(disponibilita.get(2).id_postazione);
            postazioni.add(disponibilita.get(3).id_postazione);

            Random rand = new Random();
            String stato = "CONFERMATO";

            this.prenotazioniCorrenti = new LinkedList<>();

            for (Studente studente : studenti) {
                int codPrenotazione = rand.nextInt(10000000);
                this.prenotazioneCorrente = new Prenotazione(studente.getMatricola(), codPrenotazione, LocalDate.of(2025, 2, 25), 1, 1, 1, postazioni.get(studenti.indexOf(studente)), 1, stato);
                this.prenotazioniCorrenti.add(prenotazioneCorrente);
                stato = "IN ATTESA";
            }
        } else {
            System.out.println("Qualche studente non è stato trovato");
        }

        assertEquals(3, this.prenotazioniCorrenti.size());
        assertEquals("CONFERMATO", this.prenotazioniCorrenti.get(0).getStato());
        assertEquals("IN ATTESA", this.prenotazioniCorrenti.get(1).getStato());
        assertEquals("IN ATTESA", this.prenotazioniCorrenti.get(2).getStato());
        terminaInserimentoPrenotazione();
        modificaStatoPrenotazione();
    }


    public void terminaInserimentoPrenotazione(){
        this.prenotazioni.addAll(this.prenotazioniCorrenti);
        assertEquals(3, this.prenotazioni.size());
    }

    public void modificaStatoPrenotazione() throws SQLException {
        this.prenotazioni.get(1).setStato("CONFERMATO");
        assertEquals("CONFERMATO", this.prenotazioni.get(1).getStato());
    }

}
