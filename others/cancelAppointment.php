<?php
	require "conn.php";
	
	$visitid = $_POST['visitid'];

	header("Content-Type: application/json");

	$sql_query = "UPDATE visits SET status='Cancelled' WHERE visitid = '$visitid'";


	$results = mysqli_query($conn, $sql_query);

	if ($results > 0) {
        $success = ["success" => "Appointment Cancelled"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Unable to cancel appointment"];
        echo json_encode($error);
    }
?>