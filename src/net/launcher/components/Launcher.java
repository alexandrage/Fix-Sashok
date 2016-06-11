package net.launcher.components;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.launcher.run.Settings;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.java.eURLClassLoader;

public class Launcher extends Applet implements AppletStub
{
	private static final long serialVersionUID = 1L;
	private Applet mcApplet = null;
	public Map<String, String> customParameters = new HashMap<String, String>();
	private int context = 0;
	private boolean active = false;
	private URL[] urls;
	private String bin;

	public Launcher(String bin, URL[] urls)
	{
		this.bin = bin;
		this.urls = urls;
	}

	public void init()
	{
		if (mcApplet != null)
		{
			mcApplet.init();
			return;
		}


		new Runnable()
		{
			public void run()
			{
				Settings.onStartMinecraft();
			}
		}.run();

		eURLClassLoader cl = new eURLClassLoader(urls);
		System.setProperty("org.lwjgl.librarypath", bin + "natives");
		System.setProperty("net.java.games.input.librarypath", bin + "natives");
		try
		{
			BaseUtils.patchDir(cl);
			Class <?> Mine = cl.loadClass("net.minecraft.client.MinecraftApplet");
			System.setProperty("minecraft.applet.WrapperClass", Launcher.class.getName());
			int i = 0;
			for(Method m : Launcher.class.getMethods()) {
				if(m.toString().contains("java.applet.Applet") && i == 0) {
					System.setProperty("minecraft.applet.ReplaceMethod", m.getName());
					i++;
				}
			}
			Applet applet = (Applet)Mine.newInstance();
			mcApplet = applet;
			applet.setStub(this);
			applet.setSize(getWidth(), getHeight());
			setLayout(new BorderLayout());
			add(applet, "Center");
			applet.init();
			active = true;
			validate();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getParameter(String name)
	{
		String custom = (String)customParameters.get(name);
		if (custom != null) return custom;
		try
		{
			return super.getParameter(name);
		} catch(Exception e)
		{
			customParameters.put(name, null);
		}
		return null;
	}

	public void start()
	{
		if (mcApplet != null)
		{
			BaseUtils.send("Redirecting to Minecraft...");
			BaseUtils.send("--------------------------------");
			mcApplet.start();
			return;
		}
	}

	public boolean isActive()
	{
		if (context == 0)
		{
			context = -1;
			try
			{
				if (getAppletContext() != null)
					context = 1;
			} catch(Exception e){}
		}
		if (context == -1)
			return active;
		return super.isActive();
	}

	public URL getDocumentBase()
	{
		try
		{
			return new URL("http://www.minecraft.net/game/");			
		} catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void stop()
	{
		if (mcApplet != null)
		{
			active = false;
			mcApplet.stop();
			return;
		}
	}

	public void destroy()
	{
		if (mcApplet != null)
		{
			mcApplet.destroy();
			return;
		}
	}

	public void appletResize(int w, int h){}
	public void replace(Applet applet)
	{
		mcApplet = applet;
		applet.setStub(this);
		applet.setSize(getWidth(), getHeight());

		setLayout(new BorderLayout());
		add(applet, "Center");

		applet.init();
		active = true;
		applet.start();
		validate();
	}
}
