<?php
require "conn.php";
header("Content-Type: application/json");
$scheduledata = array(); 
$sql = "select p.firstname, p.lastname, v.vdate, v.vtime, v.status, v.visitid from patient p, visits v where p.patientid=v.patientid order by v.vdate desc";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($firstname, $lastname,$vdate,$vtime,$status,$visitid);
while($stmt->fetch()){
 $temp = [
 'firstname'=>$firstname,
 'lastname'=>$lastname,
 'date'=>$vdate,
 'time'=>$vtime,
 'status'=>$status,
 'visitid'=>$visitid
 ];
 array_push($scheduledata, $temp);
}
echo json_encode($scheduledata);
?>