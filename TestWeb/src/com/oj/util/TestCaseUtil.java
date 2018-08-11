package com.oj.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.ezmorph.test.ArrayAssertions;

public class TestCaseUtil {

	public static String getTestCaseResult(String type, String filename, String result) {
		Process p;
		HashMap<ArrayList<String>, ArrayList<String>> hm = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		int correctRate=0;
		int totalTestCase=1;
		int correctNum = 0;	
		// 通过e:/TestCode/type.txt的内容来获取测试数据，用HashMap存储
		// 测试数据格式为 (key1):(value1), (key2):(value2), (key3):(value3)
		// 例如 (100,200):(300),(100,300):(400),(200,200):(400)
		try {
			ProcessBuilder pb = new ProcessBuilder("java", filename.replace(".java", ""));
			pb.directory(new File("e:/TestCode/TestCase/"));
			pb.redirectOutput(Redirect.PIPE);
			pb.redirectInput(Redirect.PIPE);
			pb.redirectError(Redirect.PIPE);
			p = pb.start();
			hm = getTestData(type);
			totalTestCase=hm.size();
			for (ArrayList<String> input : hm.keySet()) {
				// 输入测试用例数据,并和标准答案进行比较
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
				for (String s : input) {
					writer.write(s);
					writer.write('\n');
				}
				//writer.close();
				// 将测试结果与标准结果进行比较
				String temp1;
				StringBuilder testResult=new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
				while ((temp1 = br.readLine()) != null) {
					System.out.println(temp1);
					testResult.append(temp1);
				}
                if(testResult.toString().replace(" ","").equals(hm.get(input).get(0))) {
                	System.out.println("testResult"+testResult);
                	System.out.println("标准结果"+hm.get(input).get(1));
                	correctNum++;
                }
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "\n测试用例数量为:  "+totalTestCase+"\n通过的测试用例个数为:  "+correctNum+"\n正确率为:  "+correctRate;
	}

	// 获取txt中的测试数据，将测试数据类型设定为HashMap
	public static HashMap<ArrayList<String>, ArrayList<String>> getTestData(String type) {
		// HashMap<Object, Object> hm=new HashMap<>();
		HashMap<ArrayList<String>, ArrayList<String>> hm = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("e:/TestCode/TestCase/" + type+".txt")));
			String temp;
			while ((temp = br.readLine()) != null) {
				String arr[] = temp.split(",");
				for (String s : arr) {
					hm.put(getTestIO(s.split(":")[0]), getTestIO(s.split(":")[0]));
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

	public static ArrayList<String> getTestIO(String s) {

		String temp[] = s.replace("(", "").replace(")", "").split(",");
		ArrayList<String> list = new ArrayList<String>();
		for (String s1 : temp) {
			list.add(s1);
		}

		return list;
	}

}
