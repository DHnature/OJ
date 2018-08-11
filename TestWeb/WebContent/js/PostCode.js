/**
 * 
 */
       //Load ace 
	    var i = 1;
        var editor = ace.edit("ace_editor_demo");
        editor.session.setMode("ace/mode/java");
        editor.setTheme("ace/theme/monokai");
        editor.setFontSize(18);
        // enable autocompletion and snippets
        editor.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true
        });    
        
        editor.on('change', function (e) {
            console.log('change %o', e);
            add_event(i, 'change', e.lines);
            i++;
        });
        editor.on('cursorChange', function (obj) {
            console.log('cursorChange %o', obj);
            add_event(i, 'cursorChange', 'content');
            i++;
        });
        editor.on('copy', function (text) {
            console.log(editor.getCursorPosition());
            console.log('copy %o', text);
            add_event(i, 'copy', text.text);
            i++;
        });
        editor.on('paste', function (e) {
            console.log('paste %o', e);
            add_event(i, 'paste', e.text);
            i++;
        });
        editor.on('focus', function (e) {
            console.log('focus %o', e);
            add_event(i, 'focus', 'content');
            i++;
        });

        var editSession = editor.getSession("editSession");
            editSession.on('changeScrollTop', function (e) {
            console.log('changeScrollTop', e);
            add_event(i, 'changeScrollTop', 'content');
            i++;
        });
        editSession.on('changeScrollLeft', function (e) {
           console.log('changeScrollLeft', e);
           add_event(i, 'changeScrollLeft', 'content');
           i++;    
        });

                
        function submit(){
            console.log(editSession.getDocument());
            add_event(i, 'submit', 'content');
            i++;   
         }
        function toProfile(){
            var blob = new Blob(editSession.getDocument().$lines,{type:"text/plain;charset=utf-8"});
            saveAs(blob,"Codes.txt");
            add_event(i, 'toProfile', 'content');
            i++;   
        }

        console.log(editSession.getLength());//返回编辑器已有文本的行数
        console.log(editor.getCursorPosition());
        console.log(editor.getDragDelay());
        
        var t1 = window.setInterval(submit,120000); 
        
        
        let add_event = function (id_, name_, content_) {
        let $list_li = $('<li class="event-list-item"></li>');
            if (name_ === 'event_keydown') {
                $list_li.addClass('event-keydown');         
            }
            
            let myDate = new Date();
            let time = myDate.getTime();  // 

            let context_ = '';
            context_ += '<span>' + id_ + '</span>&nbsp;&nbsp;';
            context_ += '<span>' + time + '</span>&nbsp;&nbsp;';
            context_ += '<span>' + name_ + '</span>&nbsp;&nbsp;';
            context_ += '<span>' + content_ + '</span>';

            let $list_li_content = $(context_);
            $list_li.append($list_li_content);

            $('.event-list').append($list_li);              
        };
        
        
  //每名用户的每次请求单独设置一个任务号，暂定
  id=100 
  flag=1; //用来判断编译是否完成的标识符
  ip="http://xdhtdi.natappfree.cc";
  //若编译未完成，每隔2S向服务器发送一次请求
  //var a=setInterval(timer1,12000);
  function timer1(){
  var url=ip+"/OJ/Compile?method=polling";
  var json={"Code":editSession.getDocument().getValue()+""};
  	if(flag!=1){
  		$.ajax({
  			   type:'post',
  			   url:url,
  			   data:json,
  			   dataType:'json',
  			   success:function(json){
  				   flag=1;
  				   console.log("success");
  				   console.log("编译结果为:    "+json.result);	
  				   alert("编译结果为:    "+json.result);
  				   
  			   }, 
  			   error:function(json){
  				   flag=1;
  				   console.log("error");
  				   console.log(json);
  			   }	   
  			});	
  	}	
  	};	
    

  $("#compileCode").click(function(){		
  	url=ip+"/OJ/Compile?method=polling";	
  	var json={"Code":editSession.getDocument().getValue()+""};
  	flag=0;
  	$.ajax({
  		   type:'get',
  		   url:url,
  		   data:json,
  		   dataType:'json',
  		   success:function(json){
  			   console.log("提交情况:    "+json.result);	
  			   alert("提交情况:    "+json.result);
  		   }, 
  		   error:function(json){
  			   console.log("提交失败");		   
  		   }	   
  		});	
  });
              