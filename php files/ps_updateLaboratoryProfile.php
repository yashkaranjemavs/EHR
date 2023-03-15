<?php
	require "conn.php";
	
	$email = $_POST["emailid"];
	$password = $_POST["PASSWORD"];
	$contact = $_POST["contact"];
    $address1 = $_POST["addressline1"];
    $address2 = $_POST["addressline2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];

	header("Content-Type: application/json");
	
	$sql_query = "UPDATE laboratory SET 
	password='$password', 
	contact='$contact', 
	address1='$address1', 
	address2='$address2', 
	city='$city', 
	state='$state', 
	zip='$zip' 
	WHERE email='$email';";
	
	if(mysqli_query($conn, $sql_query)){
		$error = array("success" => "Profile Updated Successfully");
		echo json_encode($error);
	}else{
		$error = array("error" => "Error while updating profile");
		echo json_encode($error);
	}
?>