<?php
require "conn.php";

$id = $_GET["id"];

header("Content-Type: application/json");

$sql_query = "SELECT p.patientid, v.visitid, p.firstname, p.lastname, c.charges, c.patientpayment, c.insurancecoverage, c.status FROM accountcharges c, 
 patient p, visits v WHERE c.visitid = v.visitid AND v.patientid=p.patientid
AND p.patientid IN (SELECT i.patientid FROM patientinsurance i WHERE i.insurerid='$id') AND c.status='Complete'";

$results = mysqli_query($conn, $sql_query);

$coverages = array();
if (mysqli_num_rows($results) > 0) {
    while ($data = mysqli_fetch_assoc($results)) {
        $coverage = [
            "patientId" => $data["patientid"],
            "visitId" => $data["visitid"],
            "firstName" => $data["firstname"],
            "lastName" => $data["lastname"],
            "charges" => $data["charges"],
            "patientPayment" => $data["patientpayment"],
            "insuranceCoverage" => $data["insurancecoverage"],
            "status" => $data["status"]
        ];
        
        array_push($coverages, $coverage);
    }
    
    echo json_encode($coverages);
} else {
    echo json_encode($coverages);
}
?>