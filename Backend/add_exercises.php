<?php
 
include "db_connection.php";

function validate($data){
    $data = trim($data);
    $data = stripslashes($data);
    return $data;
}
if (isset($_POST['user_id']) && isset($_POST['date']) && isset($_POST['running']) && isset($_POST['dancing']) && isset($_POST['boxing']) && isset($_POST['baseball']) && isset($_POST['basketball'])&& isset($_POST['football'])&& isset($_POST['swimming'])&& isset($_POST['skiing'])&& isset($_POST['hiking'])&& isset($_POST['gymnastics'])&& isset($_POST['tennis'])&& isset($_POST['golf'])) {
    $user = validate($_POST['user_id']);
    $date = validate($_POST['date']);
    $running = validate($_POST['running']);
    $dancing = validate($_POST['dancing']);
    $boxing = validate($_POST['boxing']);
    $baseball = validate($_POST['baseball']);
    $basketball = validate($_POST['basketball']);
    $football = validate($_POST['football']);
    $swimming = validate($_POST['swimming']);
    $skiing = validate($_POST['skiing']);
    $hiking = validate($_POST['hiking']);
    $gymnastics = validate($_POST['gymnastics']);
    $tennis = validate($_POST['tennis']);
    $golf = validate($_POST['golf']);

    if (empty($user)) {
        echo "Empty user id";
        exit();
    }
    else if(empty($date)){
        echo "Empty date";
        exit();
    }
    else{
        $sql = "SELECT * FROM exercises WHERE user_id = '" . $user . "' AND date = " . $date . "";
        $result = mysqli_query($mysqli, $sql);

        if (mysqli_num_rows($result) != 0) {
            $query = $mysqli->prepare("UPDATE exercises SET running = ?, dancing = ?, boxing = ?, baseball = ?, basketball = ?, football = ?, swimming = ?, skiing = ?, hiking = ?, gymnastics = ?, tennis = ?, golf = ? WHERE user_id = ? AND date = ?;");
            $query->bind_param("ssssssssssssis", $running, $dancing, $boxing, $baseball, $basketball, $football, $swimming, $skiing, $hiking, $gymnastics, $tennis, $golf, $user, $date);
            $query->execute();
            echo "Updated!";
        }
        else{
            $query = $mysqli->prepare("INSERT INTO exercises(user_id, running, dancing, boxing, baseball, basketball, football, swimming, skiing, hiking, gymnastics, tennis, golf, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            $query->bind_param("isssssssssssss", $user, $running, $dancing, $boxing, $baseball, $basketball, $football, $swimming, $skiing, $hiking, $gymnastics, $tennis, $golf, $date);
            $query->execute();
            echo "Data Added!";
        }
    }
}
?>