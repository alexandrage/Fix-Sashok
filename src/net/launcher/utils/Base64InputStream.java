package net.launcher.utils;

import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream extends InputStream
{
	public InputStream inputStream;
	public int[] buffer;
	public int bufferCounter = 0;
	public boolean eof = false;
	public static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	public static char pad = '=';
	
	public Base64InputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}
	
	public int read() throws IOException
	{
		if (buffer == null || bufferCounter == buffer.length)
		{
			if(eof) return -1;
			acquire();
			if (buffer.length == 0)
			{
				buffer = null;
				return -1;
			}
			bufferCounter = 0;
		}
		return buffer[bufferCounter++];
	}

	public void acquire() throws IOException
	{
		char[] four = new char[4];
		int i = 0;
		do
		{
			int b = inputStream.read();
			if (b == -1)
			{
				if (i != 0) throw new IOException("Bad base64 stream");
				else
				{
					buffer = new int[0];
					eof = true;
					return;
				}
			}
			char c = (char) b;
			if (chars.indexOf(c) != -1 || c == pad) four[i++] = c;
			else if(c != '\r' && c != '\n') throw new IOException("Bad base64 stream");
		} while (i < 4);
		boolean padded = false;
		for (i = 0; i < 4; i++)
		{
			if (four[i] != pad)
			{
				if(padded) throw new IOException("Bad base64 stream");
			} else
			{
				if(!padded) padded = true;
			}
		}
		int l;
		if (four[3] == pad)
		{
			if (inputStream.read() != -1) throw new IOException("Bad base64 stream");
			eof = true;
			if (four[2] == pad) l = 1;
			else l = 2;
		} else l = 3;
		int aux = 0;
		for (i = 0; i < 4; i++)
		{
			if (four[i] != pad)
			{
				aux = aux | (chars.indexOf(four[i]) << (6 * (3 - i)));
			}
		}
		buffer = new int[l];
		for (i = 0; i < l; i++) buffer[i] = (aux >>> (8 * (2 - i))) & 0xFF;
	}

	public void close() throws IOException
	{
		inputStream.close();
	}
}