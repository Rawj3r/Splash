<?php

$mysqli = new mysqli("whm.empirestate.co.za", "TDA_Torpedokid", "iloveempirestate", "TDA_Torpedokid");

if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}

?>