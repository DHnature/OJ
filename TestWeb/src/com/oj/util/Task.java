package com.oj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class Task implements Callable<String>{
    String filename;
    String threadId;
	
public Task(String filename,String threadId) {
	// TODO Auto-generated constructor stub
	this.filename=filename;
	this.threadId=threadId;
}	
	
	@Override
	public String call() throws Exception {
		String temp;	
		StringBuilder sb=new StringBuilder();
		int totalMemory = (int)Runtime.getRuntime().totalMemory()/1024;//Java 虚拟机中的内存总量,以字节为单位
		long startTime=System.currentTimeMillis();
		try {	
			
			//执行javac命令
			Process p = new ProcessBuilder("javac", "e:TestCode//"+filename).start();	
			int freeMemory = (int)Runtime.getRuntime().freeMemory()/1024;//Java 虚拟机中的空闲内存量
			System.out.println("JVM内存占用：     "+(totalMemory-freeMemory ));
			System.out.println("javac返回值为     "+p.waitFor());
			if(p.waitFor()!=0) {
			  BufferedReader br=new BufferedReader(new InputStreamReader(p.getErrorStream(),"gbk"));
			    while((temp=br.readLine())!=null) {
			    	sb=sb.append(temp);
					System.out.println(temp);
				}
			    return sb.toString();
			}
			else {

			  BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
			    while((temp=br.readLine())!=null) {
					System.out.println(temp);
				}
			} 			
			//执行 java命令,这里需要先进入e盘
			ProcessBuilder pb=new ProcessBuilder("java", filename.replace(".java", ""));		
			pb.directory(new File("e:/TestCode/"));
			p=pb.start();
		    System.out.println("java返回值为    "+p.waitFor());
			if(p.waitFor()!=0) {
				  System.out.println("错误信息为:     ");
				  BufferedReader br2=new BufferedReader(new InputStreamReader(p.getErrorStream(),"gbk"));
				    while((temp=br2.readLine())!=null) {
				    	sb=sb.append(temp);
						System.out.println(temp);
					}
				}
				else {
				  System.out.println("输出结果为:     ");
				  BufferedReader br2=new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
				    while((temp=br2.readLine())!=null) {
				    	sb=sb.append(temp);
						System.out.println(temp);
					}
				    //进行测试
				    String result=TestCaseUtil.getTestCaseResult("sum", filename,sb.toString().replace(" ", ""));
	 			    sb.append("\n测试结果为:    "+result);
				    
				} 
			long endTime=System.currentTimeMillis();
			System.out.println("\n花费时间为:    "+(endTime-startTime)+"ms");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		return sb.toString();
	}

}
