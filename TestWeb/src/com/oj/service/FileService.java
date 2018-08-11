package com.oj.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileService {

	public  File creatFile(String javaCode) {
		//根据类名来命名java文件
	    String fileName=javaCode.substring(javaCode.indexOf("class"), javaCode.indexOf("{"));
	    fileName=fileName.replace("class", "").replace(" ", "");
	    System.out.println(fileName);
		File file=new File("E://TestCode//"+fileName+".java");
		try(PrintWriter pw=new PrintWriter(file);) {
			pw.write(javaCode);
			pw.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("写入文件出错！！！");
			e.printStackTrace();
		}
		
		return file;
	}
	
	
	
}
