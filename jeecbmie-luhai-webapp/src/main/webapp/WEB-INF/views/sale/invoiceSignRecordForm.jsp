<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/sale/invoiceSignRecord/${action}" method="post">
		<input id="id" name="id" type="hidden"  value="${invoiceSignRecord.id }" />
		
		<div id="mainDiv" class="" data-options="border:false">
			<div>
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">客户名称</th>
						<td width="30%">
<%-- 							<mytag:combobox name="customer" value="${invoiceSignRecord.customer}" type="customer" parameters="10230003" hightAuto="false"/> --%>
							<mytag:combobox name="customer" value="${invoiceSignRecord.customer}" type="customer" parameters="10230003" hightAuto="false" permission4User="true"/>
						</td>
						<th width="20%">寄票日期</th>
						<td width="30%">
							<input id="mailDate" name="mailDate" type="text" value="<fmt:formatDate value="${invoiceSignRecord.mailDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<th width="20%">签收人</th>
						<td width="30%">
							<input name="signer" type="text" value="${invoiceSignRecord.signer}" class="easyui-validatebox" />
						</td>
						<th>签收日期</th>
						<td>
							<input id="signDate" name="signDate" type="text" value="<fmt:formatDate value="${invoiceSignRecord.signDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
					<legend>系统信息</legend>
					<table width="98%" class="tableClass">
					<tr>
						<th  width="20%">登记人</th>
						<td width="30%">
							<input type="hidden" name="createrNo" value="${invoiceSignRecord.createrNo }"/>
							<input type="hidden" name="createrName" value="${invoiceSignRecord.createrName }"/>
							<input type="hidden" name="createrDept" value="${invoiceSignRecord.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${invoiceSignRecord.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							<input type="hidden" name="updateDate" value="<fmt:formatDate value='${invoiceSignRecord.updateDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${invoiceSignRecord.createrName }
						</td>
						<th  >登记部门</th>
						<td>${invoiceSignRecord.createrDept }</td>
					</tr>
					<tr>	
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${invoiceSignRecord.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<th  >最后修改时间</th>
						<td>
							<fmt:formatDate value="${invoiceSignRecord.updateDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
					</table> 
				</fieldset>
			
			<fieldset class="fieldsetClass" >
			<legend>详细信息</legend>
				<div>
					<div id="tbGoods" style="padding:5px;height:auto">
					     <div>	
							<a id="addSubs" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
							<span class="toolbar-item dialog-tool-separator"></span>
					      	<a id="updateSubs" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
					   		<span class="toolbar-item dialog-tool-separator"></span>
					   		<a id="deleteSubs" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
					     </div>
					</div>
					<table id="dgInvoiceSignRecordSub"></table>
				</div>
			</fieldset>	
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){
	if('${action}' == 'view') {
		$("#tbGoods").hide();
	}
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		 $.easyui.loading({ msg: "正在加载..." });
	    	}
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){
     		successTipNew(data,dg);
     		sub_obj.init();
			$.easyui.loaded();
	    }
	});
	if('${action}' == 'view') {
		//将输入框改成只读
		$("#mainform").find(".easyui-validatebox").attr("readonly",true);
		//处理日期控件  移除onclick
		$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
		//将下拉选改成只读
		$("#mainform").find('.easyui-combobox').combobox({
		    disabled:true
		});
		//处理日期控件
		$("#mainform").find(".easyui-my97").each(function(index,element){
			$(element).attr("readonly",true);
			$(element).removeClass("easyui-my97");
			$(element).addClass("easyui-validatebox");
		});
	}
});
var sub_obj = {
		init:function(){
			this.listSubs();
		},
		reload:function(){
			this.sub_list.datagrid("reload");
		},
		sub_list:{},
		listSubs:function(){
			this.sub_list = $('#dgInvoiceSignRecordSub').datagrid({
				method: "get",
				url: $('#id').val() ? '${ctx}/sale/invoiceSignRecordSub/list/'+$('#id').val() : '',
			  	fit : false,
				fitColumns : true,
				scrollbarSize : 0,
				border : false,
				striped:true,
				idField : 'id',
				rownumbers:true,
				singleSelect:true,
				extEditing:false,
				showFooter:true,
			    columns:[[    
					{field:'id',title:'id',hidden:true},
					{field:'quantity',title:'数量(吨)',sortable:true,width:10},
					{field:'unitPrice',title:'单价',sortable:true,width:10},
					{field:'totalPrice',title:'价税合计(元)',sortable:true,width:10},
					{field:'invoiceNo',title:'发票号码',sortable:true,width:20},
					{field:'invoiceCopies',title:'发票份数',sortable:true,width:10},
					{field:'remarks',title:'备注',sortable:true,width:40}
			    ]],
			    enableHeaderClickMenu: false,
			    enableHeaderContextMenu: false,
			    enableRowContextMenu: false
			});
		},
		sub_form:'',
		addSubs:function(){
			var idValue = $('#id').val();
			if(idValue=="" || idValue==null){
				$.messager.alert('提示','请先保存主表信息！','info');
				return;
			}
			sub_obj.sub_form = $("#dlg_sub").dialog({
				method:'GET',
			    title: '新增信息',    
			    width: 700,    
			    height: 400,     
			    href:'${ctx}/sale/invoiceSignRecordSub/create/'+idValue,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#subMainform').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sub_obj.sub_form.panel('close');
					}
				}]
			});
		},
		updateSubs:function(){
			var row = sub_obj.sub_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			sub_obj.sub_form = $("#dlg_sub").dialog({   
			    title: '修改信息',    
			    width: 700,    
			    height: 400,
			    href:'${ctx}/sale/invoiceSignRecordSub/update/'+row.id,
			    maximizable:false,
			    modal:true,
			    buttons:[{
					text:'保存',iconCls:'icon-save',
					handler:function(){
						$('#subMainform').submit(); 
					}
				},{
					text:'关闭',iconCls:'icon-cancel',
					handler:function(){
						sub_obj.sub_form.panel('close');
					}
				}]
			});
		},
		deleteSubs:function(){
			var row = sub_obj.sub_list.datagrid('getSelected');
			if(rowIsNull(row)) return;
			parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
				if (data){
					$.ajax({
						type:'get',
						url:'${ctx}/sale/invoiceSignRecordSub/delete/'+row.id,
						success: function(data){
							var data = eval('(' + data + ')');
							if(data.returnFlag=='success'){
								sub_obj.sub_list.datagrid('clearSelections');
								sub_obj.sub_list.datagrid('reload');
								parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
								return true;
							}else if(data.returnFlag=='fail'){
								parent.$.messager.alert(data.returnMsg);
								return false;
							}
							sub_obj.reload();
						}
					});
				} 
			});
		}
}
$(function(){
	sub_obj.init();
	 $("#addSubs").on("click", sub_obj.addSubs); 
	$("#updateSubs").on("click", sub_obj.updateSubs);
	$("#deleteSubs").on("click", sub_obj.deleteSubs);
});
</script>
</body>
</html>