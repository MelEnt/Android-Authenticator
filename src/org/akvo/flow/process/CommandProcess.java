package org.akvo.flow.process;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CommandProcess implements Closeable
{

	private Process process;
	private InputStream inputStream;
	private OutputStream outputStream;

	public CommandProcess(String... args) throws IOException
	{
		process = Runtime.getRuntime().exec(args);
		inputStream = new BufferedInputStream(process.getInputStream());
		outputStream = new BufferedOutputStream(process.getOutputStream());
	}

	public void writeString(Object input) throws IOException
	{
		outputStream.write(String.valueOf(input).getBytes());
		outputStream.flush();
	}

	public String readString() throws IOException
	{
		if (inputStream.available() == 0)
		{
			return null;
		}
		StringBuilder builder = new StringBuilder();
		while (inputStream.available() > 0)
		{
			builder.append((char) inputStream.read());
		}

		return builder.toString();
	}

	public String blockingReadString() throws IOException
	{
		StringBuilder builder = new StringBuilder();
		while (true)
		{
			while (inputStream.available() > 0)
			{
				builder.append((char) inputStream.read());
			}
			if (builder.length() > 0)
			{
				break;
			}
		}

		return builder.toString();
	}

	@Override
	public void close() throws IOException
	{
		process.destroy();
	}

}
