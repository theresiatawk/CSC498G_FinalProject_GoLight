<?php

include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['gender']) && isset($_POST['date_of_birth']) && isset($_POST['height']) && isset($_POST['weight'])){

    $first_name = validate($_POST['first_name']);
    $last_name = validate($_POST['last_name']);
    $email = validate($_POST['email']);
    $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
    $gender = validate($_POST['gender']);
    $date_of_birth = validate($_POST['date_of_birth']);
    $height = validate($_POST['height']);
    $weight = validate($_POST['weight']);


    date_default_timezone_set('Asia/Beirut');
    $year = intval(date('Y'));
    $month = intval(date('m'));
    $day = intval(date('d'));

    $date = strval($day.$month.$year);

    if (empty($first_name) || empty($last_name) || empty($email) || empty($password) || empty($gender) || empty($date_of_birth) || empty($height) || empty($weight)) {
        echo("Some field are required");
        exit();
    }
    else{
    
        $sql1 = "SELECT * FROM users WHERE email ='$email'";
        $result1 = mysqli_query($mysqli, $sql1);

        if (mysqli_num_rows($result1) != 0) {
            echo("This account already exist");
            exit();
        }
        else{
            $query = $mysqli->prepare("INSERT INTO users(first_name, last_name, email, password, gender, date_of_birth, weight, height, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            $query->bind_param("ssssssiis", $first_name, $last_name, $email, $password, $gender, $date_of_birth, $weight, $height, $date);
            $query->execute();
            echo "Account Created!";

        }
    }









} 

?>