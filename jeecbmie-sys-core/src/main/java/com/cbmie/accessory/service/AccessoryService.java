package com.cbmie.accessory.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.accessory.dao.AccessoryDao;
import com.cbmie.accessory.entity.Accessory;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.Reflections;

/**
 * 附件service
 */
@Service
@Transactional(readOnly = true)
public class AccessoryService extends BaseService<Accessory, Long> {

	@Autowired
	private AccessoryDao accessoryDao;

	@Override
	public HibernateDao<Accessory, Long> getEntityDao() {
		return accessoryDao;
	}

	/**
	 * 按accId查询附件
	 * @param accId
	 * @return 附件对象
	 */
	public Accessory getAcc(Long accId) {
		Accessory accessory = new Accessory();
		accessory = accessoryDao.findUniqueBy("accId", accId);
		return accessory;
	}
	
	/**
	 * 根据accParentId 查询所有附件
	 * @param accParentId
	 * @return
	 */
	public List<Accessory> getAccByPid(String accParentId) {
		return accessoryDao.findBy("accParentId", accParentId);
	}

	public List<Accessory> getListByPidAndEntity(String accParentId,String accParentEntity){
		return accessoryDao.getListByPidAndEntity(accParentId, accParentEntity);
	}
	@Transactional(readOnly = false)
	public List<Accessory> getListByPidAndEntityForWirte(String accParentId,String accParentEntity){
		return accessoryDao.getListByPidAndEntity(accParentId, accParentEntity);
	}
	@Transactional(readOnly = false)
	public void copyAttach(List<Accessory> accessory_sources,String accParentId,String accParentEntity) throws IllegalAccessException, InvocationTargetException{
		for(Accessory accessory_source : accessory_sources){
			Accessory accessory_dest = new Accessory();
			BeanUtils.copyProperties(accessory_dest, accessory_source);
			Long accId_dest = System.currentTimeMillis();
			accessory_dest.setId(null);
			accessory_dest.setAccId(accId_dest);
			if(StringUtils.isNotBlank(accParentId)){
				accessory_dest.setAccParentId(accParentId);
			}
			if(StringUtils.isNotBlank(accParentEntity)){
				accessory_dest.setAccParentEntity(accParentEntity);
			}
			File file_source = new File( accessory_source.getAccSrc() + File.separator + accessory_source.getAccId());
			File file_dest = new File(accessory_dest.getAccSrc() + File.separator + accessory_dest.getAccId());
			 if(file_source.exists()){  
		            try {  
		                FileInputStream fis = new FileInputStream(file_source);  
		                FileOutputStream fos = new FileOutputStream(file_dest);  
		                byte[] b = new byte[16];
		                int i = 0;  
		                while ((i = fis.read(b)) != -1) {  
		                    fos.write(b);  
		                    fos.flush();  
		                }  
		                fos.close();  
		                fis.close();  
		            } catch (FileNotFoundException e) {  
		                e.printStackTrace();  
		            } catch (IOException e) {  
		                e.printStackTrace();  
		            }
			 }
			 this.save(accessory_dest);
		}
	}
	public List<Accessory> getByObj(Object obj) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		String accParentEntity = obj.getClass().getPackage().getName().replace(".", "_") + "_" + com.cbmie.common.utils.StringUtils.substringBefore(obj.getClass().getSimpleName(), "_");
		PropertyFilter two = new PropertyFilter("EQS_accParentId", Reflections.invokeGetter(obj, "id").toString());
		PropertyFilter one = new PropertyFilter("EQS_accParentEntity", accParentEntity);
		filters.add(one);
		filters.add(two);
		return super.search(filters);
	}
}
