<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>  
<head>  
<title></title>  
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<!--引入引入kindeditor编辑器相关文件--> 
<link rel="stylesheet" href="${ctx}/static/plugins/kindeditor/themes/default/default.css" />
<script src="${ctx}/static/plugins/kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/kindeditor/lang/zh-CN.js" type="text/javascript"></script>
<style>
<!-- 最新价格对应css -->
.zhishu1{height:385px;}
.zhishu1 li{width:16%;font-size:12px;display:inline-block;}
.zhishu1 .yiname{min-width:90px}
#newPrice ul {list-style:none;margin:0 auto;padding:0;}
#newPrice li {border-left:1px solid #95B8E7;border-top:1px solid #95B8E7;float:left;height:25px;text-align:center;line-height:25px}
#newPrice li.b {border-bottom:1px solid #95B8E7}
#newPrice li.r {min-width:60px;border-right:1px solid #95B8E7}
#newPrice li.bold{font-size:13px;font-weight: bold;height:28px;line-height:28px;}
#newPrice {min-width:538px;}
#newPrice .huanbi{min-width:50px}

</style>
</head>


<body style="overflow:scroll">
<div  class="easyui-tabs" style="margin-bottom:5px;">
	<div id="pToDo"  data-options="title: '待办任务', iconCls: false, refreshable: false">
			<div id="tbToDo" style="padding: 5px; height: auto">
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="claim();">签收</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="deal();">办理</a>
			</div>
			<table id="dgToDo" style="height: 350px" data-options="
				rowStyler: function(index,row){
					if (row.status =='待办'){
						return 'color:#0072E3;font-style:italic;';
					}
				}
			"></table>
	</div>
	<div id="pPass" data-options="title: '传阅任务', iconCls: false, refreshable: false">	
			<div id="tbPass" style="padding: 5px; height: auto">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace('procPass');">流程跟踪</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-cv" plain="true" onclick="historic_view('procPass');">查看详情</a>
			</div>
			<table id="dgPass" style="height: 350px"></table>
	</div>
	<div id="pHaveDone" data-options="title: '已办任务', iconCls: false, refreshable: false">
			<div id="tbHaveDone" style="padding: 5px; height: auto">
				<a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="trace('haveDone');">流程跟踪</a>
				<!--  
				<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-product-design" plain="true" onclick="tracePhoto_view();">流程图片</a>
				-->
				<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-cv" plain="true" onclick="historic_view('haveDone');">查看详情</a>
			</div>
			<table id="dgHaveDone" style="height: 350px"></table>
	</div>
	<div id="相关回馈" data-options="title: '相关回馈', iconCls: false, refreshable: false">
			<div id="tbFeedback" style="padding: 5px; height: auto">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="advice();">回馈消息</a>
			</div>
			<table id="dgFeedback" style="height: 350px"></table>
	</div>
</div>
<div class="parent_element1" style="overflow: hidden;width:100%">
	<div style="width:500px;float:left;margin-right:5px;margin-bottom:5px;">
		<div style="width:100%;height:350px;padding:10px;" title="无烟煤" class="easyui-panel" data-options="fit:true">
			<div id="tablefirst" style="width:100%;height:300px;"></div><!-- 无烟煤 -->
		</div>
	</div>
		
	<div style="width:500px;float:left;margin-bottom:5px;">
		<div style="width:100%;height:350px;padding:10px;" title="动力煤" class="easyui-panel" data-options="fit:true">
			<div id="tablefirst2" style="width:100%;height:300px;"></div><!-- 动力煤 -->
		</div>
	</div>
	<!-- 网站关闭暂时去除 -->
<!-- 	<div style="width:500px;float:left;margin-right:5px;margin-bottom:5px;"> -->
<!-- 		<div style="width:100%;height:350px;padding:10px;" title="环渤海动力煤指数" class="easyui-panel" data-options="fit:true"> -->
<!-- 			<div id="bspi"></div>环渤海动力煤指数 -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div style="width:500px;float:left;margin-bottom:5px;">
		<div style="width:100%;height:350px;padding:10px;" title="最新价格" class="easyui-panel" data-options="fit:true">
			<div id="newPrice"></div><!-- 最新价格 -->
		</div>
	</div>
</div>
<!-- <div style="width:630px;height:350px;padding:10px;" title="环渤海动力煤指数" class="easyui-panel">
	<div id="bspi"></div>
</div> -->
	




