package com.cbmie.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.cbmie.system.service.DictChildService;
import com.cbmie.system.service.DictMainService;
import com.cbmie.system.utils.DictUtils;
@Controller
@RequestMapping(value="system/dictUtil")
public class DictUtilController extends BaseController {

	@Autowired
	private DictMainService dictMainService;
	
	@Autowired
	private DictChildService dictChildService;
	
	/**********************字典下拉相关 start*********************************/
	/**
	 * 根据code获取字典列表
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getDictByCode/{code}")
	@ResponseBody
	public List<DictChild> getDictByCode(@PathVariable("code") String code) {
		DictMain dictMain = dictMainService.findByCode(code);
		List<DictChild> dictChild = dictMain.getDictChild();
		return dictChild;
	}
	
	/**
	 *  根据code和id 返回具体某个字典查询字典name（单选：字典列表对应值的值显示）
	 * @param code 字典code
	 * @param childId 当前value
	 * @return
	 */
	@RequestMapping(value = "getDictNameById/{code}/{value}")
	@ResponseBody
	public String getDictById(@PathVariable("code") String code,@PathVariable("value") String childId) {
		return DictUtils.getDictLabel(childId, code, "");
	}
	
	/**
	 * 根据多选的字符串和字典值返回 多个字符串对应的值(多选：字典列表对应值的值显示)
	 * @param types 多选对应的字符串
	 * @param dict 字典值
	 * @return
	 */
	@RequestMapping(value = "getAllName/{types}/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAllName(@PathVariable("types") String types,@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return dictChildService.findAllTypeNames(types,dict);
		} else {
			return "";
		}
		
	}
	
	/**
	 *  根据code和id 返回具体某个字典查询字典name（单选：字典列表对应值的值显示）
	 * @param code 字典code
	 * @param childId 当前value
	 * @return
	 */
	@RequestMapping(value = "getDictNameByCode/{code}/{value}")
	@ResponseBody
	public String getDictByCode(@PathVariable("code") String code,@PathVariable("value") String childCode) {
		return DictUtils.getDictLabelByCode(childCode, code, "");
	}
}
