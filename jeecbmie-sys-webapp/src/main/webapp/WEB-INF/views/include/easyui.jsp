<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yesurl" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<yesurl:url value="/" var="path" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- easyui皮肤 -->
<link href="${ctx}/static/plugins/easyui/jquery-easyui-theme/<c:out value="${cookie.themeName.value}" default="default"/>/easyui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/plugins/easyui/jquery-easyui-theme/icon.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/plugins/easyui/icons/icon-all.css" rel="stylesheet" type="text/css" />

<!-- step-dc 步骤显示 所需js-->
<%-- <script src="${ctx}/static/plugins/step-dc/js/jquery.min.js" type="text/javascript"></script> --%>
<!-- step-dc 步骤显示 所需js 结束-->
<!-- step-dc 步骤显示-->
<link  href="${ctx}/static/plugins/step-dc/css/step-dc-style1.css" rel="stylesheet" >
<link  href="${ctx}/static/plugins/step-dc/css/step-dc-style2.css" rel="stylesheet" >
<script src="${ctx}/static/plugins/step-dc/js/step-jquery-dc.js"></script>
<!-- step-dc 步骤显示 结束-->
<!-- jquery核心 -->
<script src="${ctx}/static/plugins/easyui/jquery/jquery-1.11.1.min.js"></script>
<!-- easyui核心 -->
<script src="${ctx}/static/plugins/easyui/jquery-easyui-1.3.6/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<!-- jquery扩展 -->
<script type="text/javascript" src="${ctx}/static/plugins/easyui/release/jquery.jdirk.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery-validation/1.11.1/messages_bs_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery-validation/1.11.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/jquery/jquery.jqprint-0.3.js"></script><!-- jquery打印插件 -->
<script type="text/javascript" src="${ctx}/static/plugins/jquery/jquery-migrate-1.1.0.js"></script><!-- jquery迁移辅助插件，可解决版本问题 -->
<script type="text/javascript" src="${ctx}/static/plugins/echarts/echarts.js"></script><!-- echarts组件 -->
<link href="${ctx}/static/plugins/jquery-validation/1.11.1/validate.css" rel="stylesheet" type="text/css" />
<!-- jquery扩展结束 -->

<!-- plupload 扩展 -->
<link href="${ctx}/static/plugins/plupload/queue/css/jquery.plupload.queue.css"  rel="stylesheet" type="text/css"/>
<script src="${ctx}/static/plugins/plupload/plupload.js"></script>
<%-- <script src="${ctx}/static/plugins/plupload/plupload.flash.js"></script> --%>
<script src="${ctx}/static/plugins/plupload/plupload.html4.js"></script>
<script src="${ctx}/static/plugins/plupload/plupload.html5.js"></script>
<script src="${ctx}/static/plugins/plupload/zh_CN.js"></script>
<script src="${ctx}/static/plugins/plupload/queue/jquery.plupload.queue.js"></script>
<!-- plupload 扩展结束 -->

<!-- easyui扩展 -->
<link href="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.progressbar.js"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.slider.js"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.linkbutton.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.validatebox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combo.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combobox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.menu.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.searchbox.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.panel.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.window.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.dialog.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.layout.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tree.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.datagrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.treegrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combogrid.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combotree.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tabs.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.theme.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/datagrid-groupview.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/datagrid-detailview.js" type="text/javascript"></script>
<!--<script src="${ctx}/static/plugins/easyui/release/jeasyui.extensions.all.min.js"></script>-->
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/icons/jeasyui.icons.all.js" type="text/javascript"></script>
<!--<script src="${ctx}/static/plugins/easyui/release/jeasyui.icons.all.min.js"></script>-->
    
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.icons.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.gridselector.js" type="text/javascript"></script>

<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.toolbar.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.comboicons.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.comboselector.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.portal.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jquery.my97.js" type="text/javascript"></script>    
<script src="${ctx}/static/plugins/easyui/jeasyui-extensions/jeasyui.extensions.ty.js"></script>
<!-- easyui扩展结束 -->

<!-- ztree扩展 -->
<script src="${ctx}/static/plugins/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/static/plugins/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<!-- ztree样式 -->
<link href="${ctx}/static/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<!-- ztree扩展结束 -->
<!-- 流程扩展 -->
<script src="${ctx }/static/activitiExtend/activitiExtend.js"></script>
<script src="${ctx }/static/plugins/easyui/common/other.js"></script>
<link href="${ctx}/static/css/userDivFrame.css" rel="stylesheet" type="text/css" />
<script>
//全局的AJAX访问，处理AJAX清求时SESSION超时
$.ajaxSetup({
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    complete:function(XMLHttpRequest,textStatus){
          //通过XMLHttpRequest取得响应头，sessionstatus           
          var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
          if(sessionstatus=="timeout"){
               //跳转的登录页面
               window.location.replace('${ctx}/a/login');
       		}	
    }
});
</script>
<style>
body {
	font-family: '微软雅黑';
}
</style>
