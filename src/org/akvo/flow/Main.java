package org.akvo.flow;

import java.io.File;
import java.io.IOException;

import org.akvo.flow.process.CommandProcess;
import org.akvo.flow.telnet.AndroidEmulatorTelnet;

public class Main
{

	public static void main(String[] args) throws IOException, InterruptedException
	{
		if (args.length != 1)
		{
			throw new IllegalArgumentException("Expecting an argument leading to adb on local machine");
		}
		//Get adb from runtime arg
		File adb = new File(args[0]);

		//Connect to localhost:5554 with  Telnet
		try (AndroidEmulatorTelnet telnet = new AndroidEmulatorTelnet("localhost"))
		{
			System.out.println("Path of auth file: " + telnet.getAuthPath());
			//Send command: adb push <auth_file> /sdcard/
			try (CommandProcess process = new CommandProcess(adb.toString(), "push", telnet.getAuthPath(), "/sdcard/auth.txt"))
			{
				//View output
				System.out.println(process.blockingReadString());
			}
		}
	}

}
