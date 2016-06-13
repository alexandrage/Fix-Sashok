package net.launcher.utils;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.launcher.components.Files;
import net.launcher.components.Frame;
import net.launcher.run.Settings;
import net.launcher.run.Starter;
import net.launcher.theme.Message;

public class BaseUtils
{
	public static final String empty = "";
	public static int logNumber = 0;
	public static ConfigUtils config = new ConfigUtils("/launcher.config", getConfigName());

	public static Map<String, Font> fonts = new HashMap<String, Font>();
	public static Map<String, BufferedImage> imgs = new HashMap<String, BufferedImage>();

	public static BufferedImage getLocalImage(String name)
	{
		try
		{
			if(imgs.containsKey(name)) return (BufferedImage)imgs.get(name);

			BufferedImage img = ImageIO.read(BaseUtils.class.getResource("/net/launcher/theme/" + name + ".png"));
			imgs.put(name, img);
			send("Opened local image: " + name + ".png");
			return img;
		}
		catch(Exception e)
		{
			sendErr("Fail to open local image: " + name + ".png");
			return getEmptyImage();
		}
	}

	public static BufferedImage getEmptyImage()
	{
		return new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
	}

	public static void send(String msg)
	{
		if(Settings.debug) System.out.println(date()+"[Launcher thread/INFO]: "+msg);
	}

	public static void sendErr(String err)
	{
		if(Settings.debug) System.err.println(date()+"[Launcher thread/WARN]: "+err);
	}
	
	public static void sendp(String msg)
	{
		if(Settings.debug) System.out.println(msg);
	}

	public static void sendErrp(String err)
	{
		if(Settings.debug) System.err.println(err);
	}

	public static boolean contains(int x, int y, int xx, int yy, int w, int h)
	{
		return (x >= xx) && (y >= yy) && (x < xx + w) && (y < yy + h);
	}

	public static File getConfigName()
	{
		String home = System.getProperty("user.home", "");
		String path = File.separator + Settings.baseconf + File.separator + "launcher.config";
		switch(getPlatform())
		{
			case 1: return new File(System.getProperty("user.home", "") + path);
			case 2:
				String appData = System.getenv("SYSTEMDRIVE");
				if(appData != null) return new File(appData + path);
				else return new File(home + path);
			case 3: return new File(home, path);
			default: return new File(home + path);
		}
	}

	public static File getAssetsDir()
	{
		String home = System.getProperty("user.home", "");
		String path = File.separator + Settings.baseconf + File.separator;
		switch(getPlatform())
		{
			case 1: return new File(System.getProperty("user.home", "") + path);
			case 2:
				String appData = System.getenv("SYSTEMDRIVE");
				if(appData != null) return new File(appData + path);
				else return new File(home + path);
			case 3: return new File(home, path);
			default: return new File(home + path);
		}
	}	

	public static File getMcDir()
	{
		String home = System.getProperty("user.home", "");
		String path = Settings.pathconst.replaceAll("%SERVERNAME%", getClientName());
		switch(getPlatform())
		{
			case 1: return new File(System.getProperty("user.home", ""), path);
			case 2:
				String appData = System.getenv("SYSTEMDRIVE");
				if(appData != null) return new File(appData, path);
				else return new File(home, path);
			case 3: return new File(home, path);
			default: return new File(home, path);
		}
	}

	public static int getPlatform()
	{
		String osName = System.getProperty("os.name").toLowerCase();

		if(osName.contains("win")) return 2;
		if(osName.contains("mac")) return 3;
		if(osName.contains("solaris")) return 1;
		if(osName.contains("sunos")) return 1;
		if(osName.contains("linux")) return 0;
		if(osName.contains("unix")) return 0;

		return 4;
	}

	public static String buildUrl(String s)
	{
		return Settings.http + Settings.domain + "/" + Settings.siteDir + "/" + s;
	}

	static
	{
		config.load();
	}

