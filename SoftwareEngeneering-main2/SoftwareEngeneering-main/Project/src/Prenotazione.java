import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Prenotazione {
    private int id_stud_prenotato;
    private int codicePrenotazione;
    private LocalDate data;
    private Time orario_inizio;
    private Time orario_fine;
    private int materia;
    private int id_aula;
    private int id_tavolo;
    private int id_postazione;

    // Costruttore
    public Prenotazione(int id_stud_prenotato, int codicePrenotazione, LocalDate data, Time orario_inizio, Time orario_fine,
                        int materia, int id_aula, int id_tavolo, int id_postazione) {
        this.id_stud_prenotato = id_stud_prenotato;
        this.codicePrenotazione = codicePrenotazione;
        this.data = data;
        this.orario_inizio = orario_inizio;
        this.orario_fine = orario_fine;
        this.materia = materia;
        this.id_aula = id_aula;
        this.id_tavolo = id_tavolo;
        this.id_postazione = id_postazione;
    }

    // Getter e Setter


    public int getId_stud_prenotato() { return id_stud_prenotato; }

    public void setId_stud_prenotato(int id_stud_prenotato) { this.id_stud_prenotato = id_stud_prenotato; }

    public int getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(int codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public LocalDate getData() {
        return data;
    }

    public Time getOrario_inizio() {
        return orario_inizio;
    }

    public Time getOrario_fine() {
        return orario_fine;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOrario_inizio(Time orario_inizio) {
        this.orario_inizio = orario_inizio;
    }

    public void setOrario_fine(Time orario_fine) {
        this.orario_fine = orario_fine;
    }

    public int getMateria() {
        return materia;
    }

    public void setMateria(int materia) {
        this.materia = materia;
    }

    public int getId_aula() {
        return id_aula;
    }

    public void setId_aula(int id_aula) {
        this.id_aula = id_aula;
    }

    public int getId_tavolo() {
        return id_tavolo;
    }

    public void setId_tavolo(int id_tavolo) {
        this.id_tavolo = id_tavolo;
    }

    public int getId_postazione() {
        return id_postazione;
    }

    public void setId_postazione(int id_postazione) {
        this.id_postazione = id_postazione;
    }


    @Override
    public String toString() {
        return "Prenotazione{" +
                "id_stud_prenotato=" + id_stud_prenotato +
                ", codicePrenotazione=" + codicePrenotazione +
                ", data=" + data +
                ", orario_inizio=" + orario_inizio +
                ", orario_fine=" + orario_fine +
                ", materia=" + materia +
                ", id_aula=" + id_aula +
                ", id_tavolo=" + id_tavolo +
                ", id_postazione=" + id_postazione +
                '}';
    }
}
