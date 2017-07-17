package net.launcher.run;

public class Settings {
	/** Настройка заголовка лаунчера */
	public static final String title = "Launcher";

	/** Заголовок клиента (старые версии) */
	public static final String titleInGame = "Minecraft";

	/**
	 * Папка с файлом конфигурации
	 */
	public static final String baseconf = "voxelaria";

	/**
	 * Конструктор пути к папке с MC
	 */
	public static final String pathconst = "voxelaria/%SERVERNAME%";

	/** Папка скинов */
	public static final String skins = "MinecraftSkins/";

	/** Папка плащей */
	public static final String cloaks = "MinecraftCloaks/";

	/** Параметры подключения */
	/**
	 * Протокол подключения https:// если есть ssl сертификат
	 */
	public static final String http = "http://";

	/** Домен сайта */
	public static final String domain = "localhost";

	/**
	 * Папка с файлами лаунчера на сайте
	 */
	public static final String siteDir = "site";

	/**
	 * Ссылка на регистрацию, при useRegister = false
	 */
	public static final String RegisterUrl = http + "localhost/";

	/**
	 * Ссылка на обновления лаунчера. Не писать на конце ".exe .jar"!
	 */
	public static final String updateFile = http + "localhost/site/launcher/fix";

	/** Ссылка на страницу покупки ваучеров */
	public static final String buyVauncherLink = "http://plati.ru/";

	public static final String iMusicname = "001.mp3";

	/**
	 * Количество потоков для загрузки файлов.
	 */
	public static final int thread = 8;

	/** Список запрещенных процессов. */
	public static final String[] p = { "wireshark", "cheat" };

	/** Высота окна клиента */
	public static int height = 532;

	/** Ширина окна клиента */
	public static int width = 900;

	/** Выделение памяти при первом запуске. */
	public static int defaultmemory = 768;

	/** Тут ничего не трогать! */
	public static String[] servers = { "Offline, localhost, 25565, 1.5.2", };

	/** Настройка панели ссылок */
	public static final String[] links = {
			/**
			 * Для отключения добавьте в адрес ссылки # "url::http://localhost",
			 */
			"::#", };

	/** Настройки структуры лаунчера */
	/** Использовать функцию автозахода на выбранный сервер */
	public static boolean useAutoenter = false;

	/** Использовать Регистрацию в лаунчере */
	public static boolean useRegister = false;

	/** Использовать стандартный браузер для открытия ссылок */
	public static boolean useStandartWB = true;

	/** Использовать Личный кабинет */
	public static boolean usePersonal = true;

	/** Использовать кастомный фрейм */
	public static boolean customframe = true;

	/** Использовать скрытие консоли клиента */
	public static boolean useConsoleHider = false;

	/** Перепроверка jar через 30 секунд */
	public static boolean useModCheckerTimer = true;

	/** Количество раз перепроверки jar во время игры */
	public static int useModCheckerint = 2;

	/**
	 * Скачивать assets из папки, или из архива (true=из папки false=из архива)
	 * в connect.php должно быть так же.
	 */
	public static boolean assetsfolder = false;

	/** Ключ сессии. */
	public static final String protectionKey = "1234567890";

	/** 16 Character Key Ключ пост запросов */
	public static final String key1 = "1234567891234567";

	/** 16 Character Key Ключ пост запросов */
	public static final String key2 = "1234567891234567";

	/** Отображать все действия лаунчера (отладка)(true/false) */
	public static boolean debug = true;

	/** Отрисовывать границы элементов лаунчера */
	public static boolean drawTracers = false;

	/** Версия лаунчера */
	public static final String masterVersion = "final_RC4";
	/**
	 * Ставим true после окончания настройки! Необходимо для автообновления
	 * лаунчера после смены ключей key1 key2.
	 */
	public static boolean release = false;

	public static void onStart() {
	}

	public static void onStartMinecraft() {
	}
}