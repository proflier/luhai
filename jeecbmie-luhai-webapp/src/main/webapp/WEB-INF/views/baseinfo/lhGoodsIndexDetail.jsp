<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
		<div id="mainDiv" class="" data-options="border:false">
			<div data-options="title: '商品指标信息', iconCls: false, refreshable: false" >
				<fieldset class="fieldsetClass" >
				<legend>商品指标信息</legend>
				<table width="98%" class="tableClass" >
					<tr>
						<th width="20%">名称</th>
						<td width="30%">
							${goodsIndex.indexName }
						</td>
						<th width="20%">编码</th>
						<td width="30%">
							${goodsIndex.indexCode }
						</td>
					</tr>
					<tr>
						<th>英文名称</th>
						<td>
							${goodsIndex.indexNameE }
						</td>
						<th>显示顺序</th>
						<td>
							${goodsIndex.showOrder }
						</td>
					</tr>
					<tr>
						<th>单位</th>
						<td >
							${fns:getDictLabelByCode(goodsIndex.unit,'INDEXUNIT','')}
						</td>
						<th>状态</th>
						<td >
							${goodsIndex.status == 0?'停用':'启用'}
						</td>
					</tr>
					<tr>
						<th >备注</th>
						<td colspan="3">
							${goodsIndex.comments }
						</td>
					</tr>
				</table>
				</fieldset>
				<fieldset class="fieldsetClass" >
				<legend>系统信息</legend>
				<table width="98%" class="tableClass">
					<tr>
						<th  >经办人</th>
						<td>
							<input type="hidden" name="createrNo" value="${goodsIndex.createrNo }"/>
							<input type="hidden" name="createrName" value="${goodsIndex.createrName }"/>
							<input type="hidden" name="createrDept" value="${goodsIndex.createrDept }"/>
							<input type="hidden" name="createDate" value="<fmt:formatDate value='${goodsIndex.createDate  }' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							${goodsIndex.createrName }</td>
						<th  >经办部门</th>
						<td>${goodsIndex.createrDept }</td>
						<th  >登记时间</th>
						<td>
							<fmt:formatDate value="${goodsIndex.createDate  }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</table>
				</fieldset>
			</div>
		</div>	
</body>
</html>