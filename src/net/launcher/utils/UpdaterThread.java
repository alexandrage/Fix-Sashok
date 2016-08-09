package net.launcher.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdaterThread {
	public static void run(String files) {
		try {
			String boundary = PostUtils.randomString() + PostUtils.randomString() + PostUtils.randomString();
			BaseUtils.send(files);
			String pathTo = BaseUtils.getAssetsDir().getAbsolutePath();
			String urlTo = BaseUtils.buildUrl("clients");
			File dir = new File(pathTo);
			ThreadUtils.currentfile = files;
			ThreadUtils.state = "Закачка файлов...";
			byte[] buffer = new byte[65536];
			String file = files.replace(" ", "%20");
			try {
	            dir = new File(pathTo + "/" + files.substring(0, files.lastIndexOf("/")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!dir.exists()) dir.mkdirs();
			HttpURLConnection ct = null;
	        URL url = new URL(urlTo + file);
	        ct = (HttpURLConnection) url.openConnection();
	        ct.setRequestMethod("GET");    
	        ct.setRequestProperty("User-Agent", "Launcher/64.0");
	        ct.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
	        ct.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	        ct.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			InputStream is = new BufferedInputStream(ct.getInputStream());
			FileOutputStream fos = new FileOutputStream(pathTo + "/" + files);
			int bs = 0;
			while((bs = is.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, bs);
				ThreadUtils.downloadedAmount += bs;
				ThreadUtils.currentsize += bs;
				ThreadUtils.procents = (int)(ThreadUtils.currentsize * 100 / ThreadUtils.totalsize);
			}
			is.close();
			fos.close();
		} catch (Exception e) { 
			e.printStackTrace();
			ThreadUtils.error = true;
		}
	}
}