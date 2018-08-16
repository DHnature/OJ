/**
 * 
 */
$(document).ready(function(){
	loadLoginBar();
});

function loadLoginBar(){
	var username=getCookie("sname");
	if(username==null||typeof(username)=="undefined"||username==0){
		$("#loginBar").replaceWith(
				"<nav id='login' >" +
				"<img src='img/default_profile_photo1.png' class='profile_photo'/>"+
				"<a href='http://localhost:8080/OJ/login.html' style='text-decoration:none;'>登录</a>" +
				"</nav>"); 
	}
	else{
		$("#loginBar").replaceWith("<img src='img/default_profile_photo2.png' class='profile_photo'/>" +
				                   "<nav id='login'><a style='text-decoration:none;color:red;'>"+"用户名:    "+username+"</a>|" +
				                   "<a id='exit' style='cursor:pointer;'>退出</a></nav>"); 
		$("#exit").click(function(){
			var x;
			var r=confirm("是否退出登录？");
			if (r==true){
				removeCookie("sname");
				location.reload();
			}
			else{
			}
		
		});
		
		
	}
	
	
	
	
	
	
}
