<html>
	<head></head>
	<body>
	<?php
		define('INCLUDE_CHECK',true);
		include("connect.php");
		@$action = $_GET['action'];
		@$pass = $_GET['pass'];
		if(empty($action) || empty($pass)) {
	?>
		<form action="">
			<b>Выберите действие: </b><br>
			<input type="radio" name="action" value="addkeys">Добавить ваучеры</input><br>
			<input type="radio" name="action" value="setmoney">Изменить счет</input><br>
			<input type="radio" name="action" value="resetpass" disabled>Сбросить пароль</input><br>
			<hr><b>Пароль (Равен паролю бд):</b>
			<input type="password" name="pass"/><br>
			<input type="submit">
		</form>
	<?php } else if($pass == $db_pass)
		{
			if($action == 'addkeys' && (empty($_GET['price']) || empty($_GET['symb']) || empty($_GET['col']) || empty($_GET['len']))) { ?>
			<form action="">
				<input type="hidden" name="action" value="<?php echo $action;?>" />
				<input type="hidden" name="pass" value="<?php echo $pass;?>" />
				Цена ваучера: <input type="text" name="price" value="100" /><br>
				Символы: <input type="text" name="symb" value="ABCDEF1234567890" /><br>
				Длина: <input type="text" name="len" value="8" /><br>
				Количество: <input type="text" name="col" value="50" /><br>
				<input type="submit">
			</form> <?php }
			else if($action == 'addkeys')
			{
				for($i = 0; $i < $_GET['col']; $i++)
				{
				    $amount = $_GET['price'];
				    $key = genKey($_GET['len']);
				    $stmt = $db->prepare("INSERT INTO sashok724_launcher_keys (`key`,`amount`) VALUES (:key, :amount)");
			        $stmt->bindValue(':key', $key);
			        $stmt->bindValue(':amount', $amount);
			        $stmt->execute();
				}
				echo "Успешно";
			} else if($action == "setmoney" && (empty($_GET['unick']) || empty($_GET['money']))) { ?>
			<form action="">
				<input type="hidden" name="action" value="<?php echo $action;?>" />
				<input type="hidden" name="pass" value="<?php echo $pass;?>" />
				Ник игрока: <input type="text" name="unick" /><br>
				Деньги: <input type="text" name="money" /><br>
				<input type="submit">
			</form> <?php } else if($action == "setmoney")
			{
				echo "в разработке....";
			}
		}
		else echo "<b>Неправильный пароль!</b>";
		
		function genKey($length)
		{
			$chars = $_GET['symb'];
			$numChars = strlen($chars);
			$string = '';
			for ($i = 0; $i < $length; $i++) $string .= substr($chars, rand(1, $numChars) - 1, 1);
			return $string;
		}
	?>
	</body>
</html>