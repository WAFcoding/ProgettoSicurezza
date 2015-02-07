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
-- Database: `trustedUsersDb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `registered_request`
--

CREATE TABLE IF NOT EXISTS `registered_request` (
`id` int(11) NOT NULL,
  `name` varchar(30) COLLATE utf8_bin NOT NULL,
  `surname` varchar(30) COLLATE utf8_bin NOT NULL,
  `country` varchar(30) COLLATE utf8_bin NOT NULL,
  `countryCode` varchar(2) COLLATE utf8_bin NOT NULL,
  `city` varchar(30) COLLATE utf8_bin NOT NULL,
  `organization` varchar(30) COLLATE utf8_bin NOT NULL,
  `secidentifier` varchar(60) COLLATE utf8_bin NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `publicKey` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Contiene tutte le richieste di registrazione pendenti' AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `registered_request`
--

INSERT INTO `registered_request` (`id`, `name`, `surname`, `country`, `countryCode`, `city`, `organization`, `secidentifier`, `status`, `publicKey`) VALUES
(1, 'Giorgio', 'Bianchi', 'Italy', 'IT', 'Rome', 'blablabla', '301BEC10E916FC9AC23F6D6FB6291953917C8314', 1, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCo/6F8Qtjcb3r+mI2/D2omHgxiHkVIzFhZdp3KNznIpd7ZLRuDO6Lehyjuu7SFdWEpDomDxJrPP5dvbILKPTZ8YAXDC2EHNAaJKRyLgI8Z2yXOxv91uw7dhXPRVT19DMFFjbmJNK0uJz2uTFWkBbxpLEyM36UPcz3q+xkF4a6tVwIDAQAB');

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
('Giorgio_1', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCo/6F8Qtjcb3r+mI2/D2omHgxiHkVIzFhZdp3KNznIpd7ZLRuDO6Lehyjuu7SFdWEpDomDxJrPP5dvbILKPTZ8YAXDC2EHNAaJKRyLgI8Z2yXOxv91uw7dhXPRVT19DMFFjbmJNK0uJz2uTFWkBbxpLEyM36UPcz3q+xkF4a6tVwIDAQAB', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `registered_request`
--
ALTER TABLE `registered_request`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `registered_request`
--
ALTER TABLE `registered_request`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
