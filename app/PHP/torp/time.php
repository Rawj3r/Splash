<?php
if (isset($_GET['timer'])) {
	# code...
	$timer = $_GET['timer'];

	echo gmdate("H:i:s", $timer);
}
?>
<form action="time.php" method="get">
	<input type="number" name="timer"  >
	<input type="submit" value="Submit" >
</form>