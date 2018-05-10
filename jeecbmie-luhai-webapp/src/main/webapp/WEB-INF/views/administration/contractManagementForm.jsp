<%@page import="com.cbmie.lh.administration.entity.ContractManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tlds/mytag.tld" %>
<style>
</style>
</head>

<body>
	<div>
		<form id="mainform"  action="${ctx}/administration/contractManagement/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<input type="hidden" id="id" name="id" value="${contractManagement.id }" />
			<div data-options="title: '基本信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>合同信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >合同类别</th>
						<td >
							<input id="contractCategory" name="contractCategory" type="text" value="${contractManagement.contractCategory }"  class="easyui-validatebox"  />
						</td>
						<th>归档编号</th>
						<td>
							<input id="innerContractNo" name="innerContractNo" type="text" value="${contractManagement.innerContractNo }"  class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th >合同编号</th>
						<td >
							<input id="contractNo" name="contractNo" type="text" value="${contractManagement.contractNo }" class="easyui-validatebox"  data-options="required:true" />
						</td>
						<th >签约方</th>
						<td >
							<mytag:combobox name="signAffi" value="${contractManagement.signAffi}" type="customer" parameters="10230001or10230003"/>
						</td>
					</tr>
					<tr>
						<th width="20%">合同名称</th>
						<td width="30%">
							<input id="contractName" name="contractName" type="text" value="${contractManagement.contractName }"  class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th  width="20%">签约日期</th>
						<td width="30%">
							<input id="singnDate" name="singnDate" type="text" value="<fmt:formatDate value="${contractManagement.singnDate }"/>" 
								class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th>总数量</th>
						<td>
							<input id="quantity" name="quantity" type="text" value="${contractManagement.quantity }"   class="easyui-numberbox"  data-options="required:true,precision:'3',suffix:'吨'" />
						</td>
						<th>单价</th>
						<td>
							<input id="unitPrice" name="unitPrice" type="text" value="${contractManagement.unitPrice }"   class="easyui-numberbox"  data-options="required:true,precision:'3',suffix:'￥/吨'" />
						</td>
					</tr>
					<tr>
						<th >总金额</th>
						<td colspan="3">
							<input id="amount" name="amount" type="text" value="${contractManagement.amount }"   class="easyui-numberbox"  data-options="required:true,precision:'3',suffix:'￥'" />
						</td>
					</tr>
					<tr>
						<th>原件数量</th>
						<td>
							<input id="originalQuantity" name="originalQuantity" type="text" value="${contractManagement.originalQuantity }"   class="easyui-numberbox"  />
						</td>
						<th>复印件数量</th>
						<td>
							<input id="copyQuantity" name="copyQuantity" type="text" value="${contractManagement.copyQuantity }"   class="easyui-numberbox"   />
						</td>
					</tr>
					<tr>
						<th >盖章日期</th>
						<td >
							<input id="sealDate" name="sealDate"   type="text" value="<fmt:formatDate value="${contractManagement.sealDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
						</td>
						<th >收原件日期</th>
						<td >
							<input id="receiveOrgDate" name="receiveOrgDate"   type="text" value="<fmt:formatDate value="${contractManagement.receiveOrgDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:false"/>
						</td>
					</tr>
					<tr>
						<th >期限</th>
						<td colspan="3">
							<input id="contracTermt" name="contracTermt" value="<fmt:formatDate value="${contractManagement.contracTermt }" />" class="Wdate" />
							<input id="contracTermtEnd" name="contracTermtEnd"   type="text" value="<fmt:formatDate value="${contractManagement.contracTermtEnd }" />"  class="Wdate" />
						</td>
					</tr>
					<tr>	
						<th >交货地点</th>
						<td colspan="3">
							<input id="port" name="port" type="text" value="${contractManagement.port }" class="easyui-validatebox"   />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="2" cols="45" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,46]']">${contractManagement.comment }</textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty contractManagement.createrName ? sessionScope.user.name : contractManagement.createrName }</td>
						<th  >登记部门</th>
						<td>${empty contractManagement.createrDept ? sessionScope.user.organization.orgName : contractManagement.createrDept }</td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=ContractManagement.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${contractManagement.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
		</form>
	</div>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
					$.easyui.loading({ msg: "正在加载..." });
		    	}
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dataGrid);
				$.easyui.loaded();
