<?php
	require "conn.php";
	
	$email = $_POST["email"];
    $password = $_POST["password"];
    $role = $_POST["role"];

	header("Content-Type: application/json");

    if($role=='Admin')
    {
        $sql_query = "SELECT * FROM `admin` WHERE `emailid`='$email' AND `password`='$password';";
	    $results = mysqli_query($conn, $sql_query);
	
		if (mysqli_num_rows($results) > 0) {
			while($data = mysqli_fetch_assoc($results)){
				$user = array("emailid" => $data['emailid'], "id" => $data['emailid'], "role" => 'Admin');
				echo json_encode($user);
			}
		} else {
			$error = array("error" => "Invalid email/password");
			echo json_encode($error);
		} 
    }
	else if($role=='Patient')
    {
        $sql_query = "SELECT * FROM `patient` WHERE `emailid`='$email' AND `password`='$password';";
	    $results = mysqli_query($conn, $sql_query);

		if (mysqli_num_rows($results) > 0) {
			while($data = mysqli_fetch_assoc($results)){
				$user = array("emailid" => $data['emailid'], "id" => $data['patientid'], "role" => 'Patient');
				echo json_encode($user);
			}
		} else {
			$error = array("error" => "Invalid email/password");
			echo json_encode($error);
		} 
    }
    else if($role=='Provider')
    {
        $sql_query = "SELECT * FROM `provider` WHERE `emailid`='$email' AND `password`='$password';";
	    $results = mysqli_query($conn, $sql_query);

		if (mysqli_num_rows($results) > 0) {
			while($data = mysqli_fetch_assoc($results)){
				$user = array("emailid" => $data['emailid'], "id" => $data['providerid'], "role" => 'Provider');
				echo json_encode($user);
			}
		} else {
			$error = array("error" => "Invalid email/password");
			echo json_encode($error);
		} 
    }
    else if($role=='Laboratory')
    {
        $sql_query = "SELECT * FROM `laboratory` WHERE `emailid`='$email' AND `password`='$password';";
	    $results = mysqli_query($conn, $sql_query);
	
		if (mysqli_num_rows($results) > 0) {
			while($data = mysqli_fetch_assoc($results)){
				$user = array("emailid" => $data['emailid'], "id" => $data['laboratoryid'], "role" => 'Laboratory');
				echo json_encode($user);
			}
		} else {
			$error = array("error" => "Invalid email/password");
			echo json_encode($error);
		} 
    }
    else if($role=='Pharmacy')
    {
        $sql_query = "SELECT * FROM `pharmacy` WHERE `emailid`='$email' AND `password`='$password';";
	    $results = mysqli_query($conn, $sql_query);

		if (mysqli_num_rows($results) > 0) {
			while($data = mysqli_fetch_assoc($results)){
				$user = array("emailid" => $data['emailid'], "id" => $data['pharmacyid'], "role" => 'Pharmacy');
				echo json_encode($user);
			}
		} else {
			$error = array("error" => "Invalid email/password");
			echo json_encode($error);
		} 
    }
    else if($role=='Insurance Company')
    {
        $sql_query = "SELECT * FROM `insurancecompany` WHERE `emailid`='$email' AND `password`='$password';";
	    $results = mysqli_query($conn, $sql_query);

		if (mysqli_num_rows($results) > 0) {
			while($data = mysqli_fetch_assoc($results)){
				$user = array("emailid" => $data['emailid'], "id" => $data['insurerid'], "role" => 'Insurance Company');
				echo json_encode($user);
			}
		} else {
			$error = array("error" => "Invalid email/password");
			echo json_encode($error);
		} 
    }
    else {
		$error = array("error" => "Invalid email/password");
		echo json_encode($error);
	} 
?>