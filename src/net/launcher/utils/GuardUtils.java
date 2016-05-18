package net.launcher.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import net.launcher.components.Frame;
import net.launcher.components.Game;
import net.launcher.run.Settings;


public class GuardUtils
{
	public static boolean ret = false;
	public static List<URL> url = new ArrayList<URL>();
	static long filesize = 0;
	public static List<String> updateMods(String answer)
	{  
		ret = false;
		List<String> files = new ArrayList<String>();	
			{
				String dir = BaseUtils.getAssetsDir().getAbsolutePath().replace("\\", "/");
				String[] modsArray = answer.split("<br>")[3].split("<::>")[0].split("<:>");
				Arrays.sort(modsArray);
				List<String> site = new ArrayList<String>();
				List<String> sit = new ArrayList<String>();
				List<String> cl = new ArrayList<String>();
				List<String> client = new ArrayList<String>();
				String[] scn_dirs = answer.split("<::>")[1].split("<:b:>");
				for (int i = 0; i < scn_dirs.length; i++) {
						cl.addAll(Get.getLibs(new File(dir+File.separator+scn_dirs[i])));
					  }
				for(String rpl : cl)
				{
					client.add(rpl.replace("\\", "/"));
				}
				for(String add : modsArray)
				{
					site.add(dir+"/"+add);
					sit.add(dir+"/"+add.split(":>")[0]+":>"+add.split(":>")[1]);
					
			        if (add.contains(BaseUtils.getClientName()+"/"+"bin")) {
			        	File file = new File(dir+File.separator+add.split(":>")[0]);
			        	try {
							url.add(file.toURI().toURL());
						} catch (MalformedURLException e) {}
			        }
				}
				for (String check : client) {
			        if (!sit.contains(check) && !check.contains(dir+"/assets/skins/")) {
			            File file = new File(check.split(":>")[0]);
			            System.err.println("Delete -> "+file);
			            delete(file);
			            ret = true;
			        }
			    }
				for (String check : site) {
			        if (!client.contains(check.split(":>")[0]+":>"+check.split(":>")[1])) {
			            files.add(check.replace(dir, "").split(":>")[0]);
			            filesize += Integer.parseInt(check.replace(dir, "").split(":>")[2]);
			        }
			    }
			}
		return files;
	}


	@SuppressWarnings("deprecation")
	public static void checkMods(String answer, boolean action)
	{
			BaseUtils.sendp("ANTICHEAT: Rechecking jars....");
			GuardUtils.updateMods(answer);
			if(ret && action)
			{
				Frame.main.setError("Ошибка вторичной проверки кеша.");
				return;
			} else if(ret && !action)
			{
				BaseUtils.sendp("ANTICHEAT: Strange mods detected");
				try{
	                Class<?> af = Class.forName("java.lang.Shutdown");
	                Method m = af.getDeclaredMethod("halt0", int.class);
	                m.setAccessible(true);
	                m.invoke(null, 1);
	            } catch (Exception e) { }
				Game.start.stop();
				return;
			}
			
			BaseUtils.sendp("ANTICHEAT: Mod checking done");
	}
	
	public static void check() {
		if(GuardUtils.checkProcesses(Settings.p)) {
			try{
	            Class<?> af = Class.forName("java.lang.Shutdown");
	            Method m = af.getDeclaredMethod("halt0", int.class);
	            m.setAccessible(true);
	            m.invoke(null, 1);
	        } catch (Exception e) { }
		}
	}
	
    public static void delete(File file)
    {
        try {
            if (!file.exists()) return;
            if (file.isDirectory())
            {
                for (File f : file.listFiles()) delete(f);
                file.delete();
            } else file.delete();
        } catch (Exception e)
        {}
    }
    
    public static void getLogs(File Logs) {
  	  if (!Logs.exists()) Logs.mkdirs();
  	  for (File file : Logs.listFiles()) {
  	   if (file.isDirectory()) {
  	   } else {
  		 if (file.getName().contains(".log")) {
  			delete(file);
  		 }
  	   }
  	  }
     }
    
	   public static String hash(URL url) {
		      if(url == null) {
		         return "h";
		      } else if(urltofile(url).isDirectory()) {
		         return "d";
		      } else {
		         InputStream IS = null;
		         DigestInputStream DI = null;
		         BufferedInputStream BS = null;
		         Formatter F = null;

		         try {
		            MessageDigest MD = MessageDigest.getInstance("MD5");
		            IS = url.openStream();
		            BS = new BufferedInputStream(IS);
		            DI = new DigestInputStream(BS, MD);

		            while(DI.read() != -1) {}

		            byte[] Md = MD.digest();
		            F = new Formatter();
		            byte[] Mi = Md;
		            int I = Md.length;

		            for(int i = 0; i < I; ++i) {
		               byte Bi = Mi[i];
		               F.format("%02x", new Object[]{Byte.valueOf(Bi)});
		            }

		            String str = F.toString();
		            return str;
		         } catch (Exception e) {}
		         
		         finally {
		            try {
		            	IS.close();
		            	IS = null;
		            } catch (Exception e) {}

		            try {
		            	DI.close();
		            	DI = null;
		            } catch (Exception e) {}

		            try {
		            	BS.close();
		            	BS = null;
		            } catch (Exception e) {}

		            try {
		               F.close();
		               F = null;
		            } catch (Exception e) {}

		         }

		         return "h";
		      }
	   }
	 	   
	   public static File urltofile(URL url) {
		      try {
		         return new File(url.toURI());
		      } catch (URISyntaxException var2) {
		         return new File(url.getPath().replace("file:/", "").replace("file:", ""));
		      }
	   }
	   
	    public static boolean checkProcesses(String[] onlineData)
	    {
	        if (onlineData == null) return false;
	        
	        try
	        {
	            int platform = BaseUtils.getPlatform();
	            String line; Process p;
	            List<String> processes = new ArrayList<>();
	            
	            if (platform == 2) p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe /v /fo list");
	            else p = Runtime.getRuntime().exec("ps -e");
	            
	            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())))
	            {
	                
	                while ((line = input.readLine()) != null)
	                {
	                    processes.add(line.toLowerCase());
	                }
	                
	            }
	            
	            for(String process : processes)
	            {
	            	for(String Data : onlineData)
	            	{
	            		if(process.contains(Data.toLowerCase()))
	            		{
	            			return true;
	            		}
	            	}
	            }
	            
	        } catch (Exception e) {}
	        
	        return false;
	    }
}
