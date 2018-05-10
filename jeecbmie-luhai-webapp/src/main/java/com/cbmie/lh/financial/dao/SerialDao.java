package com.cbmie.lh.financial.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.lh.financial.entity.Serial;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 流水DAO
 */
@Repository
public class SerialDao extends HibernateDao<Serial, Long>{
	
	public Serial findByNo(Serial serial) {
		Criteria criteria = getSession().createCriteria(Serial.class);
		if (serial.getId() != null) {
			criteria.add(Restrictions.ne("id", serial.getId()));
		}
		criteria.add(Restrictions.eq("serialNumber", serial.getSerialNumber()));
		return (Serial)criteria.uniqueResult();
	}
	
	/**
	 * 找出对应合同下承兑的水单总金额
	 */
	public Double sumAcceptance(String contractNo) {
		String sql = "SELECT SUM(money) FROM serial WHERE contract_no = ? AND serial_category = '货款'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractNo);
		return (Double)sqlQuery.uniqueResult();
	}
	public List<Serial> getSerialBySaleNo(String saleConctractNo){
		String sql = "select * from LH_SERIAL a where a.contract_No=:saleConctractNo order by a.id asc";
		return this.createSQLQuery(sql).addEntity(Serial.class).setParameter("saleConctractNo", saleConctractNo).list();
	}

	public void findEntityList(Page<Serial> page, Map<String, Object> params) {
		String sql = "SELECT a.* FROM lh_serial a LEFT JOIN customer_perssion c ON a.serial_title = c.customer_code where 1=1 and a.split_status <> 'parent'";
		if(params.containsKey("serialNumber")){
			String serialNumber = params.get("serialNumber").toString();
			sql = sql+" and  a.serial_number like '%"+serialNumber+"%' ";
		}
		if(params.containsKey("serialTitle")){
			String serialTitle = params.get("serialTitle").toString();
			sql = sql+" and a.serial_title ='"+serialTitle+"' ";
		}
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser.getId()==1||currentUser.getId()==106||currentUser.getId()==135){
		}else{
			if(params.containsKey("userName")){
				String userName = params.get("userName").toString();
				sql = sql+" and ( a.claim_person='"+userName+"' or  ISNULL(a.claim_person) or a.createrno='"+userName+"' )  ";
				sql = sql+" and ( LOCATE('"+userName+"',c.ranges) or c.business_manager='"+userName+"' )  ";
			}
		}
		page.setTotalCount(this.createSQLQuery(sql).list().size());
		List<Serial> list = this.createSQLQuery(sql).addEntity(Serial.class).setFirstResult(page.getFirst() - 1).setMaxResults(page.getPageSize()).list();
		page.setResult(list);
		
	}
}
