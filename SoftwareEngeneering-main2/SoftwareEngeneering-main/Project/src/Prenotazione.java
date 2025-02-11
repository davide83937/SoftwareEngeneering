import java.time.LocalDateTime;

public class Prenotazione {
    private int codicePrenotazione;
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine;
    private int materia;
    private int id_aula;
    private int id_tavolo;
    private int id_postazione;

    // Costruttore
    public Prenotazione(int codicePrenotazione, LocalDateTime data_inizio, LocalDateTime data_fine,
                        int materia, int id_aula, int id_tavolo, int id_postazione) {
        this.codicePrenotazione = codicePrenotazione;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.materia = materia;
        this.id_aula = id_aula;
        this.id_tavolo = id_tavolo;
        this.id_postazione = id_postazione;
    }

    // Getter e Setter
    public int getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(int codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public LocalDateTime getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDateTime data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDateTime getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDateTime data_fine) {
        this.data_fine = data_fine;
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

    // Metodo toString
    @Override
    public String toString() {
        return "Prenotazione{" +
                "codicePrenotazione='" + codicePrenotazione + '\'' +
                ", data_inizio=" + data_inizio +
                ", data_fine=" + data_fine +
                ", materia='" + materia + '\'' +
                ", id_aula=" + id_aula +
                ", id_tavolo=" + id_tavolo +
                ", id_postazione=" + id_postazione +
                '}';
    }
}
