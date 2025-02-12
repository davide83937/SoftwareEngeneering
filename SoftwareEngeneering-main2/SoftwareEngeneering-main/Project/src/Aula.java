import java.util.LinkedList;

public class Aula {

    // Attributi
    private int id;               // ID dell'aula
    private int edificio;   // FK per il dipartimento, presumibilmente un intero che rappresenta il dipartimento
    private String nome;          // Nome dell'aula
    private int numerotavoli;     // Numero di tavoli nell'aula
    private int numeropostazioni; // Numero di postazioni nell'aula
    private LinkedList<Tavolo> lista_tavoli;

    // Costruttore
    public Aula(int id, int edificio, String nome) {
        this.id = id;
        this.edificio = edificio;
        this.nome = nome;
        this.numerotavoli = 0;
        lista_tavoli = new LinkedList<>();
    }

    public Aula(int id, int edificio, String nome, int numerotavoli, int numeropostazioni) {
        this.id = id;
        this.edificio = edificio;
        this.nome = nome;
        this.numerotavoli = numerotavoli;
        this.numeropostazioni = numeropostazioni;
        lista_tavoli = new LinkedList<>();
    }

    public LinkedList<Tavolo> getLista_tavoli() {
        return lista_tavoli;
    }

    public void setLista_tavoli(LinkedList<Tavolo> lista_tavoli) {
        this.lista_tavoli = lista_tavoli;
    }

    // Getter e Setter per id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter per fkdipartimento


    public void setEdificio(int edificio) {
        this.edificio = edificio;
    }

    public int getEdificio() {
        return edificio;
    }

    // Getter e Setter per nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter per numerotavoli
    public int getNumerotavoli() {
        return numerotavoli;
    }

    public void setNumerotavoli(int numerotavoli) {
        this.numerotavoli = numerotavoli;
    }

    // Getter e Setter per numeropostazioni
    public int getNumeropostazioni() {
        return numeropostazioni;
    }

    public void setNumeropostazioni(int numeropostazioni) {
        this.numeropostazioni = numeropostazioni;
    }

    // Metodo per rappresentare l'oggetto in formato stringa
    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", fkdipartimento=" + edificio +
                ", nome='" + nome + '\'' +
                ", numerotavoli=" + numerotavoli +
                ", numeropostazioni=" + numeropostazioni +
                '}';
    }

}