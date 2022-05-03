<?php
 
include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['user_id']) && isset($_POST['date']) && isset($_POST['nb_of_glasses'])) {
    $user = validate($_POST['user_id']);
    $date = validate($_POST['date']);
    $nb_of_glasses = validate($_POST['nb_of_glasses']);

    if (empty($user)) {
        exit();
    }
    else if(empty($date)){
        echo 2;
        exit();
    }
    else if(empty($nb_of_glasses)){
        echo 2;
        exit();
    }
    else{
        $sql = "SELECT * FROM water_consumptions WHERE user_id = '" . $user . "' AND date = " . $date . "";
        $result = mysqli_query($mysqli, $sql);

        if (mysqli_num_rows($result) != 0) {
            $query = $mysqli->prepare("UPDATE water_consumptions SET nb_of_glasses = ? WHERE user_id = ? AND date = ?;");
            $query->bind_param("iis", $nb_of_glasses, $user, $date);
            $query->execute();
            echo "Updated!";
        }
        else{
            echo "No data!";
        }
    }
}
?>