<div id="dlg"></div>
<div id="internalTrace"></div>
<div id="approvalFormDeal"></div>
<div id="dealDlg"></div>
<div id="dlgSaleContract"></div> <!-- 销售合同列表弹窗  -->
<div id="dlgPurchaseContract"></div> <!-- 采购合同列表弹窗  -->
<div id="dlgPurchaseDetail"></div><!-- 采销同批 - 采购合同查看明细弹窗 -->
<div id="dlgSaleDetail"></div> <!-- 采销同批 - 销售合同查看明细弹窗 -->
<div id="searchGoodsCode"></div>
<div id="dlg_rel"></div><!-- 反馈新建  -->
<div id="dlg_selectUsers"></div> 
<div id="dlg_goods"></div> 
<div id="dlg_sub"></div>
<div id="dlg_subs"></div>
<div id="dlg_operate"></div>
<div id="dlg_extra"></div>
<div id="dlg_saleContract"></div>



<!-- 流程图片 -->
<div id="tracePhoto_dlg" class="easyui-dialog" title="流程图" 
	data-options="iconCls:'icon-hamburg-product-design'" >
	<img id="tracePhoto" style="-webkit-user-select: none" src=""></img>
</div>


<script type="text/javascript">
function autoShow(){
    var parent_elementWidth=$(".parent_element1").width();
    if(parent_elementWidth>=1200){
        $(".parent_element1").children("div").css("width",49+"%");
        $(".parent_element1").children("div").find("div").css("width",98+"%");
    }else{
        $(".parent_element1").children("div").css("width",98+"%");
        $(".parent_element1").children("div").find("div").css("width",100+"%"); 
    }
}
autoShow();
 $(window).resize(function() {          
	autoShow();
}); 

var tracePhoto_dlg;
(function($) {
	tracePhoto_dlg = $("#tracePhoto_dlg").dialog({
		title: '流程图片',
	    fit:true,
	    closed:true,
	    style:{borderWidth:0},
	    maximizable:false,
	    closable:true,
	    modal:true,
	    buttons:[
	    	{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					tracePhoto_dlg.panel('close');
				}
	    	}
	    ]
	});
	
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('tablefirst'));
	option = {
		    title: {
		        text: '无烟煤'
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		    	data:['晋城无烟中块','晋城无烟末煤']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: []
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: []
		};

        $.ajax({
            type : "GET",
            url : '${ctx}/catchHtmlController/getAnthraciteTrend' ,
//             dataType : "json",
            success : function(result) {
                //将从后台接收的json字符串转换成json对象
                var jsonobj = eval('(' + result + ')');
                //给图标标题赋值
                //读取横坐标值
                option.xAxis.data = jsonobj.xAxis[0].data;
                option.series = jsonobj.series;
                //过渡控制，隐藏loading（读取中）
                myChart.hideLoading();
                 // 为echarts对象加载数据
                myChart.setOption(option);
            }
        });
        
        
        var myChart2 = echarts.init(document.getElementById('tablefirst2'));
    	option2 = {
    		    title: {
    		        text: '动力煤'
    		    },
    		    tooltip: {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		    	data:['CCI进口5500','榆林5800','鄂尔多斯5500','广州港5500']
    		    },
    		    grid: {
    		        left: '3%',
    		        right: '4%',
    		        bottom: '3%',
    		        containLabel: true
    		    },
    		    toolbox: {
    		        feature: {
    		            saveAsImage: {}
    		        }
    		    },
    		    xAxis: {
    		        type: 'category',
    		        boundaryGap: false,
    		        data: []
    		    },
    		    yAxis: {
    		        type: 'value'
    		    },
    		    series: []
    		};

            $.ajax({
                type : "GET",
                url : '${ctx}/catchHtmlController/getSteamCoalTrend' ,
//                 dataType : "json",
                success : function(result) {
                    //将从后台接收的json字符串转换成json对象
                    var jsonobj = eval('(' + result + ')');
                    //给图标标题赋值
                    //读取横坐标值
                    option2.xAxis.data = jsonobj.xAxis[0].data;
                    option2.series = jsonobj.series;
                    //过渡控制，隐藏loading（读取中）
                    myChart2.hideLoading();
                     // 为echarts对象加载数据
                    myChart2.setOption(option2);
                }
            });
            
            $.ajax({
                type : "GET",
                url : '${ctx}/catchHtmlController/getBSPI' ,
                success : function(result) {
                	$('#bspi').html(result)
                }
            });
            
            $.ajax({
                type : "GET",
                url : '${ctx}/catchHtmlController/getNewPrice' ,
                success : function(result) {
                	$('#newPrice').html(result)
                }
            });

})(jQuery);

