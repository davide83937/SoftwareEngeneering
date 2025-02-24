use ing_soft;

INSERT INTO `amministratore` (`id`, `nome`, `cognome`, `password`) VALUES
(1, 'Davide', 'Pantò', 'provona');

INSERT INTO `studente` (`id`, `Nome`, `Cognome`, `Datanascita`, `Codicefiscale`, `Email`, `Matricola`, `Fkcorsodistudio`, `Password`) VALUES
(1, 'Nicola', 'Alì\'', '12/07/2000', 'LAINCL00L12A028H', 'prova@prova.it', 1000001754, 9, 'prova');

Delete From aula;
SELECT * FROM aula;