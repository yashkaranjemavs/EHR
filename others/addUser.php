<?php
	require "conn.php";

    $firstName = $_POST["first_name"];
    $lastName = $_POST["last_name"];
    
	$sql_query = "INSERT INTO `Users` (first_name, last_name) VALUES ('$firstName', '$lastName');";
	$results = mysqli_query($conn, $sql_query);

	if ($conn->query($sql_query) === TRUE) {
		echo "Successfully inserted";
	} else {
		echo "Error: '$conn->error";
	}
	
	$conn->close();
?>