<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>  
<body>    
    <p>选定一项或多项然后点击添加或移除(按住shift或ctrl可以多选)，或在选择项上双击进行添加和移除。</p>  
    <div style="text-align: center;" >
    	 值：<input type="text" id="city"name="city" size="40" />
	    <form method="post" name="myform">   
		    <table >    
		        <tr>    
		            <td width="40%">    
		                <select style="width:100px; height:200px"  multiple name="list1" id="list1"  ondblclick="ListBox_Move('list1','list2')">    
		               		<option value="billNo">提单号</option>
		                    <option value="twoTimeBillNo">二次换单提单号</option>    
		                    <option value="downStreamClient">下游客户</option>    
		                    <option value="supplier">供应商</option>    
		                    <option value="ourUnit">我司单位</option>    
		                    <option value="expressNo">快递号</option>    
		                    <option value="expressDate">快递日期</option>    
		                    <option value="expectPortDate">预计到港日期</option>    
		                    <option value="shipmentType">装运类别</option>    
		                    <option value="trueToDate">正本到单日期</option>    
		                    <option value="currency">币种</option>    
		                    <option value="noBoxDay">免箱期天数</option>    
		                    <option value="shipDate">装船日期</option>    
		                    <option value="portName">目的港</option>    
		                    <option value="containerNumber">集装箱数</option>    
		                    <option value="area">地区</option>    
		                    <option value="forwarderPrice">包干费单价</option>    
		                    <option value="numbers">数量</option>    
		                    <option value="numberUnits">数量单位</option>    
		                    <option value="shipName">船名</option>    
		                    <option value="voyage">航次</option>    
		                    <option value="shipCompany">船公司</option>    
		                    <option value="totalP">总件数/根数</option>    
		                    <option value="invoiceDate">发票日期</option>    
		                    <option value="invoiceAmount">发票金额</option>    
		                    <option value="invoiceNo">上游发票号</option>    
		                    <option value="downInvoiceNo">下游发票号</option>    
		                    <option value="giveBillsDate">交单期</option>    
		                    <option value="createDate">登记时间</option>    
		                    <option value="createrName">登记人</option>    
		                    <option value="createrDept">登记部门</option>    
		                </select>    
		            </td>    
		            <td width="15%" align="center">    
		                <input class="easyui-linkbutton" style="width:50%;" value=">>" onclick="ListBox_Move('list1','list2')"/>
		                <br />    
		                <br />    
		                <input type="button" class="easyui-linkbutton" style="width:50%;" value="<<" onclick="ListBox_Move('list2','list1')"/>    
		            </td>    
		            <td width="40%">    
		                <select style="width:100px; height:200px"  multiple name="list2" id="list2"  ondblclick="ListBox_Move('list2','list1')">    
		                </select>    
		            </td>    
		            <td width="5%" align="center">    
		                <input type="button" class="easyui-linkbutton" style="width:100%;" value="∧" onclick="ListBox_Order('list2','up')"/>  
		                <br />    
		                <br /> 
		                <input type="button" class="easyui-linkbutton" style="width:100%;" value="∨" onclick="ListBox_Order('list2','down')"/> 
		            </td>    
		        </tr>    
		    </table>
	    </form>
    </div>      
<script language="JavaScript">      
 function ListBox_Move(listfrom,listto)  
 {  
     var size = $("#" + listfrom + " option").size();  
     var selsize = $("#" + listfrom + " option:selected").size();  
     if(size>0 && selsize>0)  
     {  
         $.each($("#"+listfrom+" option:selected"), function(i,own){  
             $(own).appendTo($("#" + listto));  
             $("#" + listfrom + "").children("option:first").attr("selected",true);   
         });  
     } 
     getvalue(document.myform.list2); 
//      document.myform.city.value = getvalue(document.myform.list2); 
 }  

function getvalue(geto) {  
         var allvalue = "";  
         for (var i = 0; i < geto.options.length; i++) {  
             allvalue += geto.options[i].value + ","; 
             alert(allvalue)
         }  
         $('#city').val(allvalue)
         $('#viewColum').val(allvalue)
     } 

 function ListBox_Order(ListName,action)  
 {  
     var size = $("#"+ListName+" option").size();  
     var selsize = $("#"+ListName+" option:selected").size();  
     if(size > 0 && selsize > 0)  
     {  
         $.each($("#"+ListName+" option:selected"),function(i,own){  
             if(action == "up")  
             {  
                 $(own).prev().insertAfter($(own));  
             }  
             else if(action == "down")//down时选中多个连靠则操作没效果  
             {  
                 $(own).next().insertBefore($(own));  
             }  
         })  
     }  
 }  
 </script>    
</body>    
</html>    