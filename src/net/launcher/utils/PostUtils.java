package net.launcher.utils;


import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.io.OutputStream;
import java.io.FileInputStream;

public class PostUtils
{
	private static Random random = new Random();
	private URLConnection connection;
	private OutputStream os = null;
	private String boundary = randomString() + randomString() + randomString();

	private void connect() throws IOException
	{
		if (os == null) os = connection.getOutputStream();
	}

	private void write(char c) throws IOException
	{
		connect();
		os.write(c);
	}

	private void write(String s) throws IOException
	{
		connect();
		os.write(s.getBytes());
	}

	private void newline() throws IOException
	{
		connect();
		write("\r\n");
	}

	private void writeln(String s) throws IOException
	{
		connect();
		write(s);
		newline();
	}

	static String randomString()
	{
		return Long.toString(random.nextLong(), 36);
	}

	private void boundary() throws IOException
	{
		write("--");
		write(boundary);
	}

	private PostUtils(URLConnection connection) throws IOException
	{
		this.connection = connection;
		connection.setDoOutput(true);
		connection.setRequestProperty("User-Agent", "Launcher/64.0");
		connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	}

	private PostUtils(URL url) throws IOException
	{
		this(url.openConnection());
	}

	private void writeName(String name) throws IOException
	{
		newline();
		write("Content-Disposition: form-data; name=\"");
		write(name);
		write('"');
	}

	private void setParameter(String name, String value) throws IOException
	{
		boundary();
		writeName(name);
		newline();
		newline();
		writeln(value);
	}

	private static void pipe(InputStream in, OutputStream out) throws IOException
	{
		byte[] buf = new byte[500000];
		int nread;
		synchronized (in)
		{
			while ((nread = in.read(buf, 0, buf.length)) >= 0) out.write(buf, 0, nread);
		}
		out.flush();
		buf = null;
	}

	private void setParameter(String name, String filename, InputStream is) throws IOException
	{
		boundary();
		writeName(name);
		write("; filename=\"");
		write(filename);
		write('"');
		newline();
		write("Content-Type: ");
		String type = URLConnection.guessContentTypeFromName(filename);
		if (type == null) type = "application/octet-stream";
		writeln(type);
		newline();
		pipe(is, os);
		newline();
	}

	private void setParameter(String name, File file) throws IOException
	{
		setParameter(name, file.getPath(), new FileInputStream(file));
	}

	private void setParameter(String name, Object object) throws IOException
	{
		if(object instanceof File) setParameter(name, (File) object);
		else setParameter(name, object.toString());
	}

	private void setParameters(Object[] parameters) throws IOException
	{
		if (parameters == null) return;
		for(int i = 0; i < parameters.length - 1; i += 2) setParameter(parameters[i].toString(), parameters[i + 1]);
	}

	private InputStream post() throws IOException
	{
		boundary();
		writeln("--");
		os.close();
		return connection.getInputStream();
	}

	public InputStream post(Object[] parameters) throws IOException
	{
		setParameters(parameters);
		return post();
	}

	public static InputStream post(URL url, Object[] parameters) throws IOException
	{
		return new PostUtils(url).post(parameters);
	}
}