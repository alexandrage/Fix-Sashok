package net.launcher.run;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import net.launcher.components.Frame;
import net.launcher.utils.BaseUtils;
import net.launcher.utils.ProcessUtils;

public class Starter {
	public static void main(String[] args) throws Exception {
		try {
			String jarpath = Starter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			int memory = BaseUtils.getPropertyInt("memory", Settings.defaultmemory);
			
			ArrayList<String> params = new ArrayList<String>();
			params.add(System.getProperty("java.home")+"/bin/java");
			if(System.getProperty("os.arch").contains("64") && System.getProperty("sun.arch.data.model").equals("32")) {
				JOptionPane.showMessageDialog(Frame.main, "Рекомендуется использовать\njava 64 bit", "Предупреждение!", javax.swing.JOptionPane.ERROR_MESSAGE, null);
			}
			if(System.getProperty("sun.arch.data.model").equals("32") && (memory>1024)) {
				memory = 1024;
				BaseUtils.setProperty("memory", 1024);
			}
			if(memory<512) {
				memory = 512;
				BaseUtils.setProperty("memory", 512);
			}
			if(memory>4096) {
				memory = 4096;
				BaseUtils.setProperty("memory", 4096);
			}
			params.add("-Xmx"+memory+"m");
			params.add("-XX:MaxPermSize=128m");
			if(System.getProperty("os.name").toLowerCase().startsWith("mac"))
			{
				params.add("-Xdock:name=Minecraft");
				params.add("-Xdock:icon="+BaseUtils.getAssetsDir().toString()+"/favicon.png");
			}
			params.add("-classpath");
			params.add(jarpath);
			params.add(Mainclass.class.getCanonicalName());
			params.add("true");
			
			ProcessBuilder pb = new ProcessBuilder(params);
			pb.directory(new File(BaseUtils.getAssetsDir().toString()));
			Process process = pb.start();
			if (process == null) throw new Exception("Launcher can't be started!");
			new ProcessUtils(process).print();
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(Frame.main, e, "Ошибка запуска", javax.swing.JOptionPane.ERROR_MESSAGE, null);
			try{
                Class<?> af = Class.forName("java.lang.Shutdown");
                Method m = af.getDeclaredMethod("halt0", int.class);
                m.setAccessible(true);
                m.invoke(null, 1);
            } catch (Exception x) {}
		}
	}
}
