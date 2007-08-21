CREATE TABLE IF NOT EXISTS `classes` (
  `department` char(3) NOT NULL default '',
  `block` int(1) NOT NULL default '0',
  `section` varchar(6) NOT NULL default '',
  `sectionurl` text NOT NULL,
  `title` text,
  `professor` varchar(128) default NULL,
  `credits` char(3) default NULL,
  `division` char(2) default NULL,
  `freshmanOnly` tinyint(1) default NULL,
  `year` varchar(5) NOT NULL default '',
  `prerequisite` text,
  `enrollment` int(2) NOT NULL default '0',
  `capacity` int(2) NOT NULL default '0',
  `building` char(2) NOT NULL default '',
  `roomnumber` varchar(4) NOT NULL default '',
  PRIMARY KEY  (`department`,`block`,`section`,`year`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
