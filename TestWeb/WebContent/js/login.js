ip = "http://localhost:8080/";
$("#sname").val("");
$("#sid").val("");

alert(getCookie("sname"))
alert(getCookie("sid"))


//登录
function check_login()
	{
	 var sname=$("#sname").val();
	 var sid=$("#sid").val();
	 if(sname=="admin" && sid=="123")
	 {
	  alert("登录成功！");
	  setCookie("sid",sid,0);
	  setCookie("sname",sname,0);
	  window.location.href=ip+"/OJ/PostCode.html"; 
	 }
	 else{
		 var url=ip+"/OJ/LogAndReg?method=login";
		 var json={"sname":sname,"sid":sid};
		 $.ajax({
				type :'post',
				url : url,
				data : json,
				dataType : 'json',
				success : function(json) {
					alert("登录情况:    " + json.result);	
					if(json.message=="0"){
						setCookie("sid",sid,1);
						setCookie("sname",sname,1);
						window.location.href=ip+"/OJ/PostCode.html"; 
					}
					else{
						$("#sname").val("");
						$("#sid").val("");
						$("#login_form").removeClass('shake_effect');  
						setTimeout(function()
						{
						  $("#login_form").addClass('shake_effect')
						},1);
					}

				},
				error : function(json) {
					alert("登录情况:    " + json.result);
					$("#login_form").removeClass('shake_effect');  
					setTimeout(function()
					{
					  $("#login_form").addClass('shake_effect')
					},1); 
				}
			});
	 }

	}



//注册

	function check_register(){
		var sname = $("#r_sname").val();
		var sid = $("#r_sid").val();
		var url=ip+"/OJ/LogAndReg?method=register"
		var json={"sname":sname,"sid":sid};
		if(sname!="" && sid!="")
		 {
			 $.ajax({
					type :'post',
					url : url,
					data : json,
					dataType : 'json',
					success : function(json) {
						alert("注册情况:    " + json.result);	
						if(json.message==0){
							window.location.href=ip+"/OJ/login.html"; 
						}
						else{
							$("#login_form").removeClass('shake_effect');  
							setTimeout(function()
							{
							  $("#login_form").addClass('shake_effect')
							},1);
						}
						
					},
					error : function(json) {
						alert("注册情况:    " + json.result);
						$("#login_form").removeClass('shake_effect');  
						setTimeout(function()
						{
						  $("#login_form").addClass('shake_effect')
						},1); 
					}
				});

		 }
		 else
		 { 
		  alert("输入值不能为空！！！");
		  $("#login_form").removeClass('shake_effect');  
		  setTimeout(function()
		  {
		   $("#login_form").addClass('shake_effect')
		  },1);  
		 }
	}
	
	
	
	
	$(function(){
		$("#create").click(function(){
			check_register();
			return false;
		})
		$("#login").click(function(){
			check_login();
			return false;
		})
		$('.message a').click(function () {
			$('form').animate({
				height: 'toggle',
				opacity: 'toggle'
			}, 'slow');
		});
	})


