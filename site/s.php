<?php
define('INCLUDE_CHECK',true);
include_once("loger.php");
@$md5 = $_GET['user'];
	try {
		if (!preg_match("/^[a-zA-Z0-9_-]+$/", $md5)){
			exit;
		}
		include("connect.php");
		$stmt = $db->prepare("SELECT user FROM usersession WHERE md5= :md5");
		$stmt->bindValue(':md5', $md5);
		$stmt->execute();
		$row = $stmt->fetch(PDO::FETCH_ASSOC);
		$realUser = $row['user'];
		if($realUser==null) {
			exit;
		}
		$time = time();
		$file1 = $capeurl.$realUser.'.png';
		$exists1 = file_exists($uploaddirp.'/'.$realUser.'.png');
		$file2 = $skinurl.$realUser.'.png';
		$exists2 = file_exists($uploaddirs.'/'.$realUser.'.png');
		if ($exists1) {
		    $cape = 
		'
		        "CAPE":
				{
					"url":"'.$capeurl.'?/'.$realUser.'$"
				}';
		} else {
			$cape = '';
		}
		if ($exists2) {
		    $skin = 
		'
		        "SKIN":
				{
					"url":"'.$skinurl.$realUser.'.png"
				}';
		} else {
			$skin = '';
		}
		if ($exists1 && $exists2) {
			$spl = ',';
		} else {
			$spl = '';
		}

		$base64 ='
		{
			"timestamp":"'.$time.'","profileId":"'.$md5.'","profileName":"'.$realUser.'","textures":
			{
				'.$skin.$spl.$cape.'
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
	} catch(PDOException $pe) {
			die($logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
	}