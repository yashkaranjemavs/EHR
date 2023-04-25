<?php
    require "conn.php";
    
    $patientid = $_POST["patientid"];
    
    header("Content-Type: application/json");
    
    $sql_query = "UPDATE patientinsurance SET status='Disable' WHERE patientid=$patientid and status='Active';";
    
    $results = mysqli_query($conn, $sql_query);
    
    if ($results > 0) {
        $success = ["success" => "Insurance disabled"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Failed to disable"];
        echo json_encode($error);
    }
?>