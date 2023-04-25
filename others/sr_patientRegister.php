<?php
	require "conn.php";

	$firstname = $_POST["firstname"];
	$lastname = $_POST["lastname"];
	$email = $_POST["email"];
	$dob = $_POST["dob"];
	$gender = $_POST["gender"];
	$password = $_POST["password"];
	$contact = $_POST["contact"];
    $address1 = $_POST["address1"];
    $address2 = $_POST["address2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];

	header("Content-Type: application/json");

	
	$sql_query = "INSERT INTO patient (password, emailid, firstname,lastname,dob,gender, contact, addressline1, addressline2, city, state, zip) VALUES ('$password','$email','$firstname','$lastname','$dob','$gender','$contact','$address1','$address2','$city','$state','$zip');";
	
	$results = mysqli_query($conn, $sql_query);
	
	if($results){
		$success = ["success" => "Registered Patient details Successfully"];
		echo json_encode($success);
	}else{
		$error = ["error" => "Error while registering"];
		echo json_encode($error);
	}
?>