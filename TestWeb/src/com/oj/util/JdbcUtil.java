package com.oj.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;






public class JdbcUtil {

	public  JdbcUtil() {
       try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }	
	

	public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fluorite?characterEncoding=UTF-8", "root",
	                "admin");
	   }
	 
	public void insertText(SaveCommand textObj){
		String sql = "insert into commands values(null,?,?,?,?,?,?,?,?,?)";
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement(sql);) {  
	        s.setString(1,textObj.getSid());
	        s.setInt(2,textObj.getCid());
	        s.setString(3,textObj.get_type());
	        s.setString(4,textObj.getTimestamp1());
	        s.setString(5,textObj.getTimestamp2());
	        s.setString(6,textObj.get_data());
	        s.setString(7,textObj.getParams1());
	        s.setString(8,textObj.getParams2());
	        s.setString(9,textObj.getEnvironment());
	        
	        s.execute();
	        System.out.println("成功");
	      } catch (SQLException e) {
	           e.printStackTrace();
	      }
	}
	
	public  JSONObject  login(String sid,String sname){
		String sql = "select * from user where sid=? and sname=?";
		JSONObject json=new JSONObject();
		String result="";
		try(Connection conn = getConnection();PreparedStatement ps=conn.prepareStatement(sql)) {
			ps.setString(1, sid);
			ps.setString(2, sname);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				result="登录成功！！！";
				json.put("result", result);
				json.put("message",0);
			}
			else {
				result="登录失败！！！";
				json.put("result", result);
				json.put("message",1);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
		
	}
	
	public  JSONObject  regisiter(String sid,String sname){
		String sql = "insert into user values(null,?,?)";
		JSONObject json=new JSONObject();
		String result="";
		try(Connection conn = getConnection();PreparedStatement ps=conn.prepareStatement(sql)) {
			ps.setString(1, sid);
			ps.setString(2, sname);
			if(!ps.execute()) {
				result="注册成功！！！";
				json.put("result", result);
				json.put("message",0);
			}
			else {
				result="注册失败！！！";
				json.put("result", result);
				json.put("message",1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
