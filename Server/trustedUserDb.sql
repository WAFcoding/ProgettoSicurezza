-- phpMyAdmin SQL Dump
-- version 4.2.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Ago 07, 2014 alle 15:47
-- Versione del server: 5.5.38-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `trustedUsersDb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(30) COLLATE utf8_bin NOT NULL,
  `pkey` varchar(300) COLLATE utf8_bin NOT NULL,
  `trustLevel` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`username`, `pkey`, `trustLevel`) VALUES
('giovanni', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC9x9TaeIL/yuqBd2bS24mirCxy/NmnwLaPjlbG1VzHv7sCUKLC5jJn0cBDZMMcnuWkUfoBlMmFoF0GAy2w2P/NYRnTyab69QgGa/AIT22wofTELk4F6UmbxgpQVFR4ngxFYBniNOHw2lkR6Z3OOJKdghQo7RaTGmhOisyR/rPgwIDAQAB', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
