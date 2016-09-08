<?php
class Security {
	public static function encrypt($input, $key) {
		return openssl_encrypt($input, "aes-128-ecb", $key);
	}
	
	public static function decrypt($sStr, $sKey) {
		return openssl_decrypt($sStr, "aes-128-ecb", $sKey);
	}
}