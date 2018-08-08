package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileUtil {

	public static File creatFile(String javaCode) {
		//存放的文件命名为(test+时刻).java
		//File file=new File("E://TestCode//test"+System.currentTimeMillis()+".java");
		File file=new File("E://TestCode//Test.java");
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
