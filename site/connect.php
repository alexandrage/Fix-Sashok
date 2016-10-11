<?php
    //Error_Reporting(E_ALL | E_STRICT);
    //Ini_Set('display_errors', true);

	if(!defined('INCLUDE_CHECK')) die("You don't have permissions to run this");
	include_once("loger.php");
	
	if (extension_loaded('openssl')) {
		include_once("security_openssl.php");
	} else if(extension_loaded('mcrypt')){
		include_once("security_mcrypt.php");
	} else {
		exit("Отсутствуют расширения mcrypt и openssl! Установите одно из двух.");
	}
	
	/* Метод хеширования пароля для интеграции с различними плагинами/сайтами/cms/форумами
	'hash_md5' 			- md5 хеширование
	'hash_authme'   	- интеграция с плагином AuthMe
	'hash_cauth' 		- интеграция с плагином Cauth
	'hash_xauth' 		- интеграция с плагином xAuth
	'hash_joomla' 		- интеграция с Joomla (v1.6- v1.7)
	'hash_ipb' 			- интеграция с IPB
	'hash_xenforo' 		- интеграция с XenForo
	'hash_wordpress' 	- интеграция с WordPress
	'hash_vbulletin' 	- интеграция с vBulletin
	'hash_dle' 			- интеграция с DLE
	'hash_drupal'     	- интеграция с Drupal (v.7)
	'hash_smf' 			- интеграция с SMF
	*/
	$crypt 				= 'hash_md5';
	
	$db_host			= 'localhost'; // Ip-адрес MySQL
	$db_port			= '3306'; // Порт базы данных
	$db_user			= 'root'; // Пользователь базы данных
	$db_pass			= 'root'; // Пароль базы данных
	$db_database		= 'fix'; //База данных
	
	$db_table       	= 'accounts'; //Таблица с пользователями
	$db_columnId  		= 'id'; //Колонка с ID пользователей
	$db_columnUser  	= 'login'; //Колонка с именами пользователей
	$db_columnPass  	= 'password'; //Колонка с паролями пользователей
	$db_tableOther 		= 'xf_user_authenticate'; //Дополнительная таблица для XenForo, не трогайте
	$db_columnSalt  	= 'members_pass_salt'; //Настраивается для IPB и vBulletin: , IPB - members_pass_salt, vBulletin - salt
    $db_columnIp  		= 'ip'; //Колонка с IP пользователей
	
	$db_columnDatareg   = 'create_time'; // Колонка даты регистрации
	$db_columnMail      = 'email'; // Колонка mail

	$banlist            = 'banlist'; //Таблица плагина Ultrabans
	
	$useban             =  false; //Бан на сервере = бан в лаунчере, Ultrabans плагин
	$useantibrut        =  true; //Защита от частых подборов пароля (Пауза 1 минута при неправильном пароле)
	
	$masterversion  	= 'final_RC4'; //Мастер-версия лаунчера
	$protectionKey		= '1234567890'; 
	$key1               = "1234567891234567";  //16 Character Key Ключ пост запросов
	$key2               = "1234567891234567"; //16 Character  Key  Ключ пост запросов
    $checklauncher      = false; //Проверка хеша лаунчера
	$md5launcherexe     = @md5_file("launcher/fix.exe");  // Сверяем MD5
	$md5launcherjar     = @md5_file("launcher/fix.jar");  // Сверяем MD5
	$temp               = true;  //Использовать файлы кеширования для ускорение авторизации и снижение нагрузки на вебсервер.
	                             //Удаляем файл хеша после обновления клиента на сервере в папке /temp/ИмяКлиента!

	$assetsfolder       = false; //Скачивать assets из папки, или из архива (true=из папки false=из архива)

