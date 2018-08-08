package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ReflectUtil;

import net.sf.json.JSONObject;
import util.CompileUtil;

import util.ThreadUtil;

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
/*		 String javaCode=request.getParameter("javaCode");
	     String userId=request.getParameter("Id");
		 //在E://TestCode文件夹中新建一个.java文件存放接收到的代码			     
		 File file=util.FileUtil.creatFile(javaCode);			 
		 //从线程池中取出一个线程执行编译任务
		 Thread.sleep(5000); 
		 String s=new ThreadUtil().CompileJavaInThread(file,userId);
		 System.out.println("返回前端信息为:   "+s);*/
		 
	}	
	public void polling(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
		     String javaCode=request.getParameter("javaCode");
		     String userId=request.getParameter("Id");
	//	     ThreadUtil.hm.get(userId);
			 File file=util.FileUtil.creatFile(javaCode);			 
			 Thread.sleep(5000); 
			 String s=new ThreadUtil().CompileJavaInThread(file,userId);		
			 System.out.println("返回前端信息为:   "+s);
			 JSONObject json=new JSONObject();
			 json.put("result", s);				 
			 response.getWriter().write(json.toString());
	}
	
}
