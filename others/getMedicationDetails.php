<?php
include "conn.php";
header("Content-Type: application/json");

$emailid = $_GET["emailid"];
$onlyPending = $_GET["onlyPending"];
$query = "SELECT m.medicationid, m.medications, m.status, v.patientid, v.patientnotes, v.providernotes, p.firstname, p.lastname, p.emailid, p.contact FROM medications as m JOIN visits as v USING (visitid) JOIN patient as p USING (patientid) WHERE m.pharmacyid = (SELECT pharmacyid FROM pharmacy WHERE emailid = '$emailid')";

if($onlyPending == 'true'){
	$query .= " AND m.status = 'Pending'";
}
$query .= "order by m.status;";

$result = mysqli_query($conn, $query);

if(mysqli_num_rows($result)>0){   
   while($row = mysqli_fetch_assoc($result)){
	   $medication = array("medicationid" => $row['medicationid'], "medications" => $row['medications'], "status" => $row['status'], "patientid" => $row['patientid'], "patientnotes" => $row['patientnotes'], "providernotes" => $row['providernotes'], "patientFirstname" => $row['firstname'], "patientLastname" => $row['lastname'], "patientemailid" => $row['emailid'], "patientcontact" => $row['contact']);
	   echo json_encode($medication)."\n";
   }	
}else{
	$error = array("error" => "Not Found");
	echo json_encode($error);
}
?>









