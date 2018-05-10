package com.cbmie.lh.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class Combobox extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对应id、name
	 */
	private String name;
	/**
	 * 对应value
	 */
	private String value;
	/**
	 * 下拉选的类别
	 */
	private String type;
	/**
	 * 是否必填
	 */
	private Boolean required = true;
	private Boolean disabled = false;
	private Boolean hightAuto = false;
	private Integer width = 0;
	/**
	 * 参数
	 */
	private String parameters;
	
	private String functionName;
	
	private String onSelectFunctionName;
	
	private Boolean permission4User = false;
	
	public int doEndTag() throws JspException {
		String ctx = super.pageContext.getRequest().getServletContext().getContextPath();
		JspWriter out = pageContext.getOut();
		
		String url = "";
		String valueField = "";
		String textField = "";
		
		//往来单位
		if("customer".equals(type)) {
			if(permission4User){
				url = ctx + "/baseInfo/baseUtil/jsonBaseInfo4UserPermission?filter_EQS_customerType=" + parameters;
			}else{
				url = ctx + "/baseInfo/baseUtil/jsonBaseInfo?filter_EQS_customerType=" + parameters;
			}
			valueField = "customerCode";
			textField = "customerName";
		} 
		//数据字典
		else if("dict".equals(type)) {
			url = ctx + "/system/dictUtil/getDictByCode/" + parameters;
			valueField = "code";
			textField = "name";
		} 
		//港口
		else if("port".equals(type)) {
			url = ctx + "/baseInfo/baseUtil/jsonGK/";
			valueField = "bm";
			textField = "gkm";
		}
		//商品类别
		else if("goodsType".equals(type)) {
			url = ctx + "/baseInfo/lhUtil/goodsTypeItems/";
			valueField = "typeCode";
			textField = "typeName";
		}
		else {
			return TagSupport.EVAL_PAGE;
		}
		//构建esayui标签
		try {
			out.print("<input class=\"easyui-combobox\" id=\"" + name + "\" name=\"" + name + "\" value=\"" + value + "\" data-options=\"");
	        out.print("url:'" + url + "',");
	        if(required){
	        	 out.print("required:true,");
	        }
	        if(disabled){
	        	out.print("disabled:true,");
	        }
	        if (width > 0) {
	        	out.print("width:" + width + ",");
	        }
	        out.print("method:'get',");
	        out.print("valueField:'" + valueField + "',");
	        out.print("textField:'" + textField + "',");
	        if(StringUtils.isNotBlank(functionName)){
	        	out.print("onChange:function(record){" + functionName + "(record)},");
	        }
	        if(StringUtils.isNotBlank(onSelectFunctionName)){
	        	out.print("onSelect:function(data){" + onSelectFunctionName + "(data)},");
	        }
	        if(hightAuto){
	        	 out.print("panelHeight:'auto',");
	        }
	        out.print("label: 'Language:',");
	        out.print("labelPosition: 'top'\">");
	        out.print("");
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return TagSupport.EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Boolean getHightAuto() {
		return hightAuto;
	}

	public void setHightAuto(Boolean hightAuto) {
		this.hightAuto = hightAuto;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getPermission4User() {
		return permission4User;
	}

	public void setPermission4User(Boolean permission4User) {
		this.permission4User = permission4User;
	}

	public String getOnSelectFunctionName() {
		return onSelectFunctionName;
	}

	public void setOnSelectFunctionName(String onSelectFunctionName) {
		this.onSelectFunctionName = onSelectFunctionName;
	}
	
	
	
	
}