var dg;
var dgToDo;
var dgRunning;
var dgHaveDone;
var dgPass;
var themeObj = {};

//待办任务列表
dgToDo=$('#dgToDo').datagrid({
	method: "get",
    url:'${ctx}/workflow/task/todo/list',
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	scrollbarSize : 0,
	rownumbers:true,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
   columns:[[    
		{field:'pid',title:'pid',hidden:true},
		{field:'id',title:'id',hidden:true},
		{field:'activityId',title:'activityId',hidden:true},
		{field:'businessKey',title:'businessKey',hidden:true},
		 {field:'priority',title:'优先级',
        	formatter: function(value,row,index){
				if (row.priority==100){
					return '加急';
				} else if(row.priority==150){
					return '紧急';
				}else {
					return '普通';
				}
			},width:5,
			styler: function(value,row,index){
				if (row.priority==100){
					return 'color:#D9B300;';
				} else if(row.priority==150){
					return 'color:red;';
				}else {
				}
			}},
        {field:'status',title:'任务状态',sortable:true,width:5},
		{field:'businessInfo',title:'业务信息',sortable:true,width:20},
        {field:'name',title:'当前节点',sortable:true,width:5},
        {field:'createTime',title:'创建时间',sortable:true,width:10}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbToDo',
    remoteSort:false
    
});
/* dgRunning=$('#dgRunning').datagrid({    
	method: "get",
    url:'${ctx}/workflow/task/running/list', 
    fit : false,
	fitColumns : true,
	border : false,
	rownumbers:true,
	striped:true,
	idField : 'processInstanceId',
	pagination:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20 ],
	singleSelect:true,
    columns:[[
		{field:'processInstanceId',title:'processInstanceId',hidden:true},
		{field:'name',title:'流程名称',sortable:true},
		{field:'businessInfo',title:'业务信息',sortable:true},
		{field:'curTaskName',title:'流程节点',sortable:true},
        {field:'curTaskCreateTime',title:'当前任务创建时间',sortable:true},
        {field:'suspended',title:'流程状态',sortable:true,
        	formatter: function(value,row,index){
				if(row.suspended == false){
					return '未挂起';
				} else {
					return '已挂起';
				}
			}	
        },
        {field:'curTaskAssignee',title:'当前处理人',sortable:true}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbRunning',
    remoteSort:false
}); */

//已办任务列表
dgHaveDone=$('#dgHaveDone').datagrid({
	method: "get",
    url:'${ctx}/workflow/task/haveDone/list', 
    fit : false,
	fitColumns : true,
	rownumbers:true,
	border : false,
	striped:true,
	scrollbarSize : 0,
	idField : 'processInstanceId',
	pagination:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[
		{field:'processInstanceId',title:'processInstanceId',hidden:true},
		{field:'taskId',title:'任务ID',sortable:true,hidden:true},
		{field:'processDefinitionId',title:'processDefinitionId',hidden:true},
		{field:'executionId',title:'executionId',hidden:true},
		{field:'activityId',title:'activityId',hidden:true},
		{field:'currentActivityId',title:'currentActivityId',hidden:true},
		{field:'name',title:'流程名称',hidden:true},
		{field:'businessInfo',title:'业务信息',sortable:true,width:20},
		{field:'taskName',title:'办理节点',sortable:true,width:10},
        {field:'startTime',title:'开始时间',sortable:true,width:10},
        {field:'endTime',title:'结束时间',sortable:true,width:10},
        {field:'callBack',title:'是否可撤回',width:5,
        	formatter: function(value,row,index){
				if(row.callBack == true){
					return '是';
				} else {
					return '';
				}
			}
        }
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbHaveDone',
    remoteSort:false
});
//传阅列表
dgPass=$('#dgPass').datagrid({
	method: "get",
    url:'${ctx}/workflow/task/pass/list',
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	scrollbarSize : 0,
	rownumbers:true,
	idField : 'id',
	pagination:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[    
		{field:'businessKey',title:'businessKey',hidden:true},
		{field:'businessInfo',title:'业务信息',sortable:true,width:20},
        {field:'createDate',title:'创建时间',sortable:true,width:20}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbPass',
    remoteSort:false
});
//反馈列表
themeObj.list=$('#dgFeedback').datagrid({
	method: "get",
    url:'${ctx}/feedback/theme/list', 
    fit : false,
    fitColumns : true,
	border : false,
	striped:true,
	scrollbarSize : 0,
	idField : 'id',
	pagination:true,
	rownumbers:true,
	pageNumber:1,
	pageSize : 10,
	pageList : [ 10, 20, 30, 40, 50 ],
	singleSelect:true,
    columns:[[
		{field:'id',title:'id',hidden:true},  
			{field:'title',title:'主题',width:20}, 
			{field:'classification',title:'反馈分类',width:20,
				formatter: function(value,row,index){
      				var val;
      				if(value!=''&&value!=null){
      					$.ajax({
      						type:'GET',
      						async: false,
      						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackClassification/"+value,
      						success: function(data){
      							if(data != null){
      								val = data;
      							} else {
      								val = '';
      							}
      						}
      					});
      					return val;
      				}else{
      					return '';
      				}
      			}},
			{field:'types',title:'反馈类型',width:20,
      				formatter: function(value,row,index){
          				var val;
          				if(value!=''&&value!=null){
          					$.ajax({
          						type:'GET',
          						async: false,
          						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackTypes/"+value,
          						success: function(data){
          							if(data != null){
          								val = data;
          							} else {
          								val = '';
          							}
          						}
          					});
          					return val;
          				}else{
          					return '';
          				}
          			}},
   			{field:'state',title:'状态',width:20,
   				formatter: function(value,row,index){
       				var val;
       				if(value!=''&&value!=null){
       					$.ajax({
       						type:'GET',
       						async: false,
       						url:"${ctx}/system/dictUtil/getDictNameByCode/feedbackState/"+value,
       						success: function(data){
       							if(data != null){
       								val = data;
       							} else {
       								val = '';
       							}
       						}
       					});
       					return val;
       				}else{
       					return '';
       				}
       			}},
			{field:'createDate',title:'创建日期',width:20,
				formatter:function(value,row,index){
					if(value == null){
						return null;
					}
            		var time = new Date(value);
            		return time.format("yyyy-MM-dd");
            	}}
    ]],
    sortName:'id',
    sortOrder:'desc',
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbFeedback',
    remoteSort:false
});
//弹窗修改
function advice(){
	var row = themeObj.list.datagrid('getSelected');
	if(rowIsNull(row)) return;
	themeObj.form=$("#dlg").dialog({   
	    title: '意见',    
	    href:'${ctx}/feedback/content/list/'+row.id,
	    modal:true,
	    maximizable:false,
	    fit : true,
	    buttons:[{
			text:'关闭',iconCls:'icon-cancel',
			handler:function(){
				themeObj.form.panel('close');
			}
		}]
	});
}
//流程跟踪
function trace(str) {
	var row;
	if(str == 'running'){
		row = dgRunning.datagrid('getSelected');
	}else if(str == 'haveDone'){
		row = dgHaveDone.datagrid('getSelected');
	}else if(str=='procPass'){
		row = dgPass.datagrid('getSelected');
	}
	if(rowIsNull(row)) return;
	$.ajaxSetup({type : 'GET'});
	business_page.traceProc(row.processInstanceId);
}

//流程图片
function tracePhoto_view() {
	var his_row = dgHaveDone.datagrid('getSelected');
	if(rowIsNull(his_row)) return;
	var u = '${ctx}/workflow/trace/photo/'+his_row.processDefinitionId+'/'+his_row.processInstanceId;
	$("#tracePhoto").attr("src",u);
	tracePhoto_dlg.dialog('open');
	/**
	$.ajax({
        url : u,
        success : function(result) {
        	alert(result);
        	//$("#tracePhoto").attr("src","data:image/jpg/png;base64,"+result);
        	$("#tracePhoto").attr("src",u);
        }  
    });
	**/
	/****/
}

//查看已办详情
function historic_view(str) {
	var his_row;
	var curUrl;
	if(str=='haveDone'){
		his_row = dgHaveDone.datagrid('getSelected');
	}else if(str=='procPass'){
		his_row = dgPass.datagrid('getSelected');
	}
	if(rowIsNull(his_row)) return;
	if(str=='haveDone'){
		curUrl='${ctx}/workflow/businessInfo/'+his_row.entityId+"/"+his_row.viewUrlKey+"/-1";
	}else if(str=='procPass'){
		curUrl='${ctx}/workflow/businessInfo/'+his_row.businessKey+"/"+his_row.processKey;
		if(his_row.readFlag=='0'){
			curUrl = curUrl+"/"+his_row.detailId;
		}else{
			curUrl = curUrl+"/-1";
		}
		
	}
	his_d=$("#dlg").dialog({
		title: '查看详情',
		href:curUrl,
	    fit:true,
	    style:{borderWidth:0},
	    maximizable:false,
	    closable:false,
	    modal:true,
	    buttons:[
	    	{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					his_d.panel('close');
				}
	    	}
	    ]
	});
}

//流程办理
function deal() {
	var row = dgToDo.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.status == "待签收"){
		$.messager.alert("任务未签收，不能进行审批！");
		return;
	}
	var buttons = new Array();
	buttons.push(
			{
		    	text:'流程跟踪',
		    	iconCls:'icon-ok',
				handler:function(){
					business_page.traceProc(row.pid);
				}
		    }
	);
	if("1" == row.editable){
		buttons.push(
				{
			    	text:'保存',iconCls:'icon-save',
					handler:function(){
						if(typeof accept == "function"){
							accept();
						}
						var isValid = $("#mainform").form('validate');//验证表单
						if(!isValid){
							return false;
						}
						$("#mainform").submit();
					}
				}
		);
		var button_name='办理';
		if("apply"==row.activityId){
			button_name='提交申请';
		}
		buttons.push(
				{
					text:button_name, iconCls:'icon-hamburg-up',
					handler:function(){
						if(typeof accept == "function"){
							accept();
						}
						var isValid = $("#mainform").form('validate');//验证表单
						if(!isValid){
							return false;
						}
						var jsonData = $("#mainform").serializeArray();//将页面表单序列化成一个JSON结构的对象
						$.ajax({
							type:'post',
							dataType:'json',
							async:false,
							url:$("#mainform").attr("action"),
							data:jsonData,
							success:function(data){
								if(data.returnFlag=="success"){
									business_page.procFirstStep="no";
									var t=business_page.commitProc(row.pid,row.id,row.businessKey,row.key);
									business_page.tempDialog.push(t);
									business_page.tempColseDialog.push(t);
									business_page.tempDialog.push(d);
									business_page.tempDatagrid.push(dgHaveDone);
									business_page.tempDatagrid.push(dgToDo);
								}
							},
							error :function(){
								$.messager.alert('提示','提交失败！','info');
							}
						});
					}
				}	
		);
		if("apply"==row.activityId){
			buttons.push(
					{
						text:'废除流程',iconCls:'icon-hamburg-busy',
						handler:function(){
							parent.$.messager.confirm('提示', '您确定要废除该流程？', function(data){
								if (data){
									$.ajax({
										type:'get',
										url:"${ctx}/workflow/abolishApply/"+row.id,
										success: function(data){
											successTip(data,dgToDo,d);
										//	dgRunning.datagrid('reload');
										}
									});
								}
							});
						}
					}
			);
		}
	}else{
		buttons.push(
				{
			    	text:'办理',
			    	iconCls:'icon-edit',
					handler:function(){
						business_page.procFirstStep="no";
						var proctDialog = business_page.commitProc(row.pid,row.id,row.businessKey,row.key);
						business_page.tempDialog.push(proctDialog);
						business_page.tempColseDialog.push(proctDialog);
						business_page.tempDialog.push(d);
						business_page.tempDatagrid.push(dgHaveDone);
						business_page.tempDatagrid.push(dgToDo);
					}
			    }
		);
	}
	buttons.push(
			{
		    	text:'关闭',
		    	iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
		    }		
	);
		d=$("#dlg").dialog({   
			noheader:true,    
		    href:'${ctx}/workflow/businessPage/'+row.id,
		    fit:true,
		    style:{borderWidth:0},
		    maximizable:false,
		    closable:false,
		    modal:true,
		    buttons:buttons
		});
}

//签收
function claim(){
	var row = dgToDo.datagrid('getSelected');
	if(rowIsNull(row)) return; 
	if(row.status == "待办"){
		$.messager.alert("任务已签收，不能重复签收！");
		return;
	}
	parent.$.messager.confirm('提示', '确定签收当前任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/task/claim/"+row.id,
				success: function(data){
					dgToDo.datagrid('clearSelections');
					dgToDo.datagrid('reload');
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
						return true;
					}else{
						parent.$.messager.alert(data);
						return false;
					}  
				}
			});
		}
	});
}

//撤回
function callBack(){
	var row = dgHaveDone.datagrid('getSelected');
	if(rowIsNull(row)) return; 
	if(!row.callBack){
		$.messager.alert("任务已签收，不能撤回！");
		return;
	}
	parent.$.messager.confirm('提示', '确定撤回当前任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/callBack/"+row.processInstanceId+"/"+row.activityId+"/"+row.currentActivityId,
				success: function(data){
					successTip(data,dgHaveDone);
					dgToDo.datagrid('reload');
					//dgRunning.datagrid('reload');
				}
			});
		}
	});
}
</script>
</body>
</html>