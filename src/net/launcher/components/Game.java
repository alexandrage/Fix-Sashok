package net.launcher.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import net.launcher.run.Settings;
import net.launcher.run.Starter;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.EncodingUtils;
import net.launcher.utils.GuardUtils;
import net.launcher.utils.ProcessUtils;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	public static Launcher mcapplet;
	private static URLClassLoader cl;
	static String Cl = null;
	Timer timer = null;
	int i = 0;
	static List<String> params = new ArrayList<String>();

	public Game(final String answer) {
		GuardUtils.getLogs(
				new File(BaseUtils.getAssetsDir().getAbsolutePath() + File.separator + BaseUtils.getClientName()));
		GuardUtils.getLogs(new File(BaseUtils.getAssetsDir().getAbsolutePath() + File.separator + "logs"));
		String bin = BaseUtils.getMcDir().toString() + File.separator;
		cl = new URLClassLoader(GuardUtils.url.toArray(new URL[GuardUtils.url.size()]));
		boolean old = false;
		try {
			if (Settings.isAplet) {
				cl.loadClass("net.minecraft.client.MinecraftApplet");
			} else {
				cl.loadClass("net.minecraft.client.Minecraft");
			}
			old = true;
		} catch (Exception e) {
		}
		String user = answer.split("<br>")[1].split("<:>")[0];
		String session = EncodingUtils.xorencode(EncodingUtils.inttostr(answer.split("<br>")[1].split("<:>")[1]),
				Settings.protectionKey);

		if (old) {
			Thread check = new Thread(new Runnable() {
				@Override
				public void run() {
					int z = 0;
					while (z < Settings.useModCheckerint) {
						GuardUtils.checkMods(answer, false);
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						z++;
					}
				}
			});
			check.start();
			BaseUtils.send("Running Minecraft");
			if (Settings.isAplet) {
				try {
					addWindowListener(new WindowListener() {
						public void windowOpened(WindowEvent e) {
						}

						public void windowIconified(WindowEvent e) {
						}

						public void windowDeiconified(WindowEvent e) {
						}

						public void windowDeactivated(WindowEvent e) {
						}

						public void windowClosed(WindowEvent e) {
						}

						public void windowActivated(WindowEvent e) {
						}

						public void windowClosing(WindowEvent e) {
							mcapplet.stop();
							mcapplet.destroy();
							System.exit(0);
						}
					});
					setForeground(Color.BLACK);
					setBackground(Color.BLACK);

					mcapplet = new Launcher(bin, GuardUtils.url.toArray(new URL[GuardUtils.url.size()]));
					mcapplet.customParameters.put("username", user);
					mcapplet.customParameters.put("sessionid", session);
					mcapplet.customParameters.put("stand-alone", "true");
					if (Settings.useAutoenter) {
						mcapplet.customParameters.put("server",
								Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[1]);
						mcapplet.customParameters.put("port",
								Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[2]);
					}
					setTitle(Settings.titleInGame);
					if (Frame.main != null) {
						Frame.main.setVisible(false);
						setBounds(Frame.main.getBounds());
						setExtendedState(Frame.main.getExtendedState());
						setMinimumSize(Frame.main.getMinimumSize());
					}
					setSize(Settings.width, Settings.height + 28);
					setMinimumSize(new Dimension(Settings.width, Settings.height + 28));
					setLocationRelativeTo(null);
					mcapplet.setForeground(Color.BLACK);
					mcapplet.setBackground(Color.BLACK);
					setLayout(new BorderLayout());
					add(mcapplet, BorderLayout.CENTER);
					validate();
					if (BaseUtils.getPropertyBoolean("fullscreen"))
						setExtendedState(JFrame.MAXIMIZED_BOTH);
					setIconImage(BaseUtils.getLocalImage("favicon"));
					setVisible(true);

					if (Settings.useConsoleHider) {
						System.setErr(new PrintStream(new NulledStream()));
						System.setOut(new PrintStream(new NulledStream()));
					}
					mcapplet.init();
					mcapplet.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.setProperty("org.lwjgl.librarypath", bin + "natives");
				System.setProperty("net.java.games.input.librarypath", bin + "natives");
				BaseUtils.patchDir(cl);
				params.add(user);
				params.add(session);
				Frame.main.setVisible(false);
				Cl = "net.minecraft.client.Minecraft";
				start.start();
			}

		} else {
			Thread check = new Thread(new Runnable() {
				@Override
				public void run() {
					int z = 0;
					while (z < Settings.useModCheckerint) {
						GuardUtils.checkMods(answer, false);
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						z++;
					}
				}
			});
			check.start();
			try {
				boolean news = false;
				BaseUtils.send("Running Minecraft");
				String jarpath = BaseUtils.getMcDir().toString() + File.separator;
				String minpath = BaseUtils.getMcDir().toString();
				String assets = BaseUtils.getAssetsDir().toString() + File.separator;
				System.setProperty("fml.ignoreInvalidMinecraftCertificates", "true");
				System.setProperty("fml.ignorePatchDiscrepancies", "true");
				System.setProperty("org.lwjgl.librarypath", jarpath + "natives");
				System.setProperty("net.java.games.input.librarypath", jarpath + "natives");
				System.setProperty("java.library.path", jarpath + "natives");
				if (BaseUtils.getPropertyBoolean("fullscreen")) {
					params.add("--fullscreen");
					params.add("true");
				} else {
					params.add("--width");
					params.add(String.valueOf(Settings.width));
					params.add("--height");
					params.add(String.valueOf(Settings.height));
				}
				if (Settings.useAutoenter) {
					params.add("--server");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[1]);
					params.add("--port");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[2]);
				}
				try {
					cl.loadClass("com.mojang.authlib.Agent");
					params.add("--accessToken");
					params.add(session);
					params.add("--uuid");
					params.add(EncodingUtils.xorencode(EncodingUtils.inttostr(answer.split("<br>")[0].split("<:>")[1]),
							Settings.protectionKey));
					params.add("--userProperties");
					params.add("{}");
					params.add("--assetIndex");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
				} catch (ClassNotFoundException e2) {
					params.add("--session");
					params.add(session);
				}
				params.add("--userType");
				params.add("legacy");
				params.add("--versionType");
				params.add("release");
				params.add("--username");
				params.add(user);
				params.add("--version");
				params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
				params.add("--gameDir");
				params.add(minpath);
				params.add("--assetsDir");
				if (Integer.parseInt(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3].replace(".",
						"")) < 173) {
					params.add(assets + "assets/virtual/legacy");
				} else {
					params.add(assets + "assets");
				}
				boolean tweakClass = false;
				try {
					cl.loadClass("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
					params.add("--tweakClass");
					params.add("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
					tweakClass = true;
				} catch (ClassNotFoundException e) {
				}
				try {
					cl.loadClass("cpw.mods.fml.common.launcher.FMLTweaker");
					params.add("--tweakClass");
					params.add("cpw.mods.fml.common.launcher.FMLTweaker");
					tweakClass = true;
				} catch (ClassNotFoundException e) {
				}
				try {
					cl.loadClass("net.minecraftforge.fml.common.launcher.FMLTweaker");
					params.add("--tweakClass");
					params.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
					tweakClass = true;
				} catch (ClassNotFoundException e) {
					try {
						cl.loadClass("org.dimdev.riftloader.launch.RiftLoaderClientTweaker");
						params.add("--tweakClass");
						params.add("org.dimdev.riftloader.launch.RiftLoaderClientTweaker");
						tweakClass = true;
						news = true;
					} catch (ClassNotFoundException e2) {
						try {
							cl.loadClass("optifine.OptiFineTweaker");
							params.add("--tweakClass");
							params.add("optifine.OptiFineTweaker");
							tweakClass = true;
						} catch (ClassNotFoundException e3) {
						}
					}
				}
				if (tweakClass) {
					Cl = "net.minecraft.launchwrapper.Launch";
				} else {
					Cl = "net.minecraft.client.main.Main";
				}
				try {
					cl.loadClass("cpw.mods.modlauncher.Launcher");
					params.add("--launchTarget");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[4]);
					params.add("--fml.forgeVersion");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[5]);
					params.add("--fml.mcVersion");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[3]);
					params.add("--fml.forgeGroup");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[6]);
					params.add("--fml.mcpVersion");
					params.add(Settings.servers[Frame.main.servers.getSelectedIndex()].split(", ")[7]);
					Cl = "cpw.mods.modlauncher.Launcher";
					news = true;
				} catch (ClassNotFoundException e3) {
				}
				
				try {
					cl.loadClass("net.fabricmc.loader.launch.knot.KnotClient");
					Cl = "net.fabricmc.loader.launch.knot.KnotClient";
					news = true;
				} catch (ClassNotFoundException e3) {
				}

				Frame.main.setVisible(false);
				GuardUtils.delete(new File(assets + "assets/skins"));
				if(news) {
					startNew.start();
				} else {
					start.start();
				}
			} catch (Exception e) {
			}
		}
	}

	public static Thread start = new Thread(new Runnable() {
		@Override
		public void run() {
			try {		
				Class<?> start = cl.loadClass(Cl);
				Method main = start.getMethod("main", new Class[] { String[].class });
				main.invoke(null, new Object[] { params.toArray(new String[0]) });
			} catch (Exception e) {
				JOptionPane.showMessageDialog(Frame.main, e, "Ошибка запуска", javax.swing.JOptionPane.ERROR_MESSAGE,
						null);
				try {
					Class<?> af = Class.forName("java.lang.Shutdown");
					Method m = af.getDeclaredMethod("halt0", int.class);
					m.setAccessible(true);
					m.invoke(null, 1);
				} catch (Exception x) {
				}
			}
		}
	});
	
	public static Thread startNew = new Thread(new Runnable() {
		@Override
		public void run() {
			String jarpath = BaseUtils.getMcDir().toString() + File.separator;
			List<String> params2 = new ArrayList<String>();
			List<URL> cps = GuardUtils.url;
			params2.add(System.getProperty("java.home") + "/bin/java");
			params2.add("-Xmx"+Starter.memory+"m");
			params2.add("-Djava.library.path=" + jarpath + "natives");
			params2.add("-cp");
			StringBuilder sb = new StringBuilder();
			for (URL cp : cps) {
				sb.append(cp.getPath() + ";");
			}
			params2.add(sb.toString());
			params2.add(Cl);
			for (String param : params) {
				params2.add(param);
			}
			try {
				ProcessBuilder pb = new ProcessBuilder(params2);
				pb.directory(new File(jarpath));
				Process process = pb.start();
				new ProcessUtils(process).print();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(Frame.main, e, "Ошибка запуска", javax.swing.JOptionPane.ERROR_MESSAGE,
						null);
				try {
					Class<?> af = Class.forName("java.lang.Shutdown");
					Method m = af.getDeclaredMethod("halt0", int.class);
					m.setAccessible(true);
					m.invoke(null, 1);
				} catch (Exception x) {
				}
			}
		}
	});
}