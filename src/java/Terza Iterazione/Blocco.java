import java.sql.Date;
import java.time.LocalDate;

public class Blocco {
    private int id_blocco;
    private int matricola;
    private LocalDate data_inizio;
    private LocalDate data_fine;

    public Blocco(int id_blocco, int matricola, LocalDate data_fine){
        this.id_blocco = id_blocco;
        this.matricola = matricola;
        this.data_fine = data_fine;
    }

    public Blocco(int matricola, LocalDate data_inizio, LocalDate data_fine){
        this.matricola = matricola;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
    }

    public Blocco(int id_blocco, int matricola, LocalDate data_inizio, LocalDate data_fine){
        this.id_blocco = id_blocco;
        this.matricola = matricola;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
    }

    public int getId_blocco() {
        return id_blocco;
    }

    public int getMatricola() {
        return matricola;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setId_blocco(int id_blocco) {
        this.id_blocco = id_blocco;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
    }
}
