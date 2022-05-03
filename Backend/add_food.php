<?php
 
include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['user_id']) && isset($_POST['date']) && isset($_POST['breakfast']) && isset($_POST['lunch']) && isset($_POST['dinner']) && isset($_POST['snack'])) {
    $user = validate($_POST['user_id']);
    $date = validate($_POST['date']);
    $breakfast = validate($_POST['breakfast']);
    $lunch = validate($_POST['lunch']);
    $dinner = validate($_POST['dinner']);
    $snack = validate($_POST['snack']);

    if (empty($user)) {
        echo "Empty user id";
        exit();
    }
    else if(empty($date)){
        echo "Empty date";
        exit();
    }
    else{
        $sql = "SELECT * FROM food_consumptions WHERE user_id = '" . $user . "' AND date = " . $date . "";
        $result = mysqli_query($mysqli, $sql);

        if (mysqli_num_rows($result) != 0) {
            $query = $mysqli->prepare("UPDATE food_consumptions SET breakfast = ?, lunch = ?, dinner = ?, snack = ? WHERE user_id = ? AND date = ?;");
            $query->bind_param("ssssis", $breakfast, $lunch, $dinner, $snack, $user, $date);
            $query->execute();
            echo "Updated!";
        }
        else{
            $query = $mysqli->prepare("INSERT INTO food_consumptions(user_id, breakfast, lunch, dinner, snack, date) VALUES (?, ?, ?, ?, ?, ?);");
            $query->bind_param("isssss", $user, $breakfast, $lunch, $dinner, $snack, $date);
            $query->execute();
            echo "Data Added!";
        }
    }
}
?>