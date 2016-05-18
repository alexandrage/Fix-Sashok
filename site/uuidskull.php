<?php
define('INCLUDE_CHECK',true);
include_once("connect.php");
include_once("uuid.php");
@$user = json_decode($HTTP_RAW_POST_DATA);
try { 
    $stmt = $db->prepare("SELECT user FROM usersession WHERE user= :user");
    $stmt->bindValue(':user', $user[0]);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $realUser = $row['user'];
    if($realUser==null) {
        exit;
    }
    echo '[{"id":"'.str_replace('-', '', @uuidConvert($realUser)).'","name":"'.$realUser.'"}]';
} catch(PDOException $pe) {
    die($logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
}