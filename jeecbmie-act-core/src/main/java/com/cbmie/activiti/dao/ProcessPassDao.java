package com.cbmie.activiti.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.activiti.entity.ProcessPass;
import com.cbmie.common.persistence.HibernateDao;
@Repository
public class ProcessPassDao extends HibernateDao<ProcessPass,Long> {

	public void readProPass(String detailId){
		String sql = "update act_sys_procpassdetail a set a.read_flag='1' where a.id=:id";
		this.createSQLQuery(sql).setParameter("id", detailId).executeUpdate();
	}
	
	public List<String> getPassUsersByTaskId(String taskId){
		String sql = "select c.name from act_sys_procpass a,act_sys_procpassdetail b,user c "
				+ " where a.id=b.pass_id and a.task_id=:taskId and b.assignee=c.login_name ";
		List<String> list = this.createSQLQuery(sql).setParameter("taskId", taskId).list();
		return list;
	}
}
