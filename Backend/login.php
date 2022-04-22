<?php

session_start(); 
include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['email']) && isset($_POST['password'])) {

$email = validate($_POST['email']);
$password = validate($_POST['password']);

    if (empty($email)) {
    echo("User Name is required");
    exit();
    }
    else if(empty($password)){
        echo("Password is required");
        exit();
    }
    else{
        $sql = "SELECT * FROM users WHERE email ='$email' AND password ='$password'";
        $result = mysqli_query($mysqli, $sql);

        if (mysqli_num_rows($result) != 0) {
            $row = mysqli_fetch_assoc($result);

            if ($row['email'] === $email && $row['password'] === $password) {
                echo "Logged in!";
                $_SESSION['first_name'] = $row['first_name'];
                $_SESSION['last_name'] = $row['last_name'];
                $_SESSION['user_id'] = $row['user_id'];
                echo $_SESSION['first_name'];
                exit();
            }   
            else{
                echo("Incorect User name or password");
                exit();
            }
        }
        else{
            echo("Incorect User name or password");
            exit();
        }
    }
}
?>