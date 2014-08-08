-- phpMyAdmin SQL Dump
-- version 4.2.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Ago 08, 2014 alle 19:49
-- Versione del server: 5.5.38-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.3

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
(1, 'tG8QHnaDzPqDdcEn1yE16g=='),
(2, 'j7xPqAeVJPf6yI/Eox1hxw=='),
(3, '+kiv32b3O3T4P78zoZn22kg5Gdt/r+pfljNeSe6V9ds='),
(4, 'vUGUd2TwLmhv8ddImgoEzQ3+xaz26i2ANk6rgmDHl4Y=');

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
