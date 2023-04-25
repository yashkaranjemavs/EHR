<?php
session_start();
	require "conn.php";
    $providerid = $_POST["providerid"];
	header("Content-Type: application/json");
        $sql_query = "SELECT * FROM provider WHERE providerid=$providerid limit 1;";
	    $results = mysqli_query($conn, $sql_query);
	
		if (mysqli_num_rows($results) > 0) 
		{
			if($data = mysqli_fetch_assoc($results))
			{
				$providerdetails = array("firstname" => $data['firstname'], "lastname" => $data['lastname'], "emailid" => $data['emailid'],"password" => $data['PASSWORD'],"contact" => $data['contact'],"address1" => $data['addressline1'],"address2" => $data['addressline2'],"city" => $data['city'],"state" => $data['state'],"zip" => $data['zip']);
				//$_SESSION["providerid"] = $data['providerid'];
				print(json_encode($providerdetails));
			}
		} 
		else 
		{
			$error = array("error" => "something went wrong");
			echo json_encode($error);
		} 

?>