<?php
require 'connect.php';
require 'test.php';
require 'con.php';

Class LeaderBoard{

    public function leaderBoard(){

        global $mysqli;
        global $db;
        
        if (!empty($_GET)) {
            try{
                $levels = array();
                if (isset($_GET["deposit"])) {
                    # code...
                    $deposit= $_GET["deposit"];
                }
                if (isset($_GET['username'])) {
                    // Prepare IN parameter
                    $username = $_GET['username'];
                }

                if (empty($username)) {
                    # code...
                    echo(json_encode("Usernmae not valid"));
                }
                
                $mysqli->query("SET @uname = " . "'" . $mysqli->real_escape_string($username) . "'");
                $result = $mysqli->query("SELECT * FROM User_ ORDER BY User_ID ASC");
                if(!$result) die("CALL failed: (" . $mysqli->errno . ") " . $mysqli->error);

                if ($result->num_rows>0) {
                    # code...
                    while($row = mysqli_fetch_array($result, MYSQLI_ASSOC)){
                        $id = $row["User_ID"];
                    }
                    
                }

                }catch(Exception $e){
                    die("Error occurred:" . $e->getMessage());
                }
         //   $result->close();
         //   $mysqli->next_result();

           $result = $mysqli->query("SELECT * FROM Level_");
            if (!$result) {
                die("failed  on line 50: " .$mysqli->errno);
            }

            if ($result->num_rows>0) {
                # code...
                while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
                    # code...
                    $lev_id = $row["Level_ID"];
                    $lev_name = $row["Level_Name"];
                    $lev_desc =  $row["Level_Description"];
                }
            }
            //    echo(json_encode($levels));

            $query = "SELECT * FROM Level_ ORDER BY Level_ID ASC";

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
            if ($rows != false) {
                # code...
                $response["success"] = 1;
                $response["message"] = "levels successfuly returned!";
                $response["levels"]  = array();
                foreach ($rows as $row ){
                    # code...
                    $getLevels = array();
                    $getLevels["Level_ID"] = $row["Level_ID"];
                    $getLevels["Level_Name"] = $row["Level_Name"];
                    $getLevels["Level_Description"] = $row["Level_Description"];

                    array_push($response["levels"], $getLevels );
                }
            }

           // echo(json_encode($response));

            $result = $mysqli->query("SELECT * FROM Stage WHERE Level_ID = $lev_id");
              if (!$result) {
                die("failed : " .$mysqli->error);
            }

            if ($result->num_rows>0) {
                # code...
                while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
                    # code...
                    $stage_id = $row["Stage_ID"];
                    $level_id = $row["Level_ID"];
                    $stage_name =  $row["Stage_Name"];
                }
            }



            $result = $mysqli->query("SELECT * FROM User_Progress");
            if (!$result) {
                # code...
                die("Failed: " .$mysqli->errno);
            }
            if ($result->num_rows>0) {
                # code...
                while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC)) {
                    # code...
                    $progress_id = $row["Progress_ID"];
                    $pr_stage_id = $row["Stage_ID"];
                    $pr_user_id = $row["User_ID"];
                }
            }

            //$pr_stage_id = 1;

            $query = "SELECT * FROM vwLeaderBoard WHERE  _stage_id = $pr_stage_id ORDER BY _progress Asc ";

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
                $response["success"] = 1;
                $response["message"] = "Data returned!";
                $response["leaderboard"]  = array();

                foreach($rows as $row){
                    $leaderboard = array();
                    $leaderboard["user_id"] = $row["_user_id"];
                    $leaderboard["stage_id"] = $row["_stage_id"];
                    $leaderboard["picture"] = $row["_picture"];
                    $leaderboard["name"] = $row["_name"];
                    $leaderboard["progress"] = $row["_progress"];

                    //update our repsonse JSON data
                    array_push($response["leaderboard"], $leaderboard);
                }
                echo(json_encode($response));
            }
        }else{
            ?>
            <form action="leaderboard.php" method="get" >
                <input type="text" name="username" />
                <input type="submit" value="submit" />
            </form>
            <?php
        }

    }
}
$inst = new LeaderBoard;
$inst->leaderBoard();
?>