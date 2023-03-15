<?php
	require "connection.php";
	
	$emailid = $_POST["emailid"];
	$contact = $_POST["contact"];
    $addressline1 = $_POST["addressline1"];
    $addressline2 = $_POST["addressline2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];

	header("Content-Type: application/json");
	
	$sql_query = "UPDATE pharmacy SET contact='$contact', addressline1='$addressline1', addressline2='$addressline2', city='$city', state='$state', zip='$zip' WHERE emailid='$emailid';";
	
	if(mysqli_query($conn, $sql_query)){
		$error = array("success" => "Profile Updated Successfully");
		echo json_encode($error);
	}else{
		$error = array("error" => "Error while updating profile");
		echo json_encode($error);
	}
?>

