package com.cbmie.activiti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.dao.ProcessEntityDao;
import com.cbmie.activiti.entity.ProcessEntity;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.exception.ExceptionCode;
import com.cbmie.system.exception.SystemException;
@Service
public class ProcessEntityService extends BaseService<ProcessEntity, Long> {
	
	@Autowired  
	RepositoryService repositoryService; 
	
	@Autowired
	private ProcessEntityDao entityDao;
	@Override
	public HibernateDao<ProcessEntity, Long> getEntityDao() {
		return entityDao;
	}
	
	/**
	 * 通过key获取流程实体设置
	 * */
	public ProcessEntity getProcEntityByKey(String procDefKey){
		ProcessEntity pe =entityDao.findUniqueBy("procDefKey", procDefKey);
		return pe;
	}

	public String getClazzFullName(String procDefKey) throws SystemException{
			ProcessEntity pe =entityDao.findUniqueBy("procDefKey", procDefKey);
			if(pe!=null){
				return pe.getClazzFullname();
			}else{
				throw new SystemException(ExceptionCode.DATA_EXCEPTION_CODE,"流程设置中没有找到实体类名称");
			}
	}
	
	/**
	 * 获取视图form
	 * @throws SystemException 
	 * */
	public String getViewFormBykey(String procDefKey) throws SystemException{
			String result = null;
			ProcessEntity pe =entityDao.findUniqueBy("procDefKey", procDefKey);
			if(pe!=null){
				String view = pe.getEntityView();
				if(view!=null){
					if(view.contains(",")){
						result = view.split(",")[0];
					}else{
						result = view;
					}
				}
			}
			if(result==null){
				throw new SystemException(ExceptionCode.DATA_EXCEPTION_CODE,"没有找到对应的业务url");
			}else{
				return result;
			}
	}
	
	public List<Map<String,String>> getAllProcIns(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
 	    		.latestVersion().orderByProcessDefinitionName().asc();
    	Map<String,String> result = null;
    	List<ProcessDefinition> all = query.list();
    	for(ProcessDefinition temp : all){
    		result = new HashMap<String,String>();
    		result.put("id", temp.getKey());
    		result.put("value", temp.getName()==null?temp.getKey():temp.getName());
    		list.add(result);
    	}
    	return list;
	}
	
	/**
	 * 获取视图info
	 * @throws SystemException 
	 * */
	public String getViewInfoBykey(String procDefKey) throws SystemException{
		String result = null;
		ProcessEntity pe =entityDao.findUniqueBy("procDefKey", procDefKey);
		if(pe!=null){
			String view = pe.getEntityView();
			if(view!=null){
				if(view.contains(",")){
					result = view.split(",")[1];
				}else{
					result = view;
				}
			}
		}
		if(result==null){
			throw new SystemException(ExceptionCode.DATA_EXCEPTION_CODE,"没有找到对应的业务页面");
		}else{
			return result;
		}
	}

	public List<ProcessEntity> findProcHave(String clazzFullname) {
		List<ProcessEntity> processEntitys = entityDao.findBy("clazzFullname", clazzFullname);
		return processEntitys;
	}
}
