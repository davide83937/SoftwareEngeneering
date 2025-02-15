public class Postazione {
    private int id;
    private int id_tavolo;
    private int id_aula;


    public Postazione(int id, int id_tavolo, int id_aula){
        this.id = id;
        this.id_tavolo = id_tavolo;
        this.id_aula = id_aula;

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


    @Override
    public String toString() {
        return "Postazione{" +
                "id=" + id +
                ", id_tavolo=" + id_tavolo +
                ", id_aula=" + id_aula +

                '}';
    }
}
