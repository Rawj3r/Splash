<?php
require 'connect.php';
require 'test.php';
require 'con.php';

Class leaderBoard{
	function getLeaders(){
		global $mysqli;
		global $db;

		$result = $mysqli->query("SELECT * FROM User_");
		if(!$result) die("CALL failed: (" . $mysqli->errno . ") " . $mysqli->error);

		if ($result->num_rows>0) {			
		# code...
			while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)){
				$id = $row["User_ID"];
			}
			//echo(json_encode($id));
		}

		$result->close();
		$mysqli->next_result();
		try {
			if (!empty($id)) {
				# code...
				$mysqli->query("")
			}
		} catch (Exception $e) {
			
		}
	}
}
?>