<?php
session_start();
	require "conn.php";
	$patientid = $_GET["patientid"];
	header("Content-Type: application/json");
		$sql_query = "SELECT * FROM patient WHERE patientid = $patientid limit 1;";
		$results = mysqli_query($conn, $sql_query);

		if (mysqli_num_rows($results) > 0) {
			while ($data = mysqli_fetch_assoc($results)) {
				$patientdetails = array(
				    "patientid" => $data['patientid'],
					"firstname" => $data['firstname'],
					"lastname" => $data['lastname'],
					"gender" => $data['gender'],
					"dob" => $data['dob'],
					"emailid" => $data['emailid'],
					"password" => $data['password'],
					"contact" => $data['contact'],
					"addressline1" => $data['addressline1'],
					"addressline2" => $data['addressline2'],
					"city" => $data['city'],
					"state" => $data['state'],
					"zip" => $data['zip']
				);

			print(json_encode($patientdetails));

			}

		} else {
			$error = ["error" => "Patient not found"];
			echo json_encode($error);
		}

?>