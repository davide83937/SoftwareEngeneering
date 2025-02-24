# Progetto di Ingegneria del Software - Gestione Aule Studio

Questa repository contiene il progetto realizzato per il corso di Ingegneria del Software presso l'Università degli Studi di Catania.  
Il progetto consiste nella creazione di un applicativo che permetta la gestione dei posti in aula studio:  
- **Studenti:** possono prenotare un posto in aula studio per una determinata data ed ora.  
- **Amministratori:** gestiscono e monitorano le prenotazioni e possono intervenire per la manutenzione ed il controllo degli accessi.

---

## Composizione del Progetto

La struttura della repository è organizzata in modo da separare la documentazione dal codice sorgente, facilitando la manutenzione e la consultazione dei vari elaborati.

### `doc`
Questa directory contiene tutta la documentazione relativa al progetto:
- **Fase di Ideazione:**  
  - Documento di Visione  
  - Glossario  
  - Modello dei Casi d'Uso  
  - Suddivisione delle Iterazioni  
  - Specifiche URPS+
- **Iterazioni:**  
  - All'interno di questa cartella sono presenti i file Astah contenenti i diagrammi UML utilizzati durante le varie iterazioni del progetto.

### `src`
Questa directory contiene il codice sorgente del progetto.  
- **`java`**  
  All'interno di questa cartella è presente il codice Java che implementa le funzionalità principali dell'applicativo.  
  Il codice include:
  - L'interazione con il database
  - La gestione delle prenotazioni
  - L'implementazione di servizi REST che permettono agli utenti di interagire con il sistema (ad esempio, prenotare un posto o verificare la disponibilità in tempo reale).

---

