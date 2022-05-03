<?php
 
include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['user_id']) && isset($_POST['date'])) {
    $user = validate($_POST['user_id']);
    $date = validate($_POST['date']);

    if (empty($user)) {
        exit();
    }
    else if(empty($date)){
        exit();
    }
    else{
        $sql = "SELECT nb_of_glasses FROM water_consumptions WHERE user_id = '" . $user . "' AND date = " . $date . "";
        $result = mysqli_query($mysqli, $sql);

        if (mysqli_num_rows($result) != 0) {
            $raw = mysqli_fetch_assoc($result);
            $response = [];
            $number_of_glasses = $raw['nb_of_glasses'];
            $response[] = $raw;
            $json_respnse = json_encode($response);
            echo $json_respnse;
        }
        else{
            echo "0";
        }
    }
}  

?>