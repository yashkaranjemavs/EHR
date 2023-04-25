<?php
header("Content-Type: application/json");
	require "conn.php";
    $name = $_POST["name"];
	$facility = $_POST["facility"];
	$contact = $_POST["contact"];
	$emailid = $_POST["emailid"];
	$address1 = $_POST["address1"];
	$address2 = $_POST["address2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];
	$password= 'providerPassword123';
	if($facility=='Laboratory')
	{
		$sql_query2="select laboratoryid from laboratory where emailid='$emailid'";
		$results2 = mysqli_query($conn, $sql_query2);
		if(mysqli_num_rows($results2) > 0)
		{
			$error = array("error" => "Laboratory already exists.");
			echo json_encode($error);
		}
		else
		{
			$sql_query = "insert into laboratory(name,contact,emailid,password,addressline1,addressline2,city,state, zip) values('$name','$contact','$emailid','$password','$address1','$address2','$city','$state','$zip')";
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
	}
	else if($facility=='Pharmacy')
	{
		$sql_query2="select pharmacyid from pharmacy where emailid='$emailid'";
		$results2 = mysqli_query($conn, $sql_query2);
		if(mysqli_num_rows($results2) > 0)
		{
			$error = array("error" => "Pharmacy already exists.");
			echo json_encode($error);
		}
		else
		{
			$sql_query = "insert into pharmacy(name,contact,emailid,password,addressline1,addressline2,city,state, zip) values('$name','$contact','$emailid','$password','$address1','$address2','$city','$state','$zip')";
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
	}
	else if($facility=='Insurance')
	{
		$sql_query2="select insurerid from insurancecompany where emailid='$emailid'";
		$results2 = mysqli_query($conn, $sql_query2);
		if(mysqli_num_rows($results2) > 0)
		{
			$error = array("error" => "Insurance company already exists.");
			echo json_encode($error);
		}
		else
		{
			$sql_query = "insert into insurancecompany(name,contact,emailid,password,addressline1,addressline2,city,state, zip) values('$name','$contact','$emailid','$password','$address1','$address2','$city','$state','$zip')";
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
	}	
?>