package com.cbmie.lh.feedback.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name="LH_FEEDBACK_THEME")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class FeedbackTheme extends BaseEntity {
	/**主题*/
	private String title;
	/**状态*/
	private String state="10990001";
	/**分类*/
	private String classification;
	/**类型*/
	private String types;
	/**重要性*/
	private String importance;
	/**反馈对象类别*/
	private String feedbackObjectType;
	/**公共反馈
	 * 0 否 1是
	 * */
	private String feedbackPublic = "0";
	/**反馈对象*/
	private String feedbackObject;
	/**发送手机短信
	 *0 否 1是
	 */
	private String mobile = "0";
	/**负责人*/
	private String dutyUser;
	/**负责部门*/
	private Integer dutyDept;
	/**解决期限*/
	private Date terminalDate;
	/**关闭人员*/
	private Integer closeUser;
	/**描述*/
	private String description;

	private String dealResult;
	/**讨论组 暂停使用*/
	private Long discussGroupId;
	
	private List<FeedbackFile> files = new ArrayList<FeedbackFile>();

	private List<ThemeMember> members = new ArrayList<ThemeMember>();
	
	private List<FeedbackContent> contents = new ArrayList<FeedbackContent>();
	
	//临时变量
	/**讨论人员ID*/
	private String themeMemberIds;
	/**讨论人员name*/
	private String themeMembers;
	/**关键人员ID*/
	private String themeMemberKeyIds;
	/**关键人员name*/
	private String themeMemberKeys;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=15)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(length=15)
	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	@Column(length=15)
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	@Column(length=15)
	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getFeedbackObjectType() {
		return feedbackObjectType;
	}

	public void setFeedbackObjectType(String feedbackObjectType) {
		this.feedbackObjectType = feedbackObjectType;
	}

	public String getFeedbackObject() {
		return feedbackObject;
	}

	public void setFeedbackObject(String feedbackObject) {
		this.feedbackObject = feedbackObject;
	}

	public String getDutyUser() {
		return dutyUser;
	}

	public void setDutyUser(String dutyUser) {
		this.dutyUser = dutyUser;
	}

	public Integer getDutyDept() {
		return dutyDept;
	}

	public void setDutyDept(Integer dutyDept) {
		this.dutyDept = dutyDept;
	}

	public Date getTerminalDate() {
		return terminalDate;
	}

	public void setTerminalDate(Date terminalDate) {
		this.terminalDate = terminalDate;
	}

	public Integer getCloseUser() {
		return closeUser;
	}

	public void setCloseUser(Integer closeUser) {
		this.closeUser = closeUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="themeId",orphanRemoval=true)
	@OrderBy(value="id")
	public List<FeedbackFile> getFiles() {
		return files;
	}

	
	public void setFiles(List<FeedbackFile> files) {
		this.files = files;
	}

	public Long getDiscussGroupId() {
		return discussGroupId;
	}

	public void setDiscussGroupId(Long discussGroupId) {
		this.discussGroupId = discussGroupId;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

	public String getFeedbackPublic() {
		return feedbackPublic;
	}

	public void setFeedbackPublic(String feedbackPublic) {
		this.feedbackPublic = feedbackPublic;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Transient
	public String getThemeMemberIds() {
		return themeMemberIds;
	}

	public void setThemeMemberIds(String themeMemberIds) {
		this.themeMemberIds = themeMemberIds;
	}
	@Transient
	public String getThemeMembers() {
		return themeMembers;
	}

	public void setThemeMembers(String themeMembers) {
		this.themeMembers = themeMembers;
	}
	@Transient
	public String getThemeMemberKeyIds() {
		return themeMemberKeyIds;
	}

	public void setThemeMemberKeyIds(String themeMemberKeyIds) {
		this.themeMemberKeyIds = themeMemberKeyIds;
	}
	@Transient
	public String getThemeMemberKeys() {
		return themeMemberKeys;
	}

	public void setThemeMemberKeys(String themeMemberKeys) {
		this.themeMemberKeys = themeMemberKeys;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="themeId")
	@OrderBy(value="id")
	public List<ThemeMember> getMembers() {
		return members;
	}

	public void setMembers(List<ThemeMember> members) {
		this.members = members;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="feedbackThemeId",orphanRemoval=true)
	@OrderBy(value="id")
	public List<FeedbackContent> getContents() {
		return contents;
	}

	public void setContents(List<FeedbackContent> contents) {
		this.contents = contents;
	}
	
}
