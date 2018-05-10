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
import com.cbmie.lh.baseInfo.entity.Signet;
import com.cbmie.lh.baseInfo.service.SignetService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="/baseInfo/signet")
public class SignetController extends BaseController {

	@Autowired
	private SignetService signetService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "baseinfo/lhSignetList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<Signet> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = signetService.search(page, filters);
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
		Signet signet = new Signet();
		User currentUser = UserUtil.getCurrentUser();
		signet.setCreaterNo(currentUser.getLoginName());
		signet.setCreaterName(currentUser.getName());
		signet.setCreaterDept(currentUser.getOrganization().getOrgName());
		signet.setCreateDate(new Date());
		String signetCode = sequenceCodeService.getNextCode("003002").get("returnStr");
		signet.setSignetCode(signetCode);
		model.addAttribute("signet", signet);
		model.addAttribute("action", "create");
		return "baseinfo/lhSignetForm";
	}
	 
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Signet signet, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!signetService.checkCodeUique(signet)) {
				String signetCode = sequenceCodeService.getNextCode("003002").get("returnStr");
				signet.setSignetCode(signetCode);
				currnetSequence = signetCode;
			}
			if(signet.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("003002");
			}
			signetService.save(signet);
			return setReturnData("success", currnetSequence!=null?"印章编码重复，已自动生成不重复编码并保存":"保存成功", signet.getId(),currnetSequence);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",signet.getId());
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
		model.addAttribute("signet", signetService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/lhSignetForm";
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
	public String update(@Valid @ModelAttribute @RequestBody Signet signet, Model model) throws JsonProcessingException {
//		if (!signetService.checkCodeUique(signet)) {
//			return setReturnData("fail","编码重复",signet.getId());
//		}
		User currentUser = UserUtil.getCurrentUser();
		signet.setUpdaterNo(currentUser.getLoginName());
		signet.setUpdaterName(currentUser.getName());
		signet.setUpdateDate(new Date());
		signetService.update(signet);
		return setReturnData("success","保存成功",signet.getId());
	}
	@ModelAttribute
	public void getSignet(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("signet", signetService.get(id));
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
		signetService.delete(id);
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
		model.addAttribute("signet", signetService.get(id));
		return "baseinfo/lhSignetDetail";
	}
}
