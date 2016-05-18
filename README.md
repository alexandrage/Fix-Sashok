Сразу предупреждаю, на бесплатном hostinger.ru небудет работать правильно.

src тут https://github.com/alexandrage/Fix-Sashok
clients тут https://cloud.mail.ru/public/1fd2bdc3ca7f/site + демо клиенты.

Добавлена регистрация в лаунчере.
Регистрация настроена на хеш hash_md5 и hash_dle.
Добавлена функция бана в лаунчере связанная с плагином Ultrabans.
Добавлено кеширование хешей клиентов, для ускорения авторизации.
Для обновления кеша удалите файл /temp/ИмяКлиента.
Включается в конфиге $temp = (true false), по умолчанию включено.

Классы для авторизации 1.6.4 forge cauldron на моем диске с клиентами, ссылка выше.

Дописаны скрипты для авторизации 1.7.2-1.8.0.
Ссылки на  новые скрипты указываем в классе YggdrasilMinecraftSessionService.class. В сервере тот же класс.
Используйте authlib только из моей сборки, там фикс скинов для клиентов 1.7.10 и 1.8.1
"https://sessionserver.mojang.com/session/minecraft/join" -> "http://minecraft/site/j.php"
"https://sessionserver.mojang.com/session/minecraft/hasJoined" -> "http://minecraft/site/h.php"
"https://sessionserver.mojang.com/session/minecraft/profile/" -> "http://minecraft/site/s.php?user="
Ссылка для скинов блока головы в классе YggdrasilGameProfileRepository.class, класс изменен, брать с моего authlib.
"https://api.mojang.com/profiles/" -> "http://minecraft/site/uuidskull.php"
Переписана веб часть под mysql-pdo.
Запуск новых версий теперь в аплете лаунчера.
Полное шифрование запросов лаунчер-вебчасть.
Исправлена проверка клиента, теперь проверяются все подпапки в bin-mods-coremods.
Изменяйте в модах папку конфига (mods на config) ( Ре минимап и we cui).

Полное изменение загрузки клиента.
Новая структура клиента должна быть такой
clients/assets/ ресурс файлы. При режиме zip clients/assets.zip
clients/voxelaria/config.zip конфиги модов и ресурскаки, расспаковывается в корень папки клиента.
clients/voxelaria/bin/ jar файлы клиента, можно
использовать подпапки bin/libraries/ и тд.
clients/voxelaria/mods/  zip-jar файлы, модов, можно использовать
подпапки mods/lib/lib.jar
clients/voxelaria/coremods/ zip-jar файлы коремодов (используется
только устаревшими версиями minecraft) оставить пустой, если не требуется.
clients/voxelaria/natives/ нативы для lwjgl.
Некоторые моды качают свои либы в подпапку типо mods/1.6.4/
не забывайте их заливать на сервер.

Папка assets может качатся архивом или пофайлово, переключается в конфиге.

Если при настройке непонятная ошибка, авторизуйтесь с проверочного скрипта.
test.php проверочный скрипт. Удалить test.php после настройки!

Прежде чем ставить лаунчер, убедитесь что расширение mcrypt установлено в вашем php.