	public static void setProperty(String s, Object value)
	{
		if(config.checkProperty(s)) config.changeProperty(s,value);
		else config.put(s,value);
	}

	public static String getPropertyString(String s)
	{
		if(config.checkProperty(s)) return config.getPropertyString(s);
		return null;
	}

	public static boolean getPropertyBoolean(String s)
	{
		if(config.checkProperty(s)) return config.getPropertyBoolean(s);
		return false;
	}

	public static int getPropertyInt(String s)
	{
		if(config.checkProperty(s)) return config.getPropertyInteger(s);
		return 0;
	}

	public static int getPropertyInt(String s, int d)
	{
		File dir = new File(BaseUtils.getAssetsDir().toString());
		if(!dir.exists()) dir.mkdirs();
		if(config.checkProperty(s)) return config.getPropertyInteger(s);
		setProperty(s, d);
		return d;
	}

	public static boolean getPropertyBoolean(String s, boolean b)
	{
		if(config.checkProperty(s)) return config.getPropertyBoolean(s);
		return b;
	}
	
	static String boundary = PostUtils.randomString() + PostUtils.randomString() + PostUtils.randomString();

    public static String runHTTP(String URL, String params, boolean send)
    {
        HttpURLConnection ct = null;
        try
        {

            URL url = new URL(URL + params);
            ct = (HttpURLConnection) url.openConnection();
            ct.setRequestMethod("GET");    
            ct.setRequestProperty("User-Agent", "Launcher/64.0");
            ct.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
            ct.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            ct.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            ct.setUseCaches(false);
            ct.setDoInput(true);
            ct.setDoOutput(true);

            ct.connect();

            InputStream is = ct.getInputStream();
            StringBuilder response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is)))
            {
                response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null)
                {
                    response.append(line);
                }
            }

            String str = response.toString();

            return str;
        } catch (Exception e)
        {
            return null;
        } finally
        {
            if (ct != null) ct.disconnect();
        }
    }
	
	public static String getURLSc(String script)
    {
        return getURL("/" + Settings.siteDir + "/" + script);
    }
	
    public static String[] getServerNames()
    {
        String[] error = { "Offline" };
        try
        {
            String url = runHTTP(getURLSc("servers.php"), "", false);

            if (url == null)
            {
                return error;
            } else if (url.contains(", "))
            {
            	Settings.servers = url.replaceAll("<br>", "").split("<::>");
                String[] serversNames = new String[Settings.servers.length];

                for (int a = 0; a < Settings.servers.length; a++)
                {
                    serversNames[a] = Settings.servers[a].split(", ")[0];
                }

                return serversNames;
            } else
            {
                return error;
            }
        } catch (Exception e)
        {
            return error;
        }
    }
	
    public static String getURL(String path)
    {
        return Settings.http + Settings.domain + path;
    }

	public static String getClientName()
	{
		return Frame.main.servers.getSelected().replaceAll(" ", empty);
	}

	public static void openURL(String url)
	{
		try
		{
			Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
			o.getClass().getMethod("browse", new Class[] { URI.class }).invoke(o, new Object[] { new URI(url)});
		} catch (Throwable e) {}
	}

	public static void deleteDirectory(File file)
	{
		if(!file.exists()) return;
		if(file.isDirectory())
		{
			for(File f : file.listFiles())
			deleteDirectory(f);
			file.delete();
		}
		else file.delete();
	}

	public static BufferedImage getSkinImage(String name)
	{
		try
		{
			BufferedImage img = ImageIO.read(new URL(buildUrl(Settings.skins + name + ".png")));
			send("Skin loaded: " + buildUrl(Settings.skins + name + ".png"));
			return img;
		}
		catch(Exception e)
		{
			sendErr("Skin not found: " + buildUrl(Settings.skins + name + ".png"));
			return getLocalImage("skin");
		}
	}

	public static BufferedImage getCloakImage(String name)
	{
		try
		{
			BufferedImage img = ImageIO.read(new URL(buildUrl(Settings.cloaks + name + ".png")));
			send("Cloak loaded: " + buildUrl(Settings.cloaks + name + ".png"));
			return img;
		}
		catch(Exception e)
		{
			sendErr("Cloak not found: " + buildUrl(Settings.cloaks + name + ".png"));
			return new BufferedImage(64, 32, 2);
		}
	}

	public static String execute(String surl, Object[] params)
	{
		try
		{
			send("Openning stream: " + surl);
			URL url = new URL(surl);

			InputStream is = PostUtils.post(url, params);
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			StringBuffer response = new StringBuffer();
			String line;
			while((line=rd.readLine())!=null){ response.append(line); }
			rd.close();
			String str1 = response.toString().trim();
			send("Stream opened for " + surl + " completed, return answer: ");
			return str1;
		} catch(Exception e)
		{
			sendErr("Stream for " + surl + " not ensablished, return null");
			return null;
		}
	}

	public static Font getFont(String name, float size)
	{
		try
		{
			if(fonts.containsKey(name)) return (Font)fonts.get(name).deriveFont(size);
			Font font = null;
			send("Creating font: " + name);
			try
			{
				font = Font.createFont(Font.TRUETYPE_FONT, BaseUtils.class.getResourceAsStream("/net/launcher/theme/" + name + ".ttf"));
			} catch(Exception e)
			{
				try
				{
					font = Font.createFont(Font.TRUETYPE_FONT, BaseUtils.class.getResourceAsStream("/net/launcher/theme/" + name + ".otf"));
				} catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
			fonts.put(name, font);
			return font.deriveFont(size);
		} catch(Exception e)
		{
			send("Failed create font!");
			throwException(e, Frame.main);
			return null;
		}
	}

	public static void throwException(Exception e, Frame main)
	{
		e.printStackTrace();
		main.panel.removeAll();
		main.addFrameComp();
		StackTraceElement[] el = e.getStackTrace();
		main.panel.tmpString = empty;
		main.panel.tmpString += e.toString() + "<:>";
		for(StackTraceElement i : el)
		{
			main.panel.tmpString += "at " + i.toString() + "<:>";
		}
		main.panel.type = 3;
		main.repaint();
	}

	public static int servtype = 2;
	public static String[] pollServer(String ip, int port)
	{
		Socket soc = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try
		{
			soc = new Socket();
			soc.setSoTimeout(6000);
			soc.setTcpNoDelay(true);
			soc.setTrafficClass(18);
			soc.connect(new InetSocketAddress(ip, port), 6000);
			dis = new DataInputStream(soc.getInputStream());
			dos = new DataOutputStream(soc.getOutputStream());
			dos.write(254);

			if (dis.read() != 255)
			{
				throw new IOException("Bad message");
			}
			String servc = readString(dis, 256);
			servc.substring(3);
			if (servc.substring(0,1).equalsIgnoreCase("§") && servc.substring(1,2).equalsIgnoreCase("1"))
			{
				servtype = 1;
				return servc.split("\u0000");

			}
			else 
			{
				servtype = 2;
				return servc.split("§");
			}

		} catch (Exception e)
		{
			return new String[] { null, null, null };
		} finally
		{
			try { dis.close();  } catch (Exception e) {}
			try { dos.close();  } catch (Exception e) {}
			try { soc.close();  } catch (Exception e) {}
		}
	}

	public static int parseInt(String integer, int def)
	{
		try
		{
			return Integer.parseInt(integer.trim());
		}
		catch (Exception e)
		{
			return def;
		}
	}

	public static String readString(DataInputStream is, int d) throws IOException
	{
		short word = is.readShort();
		if (word > d) throw new IOException();
		if (word < 0) throw new IOException();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < word; i++)
		{
			res.append(is.readChar());
		}
		return res.toString();
	}

	public static String genServerStatus(String[] args)
	{
		if (servtype == 1)
		{
			if(args[0] == null && args[1] == null && args[2] == null) return Message.serveroff;
			if(args[4] != null && args[5] != null)
			{
				if(args[4].equals(args[5])) return Message.serveroff.replace("%%", args[4]);
				return Message.serveron.replace("%%", args[4]).replace("##", args[5]);
			}
		}
		else
		if (servtype == 2)
		{


		if(args[0] == null && args[1] == null && args[2] == null) return Message.serveroff;
		if(args[1] != null && args[2] != null)
		{
			int i = args.length;
			if(args[i-2].equals(args[i-1])) return Message.serveroff.replace("%%", args[i-1]);
			return Message.serveron.replace("%%", args[i-2]).replace("##", args[i-1]);
		}
		}
		return Message.servererr;
	}

	public static BufferedImage genServerIcon(String[] args)
	{
		if(args[0] == null && args[1] == null && args[2] == null) return Files.light.getSubimage(0, 0, Files.light.getHeight(), Files.light.getHeight());
		if(args[1] != null && args[2] != null)
		{
			if(args[1].equals(args[2])) return Files.light.getSubimage(Files.light.getHeight(), 0, Files.light.getHeight(), Files.light.getHeight());
			return Files.light.getSubimage(Files.light.getHeight() * 2, 0, Files.light.getHeight(), Files.light.getHeight());
		}
		return Files.light.getSubimage(Files.light.getHeight() * 3, 0, Files.light.getHeight(), Files.light.getHeight());
	}

	public static void restart()
	{
		send("Restarting launcher...");
		try
		{
			ArrayList<String> params = new ArrayList<String>();
			params.add(System.getProperty("java.home")+"/bin/java");
	        params.add("-classpath");
	        params.add(Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	        params.add(Starter.class.getCanonicalName());
			ProcessBuilder pb = new ProcessBuilder(params);
			Process process = pb.start();
			if (process == null) throw new Exception("Launcher can't be started!");
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		System.exit(0);
	}

	public static String unix2hrd(long unix)
	{
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(unix * 1000));
	}

	public void delete(File file)
	{
		if(!file.exists()) return;

	    if(file.isDirectory())
	    {
	    	for(File f : file.listFiles()) delete(f);
	    	file.delete();
	    } else
	    {
	    	file.delete();
	    }
	}

	public static boolean checkLink(String l)
	{
		if(l.contains("#"))
		{ 
			return false;
		}		
		return true;
	}

	public static boolean existLink(String l)
	{
		if(l.contains("@"))
		{
			return true;
		}
		return false;
	}

	public static void patchDir( URLClassLoader cl ) {
		try {
			Class< ? > c = cl.loadClass( "net.minecraft.client.Minecraft" );

			send("Changing client dir...");
			
			for ( Field f : c.getDeclaredFields() ) {       
				if( f.getType().getName().equals( "java.io.File" ) & Modifier.isPrivate( f.getModifiers() ) 
						& Modifier.isStatic( f.getModifiers() )) 
				{
					f.setAccessible( true );
					f.set( null, getMcDir() );
		            send("Patching succesful, herobrine removed.");
		            return;
				}
			}
		}catch ( Exception e ) {
			sendErr( "Client not patched" );
		}
	}

	public static void updateLauncher() throws Exception
	{
		send("Launcher updater started...");
		send("Downloading file: " + Settings.updateFile+Frame.jar);

		InputStream is = new BufferedInputStream(new URL(Settings.updateFile+Frame.jar).openStream());
		FileOutputStream fos = new FileOutputStream(Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

		int bs = 0;
		byte[] buffer = new byte[65536];
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		while((bs = is.read(buffer, 0, buffer.length)) != -1)
		{
			fos.write(buffer, 0, bs);
			md5.update(buffer, 0, bs);
		}
		is.close();
		fos.close();
		BaseUtils.send("File downloaded: " + Settings.updateFile+Frame.jar);
		restart();
	}
	
	private static String date()
	{
		Date now = new Date();
	    DateFormat formatter = new SimpleDateFormat("[HH:mm:ss]");
	    String s = formatter.format(now)+" ";
		return s;
	}
}
