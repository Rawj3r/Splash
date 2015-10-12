<?php
/**
* 
*/
class DB_CONNECT{
	
	// constructor
	function __construct(){
	# connecting to database
		$this->connect();
	}

	#destructor
	function __destruct(){
		# closing database
		$this->close();
	}

	#connecting with d-base
	function connect(){
		require_once __DIR__ . '/db_config.php';

		#connecting to mysql d-base
		$con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWsORD) or die(mysql_error());
		$db = mysql_select_db(DB_DATBASE) or die(mysql_error());

		return $con;
	}

	function close(){
		mysql_close();
	}
}
?>