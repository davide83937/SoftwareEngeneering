import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaTest {

    @Mock
    private DatabaseConnection dbConnection;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private PreparedStatement mockStatement1;
    @Mock
    private PreparedStatement mockStatement2;

    @Mock
    private Aula aulaMock;

    private AulaService aulaService;


    @Mock
    private Studente studenteMock;
    @Mock
    private Disponibilita1 disponibilitaMock;

    private Sistema sistema; // Assumiamo che Sistema contenga i metodi inseriti
    private LinkedList<Integer> studenti;
    private LinkedList<Prenotazione> prenotazioniCorrenti;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        aulaService = new AulaService(); // Assumendo che AulaService contenga i metodi
        sistema = new Sistema(); // Assumendo che Sistema contenga i metodi
        sistema.studenti = new LinkedList<>(); // Popolato con i mock studenti
        studenti = new LinkedList<>();
        prenotazioniCorrenti = new LinkedList<>();
    }


    @Test
    public void testInserimentoDatiAula() throws SQLException {
        int idEdificio = 1;
        String nomeAula = "Aula 10";
        int idAula = 10;

        aulaService.inserimentoDatiAula(idEdificio, nomeAula, idAula);

        // Verifica che l'oggetto Aula sia stato creato correttamente
        Aula aula = aulaService.getAulaCorrente();
        assertNotNull(aula);
        assertEquals(idAula, aula.getId());
        assertEquals(nomeAula, aula.getNome());
    }

    @Test
    public void testTerminaInserimentoAula() throws SQLException {
        // Dati per il test
        String query = "INSERT INTO `aula1` (`fkedificio`, `nome`, `id`, `numerotavoli`, `numeropostazioni`) VALUES (?, ?, ?, ?, ?)";

        // Mock della connessione e PreparedStatement
        when(dbConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(query)).thenReturn(mockStatement);

        // Comportamento del PreparedStatement
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Mock dell'oggetto Aula nella lista
        Aula aulaMock = new Aula(10, 1, "Aula 10");
        aulaService.setAulaCorrente(aulaMock);

        // Chiamata al metodo
        aulaService.terminaInserimentoAula(dbConnection);

        // Verifica che il PreparedStatement sia stato eseguito
        verify(mockStatement).setInt(1, 1);
        verify(mockStatement).setString(2, "Aula 10");
        verify(mockStatement).setInt(3, 10);
        verify(mockStatement).setInt(4, 0);
        verify(mockStatement).setInt(5, 0);
        verify(mockStatement).executeUpdate();

        // Verifica che l'aula venga aggiunta alla lista
        assertTrue(aulaService.getListaAule().contains(aulaMock));
    }

    @Test
    public void testTerminaInserimentoAula_keyViolation() throws SQLException {
        // Simula la violazione della chiave primaria
        when(dbConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenThrow(new SQLIntegrityConstraintViolationException("Duplicate entry"));

        Aula aulaMock = new Aula(10, 1, "Aula 10");
        aulaService.setAulaCorrente(aulaMock);

        aulaService.terminaInserimentoAula(dbConnection);

        // Verifica che il messaggio di errore sia stampato
        // Qui dovresti testare che l'errore venga gestito correttamente (es. tramite System.err)
    }

    @Test
    public void testInserimentoDatiTavolo() throws SQLException {
        int id_tavolo = 1;
        int id_aula = 10;
        int n_posti = 5;

        // Chiamata al metodo che imposta i dati del tavolo
        aulaService.inserimentoDatiTavolo(id_tavolo, id_aula, n_posti);

        // Verifica che il tavolo corrente sia stato impostato correttamente
        assertNotNull(aulaService.getTavoloCorrente());
        assertEquals(id_tavolo, aulaService.getTavoloCorrente().getId());
        assertEquals(id_aula, aulaService.getTavoloCorrente().getCodiceaula());
        assertEquals(n_posti, aulaService.getTavoloCorrente().getN_postazioni());
    }

    @Test
    public void testTerminaInserimentoTavolo() throws SQLException {
        int id_tavolo = 1;
        int id_aula = 10;
        int n_posti = 5;

        // Mock della connessione e dei PreparedStatement
        when(dbConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement, mockStatement1, mockStatement2);
        when(mockStatement.executeUpdate()).thenReturn(1); // Simula l'inserimento dei dati
        when(mockStatement1.executeUpdate()).thenReturn(1); // Simula l'inserimento delle postazioni
        when(mockStatement2.executeUpdate()).thenReturn(1); // Simula l'aggiornamento dell'aula

        // Impostazione del tavolo corrente
        aulaService.inserimentoDatiTavolo(id_tavolo, id_aula, n_posti);

        // Esegui il metodo di terminazione
        aulaService.terminaInserimentoTavolo(dbConnection);

        // Verifica che il metodo executeUpdate sia stato chiamato per ogni query
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockStatement1, times(1)).executeUpdate();
        verify(mockStatement2, times(n_posti)).executeUpdate();

        // Verifica che la lista delle postazioni del tavolo sia stata aggiornata
        assertEquals(n_posti, aulaService.getTavoloCorrente().getLista_postazioni().size());

        // Verifica che il tavolo sia stato aggiunto alla lista dei tavoli dell'aula
        //assertTrue(aulaService.getListaAule().stream().anyMatch(aula -> aula.getLista_tavoli().contains(aulaService.getTavoloCorrente())));
    }




}
