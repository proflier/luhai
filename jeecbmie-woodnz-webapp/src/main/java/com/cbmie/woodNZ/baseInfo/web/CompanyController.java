package com.cbmie.woodNZ.baseInfo.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.baseInfo.entity.WoodBusinessCompany;
import com.cbmie.woodNZ.baseInfo.service.CompanyService;

/**
 * 商品信息controller
 */
@Controller
@RequestMapping("baseInfo/company")
public class CompanyController extends BaseController{
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/companyList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> portList(HttpServletRequest request) {
		Page<WoodBusinessCompany> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = companyService.search(page, filters);
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
		User currentUser = UserUtil.getCurrentUser();
		WoodBusinessCompany info = new WoodBusinessCompany();
		//info.setSqrq(time);
		//info.setSqr(currentUser.getName());
		//info.setDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("company", info);
		model.addAttribute("action", "create");
		return "baseinfo/companyForm";
	}

	/**
	 * 添加商品
	 * 
	 * @param port
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodBusinessCompany info, Model model) {
		companyService.save(info);
		return "success";
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
		model.addAttribute("company", companyService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/companyForm";
	}

	/**
	 * 修改商品
	 * 
	 * @param port
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodBusinessCompany info,Model model) {
		User currentUser = UserUtil.getCurrentUser();
		//info.setUpdaterNo(currentUser.getLoginName());
		info.setUpdater(currentUser.getName());
		info.setUpdateDate(new Date());
		companyService.update(info);
		return "success";
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
		companyService.delete(id);
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
		model.addAttribute("goods", companyService.get(id));
		model.addAttribute("action", "detail");
		return "baseinfo/companyDetail";
	}
	
	/**
	 * 获取所有商品
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getGoodsList")
	@ResponseBody
	public List<WoodBusinessCompany> getDictByCode() {
		List<WoodBusinessCompany> goodsList =  companyService.getAll();
		return goodsList;
	}
	
	@ModelAttribute
	public void getGoods(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("companyId", companyService.get(id));
		}
	}
}
