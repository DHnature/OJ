	/**
	 * 
	 */
	function setCookie(name, value, iDay) 
	{   
	    var oDate=new Date();
	    var ms=iDay*24*60*60*1000;
	    oDate.setDate(oDate.getDate()+ms);
	    document.cookie=name+'='+encodeURIComponent(value)+';expires='+oDate+";path=/";
	    
	}
	 
	function getCookie(name) 
	{ 
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 
	    if(arr=document.cookie.match(reg))
	 
	        return decodeURIComponent(arr[2]); 
	    else 
	        return null; 
	} 

	function removeCookie(name)
	{
	    setCookie(name, '', -1);
	}
	
	function removeAllCookies(){
		var strCookie=document.cookie;
		  var arrCookie=strCookie.split("; "); // 将多cookie切割为多个名/值对
		  for(var i=0;i <arrCookie.length;i++)
		{ // 遍历cookie数组，处理每个cookie对
		    var arr=arrCookie[i].split("=");
		    
		    if(arr.length>0)
		     removeCookie(arr[0]);
		}
	      alert("remove");
	}