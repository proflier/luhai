package com.cbmie.activiti.entity;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApprovalOpinion {
	
	/**
	 * 流程ID
	 */
	private String processInstanceId;
	
	/**
	 * 任务ID
	 */
	private String taskId; 
	
	/**
	 * 下一步动作类别
	 * 正常下一步1 驳回2 未来跳转3
	 * */
	private String actionType="1";
	
	/**
	 * 优先级
	 * 0 普通 1紧急 2加急
	 * */
	private String priority;
	
	/**
	 * 限时
	 * */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date limitDate;
	
	/**
	 * 跳转节点ID
	 */
	private String goId;
	
	/**
	 * 驳回节点ID（在下一步办理页面不用此属性，其他地方看到最好去掉）
	 */
	private String backId;
	
	/**
	 * 下一步节点
	 * **/
	private String directNextId;
	
	/**
	 * 业务ID
	 */
	private String businessKey;
	
	/**
	 * 业务key
	 */
	private String key;
	
	/**
	 * 意见
	 */
	private String comments;
	
	/**
	 * 业务信息页面路径
	 */
	private String businessInfoReturnUrl;
	
	/**
	 * email
	 */
	private int email;
	
	/**
	 * 短信
	 */
	private int sms;
	
	/**
	 * 候选人(一个或多个)
	 */
	private String[] candidateUserIds;
	
	/**
	 * 传阅人(>=0)
	 */
	private String[] passUserIds;
	/**
	 * 暂停使用
	 * */
	private Map<String,String[]> candidateIdMap;
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getGoId() {
		return goId;
	}

	public void setGoId(String goId) {
		this.goId = goId;
	}

	public String getBackId() {
		return backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getBusinessInfoReturnUrl() {
		return businessInfoReturnUrl;
	}

	public void setBusinessInfoReturnUrl(String businessInfoReturnUrl) {
		this.businessInfoReturnUrl = businessInfoReturnUrl;
	}

	public boolean getEmail() {
		return email == 1 ? true : false;
	}

	public void setEmail(int email) {
		this.email = email;
	}

	public boolean getSms() {
		return sms == 1 ? true : false;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public String[] getCandidateUserIds() {
		return candidateUserIds;
	}

	public void setCandidateUserIds(String[] candidateUserIds) {
		this.candidateUserIds = candidateUserIds;
	}

	public Map<String, String[]> getCandidateIdMap() {
		return candidateIdMap;
	}

	public void setCandidateIdMap(Map<String, String[]> candidateIdMap) {
		this.candidateIdMap = candidateIdMap;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public String getDirectNextId() {
		return directNextId;
	}

	public void setDirectNextId(String directNextId) {
		this.directNextId = directNextId;
	}

	public String[] getPassUserIds() {
		return passUserIds;
	}

	public void setPassUserIds(String[] passUserIds) {
		this.passUserIds = passUserIds;
	}

}
