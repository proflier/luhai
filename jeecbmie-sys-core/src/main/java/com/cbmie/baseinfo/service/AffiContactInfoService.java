package com.cbmie.baseinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.baseinfo.dao.AffiContactInfoDao;
import com.cbmie.baseinfo.entity.WoodAffiContactInfo;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 关联单位__联系人信息service
 */
@Service
@Transactional
public class AffiContactInfoService extends BaseService<WoodAffiContactInfo, Long> {

	@Autowired
	private AffiContactInfoDao affiContactInfoDao;

	@Override
	public HibernateDao<WoodAffiContactInfo, Long> getEntityDao() {
		return affiContactInfoDao;
	}
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiContactInfo
	 */
	public List<WoodAffiContactInfo> getByParentId(String parentId) {
		List<WoodAffiContactInfo> affiContactInfoList = affiContactInfoDao.findBy("parentId", parentId);
		return affiContactInfoList;
	}
	
	/**
	 * 根据ContactNo获取对象
	 * @param banNO
	 * @return
	 */
	public WoodAffiContactInfo getByNo(String banNO){
		WoodAffiContactInfo affiContactInfo = affiContactInfoDao.findUniqueBy("ContactNO", banNO);
		return affiContactInfo;
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
			List<WoodAffiContactInfo> subList = new ArrayList<WoodAffiContactInfo>();
			try {
				JsonNode jsonNode = objectMapper.readTree(subJson);
				for (JsonNode jn : jsonNode) {
					WoodAffiContactInfo saleContractSub = objectMapper.readValue(jn.toString(), WoodAffiContactInfo.class);
					subList.add(saleContractSub);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			save(baseInfo,subList);
			delete(baseInfo,subList);
		}
		

		public void save(WoodAffiBaseInfo baseInfo,List<WoodAffiContactInfo> subList){//新增或修改	
			for(WoodAffiContactInfo sub : subList) {
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
				affiContactInfoDao.save(sub);	
			}
		}
		
		
		public void delete(WoodAffiBaseInfo baseInfo,List<WoodAffiContactInfo> subList){//删除
			Long id = baseInfo.getId();
			if(id!=null && !id.equals("")){
				List<WoodAffiContactInfo> dataSubList = affiContactInfoDao.getdataListByParentId(id);//取得修改前的数据
				 List<WoodAffiContactInfo> deleteSubList = new ArrayList<WoodAffiContactInfo>();//保存要删除的数据
					for(WoodAffiContactInfo sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
						Long dataId = sub.getId();
						Boolean result = false;
						for(int i=0;i<subList.size();i++){
							if(subList.get(i).getId() == dataId){
								result  =true;
							}
						}
						if(result == false){
							WoodAffiContactInfo deleteSub = affiContactInfoDao.find(dataId);
							deleteSubList.add(deleteSub);
						}
					}
					for(WoodAffiContactInfo s:deleteSubList){//删除对应数据
						baseInfo.getAffiBankInfo().remove(s);
						s.setParentId(null);
						affiContactInfoDao.delete(s);
					}
			}else{
				return;
			}
		}
	
}
