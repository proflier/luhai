package com.cbmie.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cbmie.system.entity.User;


import org.apache.commons.lang3.StringEscapeUtils;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.baseinfo.dao.AffiCustomerInfoDao;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.entity.WoodAffiCustomerInfo;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 关联单位__客户评审service
 */
@Service
@Transactional
public class AffiCustomerInfoService extends BaseService<WoodAffiCustomerInfo, Long> {

	@Autowired
	private AffiCustomerInfoDao affiCustomerInfoDao;

	@Override
	public HibernateDao<WoodAffiCustomerInfo, Long> getEntityDao() {
		return affiCustomerInfoDao;
	}
	
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiBankInfo
	 */
	public WoodAffiCustomerInfo getByParentId(String parentId) {
		WoodAffiCustomerInfo affiCustomerInfo = new WoodAffiCustomerInfo();
		affiCustomerInfo = affiCustomerInfoDao.findUniqueBy("parentId", parentId);
		return affiCustomerInfo;
	}
	
	
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiBankInfo
	 */
	public List<WoodAffiCustomerInfo> getListByParentId(String parentId) {
		List<WoodAffiCustomerInfo> affiCustomerInfoList = affiCustomerInfoDao.findBy("parentId", parentId);
		return affiCustomerInfoList;
	}
	
	/**
	 * 
	 * @param baseInfo 主表数据
	 * @param subJson  子表json
	 */	
	public void save(WoodAffiBaseInfo baseInfo, String subJson) {
			// 转成标准的json字符串
			subJson = StringEscapeUtils.unescapeHtml4(subJson);
			// 把json转成对象
			ObjectMapper objectMapper = new ObjectMapper();
			List<WoodAffiCustomerInfo> subList = new ArrayList<WoodAffiCustomerInfo>();
			try {
				JsonNode jsonNode = objectMapper.readTree(subJson);
				for (JsonNode jn : jsonNode) {
					WoodAffiCustomerInfo saleContractSub = objectMapper.readValue(jn.toString(), WoodAffiCustomerInfo.class);
					subList.add(saleContractSub);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			save(baseInfo,subList);
			delete(baseInfo,subList);
		}
			

		public void save(WoodAffiBaseInfo baseInfo,List<WoodAffiCustomerInfo> subList){//新增或修改	
			for(WoodAffiCustomerInfo sub : subList) {
				if(sub.getId() == null){//新增
					User currentUser = UserUtil.getCurrentUser();
					sub.setParentId(baseInfo.getId().toString());
					sub.setCreaterNo(currentUser.getLoginName());
					sub.setCreaterName(currentUser.getName());
					sub.setCreateDate(new Date());
				}else if(sub.getId() != null){//修改
					User currentUser = UserUtil.getCurrentUser();
					sub.setUpdaterNo(currentUser.getLoginName());
					sub.setUpdaterName(currentUser.getName());
				}
				affiCustomerInfoDao.save(sub);	
			}
		}
		
		
		public void delete(WoodAffiBaseInfo baseInfo,List<WoodAffiCustomerInfo> subList){//删除
			Long id = baseInfo.getId();
			if(id!=null && !id.equals("")){
				List<WoodAffiCustomerInfo> dataSubList = affiCustomerInfoDao.getdataListByParentId(id);//取得修改前的数据
				 List<WoodAffiCustomerInfo> deleteSubList = new ArrayList<WoodAffiCustomerInfo>();//保存要删除的数据
					for(WoodAffiCustomerInfo sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
						Long dataId = sub.getId();
						Boolean result = false;
						for(int i=0;i<subList.size();i++){
							if(subList.get(i).getId() == dataId){
								result  =true;
							}
						}
						if(result == false){
							WoodAffiCustomerInfo deleteSub = affiCustomerInfoDao.find(dataId);
							deleteSubList.add(deleteSub);
						}
					}
					for(WoodAffiCustomerInfo s:deleteSubList){//删除对应数据
						baseInfo.getAffiBankInfo().remove(s);
						s.setParentId(null);
						affiCustomerInfoDao.delete(s);
					}
			}else{
				return;
			}
		}

}
