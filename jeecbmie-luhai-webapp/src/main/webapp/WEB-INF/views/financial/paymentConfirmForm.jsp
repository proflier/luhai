<%@page import="com.cbmie.lh.financial.entity.PaymentConfirm"%>
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
		<form id="mainform"  action="${ctx}/financial/paymentConfirm/${action}" method="post">
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '付款确认单', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>付款信息</legend>
				<table id="tableConfirm" width="97%" class="tableClass" >
					<tr>
						<td colspan="6" align="center" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">付款申请单</td>
					</tr>
					<tr>
						<td colspan="3" align="left" style="font-weight: bold;border: none;">
							<input id="id" name="id" type="hidden"  value="${paymentConfirm.id }" />
							<input id="interContractNo" name="interContractNo" type="hidden"  value="${paymentConfirm.interContractNo }" />
							<input id="goodsClasses" name="goodsClasses" type="hidden"  value="${paymentConfirm.goodsClasses }"/>
						</td>
						<td  colspan="3" align="right" style="font-weight: bold;border: none;">
								
							付款单号:<input id="confirmNo" name="confirmNo" type="text" value="${paymentConfirm.confirmNo }" readonly="readonly" class="easyui-validatebox"  data-options="required:true"/>
						</td>
					</tr>
					<tr >
					  	<td rowspan="3" >收款单位</td>
					  	<th >名称</th>
					  	<td colspan="3" >
					  		<input id="receiveUnitName" name="receiveUnitName"  value="${paymentConfirm.receiveUnitName }" class="easyui-validatebox"  />
					 	</td>
					  	<td  rowspan="7" align="center"  >付款说明:<br/>
					  		<textarea rows="8" cols="50" id="remarks" class="easyui-validatebox" name="remarks" data-options="validType:['length[0,216]']">${paymentConfirm.remarks }</textarea>
					  	</td>
					 </tr>
					 <tr >
						 <th >银行</th>
						 <td colspan="3"  >
						  	<input id="remitBank" name="remitBank" type="text" value="${paymentConfirm.remitBank }" class="easyui-validatebox" />
						 </td>
					 </tr>
					  <tr >
						 <th >账号</th>
						 <td  >
						 	<input id="remitAccount" name="remitAccount" type="text" value="${paymentConfirm.remitAccount }" readonly="readonly"  class="easyui-validatebox" data-options="prompt:'选择银行后自动加载'" />
						 </td>
						 <th  >付款类别</th>
						<td>
							<input id="contractCategory" name="contractCategory" type="text" value="${paymentConfirm.contractCategory }" class="easyui-validatebox" />
						</td>
					 </tr>
					 <tr >
						 <td rowspan='2' width="16.7%" >付款金额</td>
						 <th width="8.3%">小写</th>
						 <td width="16.7%">
						  	<input id="payMoneyXiao" name="payMoneyXiao" type="text" onchange="paymentXiaoClick()" value="<fmt:formatNumber value="${paymentConfirm.payMoneyXiao }" pattern="#.00"/>" class="easyui-validatebox" data-options="required:true" />
						 </td>
						 <th width="8.3%">币种</th>
						 <td width="16.7%">
						  	<mytag:combobox name="currency" value="${paymentConfirm.currency}" type="dict" parameters="currency" functionName="changeCurrenty"/>
						 </td>
					 </tr>
					 <tr >
				 		<th >大写</th>
					  	<td colspan="3" >
					  		<input style="width:60%;" id="payMoneyDa" name="payMoneyDa" type="text" readonly="readonly" value="${paymentConfirm.payMoneyDa }" class="easyui-validatebox"/>
					 	 </td>
					 </tr>
					 <tr >
						 <td rowspan='2'>付款信息</td>
						 <th >付款内容</th>
						 <td >
						 	<input id="payContent" name="payContent" type="text" value="${paymentConfirm.payContent }" class="easyui-validatebox" />	
						 </td>
						 <th >付款方式</th>
						 <td >
						  	<mytag:combobox name="payType" value="${paymentConfirm.payType}" type="dict" parameters="paymentMethod"/>
						 </td>
					 </tr>
					 <tr >
				 		<th >付款单位</th>
					  	<td  >
					  		<mytag:combobox name="payUnit" value="${paymentConfirm.payUnit}" type="customer" parameters="10230001" required="true"/>
					 	 </td>
					 	 <th  >业务经办人 </th>
						<td>
							<mytag:combotree name="businessManager" value="${paymentConfirm.businessManager}"  required="true"  type="userTree" />
						</td>
					 </tr>
					 <tr>
						<td align="right" style="border: none;" height="40px;" width="15%">制单人:</td>
						<td align="left" style="border: none;">${paymentConfirm.createrName }</td>
						<td align="right" style="border: none;">制单部门:</td>
						<td align="left"  style="border: none;">${paymentConfirm.createrDept }</td>
						<td align="right" style="border: none;">确认日期:</td>
						<td align="left" style="border: none;">
							<input id="confirmDate" name="confirmDate"   type="text" value="<fmt:formatDate value="${paymentConfirm.confirmDate }" />" class="easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  data-options="required:true"/>
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>费用分摊</legend>
				<%@ include file="/WEB-INF/views/financial/paymentConfirmChildList.jsp"%> 
				</fieldset>
			</div>
			<div data-options="title: '附件信息', iconCls: false, refreshable: false">
				<input id="accParentEntity" type="hidden"  value="<%=PaymentConfirm.class.getName().replace(".","_") %>" />
				<input id="accParentId" type="hidden" value="${paymentConfirm.id}" />
				<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
			</div>
		</div>	
