<?php
require "conn.php";
header("Content-Type: application/json");
$patientdata = array(); 
$sql = "select p.firstname, p.lastname, p.dob, p.gender, p.patientid from patient p order by p.firstname";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($firstname, $lastname,$dob,$gender,$patientid);
while($stmt->fetch()){
 $temp = [
 'firstname'=>$firstname,
 'lastname'=>$lastname,
 'dob'=>$dob,
 'gender'=>$gender,
 'patientid'=>$patientid
 ];
 array_push($patientdata, $temp);
}
$stmt->close();
echo json_encode($patientdata);
?>