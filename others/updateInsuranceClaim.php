<?php
    require "conn.php";
    
    $visitId = $_POST["visitId"];
    $insuranceCoverage = $_POST["insuranceCoverage"];
    
    header("Content-Type: application/json");
    
    $sql_query = "UPDATE accountcharges SET insurancecoverage=$insuranceCoverage, balance=0, STATUS='Complete' WHERE visitid=$visitId;";
    
    $results = mysqli_query($conn, $sql_query);
    
    if ($results > 0) {
        $success = ["success" => "Record updated successfully"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Failed to update"];
        echo json_encode($error);
    }
?>