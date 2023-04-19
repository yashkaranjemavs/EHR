<?php
	require "conn.php";
	$vdate = $_POST['vdate'];
	$vtime = $_POST['vtime'];
	$patientnotes = $_POST['patientnotes'];
	$patientid = $_POST['patientid'];

	header("Content-Type: application/json");

	$sql_query = "INSERT INTO visits (patientid, providerid, vdate, vtime, patientnotes, providernotes, symptoms, diagnosis, status) 
	VALUES ('$patientid', 11, '$vdate', '$vtime', '$patientnotes', 'Default', 'Default', 'Default', 'Booked');";


	$results = mysqli_query($conn, $sql_query);
	

	if ($results) {
        $success = ["success" => "Appointment Booked"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Unable to book appointment"];
        echo json_encode($error);
    }
?>