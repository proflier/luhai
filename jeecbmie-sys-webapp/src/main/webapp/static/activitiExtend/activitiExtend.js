var business_page = {};
(function($){
	var pathName=window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	var ctx = window.location.protocol+"//"+window.location.host+projectName;
	var _business = {
			//流程启动
			procStartUrl:"",
			//业务Id
			busiId:"",
			//流程实例id
			processInstanceId:"",
			//业务状态 a草稿 b变更
			busiInitState:"a",
			procFirstStep:"",
			// 业务中展示用的datagrid
			datagridDiv:"",
			//
			tempDialog:new Array(),
			tempColseDialog:new Array(),
			tempDatagrid:new Array(),
			procVerifi:function(dg){
				if(this.datagridDiv!=""){
					var row = dg.datagrid('getSelected');
					if(rowIsNull(row)){
						$.messager.alert('提示','请选择！','info');
						return false;
					}else{
						this.busiId=row.id;
					}
				}
				if(row.state != "2"){
					$.messager.alert('提示','表单当前状态，不能提交申请！','info');
					return false;
				}else{
					return true;
				}
			},
			beforeApply:function(){
				return true;
			},
			//保存并提交(业务表单id,业务保存或变更url)  用于业务开始提交 dg list dialog,busiPage form dialog,busiFormId 业务表单Id
			saveAndApply:function(dg,busiPage,busiFormId,afterSave){
				this.tempDatagrid.push(dg);
				this.tempDialog.push(busiPage);
				this.datagridDiv=dg;
				var tThis = this;
				var isValid = $("#"+busiFormId).form('validate');//验证表单
				if(!isValid){
					return false;
				}
				var jsonData = $("#"+busiFormId).serializeArray();//将页面表单序列化成一个JSON结构的对象
				$.ajax({
					type:'post',
					dataType:'json',
					async:false,
					url:$("#"+busiFormId).attr("action"),
					data:jsonData,
					success:function(data){
						if(data.returnFlag=="success"){
							var tUrl = $("#"+busiFormId).attr("action");
							if(tUrl.lastIndexOf('create')>=0){
								tUrl=tUrl.substring(0,tUrl.lastIndexOf('create'))+'update';
								$("#"+busiFormId).attr("action",tUrl);
								$("#id").val(data.returnId);
							}
							tThis.busiId = data.returnId;
							if(typeof afterSave =='function'){
								afterSave();
							}
							tThis.applyDetail();
						}else if(data.returnFlag=='fail'){
							parent.$.messager.alert(data.returnMsg);
							return false;
						}  
					},
					error :function(){
						$.messager.alert('提示','提交失败！','info');
					}
				});
			},
			//提交流程(列表提交)  用于业务开始提交
			apply:function(dg){
				this.tempDatagrid.push(dg);
				this.datagridDiv=dg;
				if(!this.procVerifi(dg) || !this.beforeApply()){
					return false;
				}
				this.applyDetail();
			},
			applyDetail:function(){
				var $this = this;
				$.ajax({
					type:'get',
					dataType:'json',
					async:false,
					url:this.procStartUrl+"/"+this.busiId,
					success: function(data){
						if(data!=null && data.processInstanceId!=null){
							$this.processInstanceId = data.processInstanceId;
							var procDialog = _business.commitProc(data.processInstanceId,data.taskId,data.businessKey,data.processKey);
							$this.tempDialog.push(procDialog);
							$this.tempColseDialog.push(procDialog);
							
						}else if(data!=null && data.msg!=null){
							if(data.msg=='no deployment'){
					    		parent.$.messager.show({ title : "提示",msg: "没有部署流程！", position: "bottomRight" });
					    	}else if(data.msg=='start fail'){
					    		parent.$.messager.show({ title : "提示",msg: "启动流程失败！", position: "bottomRight" });
					    	}else{
					    		parent.$.messager.show({ title : "提示",msg: data.msg, position: "bottomRight" });
					    	}
						}
					}
				});
			},
			//进入提交流程页面
			commitProc:function(processInstanceId_p,taskId_p,businessKey_p,processKey_p){
				$("body").append("<div id='procDialogDiv'></div>");
				var tDialog = $("#procDialogDiv").dialog({
				    title: '流程办理',
				    closable:false,
				    width: 680,
				    height: 400,
				    href:ctx+"/workflow/approvalFormDeal/"+processInstanceId_p+"/"+taskId_p+"/"+businessKey_p+"/"+processKey_p,
				    maximizable:false,
				    modal:true
				});
				return tDialog;
			},
			//流程跟踪
			traceProc:function(processInstanceId_p){
				if(processInstanceId_p == null){
					$.messager.alert('提示','流程实例不能为空！','info');
					return;
				}
				$("body").append("<div id='procTraceDialogDiv'></div>");
				var tDialog=$("#procTraceDialogDiv").dialog({   
				    title: '流程跟踪',
				    width: 800,    
				    height: 350,    
				    href:ctx+'/workflow/trace/'+processInstanceId_p,
				    maximizable:true,
				    resizable:true,
				    modal:true,
				    buttons:[
					{
						text:'催办',iconCls:'icon-redo',
						id:'traceButtonId',
						disabled:true,
						handler:function(){
							tDialog.panel('close');
							$("#procTraceDialogDiv").remove();
							$.ajax({
								type:'get',
								async:false,
								url:ctx+'/workflow/remind/'+processInstanceId_p,
								success:function(data){
								},
								error :function(){
								}
							});
						}
					},
				     {
						text:'关闭',iconCls:'icon-cancel',
						handler:function(){
							tDialog.panel('close');
							$("#procTraceDialogDiv").remove();
							}
					}]
				});
			},
			//办理页面提交成功后调用
			afterCommit:function(){  //刷新+关闭dialog+dialog Div删除
				$.each(this.tempDialog,function(i,n){
					$(n).dialog("close");
				});
				$.each(this.tempDatagrid,function(i,n){
					$(n).datagrid('clearSelections');
					$(n).datagrid('reload');
				});
				this.tempDialog.length = 0;
				this.tempColseDialog.length = 0;
				this.tempDatagrid.length = 0;
				$("#procDialogDiv").remove();
			},
			close:function(){  //关闭dialog+dialog Div删除
				if(this.procFirstStep!="no"){
					$.ajax({
						type:'get',
						async:false,
						url:ctx+'/workflow/deleteFirst/'+this.processInstanceId+'/'+this.busiInitState,
						success:function(data){
						},
						error :function(){
						}
					});
				}
				$.each(this.tempColseDialog,function(i,n){
					$(n).dialog("close");
				});
				$.each(this.tempDatagrid,function(i,n){
					$(n).datagrid('reload');
				});
				this.tempDialog.length = 0;
				this.tempColseDialog.length = 0;
				this.tempDatagrid.length = 0;
				$("#procDialogDiv").remove();
			},
			stateShow:function(state_value){
				if ("2"==state_value){
					return "草稿";
				}else if("1"==state_value){
					return "生效";
				}else if("3"==state_value){
					return "已提交";
				}else if("0"==state_value){
					return "废弃";
				}else{
					return null;
				}
			}
	};
	
	$.extend(business_page,_business);
})(jQuery);