<?php
foreach($_REQUEST as $key => $val){break;}
$imgBig = '1.png';
$imgSmall = str_replace(array('$', '/'), array('', ''), @$key.'.png');
@$img1 = imagecreatefrompng($imgBig);
@$img2 = imagecreatefrompng($imgSmall);
if($img1 and $img2) {
	imageSaveAlpha($img1, true);
	imageSaveAlpha($img2, true);
	header('Content-Type: image/png');
	$size = getimagesize($imgSmall);
	if($size[0] == 22) {
		$x2 = imagesx($img2);
		$y2 = imagesy($img2);
		imagecopyresampled(
		$img1, $img2,
		0, 0,
		0, 0,
		$x2, $y2,
		$x2, $y2
		);
		imagepng($img1);
	} else {
		imagepng($img2);
	}
	imagedestroy($img1);
	imagedestroy($img2);
} else {
	header("HTTP/1.0 404 Not Found");
}