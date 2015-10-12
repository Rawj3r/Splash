<?php
require 'connect.php';
require 'test.php';
require 'con.php';
Class bank{
	public function deposit(){

		global $mysqli;
		
		if (!empty($_POST)) {
		try{
		if (isset($_POST["deposit"])) {
			# code...
			$deposit=$_POST["deposit"];
		}
		if (isset($_POST['username'])) {
			// Prepare IN parameter
			$username = $_POST['username'];
		}

		if (empty($deposit) || empty($username)) {
			# code...
			echo(json_encode("Please enter both values"));
		}
		
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
	try {
		$mysqli->query("SET @acc = " . "'" . $mysqli->real_escape_string($userAccountNum) . "'");
		$mysqli->query("SET @id = " . "'" . $mysqli->real_escape_string($id) . "'");
		$mysqli->query("SET @amnt = " . "'" . $mysqli->real_escape_string($deposit) . "'");
		$result = $mysqli->query("CALL prUpdate_Junior_Bank(@id, @acc, @amnt)");
		if ($result) {
			# code...
			echo(json_encode("You have successfully deposited B" . $deposit . " in your account"));
		}
	} catch (Exception $e) {
		die($e->getMessage());
	}

	try {
		$query = $mysqli->query("CALL prReturn_User_Account(@id)");
		if (!$query) {
			# code...
			die("CALL failed: (" . $mysqli->errno . ") " . $mysqli->error);
		}
		if ($query->num_rows>0) {
			# code...
			while ($row = mysqli_fetch_array($query, MYSQLI_ASSOC)) {
				# code...
				//echo(json_encode("You have " . $row["Balance"] . " left in your account"));
				$balance = $row["Balance"];
			}
		}
		echo(json_encode($balance));
	} catch (Exception $e) {
		die($e->getMessage());
	}
	}else{
		?>
			
			<?php
	}
}


public function getBalance(){

	/*global $mysqli;

	//check for post data
	if (isset($_GET['username'])) {
		# code...
		$user = $_GET['username'];
	}

	if (!empty($user)) {
		# code...
		try {
			$mysqli->query("SET @uname = " . "'" . $mysqli->real_escape_string($user) . "'");
			$query = $mysqli->query("CALL getId(@uname)");
			if (!$query) {
				# code...
				die("CALL failed: (" . $mysqli->errno.  ") " . $mysqli->error);
			}
			if ($query->num_rows>0) {
				# code...
				while ($row = mysqli_fetch_array($query, MYSQLI_ASSOC)) {
					# code...
					$id = $row["User_ID"];
				}
			}
		} catch (Exception $e) {
			die($e->getMessage());
		}
		try {
			$mysqli->query("SET @id = " . "'" . $mysqli->real_escape_string($id) . "'");
			$query = $mysqli->query("prGet_Bank_Data(@id)");
			if ($query) {
				# code...
				die("CALL failed: (" . $mysqli->errno .  ") " . $mysqli->error);
			}
			if (!empty($query)) {
				# code...
				if ($query->num_rows>0) {
					# code...
					$query = mysqli_fetch_array($query);

					$result = array();
					$result["Balance"] = $query["Balance"];

					// success
            		$response["success"] = 1;

            		// user node
            		$response["get"] = array();

            		array_push($response["get"], $result);

            		echo(json_encode($response));
            		var_dump($response);
				}
			}
		} catch (Exception $e) {
			
		}
	}*/

	global $db;
	$response = array();


	if (isset($_GET["username"])) {
		# code...
		$user = $_GET["username"];
	}

	if (!empty($user)) {
		# code...
		$result = mysql_query("CALL getId('$user')");
		if (mysql_num_rows($result)>0) {
			# code...
			$getUserData = mysql_fetch_assoc($result);
			$id = $getUserData["User_ID"];
		}


		$query = "CALL prGet_Bank_Data('$id')" or die(mysql_error());
		$query_params = array();
		try {
		    $stmt   = $db->prepare($query);
			$result = $stmt->execute($query_params);
		}catch (PDOException $ex) {
		    $response["success"] = 0;
		    $response["message"] = "Database Error!";
		    die(json_encode($response));
		}

		// Finally, we can retrieve all of the found rows into an array using fetchAll 
		$rows = $stmt->fetchAll();

		if ($rows) {
			# code...
			$response["success"] = 1;
		    $response["message"] = "Data Returned!";
		    $response["data"]   = array();

		    foreach($rows as $row){
		    	$output["Balance"] = $row["Balance"];

		    	array_push($response["data"], $output);
		    }
		    echo json_encode($response);
		}

			//$result1 = mysql_query("CALL prGet_Bank_Data('$id')") or die(mysql_error());
			//if (!empty($result1)) {
				# code...
				//check for empty results
				//if (mysql_num_rows($result1)>0) {
					# code...
					//$data = mysql_fetch_array($result1);

					//$bankData = array();

					 //$bankData["Balance"] = $data["Balance"];
				//} 
			//}
		}
	}

	public function getBankStatement(){
		global $mysqli;
		global $db;
		//$username = 'test';
		try{
			//get logged in username
			if (isset($_GET['username'])) {
				# code...
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
$bankDepo = new bank;
$bankDepo->deposit();
$bankBal = new bank;
$bankBal->getBalance();
$bankState = new bank;
$bankState->getBankStatement();
?>