package com.cbmie.woodNZ.baseInfo.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.baseInfo.entity.WoodGoodsCode;
import com.cbmie.woodNZ.baseInfo.entity.WoodGoodsInfo;
import com.cbmie.woodNZ.baseInfo.service.GoodsInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 商品信息controller
 */
@Controller
@RequestMapping("baseInfo/goodsInfo")
public class GoodsInfoController extends BaseController{
	
	@Autowired
	private GoodsInfoService goodsInfoService;
	
	private String  goodsCode="";//当前商品编码
//	private String  affiliated="";//当前所属级别
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/goodsInfoList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> portList(HttpServletRequest request) {
		Page<WoodGoodsInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = goodsInfoService.search(page, filters);
		return getEasyUIData(page);
	}
	

	/**
	 * 添加商品跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		WoodGoodsInfo info = new WoodGoodsInfo();
		User currentUser = UserUtil.getCurrentUser();
		info.setSqr(currentUser.getName());
		info.setDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("goods", info);
		model.addAttribute("action", "create");
		return "baseinfo/goodsInfoForm";
	}

	/**
	 * 添加商品
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodGoodsInfo info, Model model) throws ParseException, JsonProcessingException {
		boolean change1 = !(goodsCode.equals(info.getCode()));//false 为商品编码没改过
//		boolean change2 = !(affiliated.equals(info.getAffiliated()));//false 为所属级别没改过
		if(change1 && !goodsInfoService.validateGoodsNo(info)){//商品编码重复
			String code = info.getCode();
			info.setCode(goodsCode);
			return setReturnData("fail","保存失败, 编码: "+code+" 已经存在！",info.getId());
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date time=sdf.parse(sdf.format(new Date()));
		info.setSqrq(time);
		goodsInfoService.save(info);
		goodsCode = info.getCode();
		return setReturnData("success","保存成功",info.getId());
	}

	/**
	 * 修改商品跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		goodsCode = goodsInfoService.get(id).getCode();
//		affiliated = goodsInfoService.get(id).getAffiliated();
		model.addAttribute("goods", goodsInfoService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/goodsInfoForm";
	}

	/**
	 * 修改商品
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodGoodsInfo info,Model model) throws JsonProcessingException {
		boolean change1 = !(goodsCode.equals(info.getCode()));//false 为商品编码没改过
//		boolean change2 = !(affiliated.equals(info.getAffiliated()));//false 为所属级别没改过
		if(change1  && !goodsInfoService.validateGoodsNo(info)){//商品编码重复
			String code = info.getCode();
			info.setCode(goodsCode);
			return setReturnData("fail","保存失败, 编码: "+code+" 已经存在！",info.getId());
		}else{
			User currentUser = UserUtil.getCurrentUser();
//			info.setUpdaterNo(currentUser.getLoginName());
			info.setUpdater(currentUser.getName());
			info.setUpdateDate(new Date());
			goodsInfoService.update(info);
			goodsCode = info.getCode();
			return setReturnData("success","保存成功",info.getId());
		}
	}

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		goodsInfoService.delete(id);
		return "success";
	}
	
	/**
	 * 商品明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("goods", goodsInfoService.get(id));
		model.addAttribute("action", "detail");
		return "baseinfo/goodsInfoDetail";
	}
	
	/**
	 * 获取所有商品
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getGoodsList")
	@ResponseBody
	public List<WoodGoodsInfo> getDictByCode() {
		List<WoodGoodsInfo> goodsList = goodsInfoService.getAll();
		return goodsList;
	}
	
	/**
	 * 查看商品编码
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "ckbm", method = RequestMethod.GET)
	public String ckbm(Model model) throws ParseException {
		WoodGoodsCode info = new WoodGoodsCode();
		model.addAttribute("code", info);
		model.addAttribute("action", "ckspbm");
		return "baseinfo/goodsBmForm";
	}
	
	/**
	 * 查看商品编码
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "ckspbm", method = RequestMethod.POST)
	public String ckspbm(@Valid WoodGoodsCode info,Model model) throws ParseException {
		WoodGoodsCode woodGoodsCode = new WoodGoodsCode();
		model.addAttribute("woodGoodsCode", woodGoodsCode);
		model.addAttribute("action", "ckspbm");
		return "baseinfo/goodsBmForm";
	}
	
	
	
	/**
	 * 获取商品类别
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getType")
	@ResponseBody
	public List<WoodGoodsInfo> getTypes() {
		List<WoodGoodsInfo> list = goodsInfoService.getTypes();
		return list;
	}
	
	/**
	 * 获取材种
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "getSpecies")
	@ResponseBody
	public List<WoodGoodsInfo> getSpecies() {
		List<WoodGoodsInfo> list = goodsInfoService.getSpecies();
		return list;
	}
	
	/**
	 * 根据类别设置材种
	 * 
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "setSpecies")
	@ResponseBody
	public List<WoodGoodsInfo> setSpecies(@RequestParam(value = "name") String name ) {
		List<WoodGoodsInfo> list = goodsInfoService.setSpecies(name);
		return list;
	}
	
	/**
	 * 根据类别设置等级
	 * 
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "setClasses")
	@ResponseBody
	public List<WoodGoodsInfo> setClasses(@RequestParam(value = "name") String name ) {
		List<WoodGoodsInfo> list = goodsInfoService.setClasses(name);
		return list;
	}
	
	/**
	 * 根据类别设置宽厚
	 * 
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "setSpec")
	@ResponseBody
	public List<WoodGoodsInfo> setSpec(@RequestParam(value = "name") String name ) {
		List<WoodGoodsInfo> list = goodsInfoService.setSpec(name);
		return list;
	}
	
	/**
	 * 根据类别设置长度
	 * 
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "setLength")
	@ResponseBody
	public List<WoodGoodsInfo> setLength(@RequestParam(value = "name") String name ) {
		List<WoodGoodsInfo> list = goodsInfoService.setLength(name);
		return list;
	}
	
	/**
	 * 根据类别设置品牌
	 * 
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "setBrand")
	@ResponseBody
	public List<WoodGoodsInfo> setBrand(@RequestParam(value = "name") String name ) throws UnsupportedEncodingException {
		List<WoodGoodsInfo> list = goodsInfoService.setBrand(name);
		return list;
	}
	
	
	/**
	 * 根据产品编码获取产品名称
	 */
	@RequestMapping(value = "getNameByCode/{code}")
	@ResponseBody
	public  String getNameByCode(@PathVariable("code") String code) {
		if(StringUtils.isNotBlank(code.trim())){
			return goodsInfoService.getNameByCode(code);
		}else{
			return "";
		}
	}
	
	/**
	 * 根据产品编码获取单片体积（只有板材商品会执行此计算）
	 */
	@RequestMapping(value = "getVolumeByCode/{code}/{affiliated}")
	@ResponseBody
	public  String getVolumeByCode(@PathVariable("code") String code,
			@PathVariable("affiliated") String affiliated) {
		if(StringUtils.isNotBlank(code.trim())){
			String str1 = code.substring(6,8);//四级商品编码，即宽*厚
			String str2 = code.substring(8,10);//五级商品编码，	即长度
			if(str1 != null ){
				Double hss1 = goodsInfoService.getHssByFourCode(str1, affiliated);//通过四级编码查找换算数
				Double hss2 = goodsInfoService.getHssByFiveCode(str2,affiliated);//通过五级编码查找换算数
//				Double hhsDouble1 = Double.valueOf(hss1);
//				Double hhsDouble2 = Double.valueOf(hss2);
				Double volume = hss1*hss2;
				return volume.toString();
			}	
		}
		return "";
	}

	
	
	@ModelAttribute
	public void getGoods(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("woodGoodsInfo", goodsInfoService.get(id));
		}
	}
}
