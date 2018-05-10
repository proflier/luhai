package com.cbmie.activiti.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cbmie.activiti.entity.DelegatePerson;
import com.cbmie.common.persistence.HibernateDao;
@Repository
public class DelegatePersonDao extends HibernateDao<DelegatePerson, Long> {

	public String getMandataryByConsigner(String procDefKey,String consigner){
		String sql = "select a.mandatary from act_sys_DELEGATEPERSON a "
				+ " where a.consigner=:consigner and a.begin_Date<:curDate and a.end_Date>:curDate"
				+ " and a.activate_Flag='1' ";
		if(StringUtils.isNotBlank(procDefKey)){
			sql = sql + " and (a.proc_Def_Key is null or a.proc_Def_Key='' or a.proc_Def_Key like '%"+procDefKey+"%' )";
		}
//		Set<String> set = new HashSet<String>(Arrays.asList(procDefKey.split(",")));
		List<Object> list = this.createSQLQuery(sql).setParameter("consigner", consigner).setCalendar("curDate", Calendar.getInstance()).list();
		if(list!=null && list.size()==1){
			return list.get(0).toString();
		}else{
			return null;
		}
	}
	
	public boolean checkLegal(DelegatePerson delegatePerson){
		String sql = "select * from act_sys_DELEGATEPERSON a  "
				+ " where a.consigner=:consigner and a.activate_Flag='1' and "
				+ " ((a.begin_Date<:beginDate and a.end_Date>:beginDate) or (a.begin_Date<:endDate and a.end_Date>:endDate)"
				+ " or (a.begin_Date>:beginDate and a.begin_Date<:endDate) or (a.end_Date>:beginDate and a.end_Date<:endDate)) ";
		if(delegatePerson.getId()!=null){
			sql = sql + " and a.id<>"+delegatePerson.getId();
		}
		if(StringUtils.isNotBlank(delegatePerson.getProcDefKey())){
			sql = sql + " and (a.proc_Def_Key is null or a.proc_Def_Key='' or a.proc_Def_Key='"+delegatePerson.getProcDefKey()+"')";
		}
		List list = this.createSQLQuery(sql).setParameter("consigner", delegatePerson.getConsigner())
		.setTimestamp("beginDate", delegatePerson.getBeginDate()).setTimestamp("endDate", delegatePerson.getEndDate()).list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
