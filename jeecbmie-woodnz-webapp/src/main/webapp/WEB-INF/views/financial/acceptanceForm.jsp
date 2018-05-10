<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body onload="">
<form id="mainform" action="${ctx}/financial/acceptance/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<!--移动端截取标志-->
<table width="98%" class="tableClass">
	<tr>
		<th><font color="red">*</font>提单号</th>
		<td>
		<input type="hidden" name="id" value="${id }"/>
		<input type="text" id="invoiceNo" name="invoiceNo" value="${acceptance.invoiceNo }"/>
		</td>
		<th><font color="red">*</font>信用证号</th>
		<td>
		<input type="text" id="creditNo" name="creditNo" value="${acceptance.creditNo }" class="easyui-combobox" data-options="panelHeight:'auto',required:true"/>
		</td>
		<th>银行名称</th>
		<td>
		<input type="text" id="bank" name="bank" value="${acceptance.bank }" class="easyui-validatebox" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th>付款期限</th>
		<td>
		<input type="text" id="prompt" name="prompt" value="${acceptance.prompt }" class="easyui-validatebox" readonly="true" style="background: #eee"/>
		</td>
		<th><font color="red">*</font>押汇金额</th>
		<td>
		<input type="text" id="documentaryBillsMoney" name="documentaryBillsMoney" value="${acceptance.documentaryBillsMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true,onChange:function(newValue,oldValue){getRMB(newValue)}"/>
		</td>
		<th><font color="red">*</font>押汇币种</th>
		<td>
		<input type="text" id="documentaryBillsCurrency" name="documentaryBillsCurrency" value="${acceptance.documentaryBillsCurrency }" class="easyui-validatebox" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>汇率</th>
		<td>
		<input type="text" id="rate" name="rate" value="${acceptance.rate }" class="easyui-numberbox" data-options="precision:4,required:true,onChange:function(newValue,oldValue){getRMB(null, newValue)}"/>
		</td>
		<th><font color="red">*</font>押汇日期</th>
		<td>
		<input type="text" name="documentaryBillsDate" value="<fmt:formatDate value="${acceptance.documentaryBillsDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
		<th><font color="red">*</font>是否最后一次押汇</th>
		<td>
		<input type="radio" name="documentaryBillsLast" value="1" style="margin-top:-2px" <c:if test="${acceptance.documentaryBillsLast eq 1}">checked</c:if>/>是
		<input type="radio" name="documentaryBillsLast" value="0" style="margin-top:-2px" <c:if test="${acceptance.documentaryBillsLast eq 0}">checked</c:if>/>否
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>预计还押汇日期</th>
		<td>
		<input type="text" name="documentaryBillsExpectBackTime" value="<fmt:formatDate value="${acceptance.documentaryBillsExpectBackTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
		<th>实际还押汇日期</th>
		<td>
		<input type="text" name="documentaryBillsRealityBackTime" value="<fmt:formatDate value="${acceptance.documentaryBillsRealityBackTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
		</td>
		<th><font color="red">*</font>押汇利率</th>
		<td>
		<input type="text" name="documentaryBillsRate" value="${acceptance.documentaryBillsRate }" class="easyui-numberbox" data-options="min:0,precision:4,required:true"/>
		</td>
	</tr>
	<tr>
		<th><font color="red">*</font>人民币金额</th>
		<td colspan="5">
		<input type="text" id="rmb" name="rmb" value="${acceptance.rmb }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',required:true"/>
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="5">
		<textarea name="comment" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]'],value:'${acceptance.comment }'"/>
		</td>
	</tr>
</table>
<!--移动端截取标志-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty acceptance.createDate ? now : acceptance.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty acceptance.createrName ? sessionScope.user.name : acceptance.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${acceptance.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    }
	});
	
	$('#invoiceNo').combobox({
		method:'GET',
		panelHeight:'auto',
		required:true,
		url:'${ctx}/logistics/invoiceReg/json',
		valueField : 'invoiceNo',
		textField : 'invoiceNo',
		loadFilter:function(data){
			return data.rows;
		},
		onSelect:function(record){
			$('#creditNo').combobox({
				method:'GET',
				url : '${ctx}/credit/openCredit/json?filter_EQS_contractNo=' + record.contractNo,
				valueField : 'creditNo',
				textField : 'creditNo',
				loadFilter:function(data){
					for (var i = 0; i < data.rows.length; i++) {
		                if (data.rows[i].creditNo == null) {
		                	data.rows.splice(i, i+1); //把未登记信用证号的删除
		                }
		            }
					return data.rows;
				},
				onLoadSuccess:function(){
					var data = $(this).combobox('getData');
					if (data.length > 0) {
						$(this).combobox('select', data[0].creditNo); //默认选中第一项
					}
				},
				onSelect:function(record){
					$.ajax({
						url : "${ctx}/baseinfo/affiliates/getAffiBankInfoById/" + record.bank,
						type : 'get',
						cache : false,
						dataType : 'json',
						success : function(data) {
							$('#bank').val(data.bankName); //银行名称
						}
					});
					$('#prompt').val(record.prompt); //付款期限
					$('#documentaryBillsMoney').numberbox({value:record.creditMoney}); //押汇金额
					$('#documentaryBillsCurrency').val(record.creditType); //押汇币种
					$.ajax({
						url : '${ctx}/baseinfo/exchangerate/getThisNewExchangeRate/' + record.creditType,
						async : false,
						type : 'get',
						cache : false,
						dataType : 'json',
						success : function(data) {
							$('#rate').numberbox({value:data.exchangeRateSelf});
						}
					}); //汇率(最新)
					getRMB();
				}
			}); //信用证号
		}
	}); //提单号
	
	if('${action}' == "update"){
		$('#invoiceNo').combobox("clear");
		window.setTimeout("$('#invoiceNo').combobox('select', '${acceptance.invoiceNo }');", 1000); //需要页面元素进入一定的状态才能使用，所以延迟1秒执行
	}
});

function getRMB(newDB, newRate){
	$('#rmb').numberbox({value : (newDB==null?$('#documentaryBillsMoney').numberbox('getValue'):newDB) * (newRate==null?$('#rate').numberbox('getValue'):newRate)});
}
</script>
</body>
</html>