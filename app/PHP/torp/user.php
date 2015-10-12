<?php
require_once'test.php';

Class user{
	function userData(){
		global $db;
		//gets user's info based off of a username.
		

		//$query = "SELECT * FROM User_ WHERE _UserName = :username";
		//$query = "CALL prGet_User_Profile(@user_name)"
		if (isset($_GET['username'])) {
			$uname = $_GET['username'];
		}
		
		$query = "CALL prGet_User_Profile('$uname')";

		//execute query
		
		$query_params = array(':username' => $_GET['username']);

		try {
			$stmt   = $db->prepare($query);
    		$result = $stmt->execute($query_params);
		} catch (PDOException $e) {
			/*$response["success"] = 0;
		    $response["message"] = "Database Error!";
		    die(json_encode($response));*/
		    die("Failed to run query: " . $e->getMessage());
		}

		// Finally, we can retrieve all of the found rows into an array using fetchAll 
		$rows = $stmt->fetchAll();
		if ($rows) {
			# code...
			$response["success"] = 1;
		    $response["message"] = "User data available!";
		    $response["profile"]  = array();
		    foreach ($rows as $row) {
		    	$user=array();
		        $user["username"] = $row["_UserName"];
		        $user["name"]    = $row["Name_"];
		        $user["lastName"]  = $row["Surname"];
		        $user["age"] = $row["Age"];
		        $user["gender"] = $row["Gender"];
		        $user["reg_date"] = $row["Reg_Date"];
		        $user["cellNum"] = $row["CellNum"];
		        $user["parentNum"] = $row["ParentNum"];
		        $user["userPhoto"] = $row["_Picture"];
		        
		        
		        //update our repsonse JSON data
		        array_push($response["profile"], $user);
		        
	    	}
	    	echo json_encode($response);
		}else{
			$response["success"] = 0;
    		$response["message"] = "No user data available!";
    		die(json_encode($response));
		}
	}
}
$instance = new user;
$instance -> userData();
?>