<?php

/*
Our "test.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
//http://php.net/manual/en/ref.classobj.php
require("test.php");

/**
* 
*/
class User{
    
    function updateCredintials(){
        global $db;
        $userProfile = '127.0.0.1/roger/images/ic_user_profile';
        //if posted data is not empty
        if (!empty($_POST)) {
            if (isset($_POST['fName'])) {
                $fname = $_POST['fName'];
            }
            if (isset($_POST['last_name'])) {
                $last_name = $_POST['last_name'];
            }
            if (isset($_POST['age'])) {
                $age = $_POST['age'];
            }
            if (isset($_POST['cell_num'])) {
                $cell_num = $_POST['cell_num'];
            }
            if (isset($_POST['parent_num'])) {
                $parent_num = $_POST['parent_num'];
            }
            if (isset($_POST['username'])) {
                # code...
                $username = $_POST['username'];
            }
            
        //If the username or password is empty when the user submits
        //the form, the page will die.
        //Using die isn't a very good practice, you may want to look into
        //displaying an error message within the form instead.  
        //We could also do front-end form validation from within our Android App,
        //but it is good to have a have the back-end code do a double check.
        if (empty($_POST['username']) || empty($_POST['fName']) || empty($_POST['last_name']) || empty($_POST['age']) || empty($_POST['cell_num']) || empty($_POST['parent_num']) ) {
        
        
        // Create some data that will be the JSON response 
        $response["success"] = 0;
        $response["message"] = "Please fill in all fields.";
        
        //die will kill the page and not execute any code below, it will also
        //display the parameter... in this case the JSON data our Android
        //app will parse
        die(json_encode($response));
        }   
        //If we have made it here without dying, then we are in the clear to 
        //create a new user.  Let's setup our new query to create a user.  
        //Again, to protect against sql injects, user tokens such as :user and :pass
       // $query = "INSERT INTO User_ ( _UserName, Name_, Surname, PassWord_, Age, Gender, CellNum, ParentNum ) VALUES ( :user, :fname, :last, :pass, :age, :gender, :cell, :pnum ) ";
        //$query = "UPDATE User_ SET Name_ = '$fname', Surname = '$last_name', Age = '$age', CellNum = '$cell_num', ParentNum = '$parent_num' WHERE _UserName = '$username'";
        $query = "CALL prUpdate_User_Data('$username', '$fname', '$last_name', '$age', '$cell_num', '$parent_num')";



        //('$username', '$fname', '$last_name', '$password', '$age', '$gender', '$cell_num', '$parent_num', '$userProfile')";
        
        //Again, we need to update our tokens with the actual data:
        $query_params = array(
            ':user' => $_POST['username'],
            ':fname' => $_POST['fName'],
            ':last' => $_POST['last_name'],
            //':pass' => $_POST['password'],
            ':age' => $_POST['age'],
            //':gender' => $_POST['gender'],
            ':cell' => $_POST['cell_num'],
            ':pnum' => $_POST['parent_num']
        );
        
        //time to run our query, and create the user
        try {
            $stmt   = $db->prepare($query);
            $result = $stmt->execute($query_params);
        }
        catch (PDOException $ex) {
            // For testing, you could use a die and message. 
            die("Failed to run query: " . $ex->getMessage());
            
            //or just use this use this one:
            //$response["success"] = 0;
            //$response["message"] = "Database Error2. Please Try Again!";
            //die(json_encode($response));
        }
        
        //If we have made it this far without dying, we have successfully added
        //a new user to our database.  We could do a few things here, such as 
        //redirect to the login page.  Instead we are going to echo out some
        //json data that will be read by the Android application, which will login
        //the user (or redirect to a different activity, I'm not sure yet..)
        $response["success"] = 1;
        $response["message"] = "User Data successfully updated!";
        echo json_encode($response);
        
        //for a php webservice you could do a simple redirect and die.
        //header("Location: login.php"); 
        //die("Redirecting to login.php");
        
        
    } else {
    ?>
        <h1>Register</h1> 
        <!--  (empty($_POST['username']) || empty($_POST['fName']) || empty($_POST['last_name']) || empty($_POST['password']) || empty($_POST['age']) || empty($_POST['gender']) || empty($_POST['reg_date']) || empty($_POST['cell_num']) || empty($_POST['parent_num']) )-->
        <form action="updateCredintials.php" method="post" enctype="multipart/form-data"> 
            Username:<br /> 
            <input type="text" name="username" value="" placeholder="Choose a username" /> 
            <br /><br /> 
            Full Name:<br />
            <input type="text" name="fName" placeholder="Full Names" />
            <br /><br /> 
            Last Name:<br />
            <input type="text" name="last_name" placeholder="Last Name" />
            <br /><br /> 
            Password:<br /> 
            <input type="password" name="password" value="" placeholder="Password"/> 
            <br /><br /> 
            Age:<br />
            <input type="number" name="age" placeholder="Please enter your age" />
            <br /><br />
            Gender<br />
            <input type="boolean" name="gender" placeholder="Gender" />
            <br /><br />
            Cell Number:<br/ >
            <input type="phone" name="cell_num" placeholder="Cell Number">
            <br /><br />
            Parent Cell:<br />
            <input type="phone" name="parent_num" placeholder="Parent number" />
            <br /><br /> 
            Profile Photo:<br />
            <input type="file" name="userProfile">
            <input type="submit" value="Update User Data" name="submit"/> 
        </form>
        <?php
    }
    }
}
$instance = new user();
$instance -> updateCredintials();
?>