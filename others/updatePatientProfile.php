<?php
    require "conn.php";
    
    $patientid = $_POST["patientid"];
    $firstname = $_POST["firstname"];
    $lastname = $_POST["lastname"];
    $emailid = $_POST["emailid"];
    $gender = $_POST["gender"];
    $dob = $_POST["dob"];
    $password = $_POST["password"];
    $contact = $_POST["contact"];
    $addressline1 = $_POST["addressline1"];
    $addressline2 = $_POST["addressline2"];
    $city = $_POST["city"];
    $state = $_POST["state"];
    $zip = $_POST["zip"];
    
    header("Content-Type: application/json");
    
    $sql_query = "UPDATE patient SET firstname='$firstname', lastname='$lastname', emailid='$emailid', gender='$gender', dob='$dob', password='$password', contact='$contact', addressline1='$addressline1', addressline2='$addressline2', city='$city', state='$state', zip='$zip' WHERE patientid='$patientid';";
    
    $results = mysqli_query($conn, $sql_query);
    
    if ($results > 0) {
        $success = ["success" => "Record updated successfully"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Patient not found"];
        echo json_encode($error);
    }
?>