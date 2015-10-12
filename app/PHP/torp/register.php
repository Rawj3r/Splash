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
    
    function validateCredintials(){
        global $db;
        $userProfile = '127.0.0.1/roger/images/ic_user_profile';
        //if posted data is not empty
        if (!empty($_POST)) {

            if (isset($_POST['username'])) {
                $username=$_POST['username'];
            }
            if (isset($_POST['fName'])) {
                $fname = $_POST['fName'];
            }
            if (isset($_POST['lastName'])) {
                $lastName = $_POST['lastName'];
            }
            if (isset($_POST['password'])) {
                $password = $_POST['password'];
            }
            if (isset($_POST['age'])) {
                $age = $_POST['age'];
            }
            if (isset($_POST['gender'])) {
                $gender = $_POST['gender'];
            }
            if (isset($_POST['cellNum'])) {
                $cellNum = $_POST['cellNum'];
            }
            if (isset($_POST['parentNum'])) {
                $parentNum = $_POST['parentNum'];
            }
            
        //If the username or password is empty when the user submits
        //the form, the page will die.
        //Using die isn't a very good practice, you may want to look into
        //displaying an error message within the form instead.  
        //We could also do front-end form validation from within our Android App,
        //but it is good to have a have the back-end code do a double check.
        if (empty($username) || empty($fname) || empty($lastName) || empty($password) || empty($age) || empty($gender) || empty($cellNum) || empty($parentNum) ||empty($userProfile) ) {
        
        
        // Create some data that will be the JSON response 
        $response["success"] = 0;
        $response["message"] = "Please fill in all fields.";
        
        //die will kill the page and not execute any code below, it will also
        //display the parameter... in this case the JSON data our Android
        //app will parse
        die(json_encode($response));
        }    //$query = " SELECT 1 FROM User_ WHERE _UserName = :user";
        $query = "CALL getId('$username')";

    
        //if the page hasn't died, we will check with our database to see if there is
        //already a user with the username specificed in the form.  ":user" is just
        //a blank variable that we will change before we execute the query.  We
        //do it this way to increase security, and defend against sql injections
        //now lets update what :user should be
        $query_params = array(
            ':user' => $_POST['username']
        );
        

        //reference http://php.net/manual/en/pdo.prepare.php
        //Now let's make run the query:
        try {
            // These two statements run the query against your database table. 
            $stmt   = $db->prepare($query); // Prepares a statement for execution and returns a statement object
            $result = $stmt->execute($query_params); // execute the prepared statement
        }
        catch (PDOException $ex) {
            // For testing, you could use a die and message. 
            die("Failed to run query: " . $ex->getMessage());
            
            //or just use this use this one to product JSON data:
            //$response["success"] = 0;
            //$response["message"] = "Database Error1. Please Try Again!";
            //die(json_encode($response));
        }
        //http://php.net/manual/en/pdostatement.fetch.php
        //fetch is an array of returned data.  If any data is returned,
        //we know that the username is already in use, so we murder our
        //page
        $row = $stmt->fetch();
        if ($row) {
            // For testing, we could use a die and message. 
            //die("This username is already in use");
            
            //You could comment out the above die and use this one:
            $response["success"] = 0;
            $response["message"] = "I'm sorry, this username is already in use";
            die(json_encode($response));
        }
        
        //If we have made it here without dying, then we are in the clear to 
        //create a new user.  Let's setup our new query to create a user.  
        //Again, to protect against sql injects, user tokens such as :user and :pass
       // $query = "INSERT INTO User_ ( _UserName, Name_, Surname, PassWord_, Age, Gender, CellNum, ParentNum ) VALUES ( :user, :fname, :last, :pass, :age, :gender, :cell, :pnum ) ";
        $query = "CALL prInsert_User_('$username', '$fname', '$lastName', '$password', '$age', '$gender', '$cellNum', '$parentNum', '$userProfile')";
        
        //Again, we need to update our tokens with the actual data:
        $query_params = array(
            ':user' => $_POST['username'],
            ':fname' => $_POST['fName'],
            ':last' => $_POST['lastName'],
            ':pass' => $_POST['password'],
            ':age' => $_POST['age'],
            ':gender' => $_POST['gender'],
            ':cell' => $_POST['cellNum'],
            ':pnum' => $_POST['parentNum']
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
        $response["message"] = "Username Successfully Added!";
        echo json_encode($response);
        
        //for a php webservice you could do a simple redirect and die.
        //header("Location: login.php"); 
        //die("Redirecting to login.php");
        
        
    } else {
    ?>
        <!--<h1>Register</h1> 
        <!--  (empty($_POST['username']) || empty($_POST['fName']) || empty($_POST['last_name']) || empty($_POST['password']) || empty($_POST['age']) || empty($_POST['gender']) || empty($_POST['reg_date']) || empty($_POST['cell_num']) || empty($_POST['parent_num']) )-->
        <form action="register.php" method="post" enctype="multipart/form-data"> 
            Username:<br /> 
            <input type="text" name="username" value="" placeholder="Choose a username" /> 
            <br /><br /> 
            Full Name:<br />
            <input type="text" name="fName" placeholder="Full Names" />
            <br /><br /> 
            Last Name:<br />
            <input type="text" name="lastName" placeholder="Last Name" />
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
            <input type="phone" name="cellNum" placeholder="Cell Number">
            <br /><br />
            Parent Cell:<br />
            <input type="phone" name="parentNum" placeholder="Parent number" />
            <br /><br /> 
            <input type="submit" value="Register New User" name="submit"/> 
        </form>
        <?php
    }
    }
}
$instance = new user();
$instance -> validateCredintials();
?>