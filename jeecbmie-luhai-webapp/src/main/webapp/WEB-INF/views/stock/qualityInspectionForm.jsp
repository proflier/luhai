<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.cbmie.lh.stock.entity.QualityInspection" %>
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
<form id="mainform"  action="${ctx}/stock/qualityInspection/${action}" method="post">
<input type="hidden" id="id" name="id" value="${qualityInspection.id }" />
<div id="mainDiv" class="" data-options="border:false">
	<div data-options="title: '基本信息', iconCls: false, refreshable: false" >
		<fieldset class="fieldsetClass" >
		<legend>基本信息</legend>
		<table width="98%" class="tableClass" >
			<tr>
				<th width="20%">质检编码</th>
				<td width="30%">
					<input id="inspectionNo" name="inspectionNo" type="text" value="${qualityInspection.inspectionNo }" readonly="readonly" class="easyui-validatebox" data-options="required:true"/>
				</td>
				<th width="20%">证书编号</th>
				<td width="30%">
					<input id="certificateNo" name="certificateNo" type="text" value="${qualityInspection.certificateNo }" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>合同号</th>
				<td>
					<input id="contractNo" name="contractNo" type="text" value="${qualityInspection.contractNo }" class="easyui-validatebox" data-options="required:true"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toConfigList()">合同配置</a>
				</td>
				<th>提单号/放货编号</th>
				<td>
					<input id="billOrReleaseNo" name="billOrReleaseNo" type="text" value="${qualityInspection.billOrReleaseNo }"  class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>发票号码</th>
				<td>
					<input id="invoiceNo" name="invoiceNo" type="text" value="${qualityInspection.invoiceNo }"  class="easyui-validatebox"/>
				</td>
				<th>检验单位</th>
				<td>
					<mytag:combobox name="inspectionUnit" value="${qualityInspection.inspectionUnit}" type="customer" parameters="10230013" width="200"/>
				</td>
			</tr>
			<tr>
				<th>送检日期</th>
				<td>
					<input name="sendInspectionDate" value="<fmt:formatDate value='${qualityInspection.sendInspectionDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
				<th>出检日期</th>
				<td>
					<input name="outInspectionDate" value="<fmt:formatDate value='${qualityInspection.outInspectionDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>采验方式</th>
				<td>
					<mytag:combobox name="miningTestWay" value="${qualityInspection.miningTestWay}" type="dict" parameters="MININGTESTWAY"/>
				</td>
				<th>检验方式</th>
				<td>
					<mytag:combobox name="inspectionWay" value="${qualityInspection.inspectionWay}" type="dict" parameters="INSPECTIONWAY"/>
				</td>
			</tr>
			<tr>
				<th>化验员</th>
				<td>
					<input name="labTechnicians" type="text" value="${qualityInspection.labTechnicians }"  class="easyui-validatebox"/>
				</td>
				<th>数量合计</th>
				<td>
					<input type="text" id="numTotal" name="numTotal" value="${qualityInspection.numTotal }" class="easyui-numberbox" data-options="precision:2"/>
				</td>
			</tr>
			<tr>
				<th>业务经办人</th>
				<td>
					<mytag:combotree name="businessManager" value="${qualityInspection.businessManager}" type="userTree" width="200"/>
				</td>
				<th>帐套单位</th>
				<td>
					<mytag:combobox name="setUnit" value="${qualityInspection.setUnit}" type="customer" parameters="10230001" width="200"/>
				</td>
			</tr>
			<tr>
				<th>制单日期</th>
				<td colspan="3">
					<jsp:useBean id="now" class="java.util.Date" scope="page"/>
					<input name="billDate" value="<fmt:formatDate value='${empty qualityInspection.billDate ? now : qualityInspection.billDate }'/>" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" data-options="required:true"/>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
			<legend>系统信息</legend>
			<table width="98%" class="tableClass">
				<tr>
					<th>登记人</th>
					<td>${empty qualityInspection.createrName ? sessionScope.user.name : qualityInspection.createrName }</td>
					<th>登记部门</th>
					<td>${empty qualityInspection.createrDept ? sessionScope.user.organization.orgName : qualityInspection.createrDept }</td>
				</tr>
				<tr>
					<th width="20%">登记时间</th>
					<td width="30%"><fmt:formatDate value="${ qualityInspection.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th width="20%">最后修改时间</th>
					<td width="30%"><fmt:formatDate value="${qualityInspection.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div data-options="title: '质检商品', iconCls: false, refreshable: false">
		<%@ include file="/WEB-INF/views/stock/qualityGoodsList.jsp"%>
	</div>
	<div data-options="title: '附件信息', iconCls: false, refreshable: false">
		<input id="accParentEntity" type="hidden"  value="<%=QualityInspection.class.getName().replace(".","_") %>" />
		<input id="accParentId" type="hidden" value="${qualityInspection.id}" />
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>
</div>	
</form>
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
		    		$('#inspectionNo').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
			} 
		});
		
		var tabIndex = 0;
		$('#mainDiv').tabs({
		    onSelect:function(title,index){
		    	if( $('#mainform').form('validate') && (!$("#id").val() == '')){//主表校验通过且已经保存数据
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
	
	var contractGoodsData = [];
	//弹窗加载采购、销售合同列表
	function toConfigList(){
		var	configList=$("#dlg_quality_form").dialog({
		    title: '合同列表',
		    width: 680,
		    height: 450,
		    href:'${ctx}/baseInfo/lhUtil/selectContract',
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					var row = dg_config.datagrid('getSelected');
					if(rowIsNull(row)) return;
					parent.$.messager.progress({
				        title : '提示',
				        text : '数据处理中，请稍后....'
				    });
					
					$('#contractNo').val(row.contractNo == null ? row.purchaseContractNo : row.contractNo);//合同编号
					$('#businessManager').combotree('setValue', row.businessManager);//业务经办人
					$('#setUnit').combobox('setValue', row.setUnit);//账套单位
					if (row.contractNo == null) {
						contractGoodsData = row.purchaseContractGoodsList;
					} else {
						contractGoodsData = row.saleContractSubList;
					}
					
					parent.$.messager.progress('close');
					configList.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					configList.panel('close');
				}
			}]
		});
	}

</script>
</body>
</html>