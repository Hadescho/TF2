package org.elsys.TF2Checker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	public static String errorFilePath = System.getProperty("user.home") + "\\TF2Checker\\TF2ErrorLog.txt" ; 
	private static FileWriter fileOpen(String path) throws IOException{
		File f = new File(path);
		if((f.exists()) && (f.canWrite()))
		{
			 return new FileWriter(f,true);
		}
		else
		{
			f.createNewFile();
			return new FileWriter(f);
		}	
		
	}
	public static void errorLog(String msg) throws IOException{
		FileWriter errorWriter = fileOpen(errorFilePath);
		errorWriter.append((new Date()).toString() + msg);
		errorWriter.close();
	}
}
