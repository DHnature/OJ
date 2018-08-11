package com.oj.service;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.oj.util.Task;

public class ThreadService {
	public static ExecutorService pool = Executors.newFixedThreadPool(20);

	public String CompileJavaInThread(File file, String threadId) {
		Callable<String> c = new Task(file.getName(), threadId);
		Future f = pool.submit(c);
		try {
			return f.get().toString();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "本地编译故障！！！";
	}

	public String TestCaseInThread(File file, String threadId) {
		Callable<String> c = new Task(file.getName(), threadId);
		Future f = pool.submit(c);
		try {
			return f.get().toString();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "本地编译故障！！！";
	}
}
