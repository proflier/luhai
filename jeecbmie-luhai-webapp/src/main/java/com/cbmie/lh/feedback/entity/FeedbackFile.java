package com.cbmie.lh.feedback.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Entity
@Table(name="LH_FEEDBACK_FILE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class FeedbackFile {

	private Long id;
	private String fileTypes;
	private String fileTarget;
	private Long themeId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileTarget() {
		return fileTarget;
	}
	public void setFileTarget(String fileTarget) {
		this.fileTarget = fileTarget;
	}
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getFileTypes() {
		return fileTypes;
	}
	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}
}
