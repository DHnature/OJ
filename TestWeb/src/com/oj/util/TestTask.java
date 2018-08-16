package com.oj.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class TestTask implements Callable<String> {
	String taskId;
	String filename;

	public TestTask(String taskId, String filename) {
		this.taskId = taskId;
		this.filename = filename;
	}

	@Override
	public String call() throws Exception {

		return getTestCaseResult(taskId, filename);
	}

	/**
	 * 
	 * @param taskId
	 *            任务Id
	 * @param filename
	 *            要编译的java文件的文件名
	 * @return 返回获取测试用例的结果
	 */
	public static String getTestCaseResult(String taskId, String filename) {

		Process p;
		HashMap<ArrayList<String>, ArrayList<String>> testCaseHm = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		int totalTestCase = 1;
		int correctNum = 0;
		// 测试数据格式为{(key1):(value1)} {(key2):(value2)} {(key3):(value3)}
		// 例如 {(100,200):300} {(300,200):500} {(323,412):735} (555,333):888}

		try {

			ProcessBuilder pb = new ProcessBuilder("javac", filename);
			pb.directory(new File("e:/TestCode/"));

			p = pb.start();
			// javac操作 *************************************************
			try {
				String temp;
				if (p.waitFor() != 0) {
					BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream(), "gbk"));
					while ((temp = br.readLine()) != null) {

						sb = sb.append(temp);
						System.out.println(temp);
						return temp;
					}
					br.close();

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 获取测试数据
			testCaseHm = getTestData(taskId);
			totalTestCase = testCaseHm.size();

			for (ArrayList<String> a : testCaseHm.keySet()) {
					System.out.println("Key: " + a);
					System.out.println("Value: " + testCaseHm.get(a));
				

			}

			for (ArrayList<String> input : testCaseHm.keySet()) {
				pb.command("java", filename.replace(".java", ""));
				p = pb.start();

				// 这里重定向的作用？？？
				/*
				 * pb.redirectOutput(Redirect.PIPE); pb.redirectInput(Redirect.PIPE);
				 * pb.redirectError(Redirect.PIPE);
				 */
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
				// 输入测试用例数据
				for (String s : input) {
					writer.write(s);
					writer.write('\n');
				}
				writer.flush();
				// 将测试结果与标准结果进行比较
				String temp1;
				StringBuilder testResult = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));

				while ((temp1 = br.readLine()) != null) {
					testResult.append(temp1);
				}
				if (testResult.toString().replace(" ", "").equals(testCaseHm.get(input).get(0))) {
					System.out.println("测试结果:   " + testResult);
					System.out.println("标准结果          " + testCaseHm.get(input).get(0));
					correctNum++;
				}
				
				 writer.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "\n测试用例数量为:  " + totalTestCase + "\n通过的测试用例个数为:  " + correctNum + "\n正确率为:  "
				+ ((double) correctNum / (double) totalTestCase) * 100 + "%";
	}

	/**
	 * 
	 * @param taskId  任务Id
	 *          
	 * @return 返回测试用例中的数据，并将测试数据封装为HashMap
	 */
	public static HashMap<ArrayList<String>, ArrayList<String>> getTestData(String taskId) {
		HashMap<ArrayList<String>, ArrayList<String>> hm = new HashMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(new File("e:/TestCode/TestCase/" + taskId + ".txt")));) {		
			String temp;
			while ((temp = br.readLine()) != null) {
				String arr[] = temp.split("}");
				for (String s : arr) {
					System.out.println("s为" + s);
					hm.put(getTestIO(s.split(":")[0]), getTestIO(s.split(":")[1]));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hm;
	}

	/**
	 * 
	 * @param s
	 *            解析字符串并获取测试数据的输入和输出，
	 * @return
	 */
	public static ArrayList<String> getTestIO(String s) {
		s = s.replace("}", "").replace("{", "");
		String temp[] = s.replace("(", "").replace(")", "").split(",");
		ArrayList<String> list = new ArrayList<String>();
		for (String s1 : temp) {
			list.add(s1);
		}
		return list;
	}

}
