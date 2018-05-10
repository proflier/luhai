package com.cbmie.woodNZ.reportForm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.reportForm.entity.BillsReport;

/**
 * 上游到单 log DAO
 */
@Repository
public class BillsReportDao extends HibernateDao<BillsReport, Long> {

	public List<WoodBills> getBillReportList(List<PropertyFilter> filters) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		int flag = 0;
		List<String> parentIdList = new ArrayList<String>();
		List<Long> newList = new ArrayList<Long>();
		for(PropertyFilter f : filters){
			if(f.getPropertyName().equals("cpContractNo")){
				String hql ="select s.parentId from WoodBillsGoods s  where s.cpContractNo like :cpContractNo";
				parentIdList = getSession().createQuery(hql)
			              .setParameter("cpContractNo", "%"+f.getMatchValue()+"%")
			              .list();
				for(String s:parentIdList){
					newList.add(Long.valueOf(s));
				}
				flag = 1;
				continue;
			}
			filterList.add(f);
		}
		if(flag == 1 && newList.isEmpty()){
			return new ArrayList<WoodBills>();
		}
		Criteria criteria = getSession().createCriteria(WoodBills.class);
		for(PropertyFilter f : filterList){
			criteria.add( Restrictions.like(f.getPropertyName(), "%"+f.getMatchValue()+"%") );
		}
		if(flag == 1){
			criteria.add( Restrictions.in("id",newList) );
		}
		List<WoodBills> list = criteria.list();
		return list;
	}

	//通过提单号找到下游客户
	public String getDownCustomer(String billNo) {
		Criteria criteria = getSession().createCriteria(DownstreamBill.class);
		criteria.add(Restrictions.eq("billNo", billNo));
		List<DownstreamBill> list = criteria.list();
		if(list.isEmpty()){
			return "";
		}
		return list.get(0).getDownStreamClient();
	}

}
