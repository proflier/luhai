package com.cbmie.lh.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class Combotree extends TagSupport {

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
	private Integer width = 0;
	
	private String onSelectFunctionName;
	
	public int doEndTag() throws JspException {
		String ctx = super.pageContext.getRequest().getServletContext().getContextPath();
		JspWriter out = pageContext.getOut();
		
		String url = "";
		String idField = "";
		String textField = "";
		String parentField = "";
		String onBeforeSelect = "";
		
		//往来单位
		if ("userTree".equals(type)) {
//			url = ctx + "/workflow/getCompanyUser";
			url = ctx + "/workflow/getCompanyUserAll";
			idField = "loginName";
			textField = "name";
			parentField = "pid";
			onBeforeSelect = "function(node){if(node.type!='2'){return false;}}";
		} else if ("countryAreaTree".equals(type)) {
			url = ctx + "/system/countryArea/json";
			idField = "id";
			textField = "name";
			parentField = "pid";
			onBeforeSelect = "function(node){}";
		} else {
			return TagSupport.EVAL_PAGE;
		}
		//构建esayui标签
		try {
			out.print("<input class=\"easyui-combotree\" id=\"" + name + "\" name=\"" + name + "\" value=\"" + value + "\" data-options=\"");
	        out.print("url:'" + url + "',");
	        out.print("method:'GET',");
	        if(required){
	        	 out.print("required:true,");
	        }
	        if(disabled){
	        	out.print("disabled:true,");
	        }
	        if (width > 0) {
	        	out.print("width:" + width + ",");
	        }
	        out.print("idField:'" + idField + "',");
	        out.print("textFiled:'" + textField + "',");
	        out.print("parentField:'" + parentField + "',");
	        out.print("onBeforeSelect:" + onBeforeSelect + ",");
	        out.print("animate:true,");
	        if(StringUtils.isNotBlank(onSelectFunctionName)){
	        	out.print("onSelect:function(node){" + onSelectFunctionName + "(node)},");
	        }
	        out.print("onHidePanel:function(){} \">");
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

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getOnSelectFunctionName() {
		return onSelectFunctionName;
	}

	public void setOnSelectFunctionName(String onSelectFunctionName) {
		this.onSelectFunctionName = onSelectFunctionName;
	}

}
