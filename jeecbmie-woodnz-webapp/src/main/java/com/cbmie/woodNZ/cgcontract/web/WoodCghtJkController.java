package com.cbmie.woodNZ.cgcontract.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkBak;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMx;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMxBak;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkBakService;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkMxBakService;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkMxService;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 采购合同-进口controller
 */
@Controller
@RequestMapping("woodCght/woodCghtJK")
public class WoodCghtJkController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WoodCghtJkService woodCghtJkService;
	
	@Autowired
	private WoodCghtJkBakService woodCghtJkBakService;
	
	@Autowired
	private WoodCghtJkMxService woodCghtJkMxService;
	
	@Autowired
	private WoodCghtJkMxBakService woodCghtJkMxBakService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "woodCght/woodCghtJKList";
	}

	/**
	 * 获取采购合同-进口json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<WoodCghtJk> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = woodCghtJkService.search(page, filters);
		List<WoodCghtJk>  woodCghtJkList = page.getResult();
		page.setResult(woodCghtJkList);
		return getEasyUIData(page);
	}

	/**
	 * 添加采购合同-进口跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("woodCghtJk", new WoodCghtJk());
		model.addAttribute("action", "create");
		return "woodCght/woodCghtJKForm";
	}

	/**
	 * 添加采购合同-进口
	 * 
	 * @param woodCghtJk
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodCghtJk woodCghtJk, Model model,@RequestParam("woodCghtJkMxJson") String woodCghtJkMxJson) throws JsonProcessingException {
		if (woodCghtJkService.findByNo(woodCghtJk) != null) {
			return setReturnData("fail","合同号重复",woodCghtJk.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		woodCghtJk.setCreaterNo(currentUser.getLoginName());
		woodCghtJk.setCreaterName(currentUser.getName());
		woodCghtJk.setCreateDate(new Date());
		woodCghtJk.setSummary("采购合同-进口[" + woodCghtJk.getHth() + "]");
		woodCghtJk.setCreaterDept(currentUser.getOrganization().getOrgName());
		woodCghtJkService.save(woodCghtJk);
		if(StringUtils.isNotBlank(woodCghtJkMxJson)){
			woodCghtJkMxService.save(woodCghtJk, woodCghtJkMxJson);
		}
		return setReturnData("success","保存成功",woodCghtJk.getId());
	}

	/**
	 * 修改采购合同-进口跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCghtJk", woodCghtJkService.get(id));
		model.addAttribute("action", "update");
		return "woodCght/woodCghtJKForm";
	}

	/**
	 * 修改采购合同-进口
	 * 
	 * @param woodCghtJk
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodCghtJk woodCghtJk, Model model,@RequestParam("woodCghtJkMxJson") String woodCghtJkMxJson) throws JsonProcessingException {
		if (woodCghtJkService.findByNo(woodCghtJk) != null) {
			return setReturnData("fail","合同号重复",woodCghtJk.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		woodCghtJk.setUpdaterNo(currentUser.getLoginName());
		woodCghtJk.setUpdaterName(currentUser.getName());
		woodCghtJk.setUpdateDate(new Date());
		woodCghtJk.setSummary("采购合同-进口[" + woodCghtJk.getHth() + "]");
		woodCghtJkService.update(woodCghtJk);
		if(StringUtils.isNotBlank(woodCghtJkMxJson)){
			woodCghtJkMxService.save(woodCghtJk, woodCghtJkMxJson);
		}
		return setReturnData("success","保存成功",woodCghtJk.getId());
	}
	
	/**
	 * 变更采购合同-进口 跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String change(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCghtJk", woodCghtJkService.get(id));
		model.addAttribute("action", "change");
		request.getSession().setAttribute("bakWoodCghtJk", woodCghtJkService.get(id));
		return "woodCght/woodCghtJKForm";
	}

	/**
	 * 变更采购合同-进口
	 * 
	 * @param woodCghtJk
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "change", method = RequestMethod.POST)
	@ResponseBody
	public String change(@Valid @ModelAttribute @RequestBody WoodCghtJk woodCghtJk, Model model,@RequestParam("woodCghtJkMxJson") String woodCghtJkMxJson) throws JsonProcessingException {
		try {
			if (woodCghtJkService.findByNo(woodCghtJk) != null) {
				return setReturnData("fail","合同号重复",woodCghtJk.getId());
			}
			WoodCghtJk bakWoodCghtJk = (WoodCghtJk) request.getSession().getAttribute("bakWoodCghtJk");
			//保存主表变更记录
			WoodCghtJkBak woodCghtJkBak = new  WoodCghtJkBak();
			if(bakWoodCghtJk!=null){
				BeanUtils.copyProperties(woodCghtJkBak, bakWoodCghtJk);
				User currentUser = UserUtil.getCurrentUser();
				woodCghtJkBak.setUpdaterNo(currentUser.getLoginName());
				woodCghtJkBak.setUpdaterName(currentUser.getName());
				woodCghtJkBak.setUpdateDate(new Date());
				woodCghtJkBak.setUpdaterDept(currentUser.getOrganization().getOrgName());
				woodCghtJkBak.setId(null);
				woodCghtJkBak.setChangeRelativeId(String.valueOf(woodCghtJk.getId()));
				woodCghtJkBak.setChangeReason(woodCghtJk.getChangeReason());
				woodCghtJkBak.setWoodCghtJkMxBakList(null);
				woodCghtJkBakService.save(woodCghtJkBak);
				String bakId = String.valueOf(woodCghtJkBak.getId());
				//保存子表变更记录
				List<WoodCghtJkMx> woodCghtJkMxList = new ArrayList<WoodCghtJkMx>();
				woodCghtJkMxList = bakWoodCghtJk.getWoodCghtJkMxList();
				for(WoodCghtJkMx woodCghtJkMx:woodCghtJkMxList){
					WoodCghtJkMxBak woodCghtJkMxBak = new WoodCghtJkMxBak();
					BeanUtils.copyProperties(woodCghtJkMxBak, woodCghtJkMx);
					woodCghtJkMxBak.setParentId(String.valueOf(bakId));
					woodCghtJkMxBak.setBgReason(woodCghtJk.getChangeReason());
					woodCghtJkMxBak.setId(null);
					woodCghtJkMxBakService.save(woodCghtJkMxBak);
				}
				request.getSession().removeAttribute("bakWoodCghtJk");
				woodCghtJk.setCreaterNo(currentUser.getLoginName());
				woodCghtJk.setCreaterName(currentUser.getName());
				woodCghtJk.setCreateDate(new Date());
				woodCghtJk.setSummary("采购合同-进口[" + woodCghtJk.getHth() + "]");
				woodCghtJk.setCreaterDept(currentUser.getOrganization().getOrgName());
				woodCghtJk.setChangeReason(null);
				woodCghtJk.setState("变更");
				woodCghtJk.setProcessInstanceId(null);
				woodCghtJk.setChangeRelativeId(bakId);
				woodCghtJkService.update(woodCghtJk);
				if(StringUtils.isNotBlank(woodCghtJkMxJson)){
					woodCghtJkMxService.save(woodCghtJk, woodCghtJkMxJson);
				}
			}
			if (Boolean.valueOf(request.getParameter("apply"))) {
				apply(woodCghtJk.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setReturnData("success","保存成功",woodCghtJk.getId());
	}
	
	/**
	 * 删除采购合同-进口
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		woodCghtJkService.delete(id);
		return "success";
	}
	
	/**
	 * 查看采购合同-进口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCghtJk", woodCghtJkService.get(id));
		model.addAttribute("woodCghtJkBakList", woodCghtJkBakService.findAll(id));
		model.addAttribute("woodCghtJkMxBakList", woodCghtJkMxBakService.findAll(id));
		return "woodCght/woodCghtJKDetail";
	}
	
	/**
	 * 查看采购合同-进口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showChange/{id}", method = RequestMethod.GET)
	public String showChange(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCghtJkBak", woodCghtJkBakService.get(id));
		return "woodCght/woodCghtJKBakDetail";
	}
	
	/**
	 * 修改采购合同运行状态--跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "closeOrOpen/{id}", method = RequestMethod.GET)
	public String closeOrOpen(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCghtJk", woodCghtJkService.get(id));
		model.addAttribute("action", "closeOrOpen");
		return "woodCght/woodCghtJKCloseOrOpen";
	}
	
	/**
	 * 修改采购合同运行状态
	 * 
	 * @param woodCghtJk
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "closeOrOpen", method = RequestMethod.POST)
	@ResponseBody
	public String closeOrOpen(@Valid @ModelAttribute @RequestBody WoodCghtJk woodCghtJk, Model model) throws JsonProcessingException {
			User currentUser = UserUtil.getCurrentUser();
			woodCghtJk.setUpdaterNo(currentUser.getLoginName());
			woodCghtJk.setUpdaterName(currentUser.getName());
			woodCghtJk.setUpdateDate(new Date());
			woodCghtJk.setSummary("采购合同-进口[" + woodCghtJk.getHth() + "]");
			woodCghtJkService.update(woodCghtJk);
			return setReturnData("success","保存成功",woodCghtJk.getId());
	}
	
	@ModelAttribute
	public void getWoodCghtJk(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("woodCghtJk", woodCghtJkService.get(id));
		}
	}
	
	/**
	 * 提交流程申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "apply/{id}")
	@ResponseBody
	public String apply(@PathVariable("id") Long id) {
		Map<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = UserUtil.getCurrentUser();
		WoodCghtJk woodCghtJk = woodCghtJkService.get(id);
//			woodCghtJk.setUserId((user.getLoginName()).toString());
		woodCghtJk.setState("已提交");
		woodCghtJkService.save(woodCghtJk);
		Map<String, Object> variables = new HashMap<String, Object>();
		result = activitiService.startWorkflow(woodCghtJk, "wf_woodCghtJk", variables, (user.getLoginName()).toString());
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 撤回流程申请
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId, HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				WoodCghtJk woodCghtJk = woodCghtJkService.get(id);
				woodCghtJk.setProcessInstanceId(null);
				if( StringUtils.isNotBlank(woodCghtJk.getChangeRelativeId())){
					woodCghtJk.setState("变更");
				}else{
					woodCghtJk.setState("草稿");
				}
				woodCghtJkService.save(woodCghtJk);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
	/**
	 * 采购协议配置跳转
	 * @return
	 */
	@RequestMapping(value = "loadProtocol", method = RequestMethod.GET)
	public String toAgencyConfig() {
		return "woodCght/loadProtocol";
	}
	
	/**
	 * 采购合同类别
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "goodsCode/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsCode(@PathVariable("id") Long id) {
		WoodCghtJk tmp = woodCghtJkService.get(id);
		List list = tmp.getWoodCghtJkMxList();
		String result = "";
		if(list!=null && list.size()>0){
			WoodCghtJkMx mx = (WoodCghtJkMx) list.get(0);
			if(mx!=null){
				String bm = mx.getSpbm();
				if(StringUtils.isNotBlank(bm)){
					result = bm.substring(0, 2);
				}
			}
		}
		return result;
	}
}
