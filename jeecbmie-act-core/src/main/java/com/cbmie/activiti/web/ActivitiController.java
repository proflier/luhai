package com.cbmie.activiti.web;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.entity.Variable;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.activiti.service.HandleRecordService;
import com.cbmie.activiti.service.ProcessEntityService;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.common.utils.Reflections;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.message.publisher.MessagePublisher;
import com.cbmie.system.entity.User;
import com.cbmie.system.exception.SystemException;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.OrgUserBean;
import com.cbmie.system.utils.OrgUserUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 工作流核心
 * @author zhk
 */
@Controller
@RequestMapping(value = "/workflow")
public class ActivitiController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private UserService userSerivce;
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	private ProcessEntityService procEntityService;
	
	@Autowired
	private HandleRecordService handleRecordService;
	
	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();
	
	/**流程列表展示*********************************************************************************************************************/
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "activiti/todoList";
	}
	
	/**
     * 待办任务--Portlet
     */
    @RequestMapping(value = "/task/todo/list")
    @ResponseBody
    public Map<String, Object> todoList(HttpServletRequest request) throws Exception {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getTodoList(request);
    	createPageByResult(page, result);
        return getEasyUIData(page);
    }
    @RequestMapping(value="/runProcList",method = RequestMethod.GET)
	public String runningList() {
		return "activiti/runningList";
	}
    /**
     * 读取运行中的流程
     */
    @Transactional(readOnly = true)
    @RequestMapping(value = "/task/running/list")
    @ResponseBody
    public Map<String, Object> findRunningProcessInstaces(HttpServletRequest request) {
    	Page<Map<String, Object>> page = getPage(request);
        activitiService.getRunningList(page, request);
        return getEasyUIData(page);
    }
    
    @RequestMapping(value="/task/delegate/page")
    public String toDelegateTaskPage(@RequestParam String processInstanceId,Model model) throws JsonProcessingException{
    	model.addAttribute("processInstanceId", processInstanceId);
    	List<Task> list = activitiService.getToDelegateTask(processInstanceId);
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	for(Task task : list){
    		Map<String,Object> temp = new HashMap<String,Object>();
    		temp.put("id", task.getId());
    		temp.put("name", task.getName());
    		temp.put("assignee", task.getAssignee());
    		temp.put("createTime", sf.format(task.getCreateTime()));
    		result.add(temp);
    	}
    	ObjectMapper objectMapper = new ObjectMapper();
    	String t = objectMapper.writeValueAsString(result);
    	model.addAttribute("taskList", t);
    	return "activiti/delegateTask";
    }
    @RequestMapping(value="/task/delegate/pageDetail")
    @ResponseBody
    @Transactional(readOnly=true)
    public String toDelegateTaskList(@RequestParam String processInstanceId) throws JsonProcessingException{
    	return null;
    	
    }
    @RequestMapping(value="/task/delegate",method=RequestMethod.POST)
    @ResponseBody
    public String delegateTask(String[] delegateTaskId,String delegateUserId) throws JsonProcessingException{
    	boolean flag = activitiService.delegateTask(delegateTaskId, delegateUserId); 
    	if(flag){
    		return setReturnData("success","委托成功",null);
    	}else{
    		return setReturnData("fail","委托失败",null);
    	}
    }
    
    /**
     * 已办任务
     */
    @RequestMapping(value = "/task/haveDone/list")
    @ResponseBody
    public Map<String, Object> haveDoneList(HttpServletRequest request) throws Exception {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getHaveDoneList(request);
    	createPageByResult(page, result);
        return getEasyUIData(page);
    }
    
    /**
     * 传阅任务--Portlet
     */
    @RequestMapping(value = "/task/pass/list")
    @ResponseBody
    public Map<String, Object> procPassList(HttpServletRequest request) throws Exception {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getProcPassList(request);
    	createPageByResult(page, result);
        return getEasyUIData(page);
    }
    
    /**
     * old
     * 
     * 根据taskId重新提交业务信息
     * @throws ClassNotFoundException 
     * @throws SystemException 
     */
    @RequestMapping(value = "/reApply/{pid}/{id}", method = RequestMethod.GET)
	public String reApply(@PathVariable("pid") String pid, @PathVariable("id") String id, Model model) throws ClassNotFoundException, SystemException {
    	Map<String, String> map = activitiService.getKeys(pid);
    	String processKey = map.get("processKey");
    	Long businessKey = Long.parseLong(map.get("businessKey"));
    	String className = procEntityService.getClazzFullName(processKey);
    	String entityName = Class.forName(className).getSimpleName();
    	model.addAttribute(StringUtils.lowerFirst(entityName), activitiService.getReflectionObj(processKey, businessKey));
    	model.addAttribute("action", "update");
    	String returnUrl = procEntityService.getViewFormBykey(processKey);
    	if("".equals(returnUrl)){
    		returnUrl = "error/keyNotExist";
    	}
    	return returnUrl;
	}
    
    /**
     * new
     * 
     * 根据taskId重新提交业务信息
     * @throws ClassNotFoundException 
     * @throws SystemException 
     */
    @RequestMapping(value = "/businessPage/{taskId}", method = RequestMethod.GET)
	public String businessPage(@PathVariable("taskId") String taskId, Model model) throws ClassNotFoundException, SystemException {
    	Task task = activitiService.getTaskById(taskId);
    	String formUrl = task.getFormKey();
    	Map<String, String> map = activitiService.getKeys(task.getProcessInstanceId());
    	String processKey = map.get("processKey");
    	Long businessKey = Long.parseLong(map.get("businessKey"));
    	if(formUrl==null || "".equals(formUrl)){
    		ApprovalOpinion approvalOpinion = new ApprovalOpinion();
        	approvalOpinion.setBusinessKey(map.get("businessKey"));
        	approvalOpinion.setBusinessInfoReturnUrl(procEntityService.getViewInfoBykey(processKey));
        	model.addAttribute("approvalOpinion", approvalOpinion);
        	formUrl="activiti/businessInfoForm";
    	}else{
    		/*String className = procEntityService.getClazzFullName(processKey);
        	String entityName = Class.forName(className).getSimpleName();
        	model.addAttribute(StringUtils.lowerFirst(entityName), activitiService.getReflectionObj(processKey, businessKey));
        	model.addAttribute("action", "update");*/
    		if(formUrl.indexOf("?")!=-1){
    			formUrl=formUrl.substring(0, formUrl.indexOf("?"))+"/"+businessKey+formUrl.substring(formUrl.indexOf("?"));
    		}else{
    			formUrl = formUrl+"/"+businessKey;
    		}
    		formUrl="redirect:/"+formUrl;
    	}
    	if("".equals(formUrl)){
    		formUrl = "error/keyNotExist";
    	}
    	return formUrl;
	}
    /**
     * old
     * 重新提交节点控制
     */
    @RequestMapping(value = "reApply/{taskId}", method = RequestMethod.GET)
	@ResponseBody
	public String passReApply(@PathVariable("taskId") String taskId, Model model) {
    	String comments = "重新提交申请";
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	var.setValues("1");
    	try {
            Map<String, Object> variables = var.getVariableMap();
            activitiService.passProcess(taskId, variables, comments);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
	}
    
    /**
     * 流程废除节点控制
     */
    @RequestMapping(value = "abolishApply/{taskId}", method = RequestMethod.GET)
	@ResponseBody
	public String abolishApply(@PathVariable("taskId") String taskId) {
    	try {
            activitiService.endProcess(taskId, Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue());
            return "success";
        } catch (Exception e) {
            logger.error("abolish apply:", new Object[]{taskId, e});
            return "error";
        }
	}
    
    /**流程核心功能*********************************************************************************************************************/
    
    /**
     * 流程跟踪列表跳转
     * @throws Exception 
     */
    @RequestMapping(value = "/trace/{processInstanceId}", method = RequestMethod.GET)
	public String hitasklist(@PathVariable("processInstanceId") String processInstanceId, Model model) throws Exception {
    	model.addAttribute("processDefinitionId", activitiService.findProcessDefinitionEntityByProcessInstanceId(processInstanceId).getProcessDefinition().getId());
    	model.addAttribute("pid", processInstanceId);
    	model.addAttribute("trace", activitiService.checkIfRemind(processInstanceId));
		return "activiti/hitaskList";
	}
    
    /**
     * 流程跟踪列表json
     */
    @RequestMapping(value = "/trace/list/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
	public Map<String, Object> trace(HttpServletRequest request, @PathVariable("processInstanceId") String processInstanceId) {
    	Page<Map<String, Object>> page = getPage(request);
    	List<Map<String, Object>> result = activitiService.getTraceInfo(processInstanceId);
    	createPageByResult(page, result);
        return getEasyUIData(page);
	}
    
    /**
     * 签收
     */
    @RequestMapping(value = "task/claim/{id}")
	@ResponseBody
	public String claim(@PathVariable("id") String id) {
		Map<String,Object> result = activitiService.doClaim(id);
		if((boolean) result.get("flag")){
			return "success";
		}else{
			String errorMsg = (String) result.get("errorMsg");
			return errorMsg;
		}
	}
    
    /**
     * 流程过后查看
     *	显示业务信息
     * @param businessKey 业务id
     * @param key 流程key
     * @param model
     * @return
     * @throws SystemException 
     */
    @RequestMapping(value = "businessInfo/{businessKey}/{key}/{detailId}", method = RequestMethod.GET)
	public String businessInfo(@PathVariable("businessKey") String businessKey, 
							 @PathVariable("key") String key,@PathVariable("detailId") String detailId, Model model) throws SystemException {
    	ApprovalOpinion approvalOpinion = new ApprovalOpinion();
    	approvalOpinion.setBusinessKey(businessKey);
    	approvalOpinion.setBusinessInfoReturnUrl(procEntityService.getViewInfoBykey(key));
    	if ("".equals(approvalOpinion.getBusinessInfoReturnUrl())) {
    		return "error/keyNotExist";
    	}
		model.addAttribute("approvalOpinion", approvalOpinion);
		if(detailId!=null && !"-1".equals(detailId)){
			activitiService.readProPass(detailId);
		}
		return "activiti/businessInfoForm";
	}
    
    /**
     * 审批form（保留）
     * @param pid 流程id
     * @param id 任务id
     * @param businessKey 业务id
     * @param key 流程key
     * @param model
     * @return
     * @throws SystemException 
     */
   /* @RequestMapping(value = "approvalFormDeal/{pid}/{id}/{businessKey}/{key}", method = RequestMethod.GET)
	public String toApprovalFormDeal(@PathVariable("pid") String processInstanceId, 
							 @PathVariable("id") String taskId, 
							 @PathVariable("businessKey") String businessKey, 
							 @PathVariable("key") String key, Model model) throws SystemException {
    	ApprovalOpinion approval = new ApprovalOpinion();
    	approval.setProcessInstanceId(processInstanceId);
    	approval.setTaskId(taskId);
    	approval.setBusinessKey(businessKey);
    	approval.setKey(key);
    	approval.setBusinessInfoReturnUrl(procEntityService.getViewInfoBykey(key));
    	if ("".equals(approval.getBusinessInfoReturnUrl())) {
    		return "error/keyNotExist";
    	}
    	List<ActivityImpl> backActivity = new ArrayList<ActivityImpl>();
    	List<ActivityImpl> goActivity = new ArrayList<ActivityImpl>();
    	List<ActivityImpl> nextActivity = new ArrayList<ActivityImpl>();
    	ActivityImpl curActivity=null;
    	try {
    		curActivity = activitiService.findActivitiImpl(taskId, null);
    		backActivity = activitiService.findBackAvtivity(taskId);
    		goActivity = activitiService.findGoAvtivity(taskId);
    		nextActivity= activitiService.findNextAvtivity(taskId);
    	} catch (Exception e) {
    		logger.error(e.getLocalizedMessage(), e);
    	}
		model.addAttribute("approval", approval);
		model.addAttribute("curActivity", curActivity);
		model.addAttribute("backActivity", backActivity);
		model.addAttribute("goActivity", goActivity);
		model.addAttribute("nextActivity", nextActivity);
		Map<String,Map<String,Object>> selects =  activitiService.getCandidateUsers((ActivityImpl[]) nextActivity.toArray(new ActivityImpl[nextActivity.size()]));
		model.addAttribute("selects",selects);
		return "activiti/approvalFormDeal";
	}*/
    @RequestMapping(value = "approvalFormDeal/{pid}/{id}/{businessKey}/{key}", method = RequestMethod.GET)
   	public String toApprovalFormDeal(@PathVariable("pid") String processInstanceId, 
   							 @PathVariable("id") String taskId, 
   							 @PathVariable("businessKey") String businessKey, 
   							 @PathVariable("key") String key, Model model) throws SystemException {
       	ApprovalOpinion approval = new ApprovalOpinion();
       	approval.setProcessInstanceId(processInstanceId);
       	approval.setTaskId(taskId);
       	approval.setBusinessKey(businessKey);
       	approval.setKey(key);
       	approval.setBusinessInfoReturnUrl(procEntityService.getViewInfoBykey(key));
       	if ("".equals(approval.getBusinessInfoReturnUrl())) {
       		return "error/keyNotExist";
       	}
       	List<PvmTransition> nexttransitions = new ArrayList<PvmTransition>();
       	List<ActivityImpl> backActivitys = new ArrayList<ActivityImpl>();
       	ActivityImpl backActivity = null;
       	ActivityImpl curActivity = null;
       	try {
       		curActivity = activitiService.findActivitiImpl(taskId, null);
       		backActivitys = activitiService.getDirectBackAvtivity(taskId);
       		backActivity = backActivitys.size()>0?backActivitys.get(backActivitys.size()-1):null;
       		nexttransitions = activitiService.nextPvmTransition(taskId, curActivity);
       	} catch (Exception e) {
       		logger.error(e.getLocalizedMessage(), e);
       	}
   		model.addAttribute("approval", approval);
   		model.addAttribute("curActivity", curActivity);
   		model.addAttribute("backActivity", backActivity);
   		model.addAttribute("nexttransitions", nexttransitions);
   		return "activiti/approvalFormDeal";
   	}
    
    /**
     * 获取下一步处理者
     * **/
    @RequestMapping(value = "task/prepareDealer/{taskId}/{nextId}/{nextType}", produces = "application/json;charset=UTF-8")
   	@ResponseBody
    public Map<String,Map<String,Object>> prepareNextDealer(@PathVariable("taskId") String taskId,@PathVariable("nextId") String nextId,@PathVariable("nextType")String nextType){
    	Map<String,Map<String,Object>> result = null;
    	try {
    		result = activitiService.getNextDealerAndReaderByTransition(taskId, nextId,nextType);
		} catch (Exception e) {
			logger.error("准备办理人出错{}",e.getMessage());
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * 发送
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
	@ResponseBody
    public String sendActiviti(@Valid @ModelAttribute @RequestBody ApprovalOpinion approval, Model model, String type){
    	String relationUsers = "";//相关审批人员
    	String taskId = approval.getTaskId();
    	String actionType = approval.getActionType();
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	// 正常下一步办理
    	BaseEntity entity = activitiService.getReflectionObj(approval.getKey(), Long.parseLong(approval.getBusinessKey()));
    	//当前审批对应业务的 相关人员
    	String relLoginNames = (String) Reflections.invokeGetter(entity, "relLoginNames");
    	/**初期数据库设计错误，应设置默认值“” 如果是数据库新建可以删除 */
	    if(relLoginNames==null){
	    	relLoginNames="";
	    }	
    	String currentUserName = UserUtil.getCurrentUser().getLoginName();
    	if("1".equals(actionType)){
    		Object state=Reflections.invokeGetter(entity, "state");
    		if(state!=null && state.toString().equals(ActivitiConstant.ACTIVITI_DRAFT)){
    			Reflections.invokeSetter(entity, "state", ActivitiConstant.ACTIVITI_COMMIT);
    		}
        	var.setValues("1");
        	if(relLoginNames.indexOf(currentUserName)==-1){
        		relationUsers = currentUserName;
        	}
    	}else if("2".equals(actionType)){  //驳回到上一步
    		var.setValues("0");
    	}
    	Map<String, Object> variables = var.getVariableMap();
//    	variables.put("priority", approval.getPriority());
    	variables.put("limitDate", approval.getLimitDate());
    	try {
			String exp = activitiService.findActivitiV(taskId, approval.getGoId());
			if(exp!=null){
				variables.put(exp, Arrays.asList(approval.getCandidateUserIds()));
			}
			handleRecordService.save(activitiService.findTaskById(taskId), StringUtils.isNotBlank(type) ? type : "PC");// 流程办理记录（统计手机端和PC端任务的办理比例）
			activitiService.backProcess(taskId, approval.getDirectNextId(), variables, approval.getComments());
			Task task = activitiService.setCurrentTaskPriority(approval.getProcessInstanceId(), approval.getGoId(), Integer.parseInt(approval.getPriority()));
			//存在委托
			if(approval.getCandidateUserIds()!=null && approval.getCandidateUserIds().length==1){
				String curUserId = approval.getCandidateUserIds()[0];
				activitiService.delegateTask(task.getId(),curUserId);
				//将委托前人员放入审批人员中
				if(relLoginNames.indexOf(curUserId)==-1){
					if(StringUtils.isNotBlank(relationUsers)){
						relationUsers = relationUsers +","+curUserId;
					}else{
						relationUsers = curUserId;
					}
				}
			}
			//将存在相关人员中审批人员放入 相关人员中
			if(StringUtils.isNotBlank(relationUsers)){
				relLoginNames = relLoginNames + ","+relationUsers;
				Reflections.invokeSetter(entity, "relLoginNames", relLoginNames);
			}
			if(approval.getPassUserIds()!=null && approval.getPassUserIds().length>0){
				activitiService.transProcPass(approval.getKey(),approval.getProcessInstanceId(),taskId,task==null?null:task.getId(),approval.getBusinessKey(),approval.getPassUserIds());
			}
			
			String summary = activitiService.getBusinessInfo(approval.getKey(), Long.parseLong(approval.getBusinessKey()));
			String[] candidateUserIds = approval.getCandidateUserIds();
			List<User> users = new ArrayList<User>(0);
			if (candidateUserIds != null && candidateUserIds.length > 0) {
				users = userSerivce.getUsersByLoginNames(candidateUserIds);
				for (User user : users) {
					if (user.getLoginStatus() == 1) {
						activitiService.push(user.getLoginName(), summary, approval, task);// 推送
					}
				}
			} else {
				activitiService.push(null, summary, approval, task);
			}
			
			if(approval.getEmail() || approval.getSms()){
//				List<Integer> userIds = new ArrayList<Integer>();
				/*List<String> loginNames = new ArrayList<String>();
				for(String candidateUserId : candidateUserIds){
					try{
						int userId = Integer.parseInt(candidateUserId);
						userIds.add(userId);
					}catch(NumberFormatException e){
						loginNames.add(candidateUserId);
					}
				}
				List<User> users = new ArrayList<User>();
				if(userIds.size()>0){
					Integer[] userIds_ = new Integer[userIds.size()];
					userIds.toArray(userIds_);
					users.addAll(userSerivce.getUsersByIds(userIds_));
				}
				if(loginNames.size()>0){
					String[] loginNames_ = new String[loginNames.size()];
					loginNames.toArray(loginNames_);
					users.addAll(userSerivce.getUsersByLoginNames(loginNames_));
				}*/
				
				if(approval.getEmail()){
					List<String> emails = new ArrayList<String>();
					for(User user : users){
						emails.add(user.getEmail());
					}
					Map<String,String> map = new HashMap<String,String>();
					map.put("subject", "有待办事宜："+summary);
					map.put("content", "待办事宜："+summary);
					List<Map<String,String>> subject_contents = new ArrayList<Map<String,String>>(1);
					subject_contents.add(map);
					messagePublisher.publishEmailEvent("activiti", subject_contents, emails);
				}
				if(approval.getSms()){
					List<String> mobiles = new ArrayList<String>();
					List<String> contents =new ArrayList<String>();
					contents.add("待办事宜："+summary);
					for(User user : users){
						mobiles.add(user.getPhone());
					}
					messagePublisher.publishSmsEvent("activiti", contents, mobiles);
				}
			}
			/*if(approval.getEmail()){
				
				sendMailService.sendMail(approval, 
	    				activitiService.getBusinessInfo(approval.getKey(), Long.parseLong(approval.getBusinessKey())));/// 发送邮件
			}*/
	        return "success";
		} catch (Exception e) {
			logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
			e.printStackTrace();
		} 
    	return null;
    }
    @RequestMapping(value = "deleteFirst/{processInstanceId}/{state}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteProcOnFirstStep(@PathVariable("processInstanceId") String processInstanceId,@PathVariable("state") String state){
    	activitiService.deleteProcOnFirstStep(processInstanceId, state);
    }
    /**
     * 同意节点控制
     */
    @RequestMapping(value = "agree", method = RequestMethod.POST)
	@ResponseBody
	public String passComplete(@Valid @ModelAttribute @RequestBody ApprovalOpinion approval, Model model) {
    	String taskId = approval.getTaskId();
    	String comments = approval.getComments();
    	String activityId = approval.getGoId();// 跳转节点id
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	var.setValues("1");
    	Map<String, Object> variables = var.getVariableMap();
    	try {
    		//为下一个节点加入办理人变量
    		Map<String,String[]> _all = approval.getCandidateIdMap();
    		if(_all!=null){
    			for(String exp:_all.keySet()){
    				String[] _per_acti = _all.get(exp);
    				variables.put(exp, Arrays.asList(_per_acti));
    				
    			}
    		}
    		/*if (approval.getCandidateUserIds().length >= 2) {// 会签-根据候选人分配任务
    			
    		}*/
			/*if (activityId != null) {// 获取跳转节点候选人  如果需要，可以放开，单人员需要重新处理
        		approval.setCandidateUserIds(activitiService.getCandidateUserIds(activitiService.findActivitiImpl(taskId, activityId)));
    		}
    		sendMailService.sendMail(approval, 
    				activitiService.getBusinessInfo(approval.getKey(), Long.parseLong(approval.getBusinessKey())));*/// 发送邮件
            activitiService.backProcess(taskId, activityId, variables, comments);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
	}
    
    /**
     * 驳回节点控制
     */
    @RequestMapping(value = "back", method = RequestMethod.POST)
	@ResponseBody
	public String backComplete(@Valid @ModelAttribute @RequestBody ApprovalOpinion approval, Model model) {
    	String taskId = approval.getTaskId();
    	String comments = approval.getComments();
    	String activityId = approval.getBackId();
    	Variable var = new Variable();
    	var.setKeys("pass");
    	var.setTypes("B");
    	var.setValues("0");
    	Map<String, Object> variables = var.getVariableMap();
    	try {
    		if (activityId != null) {// 获取驳回节点受让人
//        		approval.setCandidateUserIds(activitiService.getCandidateUserIds(activitiService.findActivitiImpl(taskId, activityId)));
    			String exp = activitiService.findActivitiV(taskId, activityId);
    			if(exp!=null){
    				String user = activitiService.getBackUser(taskId, activityId);
        			variables.put(exp, user);
    			}
        	}
    		/*sendMailService.sendMail(approval, 
    				activitiService.getBusinessInfo(approval.getKey(), Long.parseLong(approval.getBusinessKey())));// 发送邮件 */
            activitiService.backProcess(taskId, activityId, variables, comments);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, variables, e});
            return e.getMessage();
        }
	}
    
    /**
     * 撤回任务
     */
    @RequestMapping(value = "callBack/{processInstanceId}/{activityId}/{currentActivityId}", method = RequestMethod.GET)
    @ResponseBody
	public String callBackComplete(@PathVariable("processInstanceId") String processInstanceId,
								   @PathVariable("activityId") String activityId,
								   @PathVariable("currentActivityId") String currentActivityId, Model model) {
    	try {
			activitiService.callBackProcess(processInstanceId, activityId, currentActivityId);
			return "success";
		} catch (Exception e) {
			logger.error("error on callBack complete task activityId {}", new Object[]{activityId, e});
            return e.getMessage();
		}
    }
    
    /**
     * 流程实例当前同级任务名称集合
     */
    @RequestMapping(value = "findCurrentTaskList/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findCurrentTaskList(@PathVariable("processInstanceId") String processInstanceId) {
    	List<String> nameList = new ArrayList<String>();
    	List<Task> list = activitiService.findTaskListByKey(processInstanceId, null);
    	for (Task task : list) {
    		nameList.add(task.getName());
    	}
    	return nameList;
    }
    
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
		InputStream imageStream = activitiService.tracePhoto(procDefId, execId);
//		String result = IOUtils.toString(imageStream, "UTF-8");
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	 /**
     * 流程实例当前同级任务名称集合
     */
    @RequestMapping(value = "getCompanyUser", method = RequestMethod.GET)
    @ResponseBody
	public List<OrgUserBean> getCompanyUser(){
		List<OrgUserBean> list = OrgUserUtils.getOrgUserList();
		return list;
	}
    
    @RequestMapping(value = "getCompanyUserAll", method = RequestMethod.GET)
    @ResponseBody
	public List<OrgUserBean> getCompanyUserAll(){
		List<OrgUserBean> list = OrgUserUtils.getCompanyUserAll();
		return list;
	}
    
    
    /**
     * 强制生效
     */
    @RequestMapping(value = "effect/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public String effect(@PathVariable("processInstanceId") String processInstanceId) {
    	try {
			activitiService.endProcess(processInstanceId);
			return "success";
		} catch (Exception e) {
			logger.error("error on effect processInstance processInstanceId {}", new Object[]{processInstanceId, e});
            return e.getMessage();
		}
    }
    
    /**
     * 强制删除
     */
    @RequestMapping(value = "delete/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable("processInstanceId") String processInstanceId) {
    	try {
			activitiService.deleteProcessInstance(processInstanceId, true);
			return "success";
		} catch (Exception e) {
			logger.error("error on delete processInstance processInstanceId {}", new Object[]{processInstanceId, e});
            return e.getMessage();
		}
    }
    @RequestMapping(value = "remind/{processInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public void remind(@PathVariable("processInstanceId") String processInstanceId){
    	List<String> candidateUserIds = null;
    	try{
    		candidateUserIds = activitiService.getRemindPersons(processInstanceId);
    	}catch(Exception e){
    		logger.error("remind failed ,processInstanceId is "+processInstanceId);
    		e.printStackTrace();
    		return ;
    	}
    	Map<String, String> keys = activitiService.getKeys(processInstanceId);
    	if(candidateUserIds==null || candidateUserIds.size()==0) return;
    	String summary = activitiService.getBusinessInfo(keys.get("processKey"),Long.parseLong(keys.get("businessKey")));
    	String[] userIds = new String[candidateUserIds.size()];
    	candidateUserIds.toArray(userIds);
    	List<User> users = userSerivce.getUsersByLoginNames(userIds);
			List<String> emails = new ArrayList<String>();
			List<String> mobiles = new ArrayList<String>();
			for(User user : users){
				emails.add(user.getEmail());
				mobiles.add(user.getPhone());
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("subject", "有待办事宜："+summary);
			map.put("content", "待办事宜："+summary);
			List<Map<String,String>> subject_contents = new ArrayList<Map<String,String>>(1);
			subject_contents.add(map);
			messagePublisher.publishEmailEvent("activiti", subject_contents, emails);
			
			List<String> contents =new ArrayList<String>();
			contents.add("待办事宜："+summary);
			messagePublisher.publishSmsEvent("activiti", contents, mobiles);
    }
}
