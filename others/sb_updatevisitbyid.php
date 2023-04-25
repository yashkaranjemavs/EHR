<?php
header("Content-Type: application/json");
	require "conn.php";
    $diagnosis = $_POST["diagnosis"];
	$symptoms = $_POST["symptoms"];
	$providernotes = $_POST["providernotes"];
	$visitid = $_POST["visitid"];
	$providerid=$_POST["providerid"];
	$status="Complete";
        $sql_query = "update visits set diagnosis='$diagnosis', symptoms='$symptoms',providernotes='$providernotes',providerid='$providerid',status='$status' WHERE visitid=$visitid limit 1;";
	    if(mysqli_query($conn, $sql_query))
		{
			$sql_query1 = "SELECT * FROM visits WHERE visitid=$visitid limit 1;";
			$results1 = mysqli_query($conn, $sql_query1);
	
			if (mysqli_num_rows($results1) > 0) 
			{
				if($data = mysqli_fetch_assoc($results1))
				{
					$providerdetails = array("diagnosis" => $data['diagnosis'], "symptoms" => $data['symptoms'], "providernotes" => $data['providernotes'],"visitid" => $data['visitid']);
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