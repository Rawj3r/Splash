<?php
require 'connect.php';
require 'test.php';
require 'con.php';
Class statement{
	public function getBankStatement(){
		global $mysqli;
		global $db;
		//$username = 'test';
		try{
			//get logged in username
			if (isset($_GET['username'])) {
				//get th posted data and assign it to the variable name $username
				$username = $_GET['username'];
			}

			if (isset($_GET['category'])) {
				# code...
				$category = $_GET['category'];
			}
			
			if (!empty($username)) {
				# code...
				$mysqli->query("SET @uname = " . "'" . $mysqli->real_escape_string($username) . "'");
				$result = $mysqli->query("CALL getId(@uname)");
				if(!$result) die("CALL failed: (" . $mysqli->errno . ") " . $mysqli->error);

				if ($result->num_rows>0) {
					# code...
					while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)){
						$id = $row["User_ID"];
					}
					//echo(json_encode($id));
				}
			}
		}catch(Exception $e){
			die("Error occurred:" . $e->getMessage());
		}
		$result->close();
		$mysqli->next_result();
		try {
		$mysqli->query("SET @id = " . "'" . $mysqli->real_escape_string($id) . "'");
		$result = $mysqli->query("CALL prGet_Bank_Data(@id)");
		if(!$result) die("CALL failed: (" . $mysqli->errno . ") " . $mysqli->error);

		if ($result->num_rows>0) {
			# code...
			while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
				# code...
				$userAccountNum = $row["Account_Num"];
			}
		}
	} catch (Exception $e) {
		die($e->getMessage());
	}


	$result->close();
	$mysqli->next_result();

	$response = array();

	//$response = array();
	$query = "CALL prBank_Statement($userAccountNum, '$category')" or die(mysql_error());
	$query_params = array();
	try {
		$stmt   = $db->prepare($query);
		$result = $stmt->execute($query_params);
	} catch (PDOException $e) {
		$response["success"] = 0;
	    $response["message"] = "Database Error!";
	    die(json_encode($response));
	    //die($e->getMessage());
	}

	// Finally, we can retrieve all of the found rows into an array using fetchAll 
	$rows = $stmt->fetchAll();

	if ($rows) {
		# code...
		$response["success"] = 1;
		$response["message"] = "Your Bank Statement is ready!";
    	$response["get"]   = array();
    	

    	foreach ($rows as $row) {
    		# code...
    		$userData["Description"] = $row["Description"];
    		$userData["Trans_Date"] = $row["Trans_Date"];
    		$userData["Amount_"] = $row["Amount_"];

    		array_push($response["get"], $userData);
    	}
    	echo json_encode($response);
	}
	die();
	?>
	<form method="get" action="bank.php">
		<input type="text" placeholder="Category" name="category" ><br><br>
		<input type="text" placeholder="Uname" name="username" ><br><br>
		<input type="submit" value="getStatement">
	</form>
	<?php

	}
}
$inst = new statement;
$inst->getBankStatement();
?>