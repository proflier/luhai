package com.cbmie.lh.feedback.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.system.entity.User;
@Entity
@Table(name = "LH_THEME_MEMBER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ThemeMember {
	private Long id;
	/* 关键人员标志 1 是 0 否* */
	private String keyFlag="0";
	/* 主题Id* */
	private Long themeId;
	private User user;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(length=2)
	public String getKeyFlag() {
		return keyFlag;
	}
	public void setKeyFlag(String keyFlag) {
		this.keyFlag = keyFlag;
	}
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
