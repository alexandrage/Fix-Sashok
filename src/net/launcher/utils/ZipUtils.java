package net.launcher.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils
{
	public static void unzip(String path, String file)
	{
		try {
		Vector<ZipEntry> zipentry = new Vector<ZipEntry>();
		ZipFile zipfile = new ZipFile(file);
		Enumeration<?> en = zipfile.entries();
	
		while(en.hasMoreElements()) zipentry.addElement((ZipEntry)en.nextElement());
	
		for (int i = 0; i < zipentry.size(); i++)
		{
			ZipEntry ze = (ZipEntry)zipentry.elementAt(i);
			extractFromZip(file, path, ze.getName(), zipfile, ze);
		}
	
		zipfile.close();
		new File(file).delete();
		}
		catch(Exception ex)
		{
			BaseUtils.send(ex.toString());
		}
	}

	static void extractFromZip(String szZipFilePath, String szExtractPath, String szName, ZipFile zf, ZipEntry ze) throws Exception
	{
		if(ze.isDirectory()) return;
	
		String szDstName = slash2sep(szName);
		String szEntryDir;
	
		if(szDstName.lastIndexOf(File.separator) != -1) szEntryDir = szDstName.substring(0, szDstName.lastIndexOf(File.separator));
		else szEntryDir = "";
		File newDir = new File(szExtractPath + File.separator + szEntryDir);
	
		newDir.mkdirs();	 
	
		FileOutputStream fos = 
		new FileOutputStream(szExtractPath +
		File.separator + szDstName);
	
		InputStream is = zf.getInputStream(ze);
		byte[] buf = new byte[1024];
	
		int nLength;
	
		while(true)
		{
			nLength = is.read(buf);
			if(nLength < 0) 
			break;
			fos.write(buf, 0, nLength);
		}

		is.close();
		fos.close();
	}
	
	static String slash2sep(String src)
	{
		int i;
		char[] chDst = new char[src.length()];
		String dst;
	
		for(i = 0; i < src.length(); i++)
		{
			if(src.charAt(i) == '/') chDst[i] = File.separatorChar;
			else chDst[i] = src.charAt(i);
		}
		dst = new String(chDst);
		return dst;
	}
}
