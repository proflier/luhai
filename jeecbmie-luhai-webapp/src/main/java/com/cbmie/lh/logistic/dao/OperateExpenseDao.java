package com.cbmie.lh.logistic.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.OperateExpense;
@Repository
public class OperateExpenseDao extends HibernateDao<OperateExpense, Long> {
	public List<Map<String,Object>> getValidPort(){
		String sql = "select b.id id, a.contract_No contractNo,b.port_No portNo,b.trade_Category tradeCategory from LH_LOGISTIC_PORTCONTRACT a "
				+ "	left join LH_LOGISTIC_OPERATEEXPENSE b on a.id=b.port_Contract_Id "
				+ " where a.state='1' order by a.id desc";
		SQLQuery query = this.createSQLQuery(sql);
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> result=query.list();
		/*String[] head_alias = query.getReturnAliases();
		List<Object[]> data_list = query.list();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(Object[] data : data_list){
			Map<String,Object> data_tr = new HashMap<String,Object>();
			for(int i=0,j=head_alias.length;i<j;i++){
				data_tr.put(head_alias[i], data[i]);
			}
			result.add(data_tr);
		}*/
		return result;
	} 
}
