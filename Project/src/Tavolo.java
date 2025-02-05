import java.util.LinkedList;

public class Tavolo {

    // Attributi
    private int id;           // ID del tavolo
    private int numeroposti;  // Numero di posti disponibili sul tavolo
    private int codiceaula;       // FK per collegare il tavolo a una specifica aula
    private LinkedList<Integer> lista_postazioni;

    // Costruttore
    public Tavolo(int id, int numeroposti, int codiceaula) {
        this.id = id;
        this.numeroposti = numeroposti;
        this.codiceaula = codiceaula;
        lista_postazioni = new LinkedList<>();
    }


    public LinkedList<Integer> getLista_postazioni() {
        return lista_postazioni;
    }

    public void setLista_postazioni(LinkedList<Integer> lista_postazioni) {
        this.lista_postazioni = lista_postazioni;
    }

    // Getter e Setter per id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter per numeroposti
    public int getNumeroposti() {
        return numeroposti;
    }

    public void setNumeroposti(int numeroposti) {
        this.numeroposti = numeroposti;
    }

    // Getter e Setter per fkaula
    public int getcodiceaula() {
        return codiceaula;
    }

    public void setcodiceaula(int fkaula) {
        this.codiceaula = fkaula;
    }

    // Metodo per rappresentare l'oggetto in formato stringa
    @Override
    public String toString() {
        return "Tavolo{" +
                "id=" + id +
                ", numeroposti=" + numeroposti +
                ", fkaula=" + codiceaula +
                '}';
    }
}