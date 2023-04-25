<?php
session_start();

require "conn.php";

$laboratoryid = $_POST["laboratoryid"];


header("Content-Type: application/json");

$sql_query = "SELECT * FROM laboratory WHERE laboratoryid = $laboratoryid limit 1;";

$results = mysqli_query($conn, $sql_query);

if (mysqli_num_rows($results) > 0) {
	while ($data = mysqli_fetch_assoc($results)) {
		$user = array(
			"id" => $data["laboratoryid"],
			"name" => $data["name"],
			"email" => $data["emailid"],
			"password" => $data["PASSWORD"],
			"contact" => $data["contact"],
			"address1" => $data["addressline1"],
			"address2" => $data["addressline2"],
			"city" => $data["city"],
			"state" => $data["state"],
			"zip" => $data["zip"]
		);

		echo json_encode($user);

	}

} else {
	$error = ["error" => "Laboratory not found"];
	echo json_encode($error);
}

?>