<?php
require "conn.php";
header("Content-Type: application/json");
$insdata = array();
$sql = "SELECT i.name, COUNT(pi.insurerid) total FROM insurancecompany i, patientinsurance pi
                            WHERE i.insurerid=pi.insurerid GROUP BY i.name";
$stmt = $conn->prepare($sql);
$stmt->execute();
$stmt->bind_result($name, $total);
while($stmt->fetch())
{
 $temp = ['name'=>$name,'total'=>$total];
 array_push($insdata, $temp);
}
echo json_encode($insdata);
?>