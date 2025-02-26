public class Studente extends Utente {
    private int matricola;
    private String facolta;


    // Costruttore della classe Studente
    public Studente(String nome, String cognome, String dataNascita, String codiceFiscale, String email, int matricola, String facolta) {
        super(nome, cognome, dataNascita, codiceFiscale, email);
        this.matricola = matricola;
        this.facolta = facolta;

    }

    public Studente(int matricola, String nome, String cognome){
        super(nome, cognome);
        this.matricola = matricola;
    }


    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getFacoltà() {
        return facolta;
    }

    public void setFacoltà(String facoltà) {
        this.facolta = facoltà;
    }

}