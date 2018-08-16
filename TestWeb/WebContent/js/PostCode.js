        sid=getCookie("sid");
        alert(getCookie("sname"))
        alert(getCookie("sid"))
        var i = 1;
        //http://localhost:8080/
        //7irq5i.natappfree.cc
        ip = "http://localhost:8080/";
		var editor = ace.edit("ace_editor_demo");
		editor.session.setMode("ace/mode/java");
		editor.setTheme("ace/theme/monokai");
		editor.setFontSize(18);
		// enable autocompletion and snippets
		editor.setOptions({
			enableBasicAutocompletion : true,
			enableSnippets : true,
			enableLiveAutocompletion : true
		});
		
		editor.on('change', function(e) {
			add_event(i, 'change', [ e.action + '  ' + e.lines ]);
			i++;
		});
		
		editor.on('undo', function(e) {
			console.log(e);
			add_event(i, 'undo', '');
			i++;
		});
		editor.on('redo', function(e) {
			console.log(e);
			add_event(i, 'redo', '');
			i++;
		});
		editor.on('toggleCommentLines', function(e) {
			console.log('toggleCommentLines %o', e);
			add_event(i, 'toggleCommentLines', '');
			i++;
		});
		
		editor.on('cursorChange', function(obj) {
			var selectedText = editor.getSelectedText();
			if (selectedText !== "") {
				add_event(i, 'selectChange', [ '[' + editor.getCursorPosition().row
						+ "," + editor.getCursorPosition().column + ']            '
						+ editor.getSelectedText() ]);
			} else {
				add_event(i, 'cursorChange', [ '[' + editor.getCursorPosition().row
						+ "," + editor.getCursorPosition().column + ']' ]);
			}
			i++;
		
		});
		editor.on('copy', function(text) {
			console.log(editor.getCursorPosition());
			console.log('copy %o', text);
			add_event(i, 'copy', text.text);
			i++;
		});
		editor.on('paste', function(e) {
			console.log('paste %o', e);
			add_event(i, 'paste', e.text);
			i++;
		});
		editor.on('focus', function(e) {
			console.log('focus %o', e);
			add_event(i, 'focus', [ '[' + editor.getCursorPosition().row + ","
					+ editor.getCursorPosition().column ]
					+ ']');
			i++;
		});
        $("*").on("click", function(e){
            event.stopPropagation();
            if(e.target.id === "left" ||e.target.id === "right" || e.target.id === "container") {
                    console.log("focusOut");
                    add_event(i, 'focusOut', '');
                    i++;  
                }
        });
        
		editor.on('autoComplete', function (e) {
            console.log("autoComplete %o", e);
            add_event(i, 'autoComplete', e);
            i++;
        });
		
		var editSession = editor.getSession("editSession");
		editSession.on('changeScrollTop', function(e) {
			console.log('changeScrollTop', e);
			add_event(i, 'changeScrollTop', e);
			i++;
		});
		editSession.on('changeScrollLeft', function(e) {
			console.log('changeScrollLeft', e);
			add_event(i, 'changeScrollLeft', e);
			i++;
		});
		
		function submit() {
			console.log(editSession.getDocument());
			add_event(i, 'submit', editSession.getDocument().$lines);
			i++;
		}
		function toProfile() {
			var blob = new Blob(editSession.getDocument().$lines, {
				type : "text/plain;charset=utf-8"
			});
			saveAs(blob, "Codes.txt");
			add_event(i, 'toProfile', editSession.getDocument().$lines);
			i++;
		}
		
		console.log(editSession.getLength());// 返回编辑器已有文本的行数
		console.log(editor.getDragDelay());
		
		// ******************************************************************
		let $last_obj = null;
		
		let add_event = function(id_, name_, content_) {
		
			let myDate = new Date();
			let time = myDate.getTime(); // 
		
			if ($last_obj !== null) { // 
				if (($last_obj.hasClass("cursorChange") && name_ === 'cursorChange')
						|| ($last_obj.hasClass("selectChange") && name_ === 'selectChange')) {
					$last_obj.find('.id').text(id_);
					$last_obj.find('.time').text(time);
					$last_obj.find('.name').text(name_);
					$last_obj.find('.content').text(content_);
					return;
				}
			}
		
			let $list_li = $('<li class="event-list-item" ></li>');
			if (name_ === 'change') {
				$list_li.addClass('change');
			} else if (name_ === 'focus') {
				$list_li.addClass('focus');
			} else if (name_ === 'cursorChange') {
				$list_li.addClass('cursorChange');
			} else if (name_ === 'selectChange') {
				$list_li.addClass('selectChange');
			} else if (name_ === 'changeScrollLeft' || name_ === 'changeScrollTop') {
				$list_li.addClass('changeScroll');
			} else if (name_ === 'submit' || name_ === 'toProfile') {
				$list_li.addClass('getCodes');
			} else if (name_ === 'copy' || name_ === 'paste') {
				$list_li.addClass('copyPaste');
			} else if (name_ === 'undo' || name_ === 'redo') {
				$list_li.addClass('undoRedo');
			}else if (name_ === 'compileResult') {
				$list_li.addClass('compileResult');
			}
			else if (name_ === 'focusOut') {
				$list_li.addClass('focusOut');
			}
			let context_ = '';
			context_ += '<span class="id">' + id_ + '</span>&nbsp;&nbsp;';
			context_ += '<span class="time">' + time + '</span>&nbsp;&nbsp;';
			context_ += '<span class="name">' + name_ + '</span>&nbsp;&nbsp;';
			context_ += '<span class="content">' + content_ + '</span>';
			
			
			
		    console.log(context_);
			let $list_li_content = $(context_);
			$list_li.append($list_li_content);
		
			$('.event-list').append($list_li);
			console.log($list_li);
			var div = document.getElementById('right');
			div.scrollTop = div.scrollHeight;
			var json={"id_":id_,"time":time,"name_": name_,"content_":content_,"environment":"online","sid":sid};
		    sendInfo(json);
			$last_obj = $list_li;
		};
		
		// ******************************************************************
		//轮询这一块还没有做好，等后面具体需求下来了再做
		
		/*flag = 1; // 用来判断编译是否完成的标识符
		
		// 若编译未完成，每隔2S向服务器发送一次请求
		// var a=setInterval(timer1,12000);
		function timer1() {
			var url = ip + "/OJ/Compile?method=polling";
			// exerciseType, taskId
			var json = {
				"Code" : editSession.getDocument().getValue() + "",
				"exerciseType" : "Open",
				"taskId" : "001"
			};
			if (flag != 1) {
				$.ajax({
					type : 'post',
					url : url,
					data : json,
					dataType : 'json',
					success : function(json) {
						flag = 1;
						console.log("success");
						console.log("编译结果为:    " + json.result);
						alert("编译结果为:    " + json.result);
						
		
					},
					error : function(json) {
						flag = 1;
						console.log("error");
						console.log(json);
					}
				});
			}
		};
		*/
		// ******************************************************************	
		$("#compileCode").click(function() {
			var url = ip + "/OJ/Compile?method=polling";
			var json = {
				"Code" : editSession.getDocument().getValue() + "",
				"exerciseType" : "Open",
				"taskId" : "001"
			};
			flag = 0;
			$.ajax({
				type : 'get',
				url : url,
				data : json,
				dataType : 'json',
				success : function(json) {
					alert("提交情况:    " + json.result);
					add_event(i++, 'compileResult', json.result);
				},
				error : function(json) {
					console.log("提交失败");
				}
			});
		});
		
		function sendInfo(json){
			url = ip + "/OJ/SaveInfoFromWeb";
			$.ajax({
				type :'post',
				url : url,
				data : json,
				dataType : 'json',
				success : function(json) {
					console.log("success");
					console.log(json.message);
				},
				error : function(json) {
					console.log("fail");
				}
			});
		}
		
		