// 				goodsWindow.listGoods();
			} 
		});
		
		//子表获取list需要参数
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if( $('#mainform').form('validate') && (!$("#id").val() == '') ){//主表校验通过且已经保存数据
		    		tabIndex = index;//更换tab
		    	}else{
		    		if(index != 0){
		    			parent.$.messager.alert('提示','请先保存主表信息!!!');
		    		}
		    		$("#mainDiv").tabs("select", tabIndex);//当前tab
		    		return ;
		    	}
		    }
		});
		
		
	});
	
	$('#contracTermt').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'contracTermtEnd\')}',onpicked:function(){contracTermtEnd.click();}});
	});
	$('#contracTermtEnd').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'contracTermt\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
	
	
	//合同类型
	$('#contractCategory').combobox({
// 		panelHeight:'auto',
		url:'${ctx}/system/dictUtil/getDictByCode/contractCategoryAll',
	    valueField:'code',
	    textField:'name', 
	    required : true,
	    onLoadSuccess: function () { 
	    	var record = $('#contractCategory').combo("getText");
// 	    	alert(JSON.stringify(record))
	    	loadContract(record);
		},
		onSelect:function(record){
			loadContract(record.name);
		}
	});
	
	
	
	$('#innerContractNo').combogrid({    
		prompt: '选择合同类别后加载'
	});
	
	function loadContract(record){
		var currentVal = record;
		var urlValue ="${ctx}/administration/contractManagement/loadContract/";
		if(currentVal=="国内采购合同审批"){
			urlValue = urlValue+"国内采购合同审批";
		}else if(currentVal=="国际采购合同审批"){
			urlValue = urlValue+"国际采购合同审批";
		}else if(currentVal=="国内租船合同审批"){
			urlValue = urlValue+"国内租船合同审批";
		}else if(currentVal=="国际租船合同审批"){
			urlValue = urlValue+"国际租船合同审批";
		}else if(currentVal=="销售合同审批"){
			urlValue = urlValue+"销售合同审批";
		}else if(currentVal=="保险合同审批(内贸)"){
			urlValue = urlValue+"保险合同审批(内贸)";
		}else if(currentVal=="保险合同审批(外贸)"){
			urlValue = urlValue+"保险合同审批(外贸)";
		}else if(currentVal=="货代合同审批(报关)"){
			urlValue = urlValue+"货代合同审批(报关)";
		}else if(currentVal=="货代合同审批(货运)"){
			urlValue = urlValue+"货代合同审批(货运)";
		}else if(currentVal=="汽运合同审批"){
			urlValue = urlValue+"汽运合同审批";
		}else if(currentVal=="铁运合同审批"){
			urlValue = urlValue+"铁运合同审批";
		}else if(currentVal=="港口合同审批"){
			urlValue = urlValue+"港口合同审批";
		}else{
			urlValue="";
		}
		$('#innerContractNo').combogrid({    
			url: urlValue,   
		    panelWidth:700,    
		    method: "get",
		    idField:'innerContractNo',    
		    textField:'innerContractNo',
		    fit : false,
		    singleSelect:true,
			fitColumns : true,
		    columns:[[    
					{field:'innerContractNo',title:'归档编号',sortable:true,width:80},
	 				{field:'contractNo',title:'合同编号',sortable:true,width:80},
	 				{field:'signAffi',title:'签约方',sortable:true,width:80,
	 					formatter: function(value,row,index){
	 						var val;
	 						if(value!=''&&value!=null){
	 							$.ajax({
	 								type:'GET',
	 								async: false,
	 								url:"${ctx}/baseInfo/baseUtil/affiBaseInfoNameByCode/"+value,
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
	 					}
	 				},
	 				{field:'quantity',title:'数量',sortable:true,width:20},
	 				{field:'unitPrice',title:'单价',sortable:true,width:20},
	 				{field:'amount',title:'金额',sortable:true,width:20},
		    ]],
		    onSelect:function(rowIndex, rowData){
				$('#contractNo').val(rowData.contractNo);
				$('#signAffi').combobox('setValue', rowData.signAffi);
				$('#quantity').numberbox('setValue', rowData.quantity);
				$('#unitPrice').numberbox('setValue', rowData.unitPrice);
				$('#amount').numberbox('setValue', rowData.amount);
				$('#singnDate').val(mydatefmt(rowData.singnDate));
		    }
		});
	}
	
	function mydatefmt(val){
		var myDate=new Date(val);
	    var y=myDate.getFullYear();
	    var m=myDate.getMonth()+1;
	    var d=myDate.getDate();
	    return y+"-"+m+"-"+d;
	}
	
	$(function(){
		//var action = '${action}';
		if('${action}' == 'view') {
			$("#tbGoods").hide();
			//将输入框改成只读
			$("#mainform").find(".easyui-validatebox").attr("readonly",true);
			//处理日期控件  移除onclick
			$("#mainform").find(".easyui-validatebox").removeAttr("onclick");
			//将下拉选改成只读
			$("#mainform").find('.easyui-combobox').combobox({
			    disabled:true
			});
			//将数字框改成只读
			$("#mainform").find('.easyui-numberbox').numberbox({
			    disabled:true
			});
			$('#innerContractNo').combogrid('disable');
			//处理日期控件
			$("#mainform").find(".easyui-my97").each(function(index,element){
				$(element).attr("readonly",true);
				$(element).removeClass("easyui-my97");
				$(element).addClass("easyui-validatebox");
			});
		}
	});
	
	
</script>
</body>
</html>