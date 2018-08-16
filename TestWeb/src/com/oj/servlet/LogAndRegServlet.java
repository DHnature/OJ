package com.oj.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oj.util.JdbcUtil;
import com.oj.util.ReflectUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class LogAndReg
 */

public class LogAndRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogAndRegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");// 内容类型，解析为javascript代码或html代码
		response.setCharacterEncoding("utf-8");// 内容编码，防止出现中文乱码
		response.setHeader("Access-Control-Allow-Origin", "*");
		ReflectUtil.invokeMethod(this.getClass(), request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid=request.getParameter("sid");
		String sname=request.getParameter("sname");	
		JSONObject json=new JdbcUtil().login(sid, sname);;
		response.getWriter().write(json.toString());
	}
	
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sid=request.getParameter("sid");
		String sname=request.getParameter("sname");
		JSONObject json=new JdbcUtil().regisiter(sid, sname);
		response.getWriter().write(json.toString());
	}

}
