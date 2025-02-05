import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

public class Sistema {

    private static Sistema sistema;
    private Prenotazione prenotazioneCorrente;
    private LinkedList<Aula> listaAule;
    private  LinkedList<Tavolo> listaTavoli;


    private Sistema(){
        this.listaAule = new LinkedList<>();

    }



    public static Sistema getInstance() {
        if (sistema == null)
            sistema = new Sistema();
        else
            System.out.println("Istanza già creata");

        return sistema;
    }

    public void inserisciDatiPrenotazione(int id_aula, int id_tavolo, int postazione, int materia, LocalDateTime data_ora_inizio, LocalDateTime data_ora_fine){
        if(prenotazioneCorrente != null){
            for (Aula a: listaAule) {
               if(a.getId() == id_aula){
                   Aula aula = a;
                   for(Tavolo t: aula.getLista_tavoli()){
                       if(t.getId() == id_tavolo){
                           Tavolo tavolo = t;
                           for(Integer p: tavolo.getLista_postazioni()){
                               if (p == id_tavolo){
                                   this.prenotazioneCorrente = new Prenotazione(aula.getId(), tavolo.getId(), p, materia, data_ora_inizio, data_ora_fine);
                               }
                           }
                       }
                   }
               }

            }


            }
            }

        }

    }

}






