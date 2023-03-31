<?php
	require "conn.php";
date_default_timezone_set("America/Chicago");
	$visitid = $_POST["visitid"];
    $laboratoryid = $_POST["laboratoryid"];
    $testid = $_POST["testid"];
	$testreport = $_POST["testreport"];
	$tdate = date('Y-m-d');

	header("Content-Type: application/json");
	
	$sql_query = "UPDATE labtests SET 
	testreport ='$testreport',
	tdate='$tdate',
	status='Complete'
	WHERE testid='$testid';";
	
	if(mysqli_query($conn, $sql_query)){
		$error = array("success" => "Test Updated Successfully");
		echo json_encode($error);
	}else{
		$error = array("error" => "Error while updating tests");
		echo json_encode($error);
	}
?>