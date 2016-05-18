package net.launcher.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


public class EncodingUtils
{
	public static String encode(String str) throws RuntimeException
	{
		byte[] bytes = str.getBytes();
		byte[] encoded = encode(bytes);
		try
		{
			return new String(encoded, "ASCII");
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("ASCII is not supported!", e);
		}
	}
	
	public static String decode(String str) throws RuntimeException
	{
		try
		{
			byte[] decoded = decode(str.getBytes("ASCII"));
			return new String(decoded);
		} catch (Exception e)
		{
			return BaseUtils.empty;
		}
	}

	private static byte[] encode(byte[] bytes) throws RuntimeException
	{
		return encode(bytes, 0);
	}

	private static byte[] encode(byte[] bytes, int wrapAt) throws RuntimeException
	{
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try
		{
			encode(inputStream, outputStream, wrapAt);
		} catch (IOException e)
		{
			throw new RuntimeException("Unexpected I/O error", e);
		} finally
		{
			try
			{
				inputStream.close();
			} catch (Throwable t) {}
			try
			{
				outputStream.close();
			} catch (Throwable t) {}
		}
		return outputStream.toByteArray();
	}

	private static byte[] decode(byte[] bytes) throws RuntimeException
	{
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try
		{
			decode(inputStream, outputStream);
		} catch (IOException e)
		{
			throw new RuntimeException("Unexpected I/O error", e);
		} finally
		{
			try
			{
				inputStream.close();
			} catch (Throwable t) {}
		}
		return outputStream.toByteArray();
	}

	private static void encode(InputStream inputStream, OutputStream outputStream, int wrapAt) throws IOException
	{
		Base64OutputStream aux = new Base64OutputStream(outputStream, wrapAt);
		copy(inputStream, aux);
		aux.commit();
	}

	private static void decode(InputStream inputStream, OutputStream outputStream) throws IOException
	{
		copy(new Base64InputStream(inputStream), outputStream);
	}

	private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException
	{
		byte[] b = new byte[1024];
		int len;
		while ((len = inputStream.read(b)) != -1)
		{
			outputStream.write(b, 0, len);
		}
	}
	
	public static String xorencode(String text, String key)
	{
		String res = ""; int j = 0;
		for (int i = 0; i < text.length(); i++)
		{
			res += (char)(text.charAt(i) ^ key.charAt(j));
			j++; if(j==key.length()) j = 0;
		}
		return res;
	}
	
	public static String strtoint(String text)
	{
		String res = "";
		for (int i = 0; i < text.length(); i++) res += (int)text.charAt(i) + "-";
		res = res.substring(0, res.length() - 1);
		return res;
	}
	
	public static String inttostr(String text)
	{
		String res = "";
		for(int i = 0; i < text.split("-").length; i++) res += (char)Integer.parseInt(text.split("-")[i]);
		return res;
	}
}