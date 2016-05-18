<?php
	define('INCLUDE_CHECK',true);
	include("connect.php");
	include_once("loger.php");
	@$user     = $_GET['user'];
    @$serverid = $_GET['serverId'];
    //$logger->WriteLine($log_date." ".$user." ".$serverid); 
	try {
		if (!preg_match("/^[a-zA-Z0-9_-]+$/", $user) || !preg_match("/^[a-zA-Z0-9_-]+$/", $serverid)){
			echo "NO";	
			exit;
		}

		$stmt = $db->prepare("Select user From usersession Where user= :user And server= :serverid");
		$stmt->bindValue(':user', $user);
		$stmt->bindValue(':serverid', $serverid);
		$stmt->execute();   
       	$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$realUser = $row['user'];
		if($user == $realUser)
		{
			echo "YES";
		}
		else echo "NO";
	} catch(PDOException $pe) {
		die("bad".$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
	}
