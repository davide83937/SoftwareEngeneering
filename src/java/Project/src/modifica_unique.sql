use ing_soft;
-- delete from prenotazione;
ALTER TABLE prenotazione2
ADD CONSTRAINT unica_prenotazione UNIQUE (fkpostazione, fktavolo, fkaula, data, fkturno);
SELECT* FROM prenotazione2;