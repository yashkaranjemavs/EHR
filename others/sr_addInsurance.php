<?php
	require "conn.php";
	
	$patientid = $_POST['patientid'];
	$insurerid = $_POST['insurerid'];
	$expiry = $_POST['expiry'];

	header("Content-Type: application/json");

	$sql_query = "INSERT INTO patientinsurance (patientid, insurerid, expiry, status) VALUES ('$patientid', '$insurerid', '$expiry', 'Active');";


	$results = mysqli_query($conn, $sql_query);
	

	if ($results) {
        $success = ["success" => "Insurance added"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Unable to add insurance"];
        echo json_encode($error);
    }
?>