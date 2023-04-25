<?php
	require "conn.php";
	header("Content-Type: application/json");
	$patientid=$_POST["patientid"];
        $sql_query = "select * from patient where patientid=$patientid";
	    $results = mysqli_query($conn, $sql_query);
		if (mysqli_num_rows($results) > 0) 
		{
			if($data = mysqli_fetch_assoc($results))
			{
			    $firstname=$data['firstname'];
			    $lastname=$data['lastname'];
			    $dob= $data['dob'];
			    $gender= $data['gender'];
			    $contact=$data['contact'];
			    $emailid=$data['emailid'];
				$addressline1=$data['addressline1'];
			    $addressline2=$data['addressline2'];
			    $city= $data['city'];
			    $state= $data['state'];
			    $zip=$data['zip'];
			}
		} 
		$patientdetails = array("firstname" => $firstname,"lastname" => $lastname, "dob" => $dob, "gender" => $gender, "contact" => $contact, "emailid" => $emailid, "addressline1" => $addressline1, "addressline2" => $addressline2, "city" => $city, "state" => $state, "zip" => $zip);
        print(json_encode($patientdetails));
?>