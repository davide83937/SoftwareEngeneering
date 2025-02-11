public class Studente extends Utente {
    private int matricola;
    private String facolta;

    // Costruttore della classe Studente
    public Studente(String nome, String cognome, String dataNascita, String codiceFiscale, String email, int matricola, String facolta) {
        super(nome, cognome, dataNascita, codiceFiscale, email); // Chiamata al costruttore della classe base
        this.matricola = matricola;
        this.facolta = facolta;
    }

    // Getters e Setters per gli attributi specifici dello Studente
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