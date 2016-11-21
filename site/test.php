<?php
define('INCLUDE_CHECK',true);
include("connect.php");
@$action  = $_POST['action'];
@$client  = $_POST['client'];
@$login   = $_POST['login'];
@$passw   = $_POST['passw'];
if($action != null || $client != null || $login != null || $passw != null)
{
$aes = Security::encrypt('auth:'.$client.':'.$login.':'.$passw.':md5:null', $key2);
}
?>
<meta charset="utf-8">
<form action= "test.php" method= "POST">
<p>action<input type= "text" name= "action" value= "auth"> </p>
<p>client<input type= "text" name= "client" value= "voxelaria1710"> </p>
<p>login <input type= "text" name= "login" value= "test"> </p>
<p>passw <input type= "text" name= "passw" value= "test"> </p>
<input type= "submit" value= "Зашифровать">
</form>
<form action= "launcher.php" method= "POST">
<p>action<input type= "text" name= "action" value= <?php echo @$aes ?> > </p>
<input type= "submit" value= "Отправить aes на launcher.php">
</form>
