<?php
    require "conn.php";
    
    $id = $_POST["id"];
    $name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $contact = $_POST["contact"];
    $address1 = $_POST["address1"];
    $address2 = $_POST["address2"];
    $city = $_POST["city"];
    $state = $_POST["state"];
    $zip = $_POST["zip"];
    
    header("Content-Type: application/json");
    
    $sql_query = "UPDATE `insurancecompany` SET `name`='$name', `emailid`='$email', `password`='$password', `contact`='$contact', `addressline1`='$address1', `addressline2`='$address2', `city`='$city', `state`='$state', `zip`='$zip' WHERE `insurerid`='$id';";
    
    $results = mysqli_query($conn, $sql_query);
    
    if ($results > 0) {
        $success = ["success" => "Record updated successfully"];
        echo json_encode($success);
    } else {
        $error = ["error" => "Failed to update"];
        echo json_encode($error);
    }
?>
