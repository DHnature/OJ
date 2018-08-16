package com.oj.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oj.util.JdbcUtil;
import com.oj.util.SaveCommand;

import net.sf.json.JSONObject;

public class SaveInfoFromWebServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		int cid = Integer.parseInt(request.getParameter("id_"));
		String time = request.getParameter("time");
		String name = request.getParameter("name_");
		String content = request.getParameter("content_");
		String environment = request.getParameter("environment");
		String sid = request.getParameter("sid");
		

		SaveCommand save = new SaveCommand();

		save.setSid(sid);
		save.setEnvironment(environment);
		save.setCid(cid);
		save.set_type(name);
		save.setTimestamp1(time);
		save.setTimestamp2(null);
		save.set_data(content);
		save.setParams1(null);
		save.setParams2(null);
		new JdbcUtil().insertText(save);

		JSONObject json = new JSONObject();
		json.put("message", "收到");
		response.getWriter().write(json.toString());
	}
}
