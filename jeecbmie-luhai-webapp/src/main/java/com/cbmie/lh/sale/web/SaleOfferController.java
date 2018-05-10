package com.cbmie.lh.sale.web;

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
import com.cbmie.lh.sale.entity.SaleOffer;
import com.cbmie.lh.sale.service.SaleOfferService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 销售报盘controller
 */
@Controller
@RequestMapping("sale/saleOffer")
public class SaleOfferController extends BaseController {

	@Autowired
	private SaleOfferService saleOfferService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/saleOfferList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<SaleOffer> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = saleOfferService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		SaleOffer saleOffer = new SaleOffer();
		saleOffer.setCreateDate(new Date());
		saleOffer.setUpdateDate(new Date());
		String offerNo = sequenceCodeService.getNextCode("001029").get("returnStr");
		saleOffer.setOfferNo(offerNo);
		model.addAttribute("saleOffer", saleOffer);
		model.addAttribute("action", "create");
		return "sale/saleOfferForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleOffer saleOffer, Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (saleOfferService.findByNo(saleOffer) != null) {
			String offerNo = sequenceCodeService.getNextCode("001029").get("returnStr");
			saleOffer.setOfferNo(offerNo);
			currnetSequence = offerNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		saleOffer.setCreaterNo(currentUser.getLoginName());
		saleOffer.setCreaterName(currentUser.getName());
		saleOffer.setCreateDate(new Date());
		saleOffer.setCreaterDept(currentUser.getOrganization().getOrgName());
		//调用获取下个sequence方法，将当前sequence保存
		if(saleOffer.getId() == null){
			sequenceCodeService.getNextSequence("001029");
		}
		saleOfferService.save(saleOffer);
		return setReturnData("success", currnetSequence != null ? "报盘单号重复，已自动生成不重复编码并保存" : "保存成功", saleOffer.getId(), currnetSequence);
	}

	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleOffer", saleOfferService.get(id));
		model.addAttribute("action", "update");
		return "sale/saleOfferForm";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleOffer saleOffer, Model model) {
		if (saleOfferService.findByNo(saleOffer) != null) {
			return setReturnData("fail", "报盘单号重复！", saleOffer.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		saleOffer.setUpdaterNo(currentUser.getLoginName());
		saleOffer.setUpdaterName(currentUser.getName());
		saleOffer.setUpdateDate(new Date());
		saleOfferService.update(saleOffer);
		return setReturnData("success", "保存成功", saleOffer.getId());
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		saleOfferService.delete(id);
		return "success";
	}

	/**
	 * 查看明细跳转
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleOffer", saleOfferService.get(id));
		return "sale/saleOfferDetail";
	}

	@ModelAttribute
	public void getSaleOffer(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleOffer", saleOfferService.get(id));
		}
	}

}