//========================= Настройки ЛК =======================//	

	$uploaddirs         = 'MinecraftSkins';  //Папка скинов
	$uploaddirp         = 'MinecraftCloaks'; //Папка плащей
    $skinurl            = 'http://alexandrage.ru/site/'.$uploaddirs.'/'; //Ссылка на скины для клиентов 1.7.+
    $capeurl            = 'http://alexandrage.ru/site/'.$uploaddirp.'/'; //Ссылка на плащи для клиентов 1.7.+
	
	$usePersonal 		=  true; //Использовать личный кабинет
	$canUploadSkin		=  true; //Можно ли заливать скины
	$canUploadCloak		=  true; //Можно ли заливать плащи
	$canBuyVip			=  true; //Можно ли покупать VIP
	$canBuyPremium		=  true; //Можно ли покупать Premium
	$canBuyUnban		=  true; //Можно ли покупать разбан
	$canActivateVaucher =  true; //Можно ли активировать ваучер
	$canExchangeMoney   =  true; //Можно ли обменивать Realmoney -> IConomy
	$canUseJobs			=  true; //Можно ли использовать работы
	$usecreg			=  true; //Можно ли использовать регистрацию в лаунчере
	
	$cloakPrice			=  0;   //Цена плаща (В рублях)
	$vipPrice			=  100;  //Цена випа (В руб/мес)
	$premiumPrice		=  250;  //Цена премиума (В руб/мес)
	$unbanPrice			=  150;  //Цена разбана (В рублях)
	
	$initialIconMoney	=  30;  //Сколько денег дается при регистрации в IConomy
	$exchangeRate		=  200; //Курс обмена Realmoney -> IConomy
	
	//ВСЕ ЧТО НИЖЕ - НЕ ТРОГАТЬ!
	try {
		$db = new PDO("mysql:host=$db_host;port=$db_port;dbname=$db_database", $db_user, $db_pass);
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$db->exec("set names utf8");
    } catch(PDOException $pe) {
		die(Security::encrypt("errorsql", $key1).$logger->WriteLine($log_date."Ошибка подключения (Хост, Логин, Пароль)"));
	}
	try {
		$stmt = $db->prepare("
        CREATE TABLE IF NOT EXISTS `usersession` (
	    `user` varchar(255) DEFAULT 'user',
	    `session` varchar(255) DEFAULT NULL,
	    `server` varchar(255) DEFAULT NULL,
	    `token` varchar(255) DEFAULT NULL,
 	    `realmoney` int(255) DEFAULT '0',
 	    `md5` varchar(255) DEFAULT '0',
	    PRIMARY KEY (`user`)
	    ) ENGINE=MyISAM  DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
		$stmt = $db->prepare("
        CREATE TABLE IF NOT EXISTS `sashok724_launcher_keys` (
	    `key` varchar(255) DEFAULT NULL,
	    `amount` int(255) DEFAULT NULL
	    ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `sip` (
		  `time` varchar(255) NOT NULL,
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `sip` varchar(16) DEFAULT NULL,
		  PRIMARY KEY (`id`) USING BTREE
		) ENGINE=MyISAM  DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC AUTO_INCREMENT=0;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `jobs` (
		  `username` varchar(20) DEFAULT NULL,
		  `experience` int(11) DEFAULT NULL,
		  `level` int(11) DEFAULT NULL,
		  `job` varchar(20) DEFAULT NULL
		) ENGINE=MyISAM DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `iConomy` (
		  `id` int(255) NOT NULL AUTO_INCREMENT,
		  `username` varchar(32) NOT NULL,
		  `balance` double(64,2) NOT NULL,
		  `status` int(2) NOT NULL DEFAULT '0',
		  UNIQUE KEY `username` (`username`),
		  KEY `id` (`id`)
		) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `banlist` (
		  `name` varchar(32) NOT NULL,
		  `reason` text NOT NULL,
		  `admin` varchar(32) NOT NULL,
		  `time` bigint(20) NOT NULL,
		  `temptime` bigint(20) NOT NULL DEFAULT '0',
		  `type` int(11) NOT NULL DEFAULT '0',
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `ip` varchar(16) DEFAULT NULL,
		  PRIMARY KEY (`id`) USING BTREE
		) ENGINE=MyISAM  DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC AUTO_INCREMENT=0;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `permissions` (
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `name` varchar(50) NOT NULL,
		  `type` tinyint(1) NOT NULL,
		  `permission` text NOT NULL,
		  `world` varchar(50) NOT NULL,
		  `value` text NOT NULL,
		  PRIMARY KEY (`id`),
		  KEY `user` (`name`,`type`),
		  KEY `world` (`world`,`name`,`type`)
		) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `permissions_entity` (
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `name` varchar(50) NOT NULL,
		  `type` tinyint(1) NOT NULL,
		  `default` tinyint(1) NOT NULL DEFAULT '0',
		  PRIMARY KEY (`id`),
		  UNIQUE KEY `name` (`name`,`type`),
		  KEY `default` (`default`)
		) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `permissions_inheritance` (
		  `id` int(11) NOT NULL AUTO_INCREMENT,
		  `child` varchar(50) NOT NULL,
		  `parent` varchar(50) NOT NULL,
		  `type` tinyint(1) NOT NULL,
		  `world` varchar(50) DEFAULT NULL,
		  PRIMARY KEY (`id`),
		  UNIQUE KEY `child` (`child`,`parent`,`type`,`world`),
		  KEY `child_2` (`child`,`type`),
		  KEY `parent` (`parent`,`type`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
		$stmt = $db->prepare("
		CREATE TABLE IF NOT EXISTS `accounts` (
			`id` int(11) NOT NULL AUTO_INCREMENT,
			`login` varchar(32) DEFAULT NULL,
			`password` varchar(50) DEFAULT NULL,
			`ip` varchar(32) DEFAULT NULL,
			`create_time` datetime DEFAULT NULL,
			`email` varchar(50) DEFAULT NULL,
			PRIMARY KEY (`id`),
			UNIQUE KEY `login` (`login`)
	     ) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
		");
		$stmt->execute();
	} catch(PDOException $pe) {
		die(Security::encrypt("errorsql", $key1).$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
	}