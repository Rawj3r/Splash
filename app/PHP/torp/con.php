<?php
error_reporting(E_ALL ^ E_DEPRECATED);
			$user = "TDA_Torpedokid"; 
    		$password = "iloveempirestate"; 
    		$host = "whm.empirestate.co.za"; 
    		$dbname = "TDA_Torpedokid";
			$dbConnect = mysql_connect($host, $user, $password) or die(mysql_error());
   			$dbFound = mysql_select_db($dbname, $dbConnect) or die(mysql_error());

   ?>