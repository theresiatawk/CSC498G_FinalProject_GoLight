<?php
 
include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['email']) && isset($_POST['password'])) {

$email = validate($_POST['email']);
$password = mysqli_real_escape_string($mysqli, stripslashes(htmlspecialchars($_POST['password'])));


    if (empty($email)) {
    echo("User Name is required");
    exit();
    }
    else if(empty($password)){
        echo("Password is required");
        exit();
    }
    else{
    
        $sql = "SELECT * FROM users WHERE email = '" . $email . "'";
        $result = mysqli_query($mysqli, $sql);

        if (mysqli_num_rows($result) != 0) {
            $row = mysqli_fetch_assoc($result);

            $dbemail = $row['email'];
            $dbpassword = $row['password'];
            $response = [];
            if ($dbemail == $email && password_verify($password, $dbpassword)) {
                $response[] = $row;
                $json_respnse = json_encode($response);
                echo $json_respnse;
                exit();
            }   
            else{
                echo("Incorrect Username or password");
                exit();
            }
        }
        else{
            echo("Incorrect Username or password");
            exit();
        }
    }
}
?>