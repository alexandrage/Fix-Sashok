<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
		<meta http-equiv="Content-Language" content="ru">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Новости</title>
		<style type="text/css">
			a { color: #aaaaff; font-family: arial; font-size: 11px; }
			h2 { color: #ffffff; font-family: arial; font-size: 16px; }
			img { border:0; margin:0; }
		</style>
	</head>
	<body>
		<table width="100%">
			<tr>
				<td valign="top">
			  <?php $file = fopen("news/news.txt", "r"); while (!feof($file)) {
					$line = fgets($file);
					$news = str_replace('[title]','<h1><b><font color=#FFFFFF face="Arial">',$line);
					$news = str_replace('[/title]','</font></b></h1>',$news);
					$news = str_replace('[text]','<font color=#FFFFFF face="Arial">',$news);
					$news = str_replace('[/text]','</font>',$news);
					$news = str_replace('[center]','<center>',$news);
					$news = str_replace('[/center]','</center>',$news);
					$news = str_replace('[n]','<br>',$news);
					$news = str_replace(':)','<img src=news/data/smile.png></img>',$news);
					$news = str_replace(':(','<img src=news/data/frown.png></img>',$news);
					$news = str_replace('[rage]','<img src=news/data/mad.png></img>',$news);
					$news = str_replace('>:\\','<img src=news/data/mad.png></img>',$news);
					$news = str_replace(':\\','<img src=news/data/confused.png></img>',$news);
					$news = str_replace(':D','<img src=news/data/biggrin.png></img>',$news);
					$news = str_replace('B)','<img src=news/data/cool.png></img>',$news);
					$news = str_replace(':O','<img src=news/data/eek.png></img>',$news);
					$news = str_replace(':P','<img src=news/data/tongue.png></img>',$news);
					$news = str_replace(';D','<img src=news/data/wink.png></img>',$news);
					$news = str_replace('8)','<img src=news/data/rolleyes.png></img>',$news);
					$news = str_replace('[redface]','<img src=news/data/redface.png></img>',$news);
					$news = str_replace('/facepalm','<img src=news/data/facepalm.gif></img>',$news);
					echo($news); } fclose($file); ?>
				</td>
				
				<td width="180" valign="top">
		      <?php $file = fopen("news/sidebar.txt", "r"); while (!feof($file)) {
					$line = fgets($file);
					$news = str_replace('[title]','<h2><b>',$line);
					$news = str_replace('[/title]','</b></h2>',$news);
					$news = str_replace('[text]','<font face="Arial" color="#FFFFFF">',$news);
					$news = str_replace('[/text]','</font>',$news);
					$news = str_replace('[n]','<br>',$news);
					echo($news); } fclose($file); ?>
				</td>
			</tr>
		</table>
	</body>
</html>
