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
        exit();
    }
    else{
        $query = $mysqli->prepare("UPDATE water_consumptions SET nb_of_glasses = ? WHERE user_id = ? AND date = ?;");
        $query->bind_param("iis", $nb_of_glasses, $user_id, $date);
        $query->execute();
        echo "Information Updated!";
    }
}  

?>