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
	 * @param exerciseType  当前题目的类型，分为开放性题目和标准型题目，标准型题目只有标准答案，开放性答案不限输入输出
	 * @return              返回编译结果或测试结果
	 */
	public String CompileJavaInThread(File file, String exerciseType) {

		if (exerciseType == "Standard") {
			Callable<String> c = new CompileTask(file.getName());
			Future f = pool.submit(c);
		}

		Callable<String> c1 = new TestTask("sum", file.getName());
		Future f2 = pool.submit(c1);
		try {
			return f2.get().toString();
			// return f.get().toString()+"/n测试结果为:"+f2.get().toString();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "本地编译故障！！！";
	}

}
