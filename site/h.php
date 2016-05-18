<?php
    error_reporting(0);
	define('INCLUDE_CHECK',true);
	include_once("loger.php");
	@$user     = $_GET['username'];
    @$serverid = $_GET['serverId'];
	$bad = array('error' => "Bad login",'errorMessage' => "Bad login");
	try {
		if (!preg_match("/^[a-zA-Z0-9_-]+$/", $user) || !preg_match("/^[a-zA-Z0-9_-]+$/", $serverid)){
			exit(json_encode($bad));
		}
		include ("connect.php");
		$stmt = $db->prepare("SELECT user,md5 FROM usersession WHERE user = :user and server = :serverid");
		$stmt->bindValue(':user', $user);
		$stmt->bindValue(':serverid', $serverid);
		$stmt->execute();
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$realUser = $row['user'];
		$md5 = $row['md5'];
		if($user == $realUser)
		{
			$time = time();
			$file = $capeurl.$realUser.'.png';
			$exists = file_exists($uploaddirp.'/'.$realUser.'.png');
			if ($exists) {
			    $cape = 
			',
			        "CAPE":
					{
						"url":"'.$capeurl.'?/'.$realUser.'$"
					}';
			} else {
				$cape = '';
			}
			$base64 ='
			{
				"timestamp":"'.$time.'","profileId":"'.$md5.'","profileName":"'.$realUser.'","textures":
				{
					"SKIN":
					{
						"url":"'.$skinurl.$realUser.'.png"
					}'.$cape.'
				}
			}';
			echo '
			{
				"id":"'.$md5.'","name":"'.$realUser.'","properties":
				[
				{
					"name":"textures","value":"'.base64_encode($base64).'","signature":"Cg=="
				}
				]
			}';
            
		}
		else exit(json_encode($bad));
	} catch(PDOException $pe) {
			die("Ошибка".$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
	}