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
@Table(name = "LH_DISCUSS_MEMBER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class DiscussMember {

	private Long id;
	private User user;
	/**
	 * 关键人员标志 1 是 0 否 暂时去除
	 * */
	//private String keyFlag="0";
	private Long discussGroupId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/*@Column(length=2)
	public String getKeyFlag() {
		return keyFlag;
	}
	public void setKeyFlag(String keyFlag) {
		this.keyFlag = keyFlag;
	}*/
	@Column(nullable=false)
	public Long getDiscussGroupId() {
		return discussGroupId;
	}
	public void setDiscussGroupId(Long discussGroupId) {
		this.discussGroupId = discussGroupId;
	}
}
