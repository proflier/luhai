package com.cbmie.lh.baseInfo.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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

import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.entity.GoodsType;
import com.cbmie.lh.baseInfo.service.GoodsTypeService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="/baseInfo/goodsType")
public class GoodsTypeController extends BaseController {

	@Autowired
	private GoodsTypeService goodsTypeService;

	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "baseinfo/lhGoodsTypeList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<GoodsType> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = goodsTypeService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		GoodsType goodsType = new GoodsType();
		User currentUser = UserUtil.getCurrentUser();
		goodsType.setCreaterNo(currentUser.getLoginName());
		goodsType.setCreaterName(currentUser.getName());
		goodsType.setCreaterDept(currentUser.getOrganization().getOrgName());
		goodsType.setCreateDate(new Date());
		String typeCode = sequenceCodeService.getNextCode("002002").get("returnStr");
		goodsType.setTypeCode(typeCode);
		model.addAttribute("goodsType", goodsType);
		model.addAttribute("action", "create");
		return "baseinfo/lhGoodsTypeForm";
	}
	 
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid GoodsType goodsType, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!goodsTypeService.checkCodeUique(goodsType)) {
				String typeCode = sequenceCodeService.getNextCode("002002").get("returnStr");
				goodsType.setTypeCode(typeCode);
				currnetSequence = typeCode;
			}
			if (goodsTypeService.findTypeName(goodsType) != null) {
				return setReturnData("fail", "名称重复", goodsType.getId());
			}
			if(goodsType==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("002002");
			}
			goodsTypeService.save(goodsType);
			return setReturnData("success", currnetSequence!=null?"编码重复，已自动生成不重复编码并保存":"保存成功", goodsType.getId(),currnetSequence);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",goodsType.getId());
		}
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("goodsType", goodsTypeService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/lhGoodsTypeForm";
	}
	
	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody GoodsType goodsType, Model model) throws JsonProcessingException {
//		if (!goodsTypeService.checkCodeUique(goodsType)) {
//			return setReturnData("fail","编码重复",goodsType.getId());
//		}
		if (goodsTypeService.findTypeName(goodsType) != null) {
			return setReturnData("fail", "名称重复", goodsType.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		goodsType.setUpdaterNo(currentUser.getLoginName());
		goodsType.setUpdaterName(currentUser.getName());
		goodsType.setUpdateDate(new Date());
		goodsTypeService.update(goodsType);
		return setReturnData("success","保存成功",goodsType.getId());
	}
	@ModelAttribute
	public void getGoodsType(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("goodsType", goodsTypeService.get(id));
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		goodsTypeService.delete(id);
		return setReturnData("success","删除成功",id);
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("goodsType", goodsTypeService.get(id));
		return "baseinfo/lhGoodsTypeDetail";
	}
}
