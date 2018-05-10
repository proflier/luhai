<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<%@ taglib prefix="act" tagdir="/WEB-INF/tags/act" %>
<style>
</style>
</head>

<body>
		<form id="mainform"  action="${ctx}/financial/paymentConfirm/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '付款确认单', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>基本信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th >付款合同号</th>
						<td colspan="3">
							<input id="id" name="id" type="hidden"  value="${paymentConfirm.id }" />
							<input id="interContractNo" name="interContractNo" type="hidden"  value="${paymentConfirm.interContractNo }" />
							<input id="contractNo" name="contractNo" readonly="readonly" type="text" value="${paymentConfirm.contractNo }" class="easyui-validatebox" data-options="required:true"/>
							<input id="goodsClasses" name="goodsClasses" type="hidden"  value="${paymentConfirm.goodsClasses }"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="loadContract()">选择采购合同</a>
						</td>
					</tr>
					<tr>
						<th width="20%" >付款单号</th>
						<td width="30%">
							<input id="confirmNo" name="confirmNo" type="text" value="${paymentConfirm.confirmNo }" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<th  width="20%">确认日期</th>
						<td width="30%">
							<input id="confirmDate" name="confirmDate"   type="text" value="<fmt:formatDate value="${paymentConfirm.confirmDate }" />" datefmt="yyyy-MM-dd" 
								class="easyui-my97" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<th width="20%" >收款单位名称</th>
						<td width="30%">
							<input id="receiveUnitName" name="receiveUnitName" type="text" value="${paymentConfirm.receiveUnitName }" class="easyui-validatebox"  />
						</td>
						<th  width="20%">汇入地点</th>
						<td width="30%">
							<input id="remitArea" name="remitArea" type="text" value="${paymentConfirm.remitArea }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th width="20%" >汇入行名称</th>
						<td width="30%">
							<input id="remitBank" name="remitBank" type="text" value="${paymentConfirm.remitBank }" class="easyui-validatebox" />
						</td>
						<th  width="20%">账号</th>
						<td width="30%">
							<input id="remitAccount" name="remitAccount" type="text" value="${paymentConfirm.remitAccount }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th width="20%" >付款金额(大写)</th>
						<td width="30%">
							<input id="payMoneyDa" name="payMoneyDa" type="text" value="${paymentConfirm.payMoneyDa }" class="easyui-validatebox"/>
						</td>
						<th  width="20%">小写</th>
						<td width="30%">
							<input id="payMoneyXiao" name="payMoneyXiao" type="text" onchange="paymentXiaoClick()" 
							value="${paymentConfirm.payMoneyXiao }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th width="20%" >品名</th>
						<td width="30%">
							<input id="goodsName" name="goodsName" type="text" value="${paymentConfirm.goodsName }" class="easyui-validatebox" />
						</td>
						<th  width="20%">数量</th>
						<td width="30%">
							<input id="goodsNum" name="goodsNum" type="text" value="${paymentConfirm.goodsNum }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th width="20%" >付款方式</th>
						<td width="30%">
							<input id="payType" name="payType" type="text" value="${paymentConfirm.payType }" class="easyui-validatebox" />
						</td>
						<th  width="20%">付款内容</th>
						<td width="30%">
							<input id="payContent" name="payContent" type="text" value="${paymentConfirm.payContent }" class="easyui-validatebox"  />
						</td>
					</tr>
					<tr>
						<th >备注</th>
						<td colspan="3">
							<textarea rows="3" cols="50" id="remarks" name="remarks" value="${paymentConfirm.remarks }"></textarea>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >经办人</th>
						<td>${paymentConfirm.createrName }</td>
						<th  >经办部门</th>
						<td>${paymentConfirm.createrDept }</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
		<c:if test="${not empty paymentConfirm.processInstanceId}">
			<act:histoicFlow procInsId="${paymentConfirm.processInstanceId }" />
		</c:if>
		</form>
		
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
			} 
		});
		
		
		//付款方式
		$('#payType').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/paymentMethod',
			valueField : 'id',
			textField : 'name'
		});
		
		
		//付款内容
		$('#payContent').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/downMenu/getDictByCode/paymentContent',
			valueField : 'id',
			textField : 'name'
		});
	});
	function paymentXiaoClick(){
		var num = $("#payMoneyXiao").val();
		$("#payMoneyDa").val(NoToChinese(num));
	}
	function NoToChinese(num) { 
		if (!/^\d*(\.\d*)?$/.test(num)) { alert("Number is wrong!"); return "Number is wrong!"; } 
		var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); 
		var BB = new Array("", "拾", "佰", "仟", "万", "亿", "点", ""); 
		var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = ""; 
		for (var i = a[0].length - 1; i >= 0; i--) { 
		switch (k) { 
		case 0: re = BB[7] + re; break; 
		case 4: if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$").test(a[0])) 
		re = BB[4] + re; break; 
		case 8: re = BB[5] + re; BB[7] = BB[5]; k = 0; break; 
		} 
		if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0) re = AA[0] + re; 
		if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re; k++; 
		} 

		if (a.length > 1) //加上小数部分(如果有小数部分) 
		{ 
		re += BB[6]; 
		for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)]; 
		} 
		return re; 
		} 
	function loadContract(){
		dlgContract=$("#dlgContract").dialog({
		  	width: 800,    
		    height: 400, 
		    title: '选择采购合同',
		    href:'${ctx}/financial/paymentConfirm/toContract',
		    modal:true,
		    closable:true,
		    style:{borderWidth:0},
		    buttons:[{
				text:'确认',iconCls:'icon-ok',
				handler:function(){
					initContract();
					dlgContract.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlgContract.panel('close');
				}
			}]
		});

		
	}
</script>

</body>
</html>