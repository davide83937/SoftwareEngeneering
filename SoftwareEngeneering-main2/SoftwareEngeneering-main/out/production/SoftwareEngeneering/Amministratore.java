public class Amministratore extends Utente {

    // Costruttore della classe Amministratore
    public Amministratore(String nome, String cognome, String dataNascita, String codiceFiscale, String email) {
        super(nome, cognome, dataNascita, codiceFiscale, email); // Chiamata al costruttore della classe base
    }

    // Metodo per visualizzare le informazioni dell'Amministratore
    public void visualizzaInfo() {
        System.out.println("Nome: " + getNome());
        System.out.println("Cognome: " + getCognome());
        System.out.println("Data di Nascita: " + getDataNascita());
        System.out.println("Codice Fiscale: " + getCodiceFiscale());
        System.out.println("Email: " + getEmail());
    }

    // Metodo per aggiungere uno studente (semplice esempio)
    public void aggiungiStudente(Studente studente) {
        System.out.println("Studente aggiunto: " + studente.getNome() + " " + studente.getCognome());
    }

    // Metodo per rimuovere uno studente (semplice esempio)
    public void rimuoviStudente(Studente studente) {
        System.out.println("Studente rimosso: " + studente.getNome() + " " + studente.getCognome());
    }

    // Metodo per modificare i dettagli di uno studente
    public void modificaStudente(Studente studente, String nuovoNome, String nuovoCognome, String nuovaEmail) {
        studente.setNome(nuovoNome);
        studente.setCognome(nuovoCognome);
        studente.setEmail(nuovaEmail);
        System.out.println("Dati dello studente aggiornati: " + studente.getNome() + " " + studente.getCognome());
    }

    // Metodo per visualizzare tutte le informazioni di uno studente (ad esempio in una lista)
    public void visualizzaStudente(Studente studente) {
        System.out.println("Matricola: " + studente.getMatricola());
        System.out.println("Nome: " + studente.getNome());
        System.out.println("Cognome: " + studente.getCognome());
        System.out.println("Facoltà: " + studente.getFacoltà());
        System.out.println("Email: " + studente.getEmail());
    }
}