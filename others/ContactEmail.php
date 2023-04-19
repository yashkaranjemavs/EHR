<?php
include "conn.php";
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';
$eemail=$_POST['emailid'];
$usertype=$_POST['usertype'];
if($usertype=='Patient')
{
    $sql="select password from patient where emailid='$eemail'";
    $retval = mysqli_query($conn, $sql);
    if($row = mysqli_fetch_array($retval))
    {
        $password=$row[0];
    }
}
else if($usertype=='Provider')
{
    $sql="select password from provider where emailid='$eemail'";
    $retval = mysqli_query($conn, $sql);
    if($row = mysqli_fetch_array($retval))
    {
        $password=$row[0];
    }
}
else if($usertype=='Laboratory')
{
    $sql="select password from laboratory where emailid='$eemail'";
    $retval = mysqli_query($conn, $sql);
    if($row = mysqli_fetch_array($retval))
    {
        $password=$row[0];
    }
}
else if($usertype=='Pharmacy')
{
    $sql="select password from pharmacy where emailid='$eemail'";
    $retval = mysqli_query($conn, $sql);
    if($row = mysqli_fetch_array($retval))
    {
        $password=$row[0];
    }
}
else if($usertype=='Insurance Company')
{
    $sql="select password from insurancecompnany where emailid='$eemail'";
    $retval = mysqli_query($conn, $sql);
    if($row = mysqli_fetch_array($retval))
    {
        $password=$row[0];
    }
}
else if($usertype=='Admin')
{
    $sql="select password from admin where emailid='$eemail'";
    $retval = mysqli_query($conn, $sql);
    if($row = mysqli_fetch_array($retval))
    {
        $password=$row[0];
    }
}
$emessage="some message";
$mail = new PHPMailer(true);
$remail=$eemail;
$receive= array(
    'email' => $remail,
    'name' => 'EMR'
);
try 
{
    //Server settings
    $mail->SMTPDebug = 0;                      //Enable verbose debug output
    $mail->isSMTP();                                            //Send using SMTP
    $mail->Host       = 'ssl://mail.ssb4235.uta.cloud';                     //Set the SMTP server to send through
    $mail->SMTPAuth   = true;                                   //Enable SMTP authentication
    $mail->Username   = "marketplace@ssb4235.uta.cloud";                     //SMTP username
    $mail->Password   = "2996Shubh@ssb";                               //SMTP password
    $mail->SMTPSecure = PHPMailer::ENCRYPTION_SMTPS;            //Enable implicit TLS encryption
    $mail->Port       = 465;                                    //TCP port to connect to; use 587 if you have set `SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS`

    //Recipients
    $mail->setFrom('marketplace@ssb4235.uta.cloud', 'Mailer');
    $mail->addAddress($receive['email'], $receive['name']);
    $mail->addReplyTo('marketplace@ssb4235.uta.cloud', 'Mailer');
    //Content
    $mail->isHTML(true);                                  //Set email format to HTML
    $mail->Subject = 'Password Recovery';
    $mail->Body    = 'Password for EHR is: '.$password;
    $mail->send();
    $success = array("success" => "Password has been emailed");
	echo json_encode($success);
} 
catch (Exception $e) 
{
    $error = array("error" => "Some error");
	echo json_encode($error);
}
?>