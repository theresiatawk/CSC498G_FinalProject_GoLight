<?php

include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['user_id']) && isset($_POST['email']) && isset($_POST['gender']) && isset($_POST['date_of_birth']) && isset($_POST['height']) && isset($_POST['weight'])){

    $user_id = validate($_POST['user_id']);
    $email = validate($_POST['email']);
    $gender = validate($_POST['gender']);
    $date_of_birth = validate($_POST['date_of_birth']);
    $height = validate($_POST['height']);
    $weight = validate($_POST['weight']);


    date_default_timezone_set('Asia/Beirut');
    $year = intval(date('Y'));
    $month = intval(date('m'));
    $day = intval(date('d'));

    $date = strval($day.$month.$year);

    if (empty($email) || empty($gender) || empty($date_of_birth) || empty($height) || empty($weight)) {
        echo("Some field are required");
        exit();
    }
    else{
    
        $sql1 = "SELECT * FROM users WHERE email ='$email' && user_id != '$user_id'";
        $result1 = mysqli_query($mysqli, $sql1);

        if (mysqli_num_rows($result1) != 0) {
            echo("This email already exist");
            exit();
        }
        else{
            $query = $mysqli->prepare("UPDATE users SET email = ?, gender = ?, date_of_birth = ?, weight = ?, height = ?, date = ? WHERE user_id = ?;");
            $query->bind_param("sssiisi", $email, $gender, $date_of_birth, $weight, $height, $date, $user_id);
            $query->execute();
            echo "Information Updated!";
        }
    }
} 