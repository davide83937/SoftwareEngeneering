public class Postazione {
    private int id;
    private int id_tavolo;
    private int id_aula;
    private boolean occupato;

    public Postazione(int id, int id_tavolo, int id_aula){
        this.id = id;
        this.id_tavolo = id_tavolo;
        this.id_aula = id_aula;
        this.occupato = false;
    }

    public Postazione(int id, int id_tavolo, int id_aula, boolean occupato){
        this.id = id;
        this.id_tavolo = id_tavolo;
        this.id_aula = id_aula;
        this.occupato = occupato;
    }

    public int getId_aula() {
        return id_aula;
    }

    public void setId_aula(int id_aula) {
        this.id_aula = id_aula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tavolo() {
        return id_tavolo;
    }

    public void setId_tavolo(int id_tavolo) {
        this.id_tavolo = id_tavolo;
    }

    public boolean isOccupato() {
        return occupato;
    }

    @Override
    public String toString() {
        return "Postazione{" +
                "id=" + id +
                ", id_tavolo=" + id_tavolo +
                ", id_aula=" + id_aula +
                ", occupato=" + occupato +
                '}';
    }
}
