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
							${inStockNotice.noticeNo }
							<input type="hidden" id="id" name="id" value="${inStockNotice.id }" />
							<input type="hidden" id="relLoginNames" name="relLoginNames" value="${inStockNotice.relLoginNames }"/>
						</td>
						<th width="20%">采购合同号</th>
						<td width="30%">
							${inStockNotice.purchaseContractNo }
						</td>
						
					</tr>
					<tr>
						<th >内部合同号</th>
						<td >
							${inStockNotice.innerContractNo }
 						</td>
						<th  >供货单位</th>
						<td>
							${mytag:getAffiBaseInfoByCode(inStockNotice.deliveryUnit)}
						</td>
					</tr>
					<tr>
						<th  >帐套单位</th>
						<td >
							${mytag:getAffiBaseInfoByCode(inStockNotice.setUnit)}
						</td>
						<th  >交货方式</th>
						<td >
							${fns:getDictLabelByCode(inStockNotice.deliveryMode,'THFS','')}
						</td>
					</tr>
					<tr>
						<th  >交货起期</th>
						<td>
							<fmt:formatDate value="${inStockNotice.deliveryStartDate }" />
						</td>
						<th  >交货止期</th>
						<td>
							<fmt:formatDate value="${inStockNotice.deliveryTerm }" />
						</td>
					</tr>
					<tr>
					</tr>
					<tr>
						<th  >数量溢短装</th>
						<td>
							<c:if test="${!empty inStockNotice.moreOrLessQuantity }">
								${inStockNotice.moreOrLessQuantity }%
							</c:if>	
						</td>
						<th  >业务经办人 </th>
						<td>
							${mytag:getUserByLoginName(inStockNotice.businessManager).name}
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							${inStockNotice.comment }
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
	
</script>
</body>
</html>