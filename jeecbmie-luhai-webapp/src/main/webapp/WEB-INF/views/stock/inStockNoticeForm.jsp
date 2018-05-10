<%@page import="com.cbmie.lh.stock.entity.InStockNotice"%>
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
		<form id="mainform"  action="${ctx}/stock/inStockNotice/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '入库信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>货物来源</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">入库编号</th>
						<td width="30%">
						<input id="noticeNo" name="noticeNo" type="text" value="${inStockNotice.noticeNo }"  readonly="readonly" class="easyui-validatebox" data-options="required:true" />
						<input type="hidden" id="id" name="id" value="${inStockNotice.id }" />
						<input type="hidden" id="relLoginNames" name="relLoginNames" value="${inStockNotice.relLoginNames }"/>
						<input type="hidden" id="selectPurchase" name="selectPurchase" value="" />
						</td>
						<th width="20%">采购合同号</th>
						<td width="30%">
							<input id="purchaseContractNo" name="purchaseContractNo" type="text" value="${inStockNotice.purchaseContractNo }" style="width:60%;" readonly="readonly" class="easyui-validatebox" data-options="required:true" />
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadPurchase()">选择采购合同</a>
						</td>
						
					</tr>
					<tr>
						<th >内部合同号</th>
						<td >
						<input id="innerContractNo" name="innerContractNo" type="text" value="${inStockNotice.innerContractNo }"  style="width:60%;" readonly="readonly" class="easyui-validatebox" data-options="required:true" />
 						</td>
						<th  >供货单位</th>
						<td>
							<mytag:combobox name="deliveryUnit" value="${inStockNotice.deliveryUnit}" type="customer" parameters="10230002" hightAuto="false" permission4User="true"/>
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td >
							<mytag:combobox name="setUnit" value="${inStockNotice.setUnit}" type="customer" parameters="10230001" hightAuto="false" permission4User="true"/>
						</td>
						<th  >交货方式</th>
						<td >
							<mytag:combobox name="deliveryMode" value="${inStockNotice.deliveryMode}" type="dict" parameters="THFS"/>
						</td>
					</tr>
					<tr>
						<th  >交货起期</th>
						<td>
							<input id="deliveryStartDate" name="deliveryStartDate" value="<fmt:formatDate value="${inStockNotice.deliveryStartDate }" />" class="Wdate" />
						</td>
						<th  >交货止期</th>
						<td>
							<input id="deliveryTerm" name="deliveryTerm"   type="text" value="<fmt:formatDate value="${inStockNotice.deliveryTerm }" />"  class="Wdate" />
						</td>
					</tr>
					<tr>
					</tr>
					<tr>
						<th  >数量溢短装</th>
						<td>
							<input id="moreOrLessQuantity" name="moreOrLessQuantity" type="text" value="${inStockNotice.moreOrLessQuantity }" class="easyui-numberbox" data-options="required:true,precision:'2',suffix:'%'"  />
						</td>
						<th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${inStockNotice.businessManager}" type="userTree" />
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea rows="6" cols="90" id="comment" class="easyui-validatebox" name="comment" data-options="validType:['length[0,500]']">${inStockNotice.comment }</textarea>
						</td>
					</tr>	
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >登记人</th>
						<td>${empty inStockNotice.createrName ? sessionScope.user.name : inStockNotice.createrName }</td>
						<th  >登记部门</th>
						<td>${empty inStockNotice.createrDept ? sessionScope.user.organization.orgName : inStockNotice.createrDept }</td>
					</tr>
					<tr>
						<th width="20%" >登记时间</th>
						<td width="30%"><fmt:formatDate value="${ inStockNotice.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th width="20%" >最后修改时间</th>
						<td width="30%"><fmt:formatDate value="${inStockNotice.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				</fieldset>
			</div>
			<div data-options="title: '入库商品', iconCls: false, refreshable: false">
				<%@ include file="/WEB-INF/views/stock/inStockNoticeGoodsList.jsp"%> 
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=InStockNotice.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${inStockNotice.id}" />
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
				successTipNew(data,dg);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#noticeNo').val(data.currnetSequence);
		    	}
		    	var selectPurchase = $("#selectPurchase").val();
		    	if(selectPurchase == '1'&&data.returnFlag=='success'){
					saveGoods();
		    	}
				$.easyui.loaded();
			} 
		});
		
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
		
		$('#deliveryStartDate').bind('click',function(){
		    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'deliveryTerm\')}',onpicked:function(){deliveryTerm.click();}});
		});
		$('#deliveryTerm').bind('click',function(){
		    WdatePicker({doubleCalendar:false,startDate:'%y-{%M}-%d',minDate:'#F{$dp.$D(\'deliveryStartDate\')}',dateFmt:'yyyy-MM-dd',autoPickDate:true});
		});
	
	});
	
	//保存 
	function setGoods(){
		var row = purchase_dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$('#purchaseContractNo').val(row.purchaseContractNo);
		$('#innerContractNo').val(row.innerContractNo);
		$('#moreOrLessQuantity').numberbox('setValue',row.moreOrLessQuantity);
		$('#relLoginNames').val(row.relLoginNames);
		$("#deliveryUnit").combobox('setValue',row.deliveryUnit);
		$("#setUnit").combobox('setValue',row.setUnit);
		$('#businessManager').combotree('setValue',row.businessManager);
		$("#deliveryStartDate").val(row.deliveryStartDate);
		$("#deliveryTerm").val(row.deliveryTerm);
		$("#selectPurchase").val("1");
		saveGoods();
		$("#dlgPurchase").panel('close');
	}
	
	function saveGoods(){
		var inStockNoticeId = $("#id").val();
		var innerContractNo = $("#innerContractNo").val();
		if(inStockNoticeId!=""){
			$.ajax({
				type:'post',
				url:"${ctx}/stock/inStockNoticeGoods/saveGoods/"+innerContractNo+"/"+inStockNoticeId,
				success: function(data){
					dgInStockNoticeGoods.datagrid({
				        url :  '${ctx}/stock/inStockNoticeGoods/getInstockNoticeGoods/'+inStockNoticeId
				    });
					$('#dgInStockNoticeGoods').datagrid('clearSelections');
					$('#dgInStockNoticeGoods').datagrid('reload');
				}
			});
			$("#selectPurchase").val("");
		}
	}
	
	
</script>
</body>
</html>