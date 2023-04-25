<?php
header("Content-Type: application/json");
	require "conn.php";
    $firstname = $_POST["firstname"];
	$lastname = $_POST["lastname"];
	$dob = $_POST["dob"];
	$dob=strtotime($dob);
	$dob= date ('Y-m-d',$dob);
	$contact = $_POST["contact"];
	$emailid = $_POST["emailid"];
	$address1 = $_POST["address1"];
	$address2 = $_POST["address2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];
	$gender = $_POST["gender"];
	$password= 'providerPassword123';
	$speciality='Default';
	$license='Default';
	$sql_query2="select providerid from provider where emailid='$emailid'";
	$results2 = mysqli_query($conn, $sql_query2);
	if(mysqli_num_rows($results2) > 0)
	{
		$error = array("error" => "Provider already exists.");
		echo json_encode($error);
	}
	else
	{
		$sql_query = "insert into provider(firstname,lastname,dob,gender,contact,emailid,password,addressline1,addressline2,city,state, zip,speciality,license) values('$firstname','$lastname','$dob','$gender','$contact','$emailid','$password','$address1','$address2','$city','$state','$zip','$speciality','$license')";
		if(mysqli_query($conn, $sql_query))
		{
			
			$message = array("success" => "Data Added");
			print(json_encode($message));
		}
		else 
		{
			$error = array("error" => "something went wrong");
			echo json_encode($error);
		} 
	}	
?>