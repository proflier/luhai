package com.cbmie.tool;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.utils.Reflections;
@Service
public class ProcessInstanceNameT {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public void fillActHiProcInst() throws HibernateException, ClassNotFoundException{
		StringBuffer sf = new StringBuffer();
		sf.append("select a.id_,c.clazz_full_name,a.business_key_,b.key_ from act_hi_procinst a,act_re_procdef b,act_re_procentity c ");
		sf.append("where a.proc_def_id_=b.id_ and b.key_=c.proc_def_key");
		List<Object[]> list = sessionFactory.getCurrentSession().createSQLQuery(sf.toString()).list();
		for(Object[] obj : list){
			String calssName = obj[1].toString();
			Object entity = sessionFactory.getCurrentSession().get(Class.forName(calssName), Long.parseLong(obj[2].toString()));
			if(entity==null) continue;
			Object obj_summary=Reflections.invokeGetter(entity, "summary");
			if(obj_summary!=null){
				String sql = "update act_hi_procinst d set d.name_='"+obj_summary.toString()+"' where d.id_='"+obj[0].toString()+"'";
				sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
			}
		}
	}
	
	public void fillActRuExecution() throws HibernateException, ClassNotFoundException{
		StringBuffer sf = new StringBuffer();
		sf.append("select a.id_,c.clazz_full_name,a.business_key_,b.key_ from act_ru_execution a,act_re_procdef b,act_re_procentity c ");
		sf.append("where a.proc_def_id_=b.id_ and b.key_=c.proc_def_key");
		List<Object[]> list = sessionFactory.getCurrentSession().createSQLQuery(sf.toString()).list();
		for(Object[] obj : list){
			String calssName = obj[1].toString();
			Object entity = sessionFactory.getCurrentSession().get(Class.forName(calssName), Long.parseLong(obj[2].toString()));
			if(entity==null) continue;
			Object obj_summary=Reflections.invokeGetter(entity, "summary");
			if(obj_summary!=null){
				String sql = "update act_ru_execution d set d.name_='"+obj_summary.toString()+"' where d.id_='"+obj[0].toString()+"'";
				sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
			}
		}
	}
}
