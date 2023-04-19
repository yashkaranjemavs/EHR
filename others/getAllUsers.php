<?php
	require "conn.php";

	$sql_query = "SELECT * FROM `Users`;";
	$results = mysqli_query($conn, $sql_query);

	if (mysqli_num_rows($results) > 0) {
		while($data = mysqli_fetch_assoc($results)){
			echo $data["first_name"]." ".$data["last_name"];
			echo "<br>";
        }
	} else {
		echo "No records found";
	}
?>