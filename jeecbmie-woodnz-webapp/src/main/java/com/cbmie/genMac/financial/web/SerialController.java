package com.cbmie.genMac.financial.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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
import com.cbmie.genMac.financial.entity.Serial;
import com.cbmie.genMac.financial.service.SerialService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 水单controller
 */
@Controller
@RequestMapping("financial/serial")
public class SerialController extends BaseController {


	@Autowired
	private SerialService serialService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/serialList";
	}

	/**
	 * 获取水单json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<Serial> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = serialService.search(page, filters);
		List<Serial> serialList= page.getResult();
		List<Serial> newList = new ArrayList<Serial>();
		if(serialList.size()>0){
			Iterator<Serial> it = serialList.iterator();
			while(it.hasNext()){
				Serial serialObj = (Serial) it.next();
				if(serialObj.getSplitStatus()!=null&&serialObj.getSplitStatus().equals("parent")){
					it.remove();
				}else{
					newList.add(serialObj);
				}
			}
		}
		page.setResult(newList);
		return getEasyUIData(page);
	}

	/**
	 * 添加水单跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("serial", new Serial());
		model.addAttribute("action", "create");
		return "financial/serialForm";
	}

	/**
	 * 添加水单
	 * 
	 * @param serial
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Serial serial, Model model) throws JsonProcessingException {
		if (serialService.findByNo(serial) != null) {
			return setReturnData("fail","水单号重复！",serial.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		serial.setCreaterNo(currentUser.getLoginName());
		serial.setCreaterName(currentUser.getName());
		serial.setCreateDate(new Date());
		serial.setSummary(serial.getSerialNumber()+"["+serial.getSerialTitle()+"]");
		serial.setCreaterDept(currentUser.getOrganization().getOrgName());
		serialService.save(serial);
		return setReturnData("success","保存成功",serial.getId());
	}

	/**
	 * 修改水单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		model.addAttribute("action", "update");
		return "financial/serialForm";
	}

	/**
	 * 修改水单
	 * 
	 * @param serial
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Serial serial, Model model) throws JsonProcessingException {
		if (serialService.findByNo(serial) != null) {
			return setReturnData("fail","水单号重复！",serial.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		serial.setUpdaterNo(currentUser.getLoginName());
		serial.setUpdaterName(currentUser.getName());
		serial.setUpdateDate(new Date());
		serial.setSummary(serial.getSerialNumber()+"["+serial.getSerialTitle()+"]");
		serialService.update(serial);
		return setReturnData("success","保存成功",serial.getId());
	}
	
	/**
	 * 认领水单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "claim/{id}", method = RequestMethod.GET)
	public String claim(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		return "financial/claimSerial";
	}

	/**
	 * 认领水单
	 * 
	 * @param serial
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "claim", method = RequestMethod.POST)
	@ResponseBody
	public String claim(@Valid @ModelAttribute @RequestBody Serial serial, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		serial.setUpdaterNo(currentUser.getLoginName());
		serial.setUpdaterName(currentUser.getName());
		serial.setUpdateDate(new Date());
		serialService.update(serial);
		return  setReturnData("success","认领成功！",serial.getId());
	}
	
	
	/**
	 * 取消认领水单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "cancelClaim/{id}")
	@ResponseBody
	public String cancelClaim(@PathVariable("id") Long id) throws JsonProcessingException {
		Serial serial = serialService.get(id);
		serial.setContractNo(null);
		serial.setComments("");
		serial.setSerialCategory(null);
		serialService.update(serial);
		return  setReturnData("success","取消认领成功！",serial.getId());
	}
	
	
	/**
	 * 删除水单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		serialService.delete(id);
		return "success";
	}
	
	/**
	 * 拆分水单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "split/{id}", method = RequestMethod.GET)
	public String split(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		return "financial/splitSerial";
	}

	/**
	 * 拆分水单
	 * 
	 * @param serial
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "split", method = RequestMethod.POST)
	@ResponseBody
	public String split(@Valid @ModelAttribute @RequestBody Serial serial, Model mode,@RequestParam("perAmoutJson") String perAmoutJson) throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		String[] numArray = perAmoutJson.split(",");
		User currentUser = UserUtil.getCurrentUser();
		List<Serial> serialList = new ArrayList<Serial>();
		//获取拆分后的水单list
		for(int i = 0; i < numArray.length; i++){
			Serial serialNew = new Serial();
			BeanUtils.copyProperties(serialNew, serial);
			serialNew.setSplitStatus(String.valueOf(serial.getId()));
			serialNew.setSerialNumber(serial.getSerialNumber() + "_" + i);
			serialNew.setId(null);
			serialNew.setFundSource("内部拆分");
			serialNew.setMoney(Double.valueOf(numArray[i]));
			serialNew.setCreaterNo(currentUser.getLoginName());
			serialNew.setCreaterName(currentUser.getName());
			serialNew.setCreateDate(new Date());
			serialNew.setCreaterDept(currentUser.getOrganization().getOrgName());
			serialList.add(serialNew);
		}
		//插入拆分后的水单
		for(int i=0;i<serialList.size();i++){
			serialService.save(serialList.get(i));
		}
		//更新被拆分水单状态
		serial.setSplitStatus("parent");
		serialService.update(serial);
		return  setReturnData("success","成功拆分水单！",serial.getId());
	}
	
	/**
	 * 取消拆分除水单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "cancelSplit/{id}")
	@ResponseBody
	public String cancelSplit(@PathVariable("id") Long id) throws JsonProcessingException {
		String parentId = serialService.get(id).getSplitStatus();
		List<Serial> serialList  = serialService.findByParentId(parentId);
		//判断是否存在删除的水单
		if(serialList.size()>1){
			Serial serialSource = serialService.get(Long.valueOf(serialList.get(0).getSplitStatus()));
			Serial serial = null;
			int totalMoney = 0;
			for(int i=0;i<serialList.size();i++){
				serial = new Serial();
				serial = serialList.get(i);
				if(serial.getContractNo()!=null){
					return  setReturnData("fail","存在已经认领的水单,不能撤销！",id);
				}
				totalMoney += Double.valueOf(serial.getMoney()).intValue();
			}
			if(totalMoney!= Double.valueOf(serialSource.getMoney()).intValue()){
				return  setReturnData("fail","存在删除的拆分后的水单！不能取消拆分!",id);
			}
			//删除被拆分后的水单
			for(int i=0;i<serialList.size();i++){
				serialService.delete(serialList.get(i).getId());
			}
			//恢复原水单状态
			serialSource.setSplitStatus(null);
			serialService.update(serialSource);
			return  setReturnData("success","成功取消拆分！",id);
		}else{
			return  setReturnData("fail","存在删除的拆分后的水单！不能取消拆分!",id);
		}
	}

	@ModelAttribute
	public void getSerial(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("serial", serialService.get(id));
		}
	}

	/**
	 * 查看水单明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("serial", serialService.get(id));
		return "financial/serialDetail";
	}
	
}
