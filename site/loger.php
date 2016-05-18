<?php
date_default_timezone_set('Europe/Moscow');
	$logger = new Logger("./m.log");
    $log_date = "[" . date("d m Y H:i") . "] ";
class Logger {
    var $file;
    var $error;
    function __construct($path)
    {
        $this->file = $path;
    }
    function WriteLine($text)
    {
        $fp = fopen($this->file, "a+");
        if($fp)
        {
            fwrite($fp,$text . "\n");
        } else {
            $this->error = "Ошибка записи в лог-файл";
        }
        fclose($fp);
    }
    function Read()
    {
        if(file_exists($this->file))
        {
            return file_get_contents($this->file);
        } else {
            $this->error = "Лог-файл не существует";
        }
    }
    function Clear()
    {
        $fp = fopen($this->file,"a+");
        if($fp)
        {
            ftruncate($fp,0);
        } else {
            $this->error = "Ошибка чтения лог-файла";
        }
        fclose($fp);
    }
}