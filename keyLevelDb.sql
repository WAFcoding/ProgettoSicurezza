-- phpMyAdmin SQL Dump
-- version 4.2.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 07, 2015 alle 15:16
-- Versione del server: 5.5.41-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `keyLevelDb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `key_table`
--

CREATE TABLE IF NOT EXISTS `key_table` (
  `level` int(10) NOT NULL,
  `lkey` varchar(300) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `key_table`
--

INSERT INTO `key_table` (`level`, `lkey`) VALUES
(1, 'wFnEIRfqW3veVfJ7zAsrKk2cZtd8Sd90zV/C27F7We7Tc6nKLKROnYpzb1u4hqZO7aP+fehq5tKY\nMyUW8njYmA=='),
(2, 'kSqk7xbOtlKUnSL8PupcB6j53DQCmdAYAsbqxVWgCkapvRFYdtPl7TzDG8lB5WCZVtTBWXjnEMaI\nj3RmRwW4Dw=='),
(3, 'QMFO4eVIPeUyc9jHeIEm34nDOfKa9G22S1pKqVCtVY+yMT4AjiEesLECmBjDsTxxrnaQ9IHH4CMH\n2z32JoIvLw=='),
(4, '91oVl7YqhUCpMvjyBCb1ouUOxjH8ROIgs8D9i9qBFoASsGdhq6Mrzc9MjRrHIDhMISunpBlsUkmT+BnBieuzxA=='),
(5, 'BpfR2WX5n9e0dH2ThIjHm+ltKG5aA9HgvC3LRlHI76Jh4NHYNHZ35s7oPNQhQVzMILeQs0mgyN2o5Kex2mebwQ=='),
(6, 'q+SOqyI4KEYo8sggg7zOxjNjUYjHM5Z8lm3fENC0GXaGNCxlbcQ/ZIf2x+XKWXbRjHQHLskHBEan\ne4YeUrm6Iw=='),
(7, '8E7ImG9yxsJAIRiKlz1Sw+qvLTeLlSY5I1cNLbpXauk+lf+6H/nYlqtot8AugLRvs2zGOC3J7iirVbyy9T+nLA==');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `key_table`
--
ALTER TABLE `key_table`
 ADD PRIMARY KEY (`level`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
