<?php
require "conn.php";

$id = $_GET["id"];

header("Content-Type: application/json");

$sql_query = "SELECT p.patientid, p.firstname,p.lastname, pii.expiry FROM patient p, patientinsurance pii WHERE p.patientid=pii.patientid AND pii.status='Active' AND pii.insurerid='$id';";

$results = mysqli_query($conn, $sql_query);

$coverages = array();
if (mysqli_num_rows($results) > 0) {
    while ($data = mysqli_fetch_assoc($results)) {
        $coverage = [
            "patientId" => $data["patientid"],
            "firstName" => $data["firstname"],
            "lastName" => $data["lastname"],
            "expiry" => $data["expiry"]
        ];
        
        array_push($coverages, $coverage);
    }
    
    echo json_encode($coverages);
} else {
    echo json_encode($coverages);
}
?>