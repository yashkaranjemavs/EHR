<?php
	require "conn.php";
	$patientid = $_GET['patientid'];

	header("Content-Type: application/json");

	$sql_query = "SELECT i.name, i.emailid, i.contact, p.expiry, p.status FROM insurancecompany AS i, patientinsurance AS p WHERE patientid = '$patientid' AND p.insurerid = i.insurerid AND status = 'active'; ";


try {
	$results = mysqli_query($conn, $sql_query);
	
	if (mysqli_num_rows($results) > 0) {
	    while ($data = mysqli_fetch_assoc($results)) {
	        $insurance = [
	            "name" => $data["name"],
	            "emailid" => $data["emailid"],
	            "contact" => $data["contact"],
	            "expiry" => $data["expiry"],
	            "status" => $data["status"]
	            ];
	            
	            echo json_encode($insurance);
	    }
    } else {
        $insurance = ["insurance" => null];
        echo json_encode($insurance);
    }
} catch(Exception $e) {
    $error = ["error" => $e->getMessage()];
    echo json_encode($error);
}
?>