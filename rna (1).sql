-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2016 at 12:44 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `rna`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'himanshu', '4351334731');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `book_id` int(10) NOT NULL AUTO_INCREMENT,
  `t_id` int(5) DEFAULT NULL,
  `date_of_booking` date NOT NULL,
  `c_id` int(5) NOT NULL,
  `r_id` int(5) NOT NULL,
  `sourceLocation` int(5) NOT NULL,
  `destinationLocation` int(5) NOT NULL,
  `date_of_completion` date DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`book_id`, `t_id`, `date_of_booking`, `c_id`, `r_id`, `sourceLocation`, `destinationLocation`, `date_of_completion`) VALUES
(2, NULL, '2016-05-20', 2, 2, 1, 6, NULL),
(3, NULL, '2016-05-28', 2, 2, 1, 7, NULL),
(4, NULL, '2016-05-20', 2, 2, 5, 7, NULL),
(5, NULL, '2016-05-20', 2, 2, 1, 6, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE IF NOT EXISTS `company` (
  `c_id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `mobile` bigint(10) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`c_id`, `name`, `mobile`, `username`, `email`, `password`, `address`) VALUES
(2, 'jyoti laboratories', 9911286612, 'mohit', 'mohit@gmail.com', 'mohitmota', 'shivpuri Street hapur 110085 india'),
(3, 'vishwa electrotech', 8527035458, 'vishwa', 'vishwaelectrotech@gmail.com', '12345678', 'Bhagwanpur Roorkee '),
(5, 'Rallies India', 9810253330, 'rallies', 'ralliesindia@gmail.com', '12345678', 'Sikandrabad Ghazibad Uttar Pradesh ');

-- --------------------------------------------------------

--
-- Table structure for table `contract`
--

CREATE TABLE IF NOT EXISTS `contract` (
  `con_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `r_id` int(5) NOT NULL,
  `trucktype` varchar(50) NOT NULL,
  `c_id` int(5) NOT NULL,
  PRIMARY KEY (`con_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `contract`
--

INSERT INTO `contract` (`con_id`, `r_id`, `trucktype`, `c_id`) VALUES
(8, 2, '15Mton', 2),
(9, 3, '9Mton', 2),
(10, 4, '9Mton', 3),
(11, 4, '15Mton', 3);

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` int(5) NOT NULL AUTO_INCREMENT,
  `loc_name` varchar(50) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`location_id`, `loc_name`) VALUES
(1, 'New Delhi '),
(2, 'Mumbai '),
(3, 'Ahmedabad'),
(4, 'Bangalore'),
(5, 'Jaipur');

-- --------------------------------------------------------

--
-- Table structure for table `location_address`
--

CREATE TABLE IF NOT EXISTS `location_address` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `loc_id` int(5) NOT NULL,
  `c_id` int(5) NOT NULL,
  `address` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `location_address`
--

INSERT INTO `location_address` (`id`, `loc_id`, `c_id`, `address`) VALUES
(1, 1, 2, 'A-62 Sector-62 New Delhi 110095 India  '),
(5, 1, 2, 'B-6/119-120, sec-7, Rohini New Delhi 110085 INDIA '),
(6, 5, 2, 'B-6/119-120, sec-7, pink city Jaipur 114236 INDIA '),
(7, 5, 2, 'A-10 Main Bazaar Jaipur 110092 INDIA '),
(8, 5, 2, 'B-6/119-120, sec-7, Rohini Jaipur 114497 INDIA ');

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE IF NOT EXISTS `route` (
  `r_id` int(5) NOT NULL AUTO_INCREMENT,
  `source` varchar(30) NOT NULL,
  `destination` varchar(30) NOT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`r_id`, `source`, `destination`) VALUES
(1, 'New Delhi', 'Mumbai'),
(2, 'New Delhi', 'Jaipur'),
(3, 'Jaipur', 'New Delhi'),
(4, 'Mumbai', 'Jaipur');

-- --------------------------------------------------------

--
-- Table structure for table `transporter`
--

CREATE TABLE IF NOT EXISTS `transporter` (
  `t_id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `mobile` bigint(10) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `transporter`
--

INSERT INTO `transporter` (`t_id`, `name`, `mobile`, `username`, `email`, `password`, `address`) VALUES
(2, 'AVG Logistics', 9717334731, 'avglogistics', 'avglogistics@gmail.com', '12345678', 'dilshad Garden Street DELHI 110095 india'),
(3, 'himanshu', 9717334731, 'himansh', 'hars@jhgd.com', '1234567', 'abjdb Street ahb 111111 india'),
(4, 'saksham', 9717334731, 'saksham', 's@g.com', 'saksham', 'local Street delhi 110085 india'),
(5, 'mohit', 6546456456, 'mohit', 'himanshugoyal@live.in', 'mohitsingh', 'dilshad Garden Street delhi 110085 india');

-- --------------------------------------------------------

--
-- Table structure for table `truck`
--

CREATE TABLE IF NOT EXISTS `truck` (
  `t_id` int(5) NOT NULL AUTO_INCREMENT,
  `td_id` int(5) NOT NULL,
  `r_id` int(5) NOT NULL,
  `regno` varchar(20) NOT NULL,
  `trucktype` varchar(20) NOT NULL,
  `pan` bigint(10) NOT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `truck`
--

INSERT INTO `truck` (`t_id`, `td_id`, `r_id`, `regno`, `trucktype`, `pan`) VALUES
(3, 3, 2, '1234', '9Mton', 1234),
(4, 4, 1, 'DL7CP', '9Mton', 123456);

-- --------------------------------------------------------

--
-- Table structure for table `truck_driver`
--

CREATE TABLE IF NOT EXISTS `truck_driver` (
  `td_id` int(5) NOT NULL AUTO_INCREMENT,
  `t_id` int(5) NOT NULL,
  `name` varchar(50) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  PRIMARY KEY (`td_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `truck_driver`
--

INSERT INTO `truck_driver` (`td_id`, `t_id`, `name`, `mobile`) VALUES
(3, 5, 'mohit singh', '8460334731'),
(4, 5, 'ASEEM', '9717334731');

-- --------------------------------------------------------

--
-- Table structure for table `truck_type`
--

CREATE TABLE IF NOT EXISTS `truck_type` (
  `trucktype` varchar(50) NOT NULL,
  PRIMARY KEY (`trucktype`),
  UNIQUE KEY `truck_type` (`trucktype`),
  UNIQUE KEY `truck_type_2` (`trucktype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `truck_type`
--

INSERT INTO `truck_type` (`trucktype`) VALUES
('9Mton'),
('Container'),
('Trailer '),
('Turbo 15Mton'),
('Turbo 20Mton ');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
