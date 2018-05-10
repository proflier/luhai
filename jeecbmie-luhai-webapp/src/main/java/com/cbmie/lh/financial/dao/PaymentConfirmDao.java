package com.cbmie.lh.financial.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.financial.entity.PaymentConfirm;
@Repository
public class PaymentConfirmDao extends HibernateDao<PaymentConfirm, Long> {
	
	public PaymentConfirm findByConfirmNo(PaymentConfirm paymentConfirm){
		Criteria criteria = getSession().createCriteria(PaymentConfirm.class);
		if (paymentConfirm.getId() != null) {
			criteria.add(Restrictions.ne("id", paymentConfirm.getId()));
		}
		criteria.add(Restrictions.eq("confirmNo", paymentConfirm.getConfirmNo()));
		return (PaymentConfirm)criteria.uniqueResult();
	}

	public List<WoodAffiBaseInfo> getListNoYH(String loginName) {
//		String sql = " SELECT * FROM wood_affi_base_info WHERE wood_affi_base_info.customer_type not REGEXP '^([0-9]*,)*10(,[0-9]*)*$' and status=1 ";
		String sql = " SELECT * FROM wood_affi_base_info WHERE  NOT ( customer_type like '%10230010%' OR customer_type like '%10230002%' OR customer_type like '%10230003%' ) and status=1 UNION ";
		sql = sql + " select a.* from wood_affi_base_info a  LEFT JOIN  customer_perssion c on a.customer_code = c.customer_code ";
		sql = sql + " where ";
		sql = sql + " (LOCATE('"+loginName+"',c.ranges) or c.business_manager='"+loginName+"' or a.createrno='"+loginName+"' ) and a.status =1 ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class);
		return sqlQuery.list();
	}

	public String getNOYH(String loginName) {
		String sql =" select DISTINCT customer_code from (select  DISTINCT customer_code  from customer_perssion where LOCATE('"+loginName+"',ranges) or business_manager='"+loginName+"' UNION select customer_code from wood_affi_base_info where not (customer_type like '%10230010%' or customer_type like '%10230002%'  or customer_type like '%10230003%'  ) ) a ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<String> returnList = sqlQuery.list();
		String returnValue ="";
		if(returnList.size()>0){
			returnValue = StringUtils.join(returnList.toArray(), ",");
		}
		return returnValue;
	}
	
	public List<PaymentConfirm> findByChildCode(String code) {
		String sql = " select lh_payment_confirm.* from lh_payment_confirm join lh_payment_confirm_child on lh_payment_confirm.id=lh_payment_confirm_child.payment_confirm_id where lh_payment_confirm_child.code='"+code+"' ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PaymentConfirm.class);
		return sqlQuery.list();
	}
	
}
