-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 05, 2025 alle 10:52
-- Versione del server: 5.7.17
-- Versione PHP: 7.1.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbaulestudio`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `amministratore`
--

CREATE TABLE `amministratore` (
  `id` int(11) NOT NULL,
  `nome` varchar(32) NOT NULL,
  `cognome` varchar(32) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `aula`
--

CREATE TABLE `aula` (
  `id` int(11) NOT NULL,
  `fkdipartimento` int(11) NOT NULL,
  `nome` varchar(32) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `aula`
--

INSERT INTO `aula` (`id`, `fkdipartimento`, `nome`) VALUES
(1, 1, 'prova3'),
(2, 7, 'prova2');

-- --------------------------------------------------------

--
-- Struttura della tabella `corsidistudio`
--

CREATE TABLE `corsidistudio` (
  `id` int(11) NOT NULL,
  `corsodistudio` varchar(128) NOT NULL,
  `fkdipartimento` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `corsidistudio`
--

INSERT INTO `corsidistudio` (`id`, `corsodistudio`, `fkdipartimento`) VALUES
(1, 'Automation Engineering and Control of Complex Systems', 8),
(2, 'Communications Engineering LM-27', 8),
(3, 'Electrical Engineering for Sustainable Green Energy Transition LM-28', 8),
(4, 'Electronic Engineering LM-29', 8),
(5, 'Ingegneria elettronica L-8', 8),
(6, 'Ingegneria gestionale L-9 R', 8),
(7, 'Ingegneria gestionale L-9 R', 8),
(8, 'Ingegneria industriale L-9', 8),
(9, 'Ingegneria informatica L-8', 8),
(10, 'Ingegneria informatica LM-32', 8),
(11, 'Agricultural science and technology LM-69 R', 1),
(12, 'Biotecnologie agrarie LM-7', 1),
(13, 'Gestione dei sistemi produttivi agrari mediterranei L-25 R', 1),
(14, 'Pianificazione e sostenibilità ambientale del territorio e del paesaggio L-21', 1),
(15, 'Salvaguardia del territorio, dell\'ambiente e del paesaggio LM-75', 1),
(16, 'Scienze e tecnologie agrarie L-25', 1),
(17, 'Scienze e tecnologie agrarie LM-69', 1),
(18, 'Scienze e tecnologie alimentari L-26', 1),
(19, 'Scienze e tecnologie alimentari LM-70', 1),
(20, 'Scienze e tecnologie per la ristorazione e distribuzione degli alimenti mediterranei L-26', 1),
(21, 'A011_Discipline letterarie e Latino DPCM60_30_DC', 2),
(22, 'A011_Discipline letterarie e Latino DPCM60_30_DC', 2),
(23, 'A013_Discipline letterarie, Latino e Greco DPCM60_30_DC', 2),
(24, 'A018_Filosofia e Scienze Umane DPCM60_30_DC', 2),
(25, 'A019_Filosofia e Storia DPCM60_30_DC', 2),
(26, 'A020_Fisica DPCM60_30_DC', 2),
(27, 'A022_Italiano, Storia, Geografia nella Scuola secondaria di I grado DPCM60_30_DC', 2),
(28, 'A026_Matematica DPCM60_30_DC', 2),
(29, 'A027_Matematica e Fisica DPCM60_30_DC', 2),
(30, 'A028_Matematica e Scienze DPCM60_30_DC', 2),
(31, 'A031_Scienze degli alimenti DPCM60_30_DC', 2),
(32, 'A034_Scienze e Tecnologie chimiche DPCM60_30_DC', 2),
(33, 'A040_Tecnologie elettriche elettroniche DPCM60_30_DC', 2),
(34, 'A041_Scienze e Tecnologie informatiche DPCM60_30_DC', 2),
(35, 'A045_Scienze economico-aziendali DPCM60_30_DC', 2),
(36, 'A047_Scienze Matematiche applicate DPCM60_30_DC', 2),
(37, 'A050_Scienze naturali, chimiche e biologiche DPCM60_30_DC', 2),
(38, 'A051_Scienze, Tecnologie e Tecniche agrarie DPCM60_30_DC', 2),
(39, 'A054_Storia dell\'Arte DPCM60_30_DC', 2),
(40, 'A060_Tecnologia nella Scuola secondaria di I grado DPCM60_30_DC', 2),
(41, 'AA24_Lingua e cultura straniera (Francese) DPCM60_30_DC', 2),
(42, 'AA25_Lingua inglese e seconda lingua comunitaria nella Scuola secondaria di I grado (Francese) DPCM60_30_DC', 2),
(43, 'AB24_Lingua e cultura straniera (Inglese) DPCM60_30_DC', 2),
(44, 'AB25_Lingua inglese e seconda lingua comunitaria nella Scuola secondaria di I grado (Inglese) DPCM60_30_DC', 2),
(45, 'AC24_Lingua e cultura straniera (Spagnolo) DPCM60_30_DC', 2),
(46, 'AC25_Lingua inglese e seconda lingua comunitaria nella Scuola secondaria di I grado (Spagnolo) DPCM60_30_DC', 2),
(47, 'AD24_Lingua e cultura straniera (Tedesco) DPCM60_30_DC', 2),
(48, 'Bando A040 - Tecnologie elettriche elettroniche ? 30 CFU a.a. 2023/24 DPCM60_30_DC', 2),
(49, 'BB02_Conversazione Lingua straniera (Inglese) DPCM60_30_DC', 2),
(50, 'Discipline letterarie e Latino DPCM60_30_DC', 2),
(73, 'Finanza Aziendale LM-77', 4),
(72, 'Economia L-33', 4),
(71, 'Economia e politiche pubbliche LM-56', 4),
(69, 'Direzione aziendale LM-77', 4),
(70, 'Economia Aziendale L-18', 4),
(68, 'Tecniche di fisiopatologia cardiocircolatoria e perfusione cardiovascolare (abilitante alla professione sanitaria)', 3),
(67, 'Ostetricia (abilitante alla professione sanitaria di Ostetrica/o) L/SNT1', 3),
(66, 'Ortognatodonzia SAS-5513', 3),
(65, 'Odontoiatria e protesi dentaria LM-46', 3),
(63, 'Medicina e chirurgia LM-41', 3),
(64, 'Medicine and Surgery LM-41', 3),
(74, 'Management delle imprese per l\'economia sostenibile L-18', 4),
(75, 'Fisica L-30', 5),
(76, 'Physics LM-17', 5),
(77, 'Giurisprudenza LMG/01', 6),
(78, 'Scuola di Specializzazione per le Professioni Legali', 6),
(79, 'Chemical engineering for industrial sustainability (Ingegneria chimica per la sostenibilità industriale)', 7),
(80, 'Construction Management and Safety LM-26 R', 7),
(81, 'Ingegneria civile delle acque e dei trasporti LM-23', 7),
(82, 'Ingegneria Civile Strutturale e Geotecnica LM-23', 7),
(83, 'Ingegneria Civile, Ambientale e Gestionale L-7', 7),
(84, 'Ingegneria edile-architettura LM-4 c.u.', 7),
(85, 'Ingegneria gestionale LM-31', 7),
(86, 'Ingegneria meccanica LM-33', 7),
(87, 'Ingegneria per l\'ambiente e il territorio LM-35', 7),
(88, 'Ingegneria per la Transizione Ecologica L-9 R', 7);

-- --------------------------------------------------------

--
-- Struttura della tabella `dipartimento`
--

CREATE TABLE `dipartimento` (
  `id` int(11) NOT NULL,
  `nome` varchar(128) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `dipartimento`
--

INSERT INTO `dipartimento` (`id`, `nome`) VALUES
(1, 'AGRICOLTURA, ALIMENTAZIONE E AMBIENTE (Di3A)'),
(2, 'Alta Scuola di Formazione degli Insegnanti (ASFI)'),
(3, 'CHIRURGIA GENERALE E SPECIALITÀ MEDICO-CHIRURGICHE'),
(4, 'ECONOMIA E IMPRESA'),
(5, 'FISICA ED ASTRONOMIA \"Ettore Majorana\"'),
(6, 'GIURISPRUDENZA'),
(7, 'INGEGNERIA CIVILE E ARCHITETTURA (DICAR)'),
(8, 'INGEGNERIA ELETTRICA ELETTRONICA E INFORMATICA'),
(9, 'MATEMATICA E INFORMATICA'),
(10, 'MEDICINA CLINICA E SPERIMENTALE'),
(11, 'SCIENZE BIOLOGICHE, GEOLOGICHE E AMBIENTALI'),
(12, 'SCIENZE BIOMEDICHE E BIOTECNOLOGICHE'),
(13, 'SCIENZE CHIMICHE'),
(14, 'SCIENZE DEL FARMACO E DELLA SALUTE'),
(15, 'SCIENZE DELLA FORMAZIONE'),
(16, 'SCIENZE MEDICHE, CHIRURGICHE E TECNOLOGIE AVANZATE G.F. INGRASSIA'),
(17, 'SCIENZE POLITICHE E SOCIALI'),
(18, 'SCIENZE UMANISTICHE');

-- --------------------------------------------------------

--
-- Struttura della tabella `materia`
--

CREATE TABLE `materia` (
  `id` int(11) NOT NULL,
  `materia` varchar(32) NOT NULL,
  `fkdipartimento` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `materia`
--

INSERT INTO `materia` (`id`, `materia`, `fkdipartimento`) VALUES
(1, 'ADVANCED COMPUTER ARCHITECTURES', 10),
(2, 'ADVANCED PROGRAMMING LANGUAGES', 10),
(3, 'ALGORITMI', 10),
(4, 'ARCHITETTURE E TECNOLOGIE DEI SI', 10),
(5, 'COGNITIVE COMPUTING AND ARTIFICI', 10),
(6, 'DEEP LEARNING', 10),
(7, 'DISTRIBUTED SYSTEMS AND BIG DATA', 10),
(8, 'INDUSTRIAL AND AUTOMOTIVE REAL-T', 10),
(9, 'INDUSTRIAL INFORMATICS', 10),
(10, 'INGEGNERIA DEL SOFTWARE', 10),
(11, 'INTERNET OF THINGS BASED SMART S', 10),
(12, 'SICUREZZA DEI SISTEMI INFORMATIV', 10),
(13, 'TECNOLOGIA DEI SISTEMI DI CONTRO', 10);

-- --------------------------------------------------------

--
-- Struttura della tabella `prenotazione`
--

CREATE TABLE `prenotazione` (
  `id` int(11) NOT NULL,
  `fkstudente` int(11) NOT NULL,
  `fktavolo` int(11) NOT NULL,
  `data` date NOT NULL,
  `orainizio` datetime NOT NULL,
  `orafine` datetime NOT NULL,
  `fkmateria` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `studente`
--

CREATE TABLE `studente` (
  `id` int(11) NOT NULL,
  `Nome` varchar(32) NOT NULL,
  `cognome` varchar(32) NOT NULL,
  `matricola` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `tavolo`
--

CREATE TABLE `tavolo` (
  `id` int(11) NOT NULL,
  `numeroposti` int(11) NOT NULL,
  `fkaula` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `amministratore`
--
ALTER TABLE `amministratore`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `aula`
--
ALTER TABLE `aula`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `corsidistudio`
--
ALTER TABLE `corsidistudio`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `dipartimento`
--
ALTER TABLE `dipartimento`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `studente`
--
ALTER TABLE `studente`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `tavolo`
--
ALTER TABLE `tavolo`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `aula`
--
ALTER TABLE `aula`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT per la tabella `corsidistudio`
--
ALTER TABLE `corsidistudio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;
--
-- AUTO_INCREMENT per la tabella `tavolo`
--
ALTER TABLE `tavolo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
