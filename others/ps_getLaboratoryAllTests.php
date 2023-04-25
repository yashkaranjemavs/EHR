<?php
require "conn.php";

$laboratoryid = $_POST["laboratoryid"];

header("Content-Type: application/json");

$sql_query = "select LT.testname, LT.testid, P.firstname,P.lastname,LT.testreport,LT.tdate,LT.status FROM labtests LT JOIN visits V on LT.visitid = V.visitid JOIN patient P ON P.patientid=V.patientid WHERE laboratoryid = $laboratoryid ORDER BY LT.tdate DESC;";

$results = mysqli_query($conn, $sql_query);

$testsList = [];
if (mysqli_num_rows($results) > 0) {
while ($data = mysqli_fetch_assoc($results)) {$test = ["testname" => $data["testname"],	"testid" => $data["testid"],
			"firstname" => $data["firstname"],
			"lastname" => $data["lastname"],
			"testreport" => $data["testreport"],
			"tdate" => $data ["tdate"],
			"status" => $data ["status"]];
array_push($testsList, $test);
}
echo json_encode($testsList);
} else {
echo json_encode($testsList);
}
?>