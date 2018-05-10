package com.cbmie.system.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Log;


/**
 * 日志DAO
 */
@Repository
public class LogDao extends HibernateDao<Log, Integer>{
	
	/**
	 * 批量删除日志
	 * @param ids 日志id列表
	 */
	public void deleteBatch(List<Integer> idList){
		String hql="delete from Log log where log.id in (:idList)";
		Query query=getSession().createQuery(hql);
		query.setParameterList("idList", idList);
		query.executeUpdate();
	}
	
	/**
	 * 根据用户名分组，相应时间段的计算操作数量
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> countLog(int days) {
		String sql = "SELECT CREATER,CREATER_LOGIN_NAME,count(1) FROM log"
						+ " WHERE CREATER_LOGIN_NAME != 'admin' " + (days > 0 ? "AND CREATE_DATE BETWEEN DATE_SUB(sysdate(),INTERVAL " + days + " DAY) AND sysdate()" : "")
						+ " GROUP BY CREATER_LOGIN_NAME ORDER BY count(1) DESC";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String,Object>> result = sqlQuery.list();
		return result;
	}
	
}
