package com.oj.service;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.oj.util.CompileTask;
import com.oj.util.TestTask;
public class ThreadService {
	public static ExecutorService pool = Executors.newFixedThreadPool(20);

	/**
	 * 
	 * @param file          传入的编译文件
	 * @param exerciseType  编程题目的类型，分为开放性题目和标准型题目，标准型题目只有标准答案，开放性答案不限输入输出
	 * @param taskId        编程题目的编号
	 * @return              返回编译结果或测试结果
	 */
	
	public String CompileJavaInThread(File file, String exerciseType,String taskId) {
        StringBuilder sb=new StringBuilder();
        
		if (exerciseType.equals("Standard")) {			
			Callable<String> c = new CompileTask(file.getName());
			Future<String> f = pool.submit(c);
			//声明一个接口的变量（接口的引用）可以指向一个实现类（实现该接口的类）的实例，
			
			try {
				sb.append(f.get().toString());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Callable<String> c1 = new TestTask(taskId, file.getName());
		Future<String> f1 = pool.submit(c1);
		try {
			return sb.append(f1.get()).toString();
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "本地编译故障！！！";
	}

}
