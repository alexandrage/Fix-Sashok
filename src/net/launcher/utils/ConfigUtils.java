package net.launcher.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ConfigUtils
{
	private File out;
	private Boolean cached = false;
	private String filename = null;
	private HashMap<String,String> cache;
	private InputStream input = null;

	public ConfigUtils(String filename, String out)
	{
		this.filename = filename;
		this.out = new File(out);
	}

	public ConfigUtils(String filename, File out)
	{
		this.filename = filename;
		this.out = out;
	}
	
	public ConfigUtils(InputStream input, File out)
	{
		this.input = input;
		this.out = out;
	}
	
	public ConfigUtils(File out) throws FileNotFoundException
	{
		if (!out.exists())
			throw new FileNotFoundException("-");
		this.out = out;
	}
	
	public Boolean isCached()
	{
		return cached;
	}

	public void setCached(Boolean cached)
	{
		this.cached = cached;
		if(cached = false)
			cache = null;
	}
	
	private void create(String filename)
	{
		InputStream input = getClass().getResourceAsStream(filename);
		if (input != null)
		{
			FileOutputStream output = null;
			try
			{
				out.getParentFile().mkdirs();
				output = new FileOutputStream(out);
				byte[] buf = new byte[8192];
				int length;
				while ((length = input.read(buf)) > 0)
				{
					output.write(buf, 0, length);
				}
			} catch (Exception e) {} finally
			{
				try
				{
					input.close();
				} catch (Exception ignored){}
				try
				{
					if (output != null)
						output.close();
				}
				catch (Exception ignored){}
			}
		}
	}
	
	private void create(InputStream input)
	{
		if (input != null)
		{
			FileOutputStream output = null;
			try
			{
				output = new FileOutputStream(out);
				byte[] buf = new byte[8192];
				int length;
				while ((length = input.read(buf)) > 0)
				{
					output.write(buf, 0, length);
				}
			} catch (Exception e) {} finally
			{
				try
				{
					input.close();
				} catch (Exception ignored){}
				try
				{
					if (output != null)
						output.close();
				} catch (Exception ignored){}
			}
		}
	}
	
	private HashMap<String, String> loadHashMap()
	{
		HashMap<String,String> result = new HashMap<String,String>();
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(out));
			String line;
			while((line = br.readLine()) != null)
			{
				if ((line.isEmpty()) || (line.startsWith("#")) || (!line.contains(": "))) continue;
				String[] args = line.split(": ");
				if (args.length < 2)
				{
					result.put(args[0], null);
					continue;
				}
				result.put(args[0], args[1]);
			}
		} catch (IOException ex) {}
		finally
		{ try
		{
			br.close();
		} catch (Exception e) {}}
		return result;
	}
	
	public void load()
	{
		if (filename != null && !out.exists()) create(filename);
		if (input != null && !out.exists()) create(input);
		if (cached) cache = this.loadHashMap();
	}
	
	public String getPropertyString(String property)
	{
		try
		{
			if (cached) return cache.get(property);
			else
			{
				HashMap<String,String> contents = loadHashMap();
				return contents.get(property);
			}
		} catch (Exception e) {}
		return null;
	}
	
	public Integer getPropertyInteger(String property)
	{
		try
		{
			if (this.cached) return Integer.parseInt(cache.get(property));
			else
			{
				HashMap<String,String> contents = loadHashMap();
				return Integer.parseInt(contents.get(property));
			}
		} catch (Exception e) {}
		return null;
	}
	
	public Boolean getPropertyBoolean(String property)
	{
		try
		{
			String result;
			if (this.cached) result = this.cache.get(property);
			else
			{
				HashMap<String,String> contents = this.loadHashMap();
				result = contents.get(property);
			}
			if (result != null && result.equalsIgnoreCase("true")) return true;
			else return false;
		} catch (Exception e){}
		return null;
	}
	
	public Double getPropertyDouble(String property)
	{
		try {
			String result;
			if (cached) result = cache.get(property);
			else
			{
				HashMap<String,String> contents = this.loadHashMap();
				result = contents.get(property);
			}
			if (!result.contains("")) result += ".0";
			return Double.parseDouble(result);
		} catch (Exception e) {}
		return null;
	}
	
	public Boolean checkProperty(String property)
	{
		String check;
		try
		{
			if (cached)
				check = cache.get(property);
			else
			{
				HashMap<String,String> contents = this.loadHashMap();
				check = contents.get(property);
			}
			if(check != null) return true;
		} catch (Exception e)
		{
			return false;
		}
		
		return false;
	}
	
	private void flush(HashMap<Integer,String> newContents)
	{
		try
		{
			this.delFile(out);
			out.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(out));
			for (int i = 1; i <= newContents.size(); i ++)
			{
				String line = newContents.get(i);
				if (line == null || line.split(": ").length==1)
				{
					writer.append("");
					continue; 
				}
				writer.append(line);
				writer.append("\n");
			}
			writer.flush();
			writer.close();
			if (cached) load();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void delFile(File file)
	{
		if (file.exists()) file.delete();
	}
	
	private HashMap<Integer,String> getAllFileContents()
	{
		HashMap<Integer,String> result = new HashMap<Integer,String>();
		BufferedReader br = null;
		Integer i = 1;
		try
		{
			br = new BufferedReader(new FileReader(out));
			String line;
			
			while((line = br.readLine()) != null)
			{
				if (line.isEmpty())
				{
					result.put(i, null);
					i ++;
					continue;
				}
				
				result.put(i, line);
				i ++;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		} finally {try
		{
			br.close();
		} catch(Exception e){}}
		
		return result;
	}
	
	public void insertComment(String comment)
	{
		HashMap<Integer,String> contents = this.getAllFileContents();
		contents.put(contents.size() + 1, "#" + comment);
		flush(contents);
	}
	
	public void insertComment(String comment, Integer line)
	{
		HashMap<Integer,String> contents = getAllFileContents();
		if (line >= contents.size() + 1) return;
		HashMap<Integer,String> newContents = new HashMap<Integer,String>();
		for (int i = 1; i < line; i ++) newContents.put(i, contents.get(i));
		newContents.put(line, "#" + comment);
		for (int i = line; i <= contents.size(); i ++) newContents.put(i + 1, contents.get(i));
		flush(newContents);
	}
	
	public void put(String property, Object obj)
	{
		HashMap<Integer,String> contents = this.getAllFileContents();
		contents.put(contents.size() + 1, property + ": " + obj.toString());
		flush(contents);
	}
	
	public void put(String property, Object obj, Integer line)
	{
		HashMap<Integer,String> contents = this.getAllFileContents();
		if (line >= contents.size() + 1) return;
		HashMap<Integer,String> newContents = new HashMap<Integer,String>();
		for (int i = 1; i < line; i ++) newContents.put(i, contents.get(i));
		newContents.put(line, property + ": " + obj.toString());
		for (int i = line; i <= contents.size(); i ++) newContents.put(i + 1, contents.get(i));
		flush(newContents);
	}
	
	public void changeProperty(String property, Object obj)
	{
		HashMap<Integer,String> contents = this.getAllFileContents();
		if ((contents == null)) return;
		for (int i = 1; i <= contents.size(); i ++)
		{
			if (contents.get(i) == null) continue;
			String check = contents.get(i);
			if (check.startsWith(property))
			{
				check = check.replace(property, "");
				if (!(check.startsWith(": "))) continue;
				contents.remove(i);
				contents.put(i, property + ": " + obj.toString());
			}
		}
		this.flush(contents);
	}

	public Integer getLineCount()
	{
		HashMap<Integer,String> contents = getAllFileContents();
		return contents.size();
	}
}