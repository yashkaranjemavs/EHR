<?php
	require "conn.php";
	
	$id = $_POST["id"];
	$name = $_POST["name"];
	$email = $_POST["email"];
	$password = $_POST["password"];
	$contact = $_POST["contact"];
    $address1 = $_POST["address1"];
    $address2 = $_POST["address2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];

	header("Content-Type: application/json");
	
	$sql_query = "UPDATE laboratory SET 
	PASSWORD='$password', 
	emailid='$email',
	name='$name',
	contact='$contact', 
	addressline1='$address1', 
	addressline2='$address2', 
	city='$city', 
	state='$state', 
	zip='$zip' 
	WHERE laboratoryid='$id';";
	
	if(mysqli_query($conn, $sql_query)){
		$error = array("success" => "Profile Updated Successfully");
		echo json_encode($error);
	}else{
		$error = array("error" => "Error while updating profile");
		echo json_encode($error);
	}
?>