<%-- 		<c:if test="${not empty paymentConfirm.processInstanceId}"> --%>
<%-- 			<act:histoicFlow procInsId="${paymentConfirm.processInstanceId }" /> --%>
<%-- 		</c:if> --%>
		</form>
		
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
		
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
		    		 $.easyui.loading({ msg: "正在加载..." });
		    	}
				//alert(isValid);
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
				successTipNew(data,dg);
				var data = eval('(' + data + ')');
		    	if(data.currnetSequence!=null){
		    		$('#confirmNo').val(data.currnetSequence);
		    	}
				$.easyui.loaded();
			} 
		});
		
		//收款单位
		$('#receiveUnitName').combobox({
			required : true,
			method: "get",
			url:'${ctx}/financial/paymentConfirm/getListNoYH',
			valueField : 'customerCode',
			textField : 'customerName',
			onSelect:function(record){
				$('#remitAccount').val("");
				selectBank(record.id);
				$('#remitBank').combobox('clear');
			}
		});
		
		//付款类别
		$('#contractCategory').combobox({
	 		panelHeight:'auto',
			method: "get",
			url:'${ctx}/system/dictUtil/getDictByCode/htlb',
		    valueField:'code',
		    textField:'name', 
		    required : true,
		});
		
		
		//银行信息初始化 用来回显
		$('#remitBank').combobox({
// 			panelHeight : 'auto',
			required : true,
			method: "get",
			url:'${ctx}/baseInfo/baseUtil/jsonBankInfo',
			valueField : 'id',
			textField : 'bankName',
			prompt:'请先选择名称',
			onSelect:function(record){
				$('#remitAccount').val(record.bankNO);
			}
		});
		
		//付款方式
		$('#payType').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dictUtil/getDictByCode/paymentMethod',
			valueField : 'code',
			textField : 'name'
		});
		
		
		//付款内容
		$('#payContent').combobox({
// 			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dictUtil/getDictByCode/paymentContent',
			valueField : 'code',
			textField : 'name',
			onChange :function(newdata,olddata){
				if(olddata!=""){
					if('${paymentConfirm.id }'!=''){
						parent.$.messager.confirm('提示', '更改后将清空子表，是否继续？', function(r){
							if (r){
								$.ajax({
									type:'get',
									url:"${ctx}/financial/paymentConfirmChild/deleteByPid/${paymentConfirm.id }",
									success: function(data){
										dgChild.datagrid("reload");
									}
								});
								$.ajax({
									type:'get',
									url:"${ctx}/financial/paymentConfirm/updateById/${paymentConfirm.id }/"+newdata,
									success: function(data){
									}
								});
							}else {
								$('#payContent').combobox('setValue',$('#payContent').val());
// 								$(".messager-window").window('close');
// 								$(".messager-body").window('close');
							} 
						});
					}
				}
			}
		});
	});
	
	//过滤我司单位的银行信息
	function selectBank(pid){
		$('#remitBank').combobox({
// 			panelHeight : 'auto',
			required : true,
			method: "get",
			url : '${ctx}/baseInfo/baseUtil/selectBank/'+pid,
			valueField : 'id',
			textField : 'bankName',
			onChange:function (data) {
				$('#remitAccount').val(data);
			}
		});
	}
	
	function paymentXiaoClick(){
		var num = $("#payMoneyXiao").val();
		var currency = $("#currency").combobox('getValue');
		if(currency=='10250002'){
			$("#payMoneyDa").val("美元"+DX(num));		
		}else{
			$("#payMoneyDa").val(DX(num));
		}
		
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
	
	 function DX(n) {
	        if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
	            return "数据非法";
	        var unit = "千百拾亿千百拾万千百拾元角分", str = "";
	            n += "00";
	        var p = n.indexOf('.');
	        if (p >= 0)
	            n = n.substring(0, p) + n.substr(p+1, 2);
	            unit = unit.substr(unit.length - n.length);
	        for (var i=0; i < n.length; i++)
	            str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
	        return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
	}
	
	 function changeCurrenty(record){
		 var payMoneyDa = $("#payMoneyDa").val();
		 if(record=='10250002'){
			 if(payMoneyDa.substring(0,2)!='美元'){
			 	$("#payMoneyDa").val("美元"+payMoneyDa);
			 }
		 }else{
			 payMoneyDa = payMoneyDa.replace("美元","");
			 $("#payMoneyDa").val(payMoneyDa);
		 }
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
	
	
	$(function(){
		//var action = '${action}';
		if('${action}' == 'view') {
			$("#uploadArea").hide();
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