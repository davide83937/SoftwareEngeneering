use ing_soft;
-- delete from prenotazione;
-- ALTER TABLE prenotazione2
-- ADD CONSTRAINT unica_prenotazione UNIQUE (fkpostazione, fktavolo, fkaula, data, fkturno);
-- SELECT* FROM prenotazione2;

SELECT 
    p.id AS id_postazione, 
    p.fktavolo, 
    p.fkaula, 
    t.id AS id_turno,
    t.nome AS nome_turno,
    t.orario_inizio,
    t.orario_fine
FROM postazione1 p
JOIN turno t  -- Consideriamo tutti i turni disponibili
LEFT JOIN prenotazione2 pr 
    ON pr.fkpostazione = p.id 
    AND pr.fktavolo = p.fktavolo 
    AND pr.fkaula = p.fkaula 
    AND pr.data = '2025-02-15'  -- Sostituisci con la data desiderata
    AND pr.fkturno = t.id  -- Verifichiamo se c'è una prenotazione per quel turno
WHERE p.fkaula = 22  -- Sostituisci con l'ID dell'aula desiderata
AND pr.id IS NULL;

