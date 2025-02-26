-- INSERT INTO turno VALUES (1, "Mattina", "08:00:00", "12:00:00");
-- INSERT INTO turno VALUES (2, "Pomeriggio", "12:00:00", "16:00:00");
-- INSERT INTO turno VALUES (3, "Sera", "16:00:00", "19:30:00");

CREATE TABLE blocco(
id_blocco INT PRIMARY KEY AUTO_INCREMENT,
matricola INT NOT NULL,
data_inizio DATE NOT NULL,
data_fine DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE segnalazione (
  id_segnalazione INT PRIMARY KEY AUTO_INCREMENT,
  matricola int NOT NULL,          
  oggetto VARCHAR(50) NOT NULL,
  testo VARCHAR(200) NOT NULL,
  stato VARCHAR(20) NOT NULL
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


