package com.viepak.halalfood.SystemProperty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SysProperties {
	private static String propFileName = "/resource/app.config.txt";
	private static SysProperties sysProperties = null;
	
	private SysProperties(){
		FileInputStream propFile = null;
		try {
			propFile = new FileInputStream(propFileName);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	        Properties p =
	            new Properties(System.getProperties());
	        try {
				p.load(propFile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

	}
	
	private static void GetInstance(){
		if(sysProperties == null){
			sysProperties = new SysProperties();
		}
	}

	public static String getAppConfig(String configName) {
		GetInstance();
		return System.getProperty(configName);
	}
	
}
