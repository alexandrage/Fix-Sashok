<?php
header('Content-Type: text/html; charset=cp1251');
define('INCLUDE_CHECK',true);
include("connect.php");
include_once("loger.php");
if(!$usecreg) die("Использование регистрации выключено");
@$login     = $_POST['user'];
@$password  = $_POST['password'];
@$password2 = $_POST['password2'];
@$mail      = $_POST['email'];
$ip         = getenv('REMOTE_ADDR');
if(strlen($login) == 0){die("errorField");}
elseif(strlen($password) == 0){die("errorField");}
elseif(strlen($password2) == 0){die("errorField");}
elseif(strlen($mail) == 0){die("errorField");}

if(!preg_match("/^[a-zA-Z0-9_-]+$/", $login)) die ("errorLoginSymbol");
elseif(!preg_match("/^[a-zA-Z0-9_-]+$/", $password)) die ("passErrorSymbol");
elseif (!preg_match("/^[0-9a-z_\-.]+@[0-9a-z_\-^\.]+\.[a-z]{2,3}$/i", $mail)) die ("errorMail");
if ((strlen($login) < 2) or (strlen($login) > 16)) die ("errorSmallLogin");
if ((strlen($password) < 4) or (strlen($password) > 40)) die ("errorPassSmall");
if($password != $password2) die("errorPassToPass");

try {
 
$stmt = $db->prepare("SELECT $db_columnMail FROM $db_table WHERE $db_columnMail= :mail");
$stmt->bindValue(':mail', $mail);
$stmt->execute();
if($stmt->rowCount())
{ exit('emailErrorPovtor'); }

$stmt = $db->prepare("SELECT $db_columnUser FROM $db_table WHERE $db_columnUser= :login");
$stmt->bindValue(':login', $login);
$stmt->execute();
if($stmt->rowCount())
{ exit('loginErrorPovtor'); }

$stmt = $db->prepare("SELECT $db_columnIp FROM $db_table WHERE $db_columnIp= :ip");
$stmt->bindValue(':ip', $ip);
$stmt->execute();
if($stmt->rowCount())
{ exit('Erroripip'); }


if($crypt == 'hash_md5')
{ 
$checkPass = md5($password);
}
else if($crypt == 'hash_dle')
{ 
$checkPass = md5(md5($password));
}
else die("hasherror");
$stmt = $db->prepare("INSERT INTO $db_table ($db_columnUser,$db_columnPass,$db_columnMail,$db_columnDatareg,$db_columnIp) VALUES(:login,:checkPass,:mail,NOW(),'$ip')");
$stmt->bindValue(':login', $login);
$stmt->bindValue(':checkPass', $checkPass);
$stmt->bindValue(':mail', $mail);
$stmt->execute();
echo "done";
} catch(PDOException $pe) {
	die("errorsql".$logger->WriteLine($log_date.$pe));  //вывод ошибок MySQL в m.log
}
