<?php
	require "conn.php";
	
	$medicationid = $_POST["medicationid"];
	$status = $_POST["status"];

	header("Content-Type: application/json");
	
	$sql_query = "UPDATE medications SET status='$status' WHERE medicationid='$medicationid';";
	echo $sql_query;
	
	if(mysqli_query($conn, $sql_query)){
		$error = array("success" => "Status Updated Successfully");
		echo json_encode($error);
	}else{
		$error = array("error" => "Error while updating status");
		echo json_encode($error);
	}
?>


