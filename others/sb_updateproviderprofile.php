<?php
header("Content-Type: application/json");
	require "conn.php";
    $providerid = $_POST["providerid"];
	$firstname = $_POST["firstname"];
	$lastname = $_POST["lastname"];
	$emailid = $_POST["emailid"];
	$password = $_POST["password"];
	$contact = $_POST["contact"];
	$address1 = $_POST["address1"];
	$address2 = $_POST["address2"];
	$city = $_POST["city"];
	$state = $_POST["state"];
	$zip = $_POST["zip"];
        $sql_query = "update provider set firstname='$firstname', lastname='$lastname',contact='$contact',emailid='$emailid',PASSWORD='$password',
		addressline1='$address1', addressline2='$address2',city='$city',state='$state',zip='$zip' WHERE providerid=$providerid limit 1;";
	    if(mysqli_query($conn, $sql_query))
		{
			$sql_query1 = "SELECT * FROM provider WHERE providerid=$providerid limit 1;";
			$results1 = mysqli_query($conn, $sql_query1);
	
			if (mysqli_num_rows($results1) > 0) 
			{
				if($data = mysqli_fetch_assoc($results1))
				{
					$providerdetails = array("firstname" => $data['firstname'], "lastname" => $data['lastname'], "emailid" => $data['emailid'],"password" => $data['PASSWORD'],"contact" => $data['contact'],"address1" => $data['addressline1'],"address2" => $data['addressline2'],"city" => $data['city'],"state" => $data['state'],"zip" => $data['zip']);
					print(json_encode($providerdetails));
				}
			} 
			else 
			{
				$error = array("error" => "something went wrong");
				echo json_encode($error);
			} 
		} 
		else
		{
			echo "ERROR: Could not able to execute $sql_query. " . mysqli_error($conn);
		}

?>