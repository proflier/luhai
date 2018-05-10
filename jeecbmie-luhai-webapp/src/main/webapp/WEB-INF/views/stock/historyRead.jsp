<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/plugins/artTemplate/test/js/ajaxfileupload.js"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
<input  type="file" id="file" name="file" />
<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="ajaxFileUpload()" plain="true">上传</a> 
</div>
<script type="text/javascript">

//上传
function ajaxFileUpload(){
		parent.$.messager.progress({  
	        title : '提示',  
	        text : '数据处理中，请稍后....'  
	    });
		//重新绑定change事件
		$("#file").change(function(){  
            var filename = $(this).val();  
            $("#file").val(filename);  
        });		
		$.ajaxFileUpload({
            url:"${ctx}/stock/inStock/readExcel/",//用于文件上传的服务器端请求地址
            secureuri:false ,//一般设置为false
            fileElementId:'file',//文件上传控件的id属性  <input type="file" id="file" name="file" />
            dataType: 'JSON',//返回值类型 一般设置为json
            cache:false,
            success: function (data, status){  //服务器成功响应处理函数
//             	if(data=='success'){
// 					parent.$.messager.show({ title : "提示",msg: "上传成功！", position: "bottomRight" });
//             	}else{
//             		parent.$.messager.show({ title : "提示",msg: "数据错误，上传失败！", position: "bottomRight" });
//             	}
            	var data = eval('(' + data + ')');
            	if(data.returnFlag=='success'){
            		parent.$.messager.show({ title : "提示",msg: data.returnMsg, position: "bottomRight" });
            	}else if(data.returnFlag=='fail'){
            		parent.$.messager.alert(data.returnMsg);
            	}
				parent.$.messager.progress('close');
            },
            complete: function(xmlHttpRequest) {
            	//重新绑定change事件
            	$("#file").replaceWith('<input type="file" id="file" name="file"/>');
            	$("#file").on("change", function(){  
                    var filename = $(this).val();  
                    $("#file").val(filename);  
                });
            },
            error: function (data, status, e){//服务器响应失败处理函数
            	parent.$.messager.alert({ title : "提示",msg: "服务器无响应，上传失败！", position: "bottomRight" });
				parent.$.messager.progress('close');
            }
        });
    return false;
}

</script>
</body>
</html>