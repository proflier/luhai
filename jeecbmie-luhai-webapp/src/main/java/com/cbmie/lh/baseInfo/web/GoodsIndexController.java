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
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="/baseInfo/goodsIndex")
/**
 * 商品指标
 * */
public class GoodsIndexController extends BaseController {

	@Autowired
	private GoodsIndexService indexService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "baseinfo/lhGoodsIndexList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<GoodsIndex> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = indexService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "jsonAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsListNoPer(HttpServletRequest request) {
		Page<GoodsIndex> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = indexService.searchNoPermission(page, filters);
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
		GoodsIndex goodsIndex = new GoodsIndex();
		User currentUser = UserUtil.getCurrentUser();
		goodsIndex.setCreaterNo(currentUser.getLoginName());
		goodsIndex.setCreaterName(currentUser.getName());
		goodsIndex.setCreaterDept(currentUser.getOrganization().getOrgName());
		goodsIndex.setCreateDate(new Date());
		String indexCode = sequenceCodeService.getNextCode("002004").get("returnStr");
		goodsIndex.setIndexCode(indexCode);
		model.addAttribute("goodsIndex", goodsIndex);
		model.addAttribute("action", "create");
		return "baseinfo/lhGoodsIndexForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid GoodsIndex goodsIndex, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!indexService.checkCodeUique(goodsIndex)) {
				String indexCode = sequenceCodeService.getNextCode("002004").get("returnStr");
				goodsIndex.setIndexCode(indexCode);
				currnetSequence = indexCode;
			}
			if (indexService.findByIndexName(goodsIndex) != null) {
				return setReturnData("fail", "名称重复", goodsIndex.getId());
			}
			if(goodsIndex.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("002004");
			}
			indexService.save(goodsIndex);
			return setReturnData("success", currnetSequence!=null?"开证申请号重复，已自动生成不重复编码并保存":"保存成功", goodsIndex.getId(),currnetSequence);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",goodsIndex.getId());
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
		model.addAttribute("goodsIndex", indexService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/lhGoodsIndexForm";
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
	public String update(@Valid @ModelAttribute @RequestBody GoodsIndex goodsIndex, Model model) throws JsonProcessingException {
//		if (!indexService.checkCodeUique(goodsIndex)) {
//			return setReturnData("fail","编码重复",goodsIndex.getId());
//		}
		if (indexService.findByIndexName(goodsIndex) != null) {
			return setReturnData("fail", "名称重复", goodsIndex.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		goodsIndex.setUpdaterNo(currentUser.getLoginName());
		goodsIndex.setUpdaterName(currentUser.getName());
		goodsIndex.setUpdateDate(new Date());
		indexService.update(goodsIndex);
		return setReturnData("success","保存成功",goodsIndex.getId());
	}
	@ModelAttribute
	public void getGoodsIndex(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("goodsIndex", indexService.get(id));
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
		indexService.delete(id);
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
		model.addAttribute("goodsIndex", indexService.get(id));
		return "baseinfo/lhGoodsIndexDetail";
	}
	
}
