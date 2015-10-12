<?php
/**
* 
*/

require "test.php";
require 'con.php';
 
   

class jrBank{
	public function borrowBarc(){
		# code...
		global $db;
	}
	public function deposit(){
		global $dbFound;
		global $db;
		$id = 0;
		$userAccountNum = 0;
		if (!empty($_POST)) {
			
		if (isset($_POST["deposit"])) {
			# code...
			$deposit=$_POST["deposit"];
		}
		if (isset($_POST['username'])) {
			# code...
			$user = $_POST['username'];
		}

		if (empty($deposit) || empty($user)) {
			# code...
			echo(json_encode("Please enter both values"));
		}

		if ($dbFound) {
			# code...
			$query = mysql_query("SELECT * FROM User_ WHERE _UserName = '$user' ") or die();
			if (mysql_num_rows($query)>0) {
			$display = mysql_fetch_assoc($query);
				# code...
				 $id = $display["User_ID"];
			}
		}

		

		//$query = "SELECT 1 FROM Junior_Bank WHERE User_ID='$id'";
		$query = mysql_query("SELECT * FROM Junior_Bank WHERE User_ID= $id ") or die(mysql_error());
		if (mysql_num_rows($query)>0) {
			# code...
			$getAccount = mysql_fetch_assoc($query);
			 $userAccountNum = $getAccount["Account_Num"];
		}
		
			$query = "UPDATE Junior_Bank SET Balance = $deposit WHERE User_ID = '$id' AND Account_Num = '$userAccountNum'";
			// UPDATE Junior_Bank SET Balance = _amount WHERE User_ID = _uid;

			//$query = "INSERT INTO Junior_Bank(Balance) VALUES()";
			$query_params = array(
				':balance'=>$_POST["deposit"]
			);

			try {
				$stmnt = $db->prepare($query);
				$result = $stmnt->execute($query_params);
			} catch (PDOException $e) {
				die($e->getMessage());
			}
			$response["success"] = 1;
        	$response["message"] = "User Data successfully updated!";
        	echo json_encode($response);
		}else{
			?>
			<form action="jrBank.php" method="post" >
				<input type="text" name="deposit" placeholder="deposit" /><br /><br />
				<input type="text" name="username" place="username" placeholder="Username"><br /><br />
				<input type="submit" value="Deposit" > 
			</form>
			<?php
		}
	}
}
$instantitate = new jrBank();
$instantitate->deposit();
?>
