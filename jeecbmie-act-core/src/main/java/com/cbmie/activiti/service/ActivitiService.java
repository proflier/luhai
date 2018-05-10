package com.cbmie.activiti.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.dao.DelegatePersonDao;
import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.entity.ProcessConfig;
import com.cbmie.activiti.entity.ProcessEntity;
import com.cbmie.activiti.entity.ProcessPass;
import com.cbmie.activiti.entity.ProcessPassDetail;
import com.cbmie.activiti.entity.Variable;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.SimpleHibernateDao;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.IDGenerator;
import com.cbmie.common.utils.PropertiesLoader;
import com.cbmie.common.utils.Reflections;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.service.UserRoleService;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;

@Service
@Transactional
public class ActivitiService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private OrganizationService organizationService;
	
	/*@Autowired
	private SendMailService sendMailService;
	*/
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ProcessConfigService processConfigService;
	
	/*@Autowired
	private FormService formService;*/
	
	@Autowired
	private ProcessEntityService procEntityService;
	
	@Autowired
	private ProcessPassService passService;
	
	@Autowired
	private DelegatePersonDao delegatePersonDao;
	
	protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

	/**
	 * 启动流程
	 *
	 * @param entity
	 */
	/*public ProcessInstance startWorkflow(Object obj, String processKey, Map<String, Object> variables, String userId) {
		ProcessInstance processInstance = null;
		try {
			String businessKey = Reflections.invokeGetter(obj, "id").toString();
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(userId);

			processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
			String processInstanceId = processInstance.getId();
			Reflections.invokeSetter(obj, "processInstanceId", processInstanceId);
			
			// 获取申请人任务节点，自动向下推送一次
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			doClaim(taskList.get(0).getId());
			commitProcess(taskList.get(0).getId(), null, null);
			
			// 获取申请人任务节点的下一节点的候选人,发邮件/短信
			List<Task> taskList_ = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			for (Task task : taskList_) {
				ActivityImpl ai = findActivitiImpl(task.getId(), null);
				String[] candidateUserIds = getCandidateUserIds(ai);
				ApprovalOpinion approval = new ApprovalOpinion();
				approval.setBusinessKey(businessKey);
				approval.setKey(processKey);
				approval.setCandidateUserIds(candidateUserIds);
				approval.setEmail(1);
				sendMailService.sendMail(approval, getBusinessInfo(approval.getKey(), Long.parseLong(approval.getBusinessKey())));
			}
			
			logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { "testapply", businessKey, processInstanceId, variables });
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;
	}*/
	/**
	 * 启动流程
	 *
	 * @param entity
	 */
	public Map<String,Object> startWorkflow(Object obj, String processKey, Map<String, Object> variables, String userId) {
//		User currentUser = UserUtil.getCurrentUser();
//		String orgPrefix = organizationService.getCompany(currentUser.getOrganization()).getOrgPrefix();
//		if(StringUtils.isNotBlank(orgPrefix)){
//			processKey = orgPrefix + processKey;
//		}
		ProcessInstance processInstance = null;
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Object processInstanceIdObj=Reflections.invokeGetter(obj, "processInstanceId");
			String processInstanceId = processInstanceIdObj==null?null:processInstanceIdObj.toString();
			String businessKey = Reflections.invokeGetter(obj, "id").toString();
			if(processInstanceId==null){
				// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
				identityService.setAuthenticatedUserId(userId);
				variables.put("deptId", Reflections.invokeGetter(obj, "deptId").toString());
				variables.put("companyId", Reflections.invokeGetter(obj, "companyId").toString());
				processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
				processInstanceId = processInstance.getId();
				Reflections.invokeSetter(obj, "processInstanceId", processInstanceId);
			}
			String processInstanceName = constructProcInstanceName(processInstanceId,obj);
			runtimeService.setProcessInstanceName(processInstanceId, processInstanceName);
			// 获取申请人任务节点，自动向下推送一次
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			doClaim(taskList.get(0).getId());
			result.put("processInstanceId", processInstanceId);
			result.put("taskId", taskList.get(0).getId());
			result.put("businessKey", businessKey);
			result.put("processKey",processKey );
			logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}",
					new Object[] { processKey, businessKey, processInstanceId, variables });
		}catch(ActivitiObjectNotFoundException e){
			logger.warn("没有部署流程!", e);
			result.put("msg", "no deployment");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("msg", "start fail");
		} finally {
			identityService.setAuthenticatedUserId(null);
			return result;
		}
	}
	
	/**
	 * 根据流程定义中的描述构造流程实体名称
	 * */
	private String constructProcInstanceName(String processInstanceId,Object businessEntity){
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		ProcessDefinition cur_proc_def = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
		String exp = cur_proc_def.getDescription();
		exp = StringUtils.isBlank(exp)?"${summary}":exp;
		StringBuffer result = new StringBuffer();
		String[] exp_array = exp.split("[+]");
		for(String exp_item : exp_array){
			if(StringUtils.isNotBlank(exp_item) && exp_item.startsWith("${") && exp_item.endsWith("}")){
				String exp_field_businessEntity = exp_item.substring(2, exp_item.length()-1);
				Object field_obj=Reflections.invokeGetter(businessEntity, exp_field_businessEntity);
				if(field_obj!=null){
					result.append(field_obj.toString());
				}
			}else{
				result.append(exp_item);
			}
		}
		return result.toString();
	}
	/**
	 * 删除流程实例
	 */
	public boolean deleteProcessInstance(String processInstanceId) {
		// 获取所属流程下所有任务，并按开始时间倒序排列
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByTaskId().desc().list();
		if (htiList.size() == 2 && htiList.get(0).getAssignee() == null) {
			runtimeService.deleteProcessInstance(processInstanceId, Enum.valueOf(AbolishReason.class, "CALLBACK").getValue());
			historyService.deleteHistoricProcessInstance(processInstanceId);
			historyService.deleteHistoricTaskInstance(htiList.get(0).getId());
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 删除流程实例
	 */
	public boolean deleteProcessInstance(String processInstanceId, boolean flag) {
		// 获取所属流程下所有任务，并按开始时间倒序排列
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByTaskId().desc().list();
		if ((htiList.size() == 2 && htiList.get(0).getAssignee() == null) || flag) {
			if (runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list().size() > 0) {
				runtimeService.deleteProcessInstance(processInstanceId, Enum.valueOf(AbolishReason.class, "CALLBACK").getValue());
			}
			historyService.deleteHistoricProcessInstance(processInstanceId);
			historyService.deleteHistoricTaskInstance(htiList.get(0).getId());
			return true;
		} else {
			return false;
		}
	}
	public void deleteProcOnFirstStep(String processInstanceId,String state){
		try {
			state=state==null?ActivitiConstant.ACTIVITI_DRAFT:state;
			if("a".equals(state)){
				state=ActivitiConstant.ACTIVITI_DRAFT;
			}
			ProcessInstance procInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			BaseEntity entity = getReflectionObj(procInstance.getProcessDefinitionId().split(":")[0], Long.parseLong(procInstance.getBusinessKey()));
			deleteProcessInstance(processInstanceId,AbolishReason.GIVEUP);
			Reflections.invokeSetter(entity, "state", state);
			Reflections.invokeSetter(entity, "processInstanceId", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	} 
	/**
	 * 删除流程实例
	 */
	public void deleteProcessInstance(String processInstanceId,AbolishReason reason) throws Exception{
		List<ProcessInstance> procInstances = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list();
		if(procInstances!=null && procInstances.size()>0){
			runtimeService.deleteProcessInstance(processInstanceId, reason.getValue());
		}
		historyService.deleteHistoricProcessInstance(processInstanceId);
	}

	/**
	 * 根据className和id获取数据对象 利用配置文件和反射
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public BaseEntity getReflectionObj(String className, Long id) {
		try {
			/*PropertiesLoader propertiesLoader_clazz = new PropertiesLoader("workflowClazz.properties");
			String fullName = propertiesLoader_clazz.getProperty(className); */
			String fullName = procEntityService.getClazzFullName(className);
			Class clazz = Class.forName(fullName);
			BaseEntity obj = (BaseEntity)getReflectionObject(clazz, id);
			return obj;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据类名和id获取数据对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getReflectionObject(Class clazz, Long id) {
		SimpleHibernateDao simpleHibernateDao = new SimpleHibernateDao(sessionFactory, clazz);
		return simpleHibernateDao.getSession().load(clazz, id);
	}

	/**
	 * 获取待办任务
	 */
	public List<Map<String, Object>> getTodoList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		User user = UserUtil.getCurrentUser();
		String loginName = null;
		if (user == null) {
			loginName = request.getParameter("loginName");
		} else {
			loginName = user.getLoginName();
		}
		// 已经签收的任务
		List<Task> todoList = taskService.createTaskQuery().taskAssignee(loginName).active().orderByTaskPriority().desc()
				.orderByTaskCreateTime().desc().list();
		for (Task task : todoList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

			Map<String, Object> singleTask = packageTaskInfo(task, processDefinition);
			singleTask.put("status", "待办");
			result.add(singleTask);
		}

		// 等待签收的任务
		List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(loginName).active().orderByTaskPriority().desc()
				.orderByTaskCreateTime().desc().list();
		for (Task task : toClaimList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

			Map<String, Object> singleTask = packageTaskInfo(task, processDefinition);
			singleTask.put("status", "待签收");
			result.add(singleTask);
		}
		return result;
	}

	/**
	 * 获取正在运行的流程
	 */
	public void getRunningList(Page<Map<String, Object>> page, HttpServletRequest request) {
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().includeProcessVariables().active();
		if (StringUtils.isNotBlank(request.getParameter("module"))) {
			query.processDefinitionKey(request.getParameter("module").trim());
		}
		if (StringUtils.isNotBlank(request.getParameter("applyPerson"))) {
			query.variableValueLike("apply_AS", request.getParameter("applyPerson").trim());
		}
		if (StringUtils.isNotBlank(request.getParameter("busiInfo"))) {
			query.processInstanceNameLike("%"+request.getParameter("busiInfo").trim()+"%");
		}
		query.orderByProcessInstanceId().desc();
		page.setTotalCount(query.count());
		List<ProcessInstance> list = query.listPage(page.getFirst() - 1, page.getPageSize());
		for (ProcessInstance processInstance : list) {
//			String businessKey = processInstance.getBusinessKey();
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().list();
			Map<String, Object> singleProcess = new HashMap<String, Object>();
//			String processKey = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId()).getKey();
//			String businessInfo = getBusinessInfo(processKey, Long.parseLong(businessKey));
			ProcessDefinition pdf = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
			String modulename = StringUtils.isBlank(pdf.getName())?pdf.getKey():pdf.getName();
			singleProcess.put("moduleName", modulename);
			singleProcess.put("processInstanceId", processInstance.getId());
			singleProcess.put("businessInfo", processInstance.getName());
			singleProcess.put("processDefinitionId", processInstance.getProcessDefinitionId());
			singleProcess.put("suspended", processInstance.isSuspended());
			Map<String,Object> varMap = processInstance.getProcessVariables(); //runtimeService.getVariables(processInstance.getId());
			String aperson = null;
			if(varMap.containsKey("apply_AS")){
				Object t = varMap.get("apply_AS");
				if(t==null){
					aperson = null;
				}else if(AbstractList.class.isAssignableFrom(t.getClass())){
					aperson = StringUtils.join((AbstractList)t, ",");
				}else{
					aperson = t.toString();
				}
			}
			singleProcess.put("applyPerson", aperson);
			List<String> taskName = new ArrayList<String>();
			List<String> taskAssignee = new ArrayList<String>();
			for (Task task : tasks) {
				taskName.add(task.getName());
				taskAssignee.add(task.getAssignee());
			}
			singleProcess.put("curTaskCreateTime", (tasks!=null && tasks.size()>0)?DateUtils.formatDateTime(tasks.get(0).getCreateTime()):"");
			singleProcess.put("curTaskName", StringUtils.join(taskName, ","));
			singleProcess.put("curTaskAssignee", StringUtils.join(taskAssignee, ","));
				page.getResult().add(singleProcess);
		}
	}
	
	/**
	 * 获取传阅任务
	 */
	public List<Map<String, Object>> getProcPassList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		User user = UserUtil.getCurrentUser();
		String loginName = null;
		if (user == null) {
			loginName = request.getParameter("loginName");
		} else {
			loginName = user.getLoginName();
		}
		String sql = "select a.proc_key,a.proc_instance_id,a.business_key,a.create_date,b.id,b.read_flag "
				+ " from act_sys_procpass a,act_sys_procpassdetail b where a.id=b.pass_id and b.assignee=:userName "
				+ " order by a.create_date desc ";
		SQLQuery query = passService.getEntityDao().createSQLQuery(sql);
		query.setParameter("userName", loginName);
		List<Object[]> objs = query.list();
		for (Object[] obj : objs) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("processKey", obj[0]==null?null:obj[0].toString());
			map.put("processInstanceId", obj[1]==null?null:obj[1].toString());
			map.put("businessKey", obj[2]==null?null:obj[2].toString());
			map.put("createDate", obj[3]==null?null:DateUtils.formatDateTime((Date)obj[3]));
			map.put("detailId", obj[4]==null?null:obj[4].toString());
			map.put("readFlag", obj[5]==null?null:obj[5].toString());
			map.put("businessInfo","");
			if(obj[1]!=null){
				List<HistoricProcessInstance> list =  historyService.createHistoricProcessInstanceQuery().processInstanceId(obj[1].toString()).list();
				if(list!=null && list.size()>0){
					map.put("businessInfo",list.get(0).getName());
				}
			}
			boolean flag = true;
			if (StringUtils.isNotBlank(request.getParameter("businessInfo"))) {
				flag = flag && String.valueOf(map.get("businessInfo")).contains(request.getParameter("businessInfo"));
			}
			if (flag) {
				result.add(map);
			}
		}
		return result;
	}
	/**
	 * 获取已办任务
	 */
	public List<Map<String, Object>> getHaveDoneList(HttpServletRequest request) {
		long start = System.currentTimeMillis();
//		PropertiesLoader propertiesLoader_Info = new PropertiesLoader("workflowInfoUrl.properties");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		User user = UserUtil.getCurrentUser();
		String loginName = null;
		if (user == null) {
			loginName = request.getParameter("loginName");
		} else {
			loginName = user.getLoginName();
		}
//		HistoricTaskInstanceQuery htiq = historyService.createHistoricTaskInstanceQuery().processUnfinished()
//				.taskAssignee(user.getLoginName()).finished().orderByHistoricTaskInstanceEndTime().desc();
		
		HistoricTaskInstanceQuery htiq = historyService.createHistoricTaskInstanceQuery().taskAssignee(loginName).finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		
		List<HistoricTaskInstance> list = htiq.list();

		for (HistoricTaskInstance hti : list) {
			Map<String, Object> singleTask = new HashMap<String, Object>();

			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(hti.getProcessInstanceId()).singleResult();
			String pdKey = repositoryService.getProcessDefinition(hti.getProcessDefinitionId()).getKey();
			String entityId = hpi.getBusinessKey();
//			String businessInfo = getBusinessInfo(pdKey, Long.parseLong(entityId));
			String businessInfo = hpi.getName();
			// 获取所属流程下所有任务，并按开始时间倒序排列
			List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(hpi.getId()).orderByTaskId().desc().list();
			// 当集合中第二个key是已办key且第一个"未签收"时可撤回
			if (htiList.size()>1 && hti.getTaskDefinitionKey().equals(htiList.get(1).getTaskDefinitionKey())
					&& htiList.get(0).getAssignee() == null) {
				singleTask.put("callBack", true);
			}

			singleTask.put("name", repositoryService.getProcessDefinition(hpi.getProcessDefinitionId()).getName());
			singleTask.put("processInstanceId", hpi.getId());
			singleTask.put("businessInfo", businessInfo);
			singleTask.put("activityId", htiList.size()>1?htiList.get(1).getTaskDefinitionKey():null);
			singleTask.put("taskId", hti.getId());
			singleTask.put("taskName", hti.getName());
			singleTask.put("currentActivityId", htiList.get(0).getTaskDefinitionKey());
			singleTask.put("startTime", DateUtils.formatDateTime(hti.getStartTime()));
			singleTask.put("endTime", DateUtils.formatDateTime(hti.getEndTime()));
			singleTask.put("viewUrlKey", pdKey);
			singleTask.put("entityId", entityId);
			singleTask.put("executionId", hti.getExecutionId());
			singleTask.put("processDefinitionId", hti.getProcessDefinitionId());

			boolean flag = true;
			if (StringUtils.isNotBlank(request.getParameter("businessInfo"))) {//业务信息
				flag = flag && String.valueOf(singleTask.get("businessInfo")).contains(request.getParameter("businessInfo"));
			}
			if (StringUtils.isNotBlank(request.getParameter("person"))) {//经办人
				boolean personFlag = false;
				for (HistoricTaskInstance hti2 :  htiList) {
					if (StringUtils.isNotBlank(hti2.getAssignee())) {
						if (userService.getUserByLoginName(hti2.getAssignee()).getName().contains(request.getParameter("person"))) {
							personFlag = true;
							break;
						}
					}
				}
				flag = flag && personFlag;
			}
			if (StringUtils.isNotBlank(request.getParameter("current"))) {//当前审批节点
				flag = flag && htiList.get(0).getName().contains(request.getParameter("current"));
			}
			if (StringUtils.isNotBlank(request.getParameter("startDate")) && StringUtils.isNotBlank(request.getParameter("endDate"))) {//处理结束时间段
				Date startDate = DateUtils.parseDate(request.getParameter("startDate"));
				Date endDate = DateUtils.parseDate(request.getParameter("endDate"));
				flag = flag && (hti.getEndTime().getTime() >= startDate.getTime()) && (hti.getEndTime().getTime() <= endDate.getTime());
			}
			if (flag) {
				result.add(singleTask);
			}
		}
		System.out.println("-----getHaveDoneList---- 方法耗时: " + (System.currentTimeMillis() - start));
		return result;
	}

	/**
	 * 获取流程、业务相关key
	 */
	public Map<String, String> getKeys(String processInstanceId) {
		Map<String, String> map = new HashMap<String, String>();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list()
				.get(0);
		String businessKey = pi.getBusinessKey();
		String processKey = repositoryService.getProcessDefinition(pi.getProcessDefinitionId()).getKey();
		map.put("processKey", processKey);
		map.put("businessKey", businessKey);
		return map;
	}

	/**
	 * 获取流程跟踪列表
	 */
	public List<Map<String, Object>> getTraceInfo(String processInstanceId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		HistoricTaskInstanceQuery tiq = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc();
		List<HistoricTaskInstance> listhistory = tiq.list();
		
		for (HistoricTaskInstance taskInstance : listhistory) {
			Map<String, Object> singleTask = new HashMap<String, Object>();
			String taskid = taskInstance.getId();
			HistoricVariableInstanceQuery hivarinstq = historyService.createHistoricVariableInstanceQuery();
			List<HistoricVariableInstance> hvi = hivarinstq.taskId(taskid).list();
			List<String> passUsers = passService.getPassUsersByTaskId(taskid);
			String comments = "";
			for (HistoricVariableInstance var : hvi) {
				if ("comments".equals(var.getVariableName())) {
					comments = (String) var.getValue();
				}
			}
			String dealers = "";
			if(taskInstance.getAssignee() == null){
				List<HistoricIdentityLink> links = historyService.getHistoricIdentityLinksForTask(taskInstance.getId());
				if(links!=null){
					for(HistoricIdentityLink link : links){
						if("candidate".equals(link.getType())){
							String curName = getUserNameByLoginName(link.getUserId());
							if(StringUtils.isNotBlank(curName)){
								if(StringUtils.isNotBlank(dealers)){
									dealers=dealers+","+curName;
								}else{
									dealers=curName;
								}
							}
						}
					}
				}
			}else{
				String curName = getUserNameByLoginName(taskInstance.getAssignee());
				if(StringUtils.isBlank(curName)){
					dealers = taskInstance.getAssignee();
				}else{
					dealers = curName;
				}
			}
			singleTask.put("id", taskInstance.getId());
			singleTask.put("name", taskInstance.getName());
			singleTask.put("assignee", dealers);
			singleTask.put("passUsers", StringUtils.join(passUsers.iterator(), ","));
			singleTask.put("startTime", DateUtils.formatDateTime(taskInstance.getStartTime()));
			singleTask.put("endTime", DateUtils.formatDateTime(taskInstance.getEndTime()));
			singleTask.put("distanceTimes", DateUtils.formatDistanceTimes(DateUtils.formatDateTime(taskInstance.getStartTime()), DateUtils.formatDateTime(taskInstance.getEndTime())));
			singleTask.put("comments", comments);
			result.add(singleTask);
		}
		return result;
	}
	
	/**
	 * 根据processInstanceId获取ProcessDefinitionEntity
	 * @param processInstanceId
	 * @return ProcessDefinitionEntity
	 * @throws Exception
	 */
	public ProcessDefinitionEntity findProcessDefinitionEntityByProcessInstanceId(String processInstanceId) throws Exception {
		String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();
//		ProcessDefinitionEntity processDefinitionEntity = repositoryService.getProcessDefinition(processDefinitionId);
		
		// 取得流程定义
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
		if (processDefinitionEntity == null) {
			throw new Exception("流程定义未找到!");
		}
		return processDefinitionEntity;
	}

	/**
	 * 根据流程定义id获取流程定义示例
	 */
	private ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
		if (processDefinition == null) {
			processDefinition = repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
		}
		return processDefinition;
	}

	/**
	 * 待办任务json打包
	 */
	private Map<String, Object> packageTaskInfo(Task task, ProcessDefinition processDefinition) throws Exception {
		Map<String, Object> singleTask = new HashMap<String, Object>();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.list().get(0);
		String businessKey = pi.getBusinessKey();

		String activityId = findTaskById(task.getId()).getTaskDefinitionKey();
		/*List<FormProperty> list = formService.getTaskFormData(task.getId()).getFormProperties();
		boolean editable = false;
		for(FormProperty fp : list){
			if("form_edit".equals(fp.getId())){
				editable = "true".equals(fp.getValue());
			}
		}*/
		singleTask.put("id", task.getId());
//		singleTask.put("formEdit", editable);
		singleTask.put("activityId", activityId);
		singleTask.put("name", task.getName());
		singleTask.put("createTime", DateUtils.formatDateTime(task.getCreateTime()));
		singleTask.put("pdname", processDefinition.getName());
		singleTask.put("key", processDefinition.getKey());
		singleTask.put("pdversion", processDefinition.getVersion());
		singleTask.put("pid", task.getProcessInstanceId());
		singleTask.put("businessInfo", pi.getName());
		singleTask.put("businessKey", businessKey);
		singleTask.put("priority", task.getPriority());
		if(task.getFormKey()!=null){
			singleTask.put("editable", "1");
		}else{
			singleTask.put("editable", "0");
		}
		return singleTask;
	}

	/**
	 * 获取业务信息
	 */
	public String getBusinessInfo(String processKey, Long id) {
		try {
			BaseEntity baseEntity = getReflectionObj(processKey, id);
//			System.out.println("~~~~~~~~~~~~~~~~id : " + baseEntity.getId());
//			System.out.println("~~~~~~~~~~~~~~~~createrNo : " + baseEntity.getCreaterNo());
//			System.out.println("~~~~~~~~~~~~~~~~summary : " + baseEntity.getSummary() + "; id : " + baseEntity.getId());
			String summary = baseEntity.getSummary();
			return summary;
		} catch(ObjectNotFoundException e) {
//			throw new ObjectNotFoundException(e, "entityName = " + processKey + "; id = " + id);
			return "该表单已被删除[" + processKey + ":" + id + "]";
		}
	}

	public Map<String,Object> doClaim(String taskId) {
		Map<String,Object> map = new HashMap<String,Object>();
//		try{
		 Long count = taskService.createTaskQuery().taskUnassigned().taskId(taskId).count();
		 if(count>0){
			 taskService.claim(taskId, UserUtil.getCurrentUser().getLoginName());
				map.put("flag", true);
		 }else{
			map.put("flag", false);
			map.put("errorMsg", "已被他人签收");
			map.put("erroType", "2");  //2为他人签收
		 }
			return map;
		/*}catch(ActivitiObjectNotFoundException e){
			map.put("flag", false);
			map.put("errorMsg", "没有此任务");
			map.put("erroType", "1");  //1为无此任务
		}
		catch(ActivitiTaskAlreadyClaimedException e){
			map.put("flag", false);
			map.put("errorMsg", "已被他人签收");
			map.put("erroType", "2");  //2为他人签收
		}catch(Exception e){
			map.put("flag", false);
			map.put("errorMsg", "出现错误");
			map.put("erroType", "3");  //2为他人签收
		}finally{
			System.out.println(111);
		}
		return map;*/
	}

	/**
	 * 审批通过(驳回直接跳回功能需后续扩展)
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 */
	public void passProcess(String taskId, Map<String, Object> variables, String comments) throws Exception {
		// List<Task> tasks = taskService.createTaskQuery().parentTaskId(taskId)
		// .taskDescription("jointProcess").list();
		// for (Task task : tasks) {// 级联结束本节点发起的会签任务
		// commitProcess(task.getId(), null, null);
		// }
		taskService.setVariableLocal(taskId, "comments", comments);// 与任务ID绑定
		commitProcess(taskId, variables, null);
	}

	/**
	 * 根据当前任务ID，查询可以驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {
		List<ActivityImpl> rtnList = null;
		/*
		 * if (processOtherService.isJointTask(taskId)) {// 会签任务节点，不允许驳回 rtnList
		 * = new ArrayList<ActivityImpl>(); } else { rtnList =
		 * iteratorBackActivity(taskId, findActivitiImpl(taskId, null), new
		 * ArrayList<ActivityImpl>(), new ArrayList<ActivityImpl>()); }
		 */
		rtnList = iteratorBackActivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>(),
				new ArrayList<ActivityImpl>());
		return reverList(rtnList);
	}

	/**
	 * 根据当前任务ID，查询可以跳转的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findGoAvtivity(String taskId) throws Exception {
		return iteratorGoActivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>(),
				new ArrayList<ActivityImpl>());
	}
	
	/**
	 * 根据当前任务ID，查询下一批任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 */
	public List<ActivityImpl> findNextAvtivity(String taskId) throws Exception {
		return nextAvtivity(taskId, findActivitiImpl(taskId, null), new ArrayList<ActivityImpl>());
	}

	/**
	 * 驳回流程
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            驳回节点ID
	 * @param variables
	 *            流程存储参数
	 * @throws Exception
	 */
	public void backProcess(String taskId, String activityId, Map<String, Object> variables, String comments)
			throws Exception {
		taskService.setVariableLocal(taskId, "comments", comments);// 与任务ID绑定
		// 查找所有并行任务节点，同时驳回
		List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(taskId).getId(),
				findTaskById(taskId).getTaskDefinitionKey());
		for (Task task : taskList) {
			commitProcess(task.getId(), variables, activityId);
		}
	}

	/**
	 * 撤回流程
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @param activityId
	 *            撤回节点ID
	 * @param currentActivityId
	 *            当前节点ID
	 * @throws Exception
	 */
	public void callBackProcess(String processInstanceId, String activityId, String currentActivityId)
			throws Exception {
		if (StringUtils.isEmpty(activityId)) {
			throw new Exception("目标节点ID为空！");
		}
		HistoricTaskInstanceQuery htiq = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId);
		List<HistoricTaskInstance> htiList = htiq.taskDefinitionKey(activityId).orderByHistoricTaskInstanceEndTime().desc().list();
		/*for (HistoricTaskInstance hti : htiList) {
			historyService.deleteHistoricTaskInstance(hti.getId());
		}*/
		
		// 查找所有并行任务节点，同时撤回
		/*List<Task> taskList = findTaskListByKey(processInstanceId, currentActivityId);
		for (Task task : taskList) {
			commitProcess(task.getId(), null, activityId);
			historyService.deleteHistoricTaskInstance(task.getId());
		}*/
		//暂时先不考虑会签等特殊情况
		if(htiList!=null && htiList.size()>0){
			historyService.deleteHistoricTaskInstance(htiList.get(0).getId());
		}//			
		//不考虑会签时此处只有一个值
		List<Task> taskList = findTaskListByKey(processInstanceId, currentActivityId);
		for (Task task : taskList) {
			commitProcess(task.getId(), null, activityId);
			List<Task> cuTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
			if(cuTask!=null && cuTask.size()>0){
				doClaim(cuTask.get(0).getId());
			}
			historyService.deleteHistoricTaskInstance(task.getId());
		}
		
	}

	/**
	 * 中止流程(特权人直接审批通过等)
	 * 
	 * @param taskId
	 */
	public void endProcess(String taskId, String abolishReason) throws Exception {
		Variable var = new Variable();
    	var.setKeys(abolishReason);
    	var.setTypes("S");
    	var.setValues(abolishReason);
    	Map<String, Object> variables = var.getVariableMap();
		commitProcess(taskId, variables, "end");
	}
	/**
	 * 结束流程
	 * @param processInstanceId 流程id
	 * @param activityId 跳转节点
	 * @param variables 流程存储参数
	 * @throws Exception
	 */
	public void endProcess(String processInstanceId) throws Exception {
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		if(tasks==null || tasks.size()==0) return;
		Task task_ = tasks.get(0);
		User currentUser = UserUtil.getCurrentUser();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(task_.getProcessDefinitionId()); 
		ActivityImpl endActivity = null;
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl  
                        .getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                	endActivity = activityImpl;
                }  
            }  
            if(endActivity!=null){
            	for (Task task : tasks) {
            		taskService.claim(task.getId(), currentUser!=null?currentUser.getLoginName():"");
        			turnTransition(task.getId(), endActivity.getId(), new HashMap<String, Object>());
        		}
            }
		
	}
	/**
	 * 转办流程
	 * 
	 * @param taskId
	 *            当前任务节点ID
	 * @param userCode
	 *            被转办人Code
	 */
	public void transferAssignee(String taskId, String userCode) {
		taskService.setAssignee(taskId, userCode);
	}

	/**
	 * 会签操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param userCodes
	 *            会签人账号集合
	 * @throws Exception
	 * 
	 *             缺少核心逻辑
	 */
	public void jointProcess(String taskId, List<String> userCodes) throws Exception {
		for (String userCode : userCodes) {
			TaskEntity task = (TaskEntity) taskService.newTask(IDGenerator.generateID().toString());
			task.setAssignee(userCode);
			task.setName(findTaskById(taskId).getName() + "-会签");
			task.setProcessDefinitionId(findProcessDefinitionEntityByTaskId(taskId).getId());
			task.setProcessInstanceId(findProcessInstanceByTaskId(taskId).getId());
			task.setParentTaskId(taskId);
			task.setDescription("jointProcess");
			taskService.saveTask(task);
		}
	}

	/**
	 * 迭代循环流程树结构，查询当前节点可驳回的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储回退节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 回退节点集合
	 */
	private List<ActivityImpl> iteratorBackActivity(String taskId, ActivityImpl currActivity,
			List<ActivityImpl> rtnList, List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点的流入来源
		List<PvmTransition> incomingTransitions = currActivity.getIncomingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		/*List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();*/
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : incomingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			// 过滤分支
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			ActivityImpl activityImpl = transitionImpl.getSource();
			String type = (String) activityImpl.getProperty("type");
			/**
			 * 并行节点配置要求：<br>
			 * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
			 */
			/*if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);
				if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
					return rtnList;
				} else {// 并行终点
					parallelGateways.add(activityImpl);
				}
			} else if ("startEvent".equals(type)) {// 开始节点，停止递归  
				return rtnList;
            } else */if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线
				exclusiveGateways.add(activityImpl);
			}
		}

		/**
		 * 根据同级userTask集合，过滤最近发生的节点
		 */
		/*currActivity = filterNewestActivity(processInstance, tempList);*/
		if (tempList.size() == 1) {
			currActivity = tempList.get(0);
			/*// 查询当前节点的流向是否为并行终点，并获取并行起点ID
			String id = findParallelGatewayId(currActivity, "start");
			if (StringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点*/
				rtnList.add(currActivity);
			/*} else {
				currActivity = findActivitiImpl(taskId, id);
			}*/

			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorBackActivity(taskId, currActivity, rtnList, tempList);
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}

		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		/*for (ActivityImpl activityImpl : parallelGateways) {
			iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
		}*/

		return rtnList;
	}
	
	/**
	 * 迭代循环流程树结构，查询当前节点可跳转的任务节点
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param currActivity
	 *            当前活动节点
	 * @param rtnList
	 *            存储跳转节点集合
	 * @param tempList
	 *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
	 * @return 跳转节点集合
	 */
	private List<ActivityImpl> iteratorGoActivity(String taskId, ActivityImpl currActivity,
			List<ActivityImpl> rtnList, List<ActivityImpl> tempList) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点的流入来源
		List<PvmTransition> outgoingTransitions = currActivity.getOutgoingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			
			ActivityImpl activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			
			if ("userTask".equals(type)) {// 用户任务
				tempList.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线
				exclusiveGateways.add(activityImpl);
			}
		}

		if (tempList.size() == 1) {
			currActivity = tempList.get(0);
			rtnList.add(currActivity);
			// 清空本次迭代临时集合
			tempList.clear();
			// 执行下次迭代
			iteratorGoActivity(taskId, currActivity, rtnList, tempList);
		}

		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			iteratorGoActivity(taskId, activityImpl, rtnList, tempList);
		}

		return rtnList;
	}
	
	/**
	 * 查询当前节点的下一用户节点
	 * @param taskId
	 * @return
	 */
	private List<ActivityImpl> nextAvtivity(String taskId, ActivityImpl currActivity, List<ActivityImpl> list) throws Exception {
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点的流入来源
		List<PvmTransition> outgoingTransitions = currActivity.getOutgoingTransitions();
		// 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
		List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();
		// 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
		List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			
			ActivityImpl activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			
			if ("parallelGateway".equals(type)) {// 并行路线
				parallelGateways.add(activityImpl);
			} else if ("exclusiveGateway".equals(type)) {// 分支路线
				exclusiveGateways.add(activityImpl);
			} else if ("userTask".equals(type)) {// 用户任务
				list.add(activityImpl);
			}
		}
	
		/**
		 * 迭代条件分支集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : exclusiveGateways) {
			nextAvtivity(taskId, activityImpl, list);
		}
	
		/**
		 * 迭代并行集合，查询对应的userTask节点
		 */
		for (ActivityImpl activityImpl : parallelGateways) {
			nextAvtivity(taskId, activityImpl, list);
		}
		
		return list;
	}

	/**
	 * 仿造nextAvtivity方法，获取下一步办理路径
	 * */
	public List<PvmTransition> nextPvmTransition(String taskId, ActivityImpl currActivity) throws Exception {
		List<PvmTransition> result_list = new ArrayList<PvmTransition>();
		// 查询流程定义，生成流程树结构
		ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
		// 获取流程下的变量
		Map<String, Object> variableMap = runtimeService.getVariables(processInstance.getId());
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (Entry<String, Object> entry : variableMap.entrySet()) {
			engine.put(entry.getKey(), entry.getValue());
		}
		// 当前节点
		List<PvmTransition> outgoingTransitions = currActivity.getOutgoingTransitions();
		// 遍历当前节点所有流入路径
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			if (transitionImpl.getProperty("conditionText") != null) {
				String conditionText = StringUtils.substringBetween(transitionImpl.getProperty("conditionText").toString(), "{", "}");
				if (conditionText != null && !conditionText.equals("pass")) {
					Boolean result = (Boolean)engine.eval(conditionText);
					if (!result) {
						continue;
					}
				}
			}
			result_list.add(pvmTransition);
		}
		return result_list;
	}
	public Map<String,Map<String, Object>> getNextDealerAndReaderByTransition(String taskId,String nextId,String nextType)throws Exception{
		Map<String, Object> dealer = getNextDealerByTransition(taskId,nextId,nextType);
		Map<String, Object> reader = getNextReaderByTransition(taskId,nextId,nextType);
		Map<String,Map<String, Object>> result = new HashMap<String,Map<String, Object>>();
		result.put("reader", reader);
		result.put("dealer", dealer);
		return result;
	}
	public Map<String, Object> getNextReaderByTransition(String taskId,String nextId,String nextType) throws Exception{
		Map<String, Object> result = null;
		if("1".equals(nextType)){
			ActivityImpl curActivity = findActivitiImpl(taskId,null);
			TransitionImpl transition = curActivity.findOutgoingTransition(nextId);
			String ex = transition.getProperty("documentation")==null?null:transition.getProperty("documentation").toString();
			if(ex==null) return null;
			String assigneeExp = dealExpress(ex);
			ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
			String processInstanceId = processInstance.getProcessInstanceId();
			result = fillSelectItemsByV(processInstanceId,nextId,assigneeExp,"2");
		}
		return result;
	}
	/**
	 * 根据transition获取下一步办理对象
	 * @return selectType A/S/M,  selectContent U/G,  directKey直接节点,   key趋势节点,  selectItems具体内容
	 * @throws Exception 
	 * */
	public Map<String, Object> getNextDealerByTransition(String taskId,String nextId,String nextType) throws Exception{
		if("2".equals(nextType)){ //驳回节点
			String backUser = getBackUser(taskId,nextId);
			 Map<String, Object> result = new HashMap<String,Object>();
			 Map<String,String> selects = new HashMap<String,String>();
			 if(backUser!=null){
				 selects.put(backUser, getUserNameByLoginName(backUser));
			 }
			 result.put("selectType", "A");
			 result.put("selectItems", selects);
			 result.put("selectContent", "U");
			 result.put("directKey", nextId);
			 result.put("key", nextId);
			 return result;
		}else if("1".equals(nextType)){
			ActivityImpl curActivity = findActivitiImpl(taskId,null);
			TransitionImpl transition = curActivity.findOutgoingTransition(nextId);
			ActivityImpl activiti = transition.getDestination();
			ActivityImpl activiti_key = activiti;
			String directKey = activiti.getId();
			String type = (String) activiti.getProperty("type");
			if(!"endEvent".equals(type) && !"userTask".equals(type)){
				List<ActivityImpl> list = new ArrayList<ActivityImpl>();
				nextAvtivity(taskId,curActivity,list);
				if(list.size()>0){
					activiti_key = list.get(0); //此处注意，未考虑并行情况
					type = (String) activiti_key.getProperty("type");
				}
			}
			if("endEvent".equals(type)){ //结束节点
				 Map<String, Object> end_map = new HashMap<String,Object>();
				 Map<String,String> end_selects = new HashMap<String,String>();
				 end_selects.put("", "结束");
				 end_map.put("selectType", "A");
				 end_map.put("selectItems", end_selects);
				 end_map.put("selectContent", "U");
				 end_map.put("directKey", directKey);
				 end_map.put("key", activiti_key.getId());
				 return end_map;
			}
			ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
			String processInstanceId = processInstance.getProcessInstanceId();
			ActivityImpl[] temp = {activiti_key};
			Map<String, Map<String, Object>> result = getCandidateUsers(temp,processInstanceId);
			if(!result.isEmpty()){
				Map<String, Object> t = result.get(activiti_key.getId());
				t.put("directKey", directKey);
				return t;
			}
		}
		return null;
	}
	private Map<String,Object> prepareBackUser(){
//		getBackUser(taskId,activityId);
		return null;
	}
	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance findProcessInstanceByTaskId(String taskId) throws Exception {
		// 找到流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(findTaskById(taskId).getProcessInstanceId()).singleResult();
		if (processInstance == null) {
			throw new Exception("流程实例未找到!");
		}
		return processInstance;
	}

	/**
	 * 根据流入任务集合，查询最近一次的流入任务节点
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param tempList
	 *            流入任务集合
	 * @return
	 */
	private ActivityImpl filterNewestActivity(ProcessInstance processInstance, List<ActivityImpl> tempList) {
		while (tempList.size() > 0) {
			ActivityImpl activity_1 = tempList.get(0);
			HistoricActivityInstance activityInstance_1 = findHistoricUserTask(processInstance, activity_1.getId());
			if (activityInstance_1 == null) {
				tempList.remove(activity_1);
				continue;
			}

			if (tempList.size() > 1) {
				ActivityImpl activity_2 = tempList.get(1);
				HistoricActivityInstance activityInstance_2 = findHistoricUserTask(processInstance, activity_2.getId());
				if (activityInstance_2 == null) {
					tempList.remove(activity_2);
					continue;
				}

				if (activityInstance_1.getEndTime().before(activityInstance_2.getEndTime())) {
					tempList.remove(activity_1);
				} else {
					tempList.remove(activity_2);
				}
			} else {
				break;
			}
		}
		if (tempList.size() > 0) {
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 查询指定任务节点的最新记录
	 * 
	 * @param processInstance
	 *            流程实例
	 * @param activityId
	 * @return
	 */
	private HistoricActivityInstance findHistoricUserTask(ProcessInstance processInstance, String activityId) {
		HistoricActivityInstance rtnVal = null;
		// 查询当前流程实例审批结束的历史节点
		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.activityType("userTask").processInstanceId(processInstance.getId()).activityId(activityId).finished()
				.orderByHistoricActivityInstanceEndTime().desc().list();
		if (historicActivityInstances.size() > 0) {
			rtnVal = historicActivityInstances.get(0);
		}

		return rtnVal;
	}

	/**
	 * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行ID
	 * 
	 * @param activityImpl
	 *            当前节点
	 * @param str
	 *            start/end
	 * @return
	 */
	private String findParallelGatewayId(ActivityImpl activityImpl, String str) {
		List<PvmTransition> outgoingTransitions = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : outgoingTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			activityImpl = transitionImpl.getDestination();
			String type = (String) activityImpl.getProperty("type");
			if ("parallelGateway".equals(type)) {// 并行路线
				String gatewayId = activityImpl.getId();
				String gatewayType = gatewayId.substring(gatewayId.lastIndexOf("_") + 1);
				if ("END".equals(gatewayType.toUpperCase())) {
					if (str.equals("start")) {
						return gatewayId.substring(0, gatewayId.lastIndexOf("_")) + "_start";
					} else if (str.equals("end")) {
						return gatewayId.substring(0, gatewayId.lastIndexOf("_")) + "_end";
					}
				}
			}
		}
		return null;
	}

	/**
	 * 反向排序list集合，便于驳回节点按顺序显示
	 * 
	 * @param list
	 * @return
	 */
	private List<ActivityImpl> reverList(List<ActivityImpl> list) {
		List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();
		// 由于迭代出现重复数据，排除重复
		for (int i = list.size(); i > 0; i--) {
			if (!rtnList.contains(list.get(i - 1)))
				rtnList.add(list.get(i - 1));
		}
		return rtnList;
	}

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl findActivitiImpl(String taskId, String activityId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (StringUtils.isEmpty(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);

		return activityImpl;
	}

	public String findActivitiV(String taskId, String activityId) throws Exception{
		ActivityImpl activityImpl = findActivitiImpl(taskId,activityId);
		TaskDefinition tdf = (TaskDefinition) activityImpl.getProperties().get("taskDefinition");
		if(tdf==null){ //当节点不是任务节点时
			return null;
		}
		String key = tdf.getKey();
		Expression assignee = tdf.getAssigneeExpression();
		Set<Expression> exSet = tdf.getCandidateUserIdExpressions();
		String srtExp = null;
		if(assignee!=null){
			srtExp = dealExpress(assignee.getExpressionText());
		}else if(exSet!=null && exSet.size()>0){
			String temp = "";
			for(Expression e: exSet){
				temp+=dealExpress(e.getExpressionText())+",";
			}
			srtExp = temp.substring(0, temp.length()-1);
		}
		if(srtExp!=null && srtExp.startsWith(key+"_")){
			return srtExp;
		}else{
			return null;
		}
	}
	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}

	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("任务实例未找到!");
		}
		return task;
	}

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	public List<Task> findTaskListByKey(String processInstanceId, String key) {
		if (key == null) {
			return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		}
		return taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(key).list();
	}

	/**
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程变量
	 * @param activityId
	 *            流程转向执行任务节点ID<br>
	 *            此参数为空，默认为提交操作
	 * @throws Exception
	 */
	private void commitProcess(String taskId, Map<String, Object> variables, String activityId) throws Exception {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isEmpty(activityId)) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}

	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);
		TaskEntity t = findTaskById(taskId);
		if(t.getDelegationState() != null && t.getDelegationState().equals(DelegationState.PENDING)){
			taskService.resolveTask(taskId);
		}
		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	
	/**
	 * 根据节点获取候选人(一个或多个)
	 */
	public String[] getCandidateUserIds(ActivityImpl... nextActivity) {
		List<String> candidateUserIds = new ArrayList<String>();
		for (ActivityImpl ai : nextActivity) {
			TaskDefinition tdf = (TaskDefinition) ai.getProperties().get("taskDefinition");
			Set<Expression> exSet = tdf.getCandidateUserIdExpressions();
			if (exSet.size() == 0) {
				candidateUserIds.add(tdf.getAssigneeExpression().getExpressionText());
			} else {
				Iterator<Expression> exIt = exSet.iterator();
				while (exIt.hasNext()) {
					Expression ex = exIt.next();
					candidateUserIds.add(ex.getExpressionText());
				}
			}
		}
		return candidateUserIds.toArray(new String[candidateUserIds.size()]);
	}
	
	
	/**
	 * 根据节点获取候选人 
	 * 对于assignee、candidateUserIds,candidateGroupIds只支持一种配置
	 */
	public Map<String,Map<String,Object>> getCandidateUsers(ActivityImpl[] nextActivity,String processInstanceId) {
		List<String> candidateUserIds = new ArrayList<String>();
		Map<String,Map<String,Object>> result = new HashMap<String,Map<String,Object>>();
		for (ActivityImpl ai : nextActivity) {
			
			TaskDefinition tdf = (TaskDefinition) ai.getProperties().get("taskDefinition");
			if(tdf==null){ //没有办理人
				continue;
			}
			String key = tdf.getKey();
			Expression assignee = tdf.getAssigneeExpression();
			if(assignee!=null){
				String assigneeExp = dealExpress(assignee.getExpressionText());
				result.put(key, fillSelectItemsByV(processInstanceId,key,assigneeExp,"1"));
				continue;
			}
			Set<Expression> exSet = tdf.getCandidateUserIdExpressions();
			if(exSet!=null && exSet.size()>0){
				String temp = "";
				for(Expression e: exSet){
					temp+=dealExpress(e.getExpressionText())+",";
				}
				temp = temp.substring(0, temp.length()-1);
				result.put(key, fillSelectItemsByV(processInstanceId,key,temp,"2"));
				continue;
			}
			Set<Expression> exSet1 = tdf.getCandidateGroupIdExpressions();
			if(exSet1!=null && exSet1.size()>0){
				String temp = "";
				for(Expression e: exSet1){
					temp+=dealExpress(e.getExpressionText())+",";
				}
				temp = temp.substring(0, temp.length()-1);
				result.put(key, fillSelectItemsByV(processInstanceId,key,temp,"3"));
			}
		}
		return result;
	}
	private String dealExpress(String exp){
		Pattern pattern = Pattern.compile("^\\$\\{(.+)\\}$");
		Matcher matcher = pattern.matcher(exp);
		if (matcher.find()) {
			return matcher.group(1);
		}else{
			return exp;
		}

	}
	/**
	 * 目前只有assignee、candidateUsers出现变量
	 * candidateGroups只能为常量
	 * * 注释  selectType A（指定） S（单选） M(多选)
	 * selectContent   G 组   U 人员
	 * selectRange G 组  S 发起人  C 公司 U 人员  D 部门
	 * selectItems 选择项
	 * param type 指处理人  直接1  候选人2 候选组3
	 * */
	private Map<String,Object> fillSelectItemsByV(String processInstanceId,String taskKey,String vExp,String type){
		if(vExp==null) return null;
		Map<String,Object> result_per_acti = new HashMap<String,Object>();
		Map<String,String> selects = new LinkedHashMap<String,String>();
		result_per_acti.put("key",taskKey);
		result_per_acti.put("exp", vExp);
		result_per_acti.put("selectContent", "3".equals(type)?"G":"U");
		result_per_acti.put("selectRange", "3".equals(type)?"G":"U");
		if(vExp.startsWith(taskKey+"_") && !"3".equals(type)){ //节点key  变量
			String sr_v  = vExp.substring((taskKey+"_").length());
			int sr_l = sr_v.indexOf("_");
			String setlevel = (sr_l==-1)?sr_v:sr_v.substring(0, sr_l);
			String value_range = (sr_l==-1)?null:sr_v.substring(sr_l+1);
			char[] sets = setlevel.toCharArray();
			//单选、多选、指定设置
			if('A'==sets[0]){ //指定
				result_per_acti.put("selectType", "A");
			}else if('S'==sets[0] || type.equals("1")){
				result_per_acti.put("selectType", "S");
			}else if('M'==sets[0]){
				result_per_acti.put("selectType", "M");
			}
			char range = sets[1];  //范围 G组   C公司
			if('G'==range){
				result_per_acti.put("selectRange", "G");
				List<User> selectUsers = userRoleService.getUsersByRoleCode(value_range);
				for(User user : selectUsers){
					selects.put(user.getLoginName(), user.getName());
				}
				result_per_acti.put("selectItems", selects);
			}else if('C'==range){
				result_per_acti.put("selectRange", "C");
			}else if('S'==range){
				result_per_acti.put("selectRange", "S");
				HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				if(hp!=null){
					selects.put(hp.getStartUserId(),getUserNameByLoginName(hp.getStartUserId()));
				}
				result_per_acti.put("selectItems", selects);
			}else if('D'==range){
				result_per_acti.put("selectRange", "D");
				HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				if(hp!=null){
					User user = userService.getUser(hp.getStartUserId());
					int deptId = user.getOrganization().getId();
					Map<String,String> selectUsers = userRoleService.getUsersByRoleDept(value_range,deptId);
					selects.putAll(selectUsers);
				}
				result_per_acti.put("selectItems", selects);
			}else if('U'==range){
				result_per_acti.put("selectRange", "U");
				String[] loginNames = value_range.split("_U_");
				for(String loginName :loginNames){
					selects.put(loginName, getUserNameByLoginName(loginName));
				}
				result_per_acti.put("selectItems", selects);
			}else if('F'==range){
				result_per_acti.put("selectRange", "F");
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				String businessKey = pi.getBusinessKey();
				String processKey = repositoryService.getProcessDefinition(pi.getProcessDefinitionId()).getKey();
				BaseEntity entity = getReflectionObj(processKey,Long.parseLong(businessKey));
				Object loginNameObj=Reflections.invokeGetter(entity, value_range);
				selects.put(loginNameObj.toString(), getUserNameByLoginName(loginNameObj.toString()));
				result_per_acti.put("selectItems", selects);
			}
		}else{//常量
			result_per_acti.put("selectType", "A");
			String[] vs = vExp.split(",");
			for(String v:vs){
				selects.put(v, getUserNameByLoginName(v));
			}
			result_per_acti.put("selectItems", selects);
		}
		return result_per_acti;
	}
	public static void main(String[] args) {
		String vExp = "leader_SU";
		String sr_v = vExp.substring(("leader"+"_").length());
		System.out.println(sr_v);
		int sr_l = sr_v.indexOf("_");
		System.out.println(sr_l);
		String setlevel = (sr_l==-1)?sr_v:sr_v.substring(0, sr_l);
		System.out.println(setlevel);
		String value_range = (sr_l==-1)?null:sr_v.substring(sr_l+1);
		System.out.println(value_range);
	}
	private String getUserNameByLoginName(String loginName){
		String result="";
		if(StringUtils.isNotBlank(loginName)){
			User user = userService.getUser(loginName);
			if(user!=null){
				result = user.getName();
			}
		}
		return result;
	}
	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
		// ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}
		
		// 不使用spring请使用下面的两行代码
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();

		return processDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds,new ArrayList<String>(), "宋体", "宋体", null, 1.0);
	}
	/**
	 * 获取退回节点办理人员(根据历史)
	 * **/
	public String getBackUser(String taskId,String activityId){
		String processInstanceId = ((TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult()).getProcessInstanceId();
		String userId = null;
		List<HistoricTaskInstance> ht_list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskDefinitionKey(activityId).orderByHistoricTaskInstanceEndTime().desc().list();
		if(ht_list!=null && ht_list.size()>0){
			userId = ht_list.get(0).getAssignee();
		}
		return userId;
	}
	
	/**
	 * 获取退回节点办理人员(根据历史)
	 * @throws Exception 
	 * **/
	public List<ActivityImpl> getDirectBackAvtivity(String taskId) throws Exception{
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = taskEntity.getProcessInstanceId();
		List<ActivityImpl> result =new ArrayList<ActivityImpl>();
		ActivityImpl activity = findActivitiImpl(taskId, null);
		List<ActivityImpl> candidate_activitys = new ArrayList<ActivityImpl>();
		getIncomingUserTask(activity,candidate_activitys);
		List<HistoricTaskInstance> ht_list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskId().desc().list();
		for(HistoricTaskInstance instance : ht_list){
			for(ActivityImpl activiti : candidate_activitys){
				if(activiti.getId().equals(instance.getTaskDefinitionKey())){
					result.add(activiti);
					return result;
				}
			}
		}
		return result;
	}
	
	private void getIncomingUserTask(ActivityImpl activity,List<ActivityImpl> result){
		List<PvmTransition> incomingTransitions = activity.getIncomingTransitions();
		for(PvmTransition transtion : incomingTransitions){
			TransitionImpl transitionImpl = (TransitionImpl) transtion;
			ActivityImpl activity_temp = transitionImpl.getSource();
			String type = (String) activity_temp.getProperty("type");
			if("userTask".equals(type)){
				result.add(activity_temp);
			}else{
				getIncomingUserTask(activity_temp,result);
			}
		}
	}
	
	public Task getTaskById(String taskId){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return task;
	}
	
	public Task setCurrentTaskPriority(String processInstanceId,String taskDefId,int priority){
		List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefId).active().list();
		if(list!=null && list.size()>0){
			list.get(0).setPriority(priority);
			taskService.saveTask(list.get(0));
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 传阅
	 * */
	public void transProcPass(String procKey,String procInstanceId,String taskId,String nextTaskId,String businessKey,String[] assignees){
		ProcessPass pass = new ProcessPass();
		pass.setProcKey(procKey);
		pass.setProcInstanceId(procInstanceId);
		pass.setTaskId(taskId);
		pass.setNextTaskId(nextTaskId);
		pass.setBusinessKey(businessKey);
		pass.setCreateDate(new Date());
		for(String temp : assignees){
			ProcessPassDetail detail = new ProcessPassDetail();
			detail.setProcPass(pass);
			detail.setAssignee(temp);
			pass.getDetails().add(detail);
		}
		passService.save(pass);
	}
	
	public void readProPass(String detailId){
		passService.readProPass(detailId);
	}
	
	public List<Task> getToDelegateTask(String processInstanceId) throws JsonProcessingException{
		return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	}
	public void delegateTask(String delegateTaskId,String ownerId){
		Task task = getTaskById(delegateTaskId);
		String procKey = "";
		if(task!=null){
			ProcessDefinition pd = getProcessDefinition(task.getProcessDefinitionId());
			procKey = pd.getKey();
		}
		 String mandatary = delegatePersonDao.getMandataryByConsigner(procKey,ownerId);
		 if(StringUtils.isNotBlank(mandatary)){
			 taskService.claim(delegateTaskId, ownerId);
			 taskService.delegateTask(delegateTaskId, mandatary);
		 }
	}
	public boolean delegateTask(String[] delegateTaskId,String delegateUserId){
		boolean flag = true;
		try{
			for(int i=0;i<delegateTaskId.length;i++){
				TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(delegateTaskId[i]).singleResult();
				TaskEntity t = findTaskById(delegateTaskId[i]);
				if(t!=null){
					if(StringUtils.isBlank(t.getAssignee())){
						List<IdentityLink> l = taskService.getIdentityLinksForTask(delegateTaskId[i]);
						for(IdentityLink link : l){
							if(IdentityLinkType.CANDIDATE.equals(link.getType())){
								String userId = link.getUserId();
								if(StringUtils.isBlank(userId)){
									taskService.claim(delegateTaskId[i], userId);
									break;
								}
							}
						}
					}
				}
				taskService.delegateTask(delegateTaskId[i], delegateUserId);
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
		
	}
  	
    /**判断是否可催办**/
	public boolean checkIfRemind(String processInstanceId){
		boolean canRemind = false;
		//发起人
		HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();  
		String starter = hp.getStartUserId();
		User currentUser = UserUtil.getCurrentUser();
		if(starter!=null && starter.equals(currentUser.getLoginName())){
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(processInstance!=null && !processInstance.isEnded()){
				canRemind = true;
			}
		}
		return canRemind;
	}
	
	public List<String> getRemindPersons(String processInstanceId) throws Exception{
		List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<String> persons = new ArrayList<String>();
		for(Task task : list){
			if(task.getAssignee()!=null){
				persons.add(task.getAssignee());
			}else{
				List<IdentityLink> links = taskService.getIdentityLinksForTask(task.getId());
				if(links!=null){
					for(IdentityLink link : links){
						if("candidate".equals(link.getType())){
							persons.add(link.getUserId());
						}
					}
				}
			}
		}
		return persons;
	}
	
	public void push(String loginName, String summary, ApprovalOpinion approval, Task task) throws Exception {
		PropertiesLoader pl = new PropertiesLoader("mobile.properties");
		String pushURL = null;
		if (loginName == null) {
			HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(approval.getProcessInstanceId()).singleResult();
			loginName = hp.getStartUserId();
			pushURL = pl.getProperty("endPushURL");
		} else {
			pushURL = pl.getProperty("taskPushURL");
		}
		URL url = new URL(pushURL + "?" + getParams(loginName, summary, approval, task));
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("Content-type", "text/html");
		httpConn.setRequestProperty("Accept-Charset", "utf-8");
		httpConn.setRequestProperty("contentType", "utf-8");
		httpConn.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		httpConn.disconnect();
	}

	private String getParams(String loginName, String summary, ApprovalOpinion approval, Task task) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("username=" + loginName);
		String title = URLEncoder.encode(summary, "utf-8");
		sb.append("&title=" + title);
		if (task != null) {
			String taskId = task.getId();
			sb.append("&taskId=" + taskId);
			String processInstanceId = task.getProcessInstanceId();
			sb.append("&processInstanceId=" + processInstanceId);
		}
		if (approval != null) {
			String businessKey = approval.getBusinessKey();
			sb.append("&businessKey=" + businessKey);
			String key = approval.getKey();
			sb.append("&key=" + key);
		}
		return sb.toString();
	}

	public ProcessConfig findEffectConfict(String entityView) {
		ProcessConfig processConfig = processConfigService.findEffectConfict(entityView);
		return processConfig;
	}
	
	/**
	 * 流程分支中未找到数据只能是未配置分支只有一个流程
	 * 从流程模型中根据实体寻找
	 * @param entityView
	 * @return
	 */
	public ProcessEntity  findProcHave(String entityView) {
		List<ProcessEntity> processEntitys = procEntityService.findProcHave(entityView);
		if(processEntitys.size()>1){//同一业务配置多个流程，但是未配置
			return null;
		}else{//只存在一个流程
			return processEntitys.get(0);
		}
		
	}

	/**
	 * 根据实体class查找所有当前业务所有流程实例
	 * @param entityView
	 * @return
	 */
	public List<ProcessEntity> findAllProcess(String entityView) {
		List<ProcessEntity> processEntitys = procEntityService.findProcHave(entityView);
		return processEntitys;
	}
	
	/**
	 *  获取流程唯一标识
	 * @param currentEntity
	 * @param o
	 * @return
	 */
	public String getCurrentProcessKey(Class currentEntity, Object o){
		String processKey = null;
		ProcessConfig processConfig = findEffectConfict(currentEntity.getName());
		if(processConfig!=null){//是否存在机构判断流程
			String keyValueRel = Reflections.invokeGetter(o, processConfig.getKeyWord()).toString();
			User businessManager = userService.getUserByLoginName(keyValueRel);
			Organization organizationRel = organizationService.getCompany(businessManager.getOrganization());
			List<ProcessEntity> processEntitys = findAllProcess(currentEntity.getName());
			for(ProcessEntity processEntity : processEntitys){
				if(processEntity.getProcDefKey().contains(organizationRel.getOrgBusiCode())){
					processKey = processEntity.getProcDefKey();
					return processKey;
				}
			}
		}else{//不存在
			ProcessEntity processEntity = findProcHave(currentEntity.getName());
			if(processEntity!=null){//流程图为配置或配置多个流程图
				processKey = processEntity.getProcDefKey();
				return processKey;
			}
		}
		return null;
	}
	
	
	
	
	
	
}
