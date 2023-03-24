<?php
require "conn.php";

$id = $_GET["id"];

header("Content-Type: application/json");

$sql_query = "SELECT p.patientid, p.firstname, p.lastname, c.charges, c.patientpayment, v.visitid, c.patientpayment FROM accountcharges c, patient p, visits v WHERE c.visitid = v.visitid AND v.patientid=p.patientid AND c.status='Pending' AND p.patientid IN (SELECT i.patientid FROM patientinsurance i WHERE i.insurerid=$id) AND v.status='Complete';";

$results = mysqli_query($conn, $sql_query);

$coverages = [];
if (mysqli_num_rows($results) > 0) {
    while ($data = mysqli_fetch_assoc($results)) {
        $coverage = [
            "patientId" => $data["patientid"],
            "firstName" => $data["firstname"],
            "lastName" => $data["lastname"],
            "charges" => $data["charges"],
            "patientPayment" => $data["patientpayment"],
            "visitId" => $data["visitid"]
        ];
        
        array_push($coverages, $coverage);
    }
    
    echo json_encode($coverages);
} else {
    echo json_encode($coverages);
}
?>
