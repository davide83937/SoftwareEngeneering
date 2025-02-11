import java.util.LinkedList;

public class Tavolo {

    // Attributi
    private int id;
    private String nome_aula;// ID del tavolo// Numero di posti disponibili sul tavolo
    private int codiceaula;
    private int n_postazioni;
    private LinkedList<Integer> lista_postazioni;

    // Costruttore
    public Tavolo(int id, int codiceaula, int n_postazioni) {
        this.id = id;
        this.codiceaula = codiceaula;
        this.n_postazioni = n_postazioni;
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

    public int getCodiceaula() {
        return codiceaula;
    }

    public void setCodiceaula(int codiceaula) {
        this.codiceaula = codiceaula;
    }

    public int getN_postazioni() {
        return n_postazioni;
    }

    public void setN_postazioni(int n_postazioni) {
        this.n_postazioni = n_postazioni;
    }

    // Metodo per rappresentare l'oggetto in formato stringa
    @Override
    public String toString() {
        return "Tavolo{" +
                "id=" + id +
                "nome_aula= "+nome_aula+
                ", fkaula=" + codiceaula +
                '}';
    }
}