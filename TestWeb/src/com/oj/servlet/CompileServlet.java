package com.oj.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oj.service.FileService;
import com.oj.util.CompileUtil;
import com.oj.util.ReflectUtil;
import com.oj.util.ThreadUtil;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");//内容类型，解析为javascript代码或html代码
		response.setCharacterEncoding("utf-8");//内容编码，防止出现中文乱码
		response.setHeader("Access-Control-Allow-Origin", "*");	
		ReflectUtil.invokeMethod(this.getClass(), request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
   
	public void receive(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {	 	       
		 JSONObject json=new JSONObject();
		 json.put("result", "提交成功");	
		 response.getWriter().write(json.toString());		 	    
	}	
	
	
	
	
	public void polling(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
		     String javaCode=request.getParameter("Code");
		     String userId=request.getParameter("Id");
		     System.out.println("javaCode   "+request.getParameter("Code"));
		     FileService fileService=new FileService();
			 File file=fileService.creatFile(javaCode);			 
			 String s=new ThreadUtil().CompileJavaInThread(file,userId);		
			 System.out.println("返回前端信息为:   "+s);
			 JSONObject json=new JSONObject();
			 json.put("result", s);				 
			 response.getWriter().write(json.toString());
	}
	
}
