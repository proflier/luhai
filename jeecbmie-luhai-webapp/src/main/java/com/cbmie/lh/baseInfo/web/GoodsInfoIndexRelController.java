package com.cbmie.lh.baseInfo.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
import com.cbmie.lh.baseInfo.entity.GoodsInfoIndexRel;
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.lh.baseInfo.service.GoodsInfoIndexRelService;
import com.cbmie.lh.logistic.entity.OperateExpense;
import com.cbmie.system.entity.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="baseInfo/goodsInfo/rel")
public class GoodsInfoIndexRelController extends BaseController {
	
	@Autowired
	private GoodsInfoIndexRelService relService;
	@Autowired
	private GoodsIndexService indexService;
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="son/{infoId}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(@PathVariable("infoId") Long infoId,HttpServletRequest request) {
		Page<GoodsInfoIndexRel> page = getPage(request);
		relService.getRelByInfo(page, infoId);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加到单跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{infoId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("infoId") Long infoId,Model model) throws ParseException {
		List<Long> indexIds = relService.getIndexsByInfoId(infoId);
		model.addAttribute("infoId", infoId);
		model.addAttribute("indexIds", indexIds);
		return "baseinfo/lhGoodsIndexRels";
	}
	
	/**
	 * 添加到单
	 * 
	 * @param port
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create/indexs/{infoId}", method = RequestMethod.POST)
	@ResponseBody
	public String create(@PathVariable("infoId") Long infoId, @RequestBody List<Long> indexIds) throws JsonProcessingException {
		try {
			for(Long indexId : indexIds){
				GoodsInfoIndexRel rel = new GoodsInfoIndexRel();
				rel.setInfoId(infoId);
				rel.setGoodsIndex(indexService.get(indexId));
				relService.save(rel);
			}
			return setReturnData("success","保存成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",null);
		}
	}
	
	/**
	 * 修改到单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("operateExpense", operateService.get(id));
		model.addAttribute("actionOperate", "update");
		return "logistic/operateExpenseForm";
	}
	
	@ModelAttribute
	public void getOperateExpense(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("operateExpense", operateService.get(id));
		}
	}*/

	/**
	 * 修改到单
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	/*@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OperateExpense operateExpense,Model model) throws JsonProcessingException {
		operateExpense.setUpdateDate(new Date());
		operateService.update(operateExpense);
		return setReturnData("success","保存成功",operateExpense.getId());
	}*/
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		relService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
