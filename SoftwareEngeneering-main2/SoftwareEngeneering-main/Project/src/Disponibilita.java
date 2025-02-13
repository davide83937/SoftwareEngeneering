import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {
    private int id_postazione;
    private int id_tavolo;
    private int id_aula;
    private LocalDate data_disponibile;
    private Time orario_inizio_1;
    private Time orario_fine_1;
    private Time orario_inizio_2;
    private Time orario_fine_2;
    private boolean occupato1;
    private boolean occupato2;

    public Disponibilita(int id_postazione, int id_tavolo, int id_aula, LocalDate data_disponibile, Time orario_inizio_1, Time orario_inizio_2, Time orario_fine_1, Time orario_fine_2, Boolean occupato1, Boolean occupato2){
        this.id_postazione = id_postazione;
        this.id_tavolo = id_tavolo;
        this.id_aula = id_aula;
        this.data_disponibile = data_disponibile;
        this.orario_inizio_1 = orario_inizio_1;
        this.orario_fine_1 = orario_fine_1;
        this.orario_inizio_2 = orario_inizio_2;
        this.orario_fine_2 = orario_fine_2;
        this.occupato1 = occupato1;
        this.occupato2 = occupato2;
    }

    public int getId_postazione() {
        return id_postazione;
    }

    public int getId_tavolo() {
        return id_tavolo;
    }

    public int getId_aula() {
        return id_aula;
    }

    public LocalDate getData_disponibile() {
        return data_disponibile;
    }

    public Time getOrario_inizio_1() {
        return orario_inizio_1;
    }

    public Time getOrario_fine_1() {
        return orario_fine_1;
    }

    public Time getOrario_inizio_2() {
        return orario_inizio_2;
    }

    public Time getOrario_fine_2() {
        return orario_fine_2;
    }

    public boolean isOccupato1() {
        return occupato1;
    }

    public boolean isOccupato2() {
        return occupato2;
    }

    public void setId_postazione(int id_postazione) {
        this.id_postazione = id_postazione;
    }

    public void setId_tavolo(int id_tavolo) {
        this.id_tavolo = id_tavolo;
    }

    public void setId_aula(int id_aula) {
        this.id_aula = id_aula;
    }

    public void setData_disponibile(LocalDate data_disponibile) {
        this.data_disponibile = data_disponibile;
    }

    public void setOrario_inizio_1(Time orario_inizio_1) {
        this.orario_inizio_1 = orario_inizio_1;
    }

    public void setOrario_fine_1(Time orario_fine_1) {
        this.orario_fine_1 = orario_fine_1;
    }

    public void setOrario_inizio_2(Time orario_inizio_2) {
        this.orario_inizio_2 = orario_inizio_2;
    }

    public void setOrario_fine_2(Time orario_fine_2) {
        this.orario_fine_2 = orario_fine_2;
    }

    public void setOccupato1(boolean occupato1) {
        this.occupato1 = occupato1;
    }

    public void setOccupato2(boolean occupato2) {
        this.occupato2 = occupato2;
    }

    @Override
    public String toString() {
        return "Disponibilita{" +
                "id_prenotazione=" + id_postazione +
                ", id_tavolo=" + id_tavolo +
                ", id_aula=" + id_aula +
                ", data_disponibile=" + data_disponibile +
                ", orario_inizio_1=" + orario_inizio_1 +
                ", orario_fine_1=" + orario_fine_1 +
                ", orario_inizio_2=" + orario_inizio_2 +
                ", orario_fine_2=" + orario_fine_2 +
                ", occupato1=" + occupato1 +
                ", occupato2=" + occupato2 +
                '}';
    }
}
