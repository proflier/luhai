package com.cbmie.system.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 账号密码log
 */
@Entity
@Table(name = "user_password_log")
@DynamicUpdate
@DynamicInsert
public class UserPasswordLog implements java.io.Serializable {
	private static final long serialVersionUID = -7673197212240523523L;
	private Long id;

	private String loginName;

	private String password;

	private Integer loginCount;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date lastVisit;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LOGIN_NAME", nullable = false, length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "LAST_VISIT")
	public Date getLastVisit() {
		return this.lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	@Column(name = "LOGIN_COUNT")
	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

}