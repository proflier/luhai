package com.cbmie.lh.stock.web;

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
import com.cbmie.lh.stock.entity.QualityInspection;
import com.cbmie.lh.stock.service.QualityInspectionService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 质检controller
 */
@Controller
@RequestMapping("stock/qualityInspection")
public class QualityInspectionController extends BaseController {

	@Autowired
	private QualityInspectionService qualityInspectionService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/qualityInspectionList";
	}

	/**
	 * 获取质检json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<QualityInspection> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = qualityInspectionService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加质检跳转
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		QualityInspection qualityInspection = new QualityInspection();
		qualityInspection.setCreateDate(new Date());
		qualityInspection.setUpdateDate(new Date());
		String inspectionNo = sequenceCodeService.getNextCode("001023").get("returnStr");
		qualityInspection.setInspectionNo(inspectionNo);
		model.addAttribute("qualityInspection", qualityInspection);
		model.addAttribute("action", "create");
		return "stock/qualityInspectionForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid QualityInspection qualityInspection, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (qualityInspectionService.findByNo(qualityInspection) != null) {
			String inspectionNo = sequenceCodeService.getNextCode("001023").get("returnStr");
			qualityInspection.setInspectionNo(inspectionNo);
			currnetSequence = inspectionNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		qualityInspection.setCreaterNo(currentUser.getLoginName());
		qualityInspection.setCreaterName(currentUser.getName());
		qualityInspection.setCreateDate(new Date());
		qualityInspection.setCreaterDept(currentUser.getOrganization().getOrgName());
		//调用获取下个sequence方法，将当前sequence保存
		if(qualityInspection.getId()==null){
			sequenceCodeService.getNextSequence("001023");
		}
		qualityInspectionService.save(qualityInspection);
		return setReturnData("success", currnetSequence!=null?"质检号重复，已自动生成不重复编码并保存":"保存成功", qualityInspection.getId(),currnetSequence);
	}

	/**
	 * 修改质检跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("qualityInspection", qualityInspectionService.get(id));
		model.addAttribute("action", "update");
		return "stock/qualityInspectionForm";
	}

	/**
	 * 修改质检
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody QualityInspection qualityInspection, Model model) {
		if (qualityInspectionService.findByNo(qualityInspection) != null) {
			return setReturnData("fail", "质检号重复！", qualityInspection.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		qualityInspection.setUpdaterNo(currentUser.getLoginName());
		qualityInspection.setUpdaterName(currentUser.getName());
		qualityInspection.setUpdateDate(new Date());
		qualityInspectionService.update(qualityInspection);
		return setReturnData("success", "保存成功", qualityInspection.getId());
	}

	/**
	 * 删除质检
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		qualityInspectionService.delete(id);
		return "success";
	}

	/**
	 * 查看质检明细跳转
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("qualityInspection", qualityInspectionService.get(id));
		return "stock/qualityInspectionDetail";
	}

	@ModelAttribute
	public void getQualityInspection(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("qualityInspection", qualityInspectionService.get(id));
		}
	}

}
