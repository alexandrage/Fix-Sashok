<?php
	define('INCLUDE_CHECK',true);
    include_once("loger.php");
	@$sess       = $_GET['sessionId'];
    @$sessionid  = str_replace('%3A', ':', $sess);
    @$user       = $_GET['user'];
    @$serverid   = $_GET['serverId'];
    //$logger->WriteLine($log_date." ".$user." ".$serverid); 
	
	try {
		if (sizeof($_GET)!=3 || empty ( $_GET['sessionId'] ) ||  empty ( $_GET['user'] ) || empty ( $_GET['serverId'] ) || !preg_match("/^[a-zA-Z0-9_-]+$/", $user) || !preg_match("/^[a-zA-Z0-9:_-]+$/", $sessionid) || !preg_match("/^[a-zA-Z0-9_-]+$/", $serverid)){
          exit ("Bad login");
		}
		include("connect.php");

		$stmt = $db->prepare("Select user From usersession Where session= :sessionid And user= :user");
		$stmt->bindValue(':user', $user);
		$stmt->bindValue(':sessionid', $sessionid);
		$stmt->execute();
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$realUser = $row['user'];
		if($user == $realUser)
		{
			$stmt = $db->prepare("Update usersession SET server= :serverid Where session= :sessionid And user= :user");
			$stmt->bindValue(':user', $user);
			$stmt->bindValue(':sessionid', $sessionid);
			$stmt->bindValue(':serverid', $serverid);
			$stmt->execute();
			if($stmt->rowCount() == 1) echo "OK";
			else echo "Bad login";
		}
		else echo "Bad login";
	} catch(PDOException $pe) {
			die("bad".$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
	}