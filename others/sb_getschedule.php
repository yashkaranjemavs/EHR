<?php
require "conn.php";
header("Content-Type: application/json");
$scheduledata = array(); 
$sql = "select p.firstname, p.lastname, v.vdate, v.vtime, v.patientnotes, v.visitid from patient p, visits v where p.patientid=v.patientid and v.status='Booked' order by v.vdate";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($firstname, $lastname,$vdate,$vtime,$patientnotes,$visitid);
while($stmt->fetch()){
 $temp = [
 'firstname'=>$firstname,
 'lastname'=>$lastname,
 'vdate'=>$vdate,
 'vtime'=>$vtime,
 'patientnotes'=>$patientnotes,
 'visitid'=>$visitid
 ];
 array_push($scheduledata, $temp);
}
echo json_encode($scheduledata);
?>