package net.launcher.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Get {
    static List<String> getLibs(File libsfolder) {
  	  List<String> libs = new ArrayList<String>();
  	  if (!libsfolder.exists()) libsfolder.mkdirs();
  	  for (File file : libsfolder.listFiles()) {
  	   if (file.isDirectory()) {
  	    libs.addAll(getLibs(file));
  	   } else {
  	    try {
				libs.add(file.getAbsolutePath() + ":>" + GuardUtils.hash(file.toURI().toURL()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
  	   }
  	  }
  	  return libs;
  }
  
  public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(File file : files) {
	                if(file.isDirectory()) {
	                    deleteDirectory(file);
	                }
	                else {
	                	if (!file.getName().contains("launcher.config")) {
	                		file.delete();
	                	}
	                }
	            }
	        }
	    }
	    return(directory.delete());
  }
}