public class Segnalazione {
    private int id_segnalazione;
    private int matricola;
    private String oggetto;
    private String testo;
    private String stato;

    public Segnalazione(int id_segnalazione, int matricola, String oggetto, String testo, String stato){
        this.id_segnalazione = id_segnalazione;
        this.matricola = matricola;
        this.oggetto = oggetto;
        this.testo = testo;
        this.stato = stato;
    }

    public Segnalazione(int matricola, String oggetto, String testo, String stato){
        this.matricola = matricola;
        this.oggetto = oggetto;
        this.testo = testo;
        this.stato = stato;
    }

    public int getId_segnalazione() {
        return id_segnalazione;
    }

    public void setId_segnalazione(int id_segnalazione) {
        this.id_segnalazione = id_segnalazione;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
