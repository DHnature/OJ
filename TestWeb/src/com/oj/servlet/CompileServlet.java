package com.oj.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oj.service.FileService;
import com.oj.service.ThreadService;

import com.oj.util.ReflectUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class compileServlet
 */

public class CompileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CompileServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");// 内容类型，解析为javascript代码或html代码
		response.setCharacterEncoding("utf-8");// 内容编码，防止出现中文乱码
		response.setHeader("Access-Control-Allow-Origin", "*");
		ReflectUtil.invokeMethod(this.getClass(), request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void receive(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {
		JSONObject json = new JSONObject();
		if(request.getParameter("Code").equals(null)) {
			json.put("result", "提交的代码为空！！！");
		}
		else {
			json.put("result", "提交成功");
			polling(request, response);	
		}
			
		response.getWriter().write(json.toString());
	}

	public void polling(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {
		String javaCode = request.getParameter("Code");
		String taskId = request.getParameter("taskId");
		String exerciseType = request.getParameter("exerciseType");
		System.out.println("Code为: "+javaCode);
		FileService fileService = new FileService();
		File file = fileService.creatFile(javaCode);
		JSONObject json = new JSONObject();
		if(file==null) {
			json.put("result", "java代码不符合规范！！！");
			response.getWriter().write(json.toString());
		}
		
		else {
			ThreadService threadService = new ThreadService();
			String s = threadService.CompileJavaInThread(file, exerciseType, taskId);
			System.out.println("返回前端信息为:   " + s);			
			json.put("result", s);
			response.getWriter().write(json.toString());
		}

	}

}
