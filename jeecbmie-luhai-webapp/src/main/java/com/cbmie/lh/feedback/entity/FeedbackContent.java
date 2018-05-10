package com.cbmie.lh.feedback.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="LH_FEEDBACK_CONTENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class FeedbackContent {
	private Long id;
	/**发表人*/
	private Integer userId;
	/**发表部门*/
	private Integer deptId;
	/**发表时间*/
	private Date publishDate;
	/**发表内容*/
	private String content;
	private Long feedbackThemeId;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	@Column(length=2000)
	public String getContent() {
		content = StringEscapeUtils.unescapeHtml4(content);
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getFeedbackThemeId() {
		return feedbackThemeId;
	}
	public void setFeedbackThemeId(Long feedbackThemeId) {
		this.feedbackThemeId = feedbackThemeId;
	}
}
