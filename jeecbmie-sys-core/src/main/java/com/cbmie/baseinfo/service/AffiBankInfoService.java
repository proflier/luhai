package com.cbmie.baseinfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.baseinfo.dao.AffiBankInfoDao;
import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 关联单位__银行信息service
 */
@Service
@Transactional
public class AffiBankInfoService extends BaseService<WoodAffiBankInfo, Long> {

	@Autowired
	private AffiBankInfoDao affiBankInfoDao;
	
	@Override
	public HibernateDao<WoodAffiBankInfo, Long> getEntityDao() {
		return affiBankInfoDao;
	}
	/**
	 * 按parentId查询
	 * @param parentId
	 * @return affiBankInfo
	 */
	public List<WoodAffiBankInfo> getByParentId(String parentId) {
		List<WoodAffiBankInfo> affiBankInfoList = affiBankInfoDao.findByEffect(parentId);
		return affiBankInfoList;
	}
	
	/**
	 * 按parentCode查询
	 * @param pCode
	 * @return
	 */
	public List<WoodAffiBankInfo> getByParentCode(String pCode) {
		return affiBankInfoDao.getByParentCode(pCode);
	}
	
	/**
	 * 根据bankNo获取对象
	 * @param banNO
	 * @return
	 */
	public WoodAffiBankInfo getByNo(String banNO){
		WoodAffiBankInfo affiBankInfo = affiBankInfoDao.findUniqueBy("bankNO", banNO);
		return affiBankInfo;
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
			List<WoodAffiBankInfo> subList = new ArrayList<WoodAffiBankInfo>();
			try {
				JsonNode jsonNode = objectMapper.readTree(subJson);
				for (JsonNode jn : jsonNode) {
					WoodAffiBankInfo saleContractSub = objectMapper.readValue(jn.toString(), WoodAffiBankInfo.class);
					subList.add(saleContractSub);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			save(baseInfo,subList);
			delete(baseInfo,subList);
		}
		

		public void save(WoodAffiBaseInfo baseInfo,List<WoodAffiBankInfo> subList){//新增或修改	
			for(WoodAffiBankInfo sub : subList) {
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
				affiBankInfoDao.save(sub);	
			}
		}
		
		
		public void delete(WoodAffiBaseInfo baseInfo,List<WoodAffiBankInfo> subList){//删除
			Long id = baseInfo.getId();
			if(id!=null && !id.equals("")){
				List<WoodAffiBankInfo> dataSubList = affiBankInfoDao.getdataListByParentId(id);//取得修改前的数据
				 List<WoodAffiBankInfo> deleteSubList = new ArrayList<WoodAffiBankInfo>();//保存要删除的数据
					for(WoodAffiBankInfo sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
						Long dataId = sub.getId();
						Boolean result = false;
						for(int i=0;i<subList.size();i++){
							if(subList.get(i).getId() == dataId){
								result  =true;
							}
						}
						if(result == false){
							WoodAffiBankInfo deleteSub = affiBankInfoDao.find(dataId);
							deleteSubList.add(deleteSub);
						}
					}
					for(WoodAffiBankInfo s:deleteSubList){//删除对应数据
						baseInfo.getAffiBankInfo().remove(s);
						s.setParentId(null);
						affiBankInfoDao.delete(s);
					}
			}else{
				return;
			}
		}
	
}
