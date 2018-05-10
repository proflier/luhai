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
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.entity.GoodsInformation;
import com.cbmie.lh.baseInfo.service.GoodsInformationService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="/baseInfo/goodsInformation")
/**
 * 商品信息
 * */
public class GoodsInformationController extends BaseController {
	
	@Autowired
	private GoodsInformationService infoService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "baseinfo/lhGoodsInformationList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<GoodsInformation> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = infoService.search(page, filters);
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
		GoodsInformation goodsInformation = new GoodsInformation();
		User currentUser = UserUtil.getCurrentUser();
		goodsInformation.setCreaterNo(currentUser.getLoginName());
		goodsInformation.setCreaterName(currentUser.getName());
		goodsInformation.setCreaterDept(currentUser.getOrganization().getOrgName());
		goodsInformation.setCreateDate(new Date());
		String infoCode = sequenceCodeService.getNextCode("002003").get("returnStr");
		goodsInformation.setInfoCode(infoCode);
		model.addAttribute("goodsInformation", goodsInformation);
		model.addAttribute("action", "create");
		return "baseinfo/lhGoodsInformationForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid GoodsInformation goodsInformation, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!infoService.checkCodeUique(goodsInformation)) {
				String infoCode = sequenceCodeService.getNextCode("002003").get("returnStr");
				goodsInformation.setInfoCode(infoCode);
				currnetSequence = infoCode;
			}
			if (infoService.findByInfoName(goodsInformation) != null) {
				return setReturnData("fail", "商品名称重复", goodsInformation.getId());
			}
			if(goodsInformation.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("002003");
			}
			infoService.save(goodsInformation);
			EhCacheUtils.remove(DictUtils.CACHE_GOODS_NAME );
			return setReturnData("success", currnetSequence!=null?"编码重复，已自动生成不重复编码并保存":"保存成功", goodsInformation.getId(),currnetSequence);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",goodsInformation.getId());
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
		model.addAttribute("goodsInformation", infoService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/lhGoodsInformationForm";
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
	public String update(@Valid @ModelAttribute @RequestBody GoodsInformation goodsInformation, Model model) throws JsonProcessingException {
//		if (!infoService.checkCodeUique(goodsInformation)) {
//			return setReturnData("fail","编码重复",goodsInformation.getId());
//		}
		if (infoService.findByInfoName(goodsInformation) != null) {
			return setReturnData("fail", "商品名称重复", goodsInformation.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		goodsInformation.setUpdaterNo(currentUser.getLoginName());
		goodsInformation.setUpdaterName(currentUser.getName());
		goodsInformation.setUpdateDate(new Date());
		infoService.update(goodsInformation);
		EhCacheUtils.remove(DictUtils.CACHE_GOODS_NAME );
		return setReturnData("success","保存成功",goodsInformation.getId());
	}
	@ModelAttribute
	public void getGoodsInformation(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("goodsInformation", infoService.get(id));
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
		infoService.delete(id);
		EhCacheUtils.remove(DictUtils.CACHE_GOODS_NAME );
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
		model.addAttribute("goodsInformation", infoService.get(id));
		return "baseinfo/lhGoodsInformationDetail";
	}
	
}
