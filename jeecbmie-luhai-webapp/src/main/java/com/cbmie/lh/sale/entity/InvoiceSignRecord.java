package com.cbmie.lh.sale.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
@Entity
@Table(name = "LH_INVOICE_SIGNRECORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InvoiceSignRecord extends BaseEntity {

	/**客户名称*/
	private String customer;
	private String customerView;
	@Transient
	public String getCustomerView() {
		customerView = DictUtils.getCorpName(customer);
		return customerView;
	}

	public void setCustomerView(String customerView) {
		this.customerView = customerView;
	}

	/**寄票日期*/
	private Date mailDate;
	/**签收人*/
	private String signer;
	/**签收日期*/
	private Date signDate;
	
	private List<InvoiceSignRecordSub> signRecordSubs = new ArrayList<InvoiceSignRecordSub>();

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getMailDate() {
		return mailDate;
	}

	public void setMailDate(Date mailDate) {
		this.mailDate = mailDate;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="invoiceSignRecordId")
	public List<InvoiceSignRecordSub> getSignRecordSubs() {
		return signRecordSubs;
	}

	public void setSignRecordSubs(List<InvoiceSignRecordSub> signRecordSubs) {
		this.signRecordSubs = signRecordSubs;
	}
}
