package com.cbmie.woodNZ.salesContract.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.salesContract.dao.SaleContractSubDao;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 销售子表service
 */
@Service
@Transactional
public class SaleContractSubService extends BaseService<WoodSaleContractSub, Long> {

	@Autowired 
	private SaleContractSubDao saleContractSubDao;

	@Override
	public HibernateDao<WoodSaleContractSub, Long> getEntityDao() {
		return saleContractSubDao;
	}

	public void save(WoodSaleContract saleContract, String contractSubJson) {
		// 转成标准的json字符串
		contractSubJson = StringEscapeUtils.unescapeHtml4(contractSubJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<WoodSaleContractSub> saleContractkSubList = new ArrayList<WoodSaleContractSub>();
		try {
			JsonNode jsonNode = objectMapper.readTree(contractSubJson);
			for (JsonNode jn : jsonNode) {
				WoodSaleContractSub saleContractSub = objectMapper.readValue(jn.toString(), WoodSaleContractSub.class);
				saleContractkSubList.add(saleContractSub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		save(saleContract,saleContractkSubList);
		delete(saleContract,saleContractkSubList);
	}
	

	public void save(WoodSaleContract saleContract,List<WoodSaleContractSub> saleContractSubList){//新增或修改	
		for(WoodSaleContractSub saleContractckSub : saleContractSubList) {
			if(saleContractckSub.getId() == null){//新增
				User currentUser = UserUtil.getCurrentUser();
				saleContractckSub.setParentId(saleContract.getId());
				saleContractckSub.setCreaterNo(currentUser.getLoginName());
				saleContractckSub.setCreaterName(currentUser.getName());
				saleContractckSub.setCreateDate(new Date());
			}else if(saleContractckSub.getId() != null){//修改
				User currentUser = UserUtil.getCurrentUser();
				saleContractckSub.setUpdaterNo(currentUser.getLoginName());
				saleContractckSub.setUpdaterName(currentUser.getName());
			}
			saleContractSubDao.save(saleContractckSub);	
		}
	}
	
	
	public void delete(WoodSaleContract saleContract,List<WoodSaleContractSub> saleContractSubList){//删除
		Long id = saleContract.getId();
		List<WoodSaleContractSub> dataSubList = saleContractSubDao.getdataListByParentId(id);//取得修改前的数据
        List<WoodSaleContractSub> deleteSubList = new ArrayList<WoodSaleContractSub>();//保存要删除的数据
		for(WoodSaleContractSub sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
			Long dataId = sub.getId();
			Boolean result = false;
			for(int i=0;i<saleContractSubList.size();i++){
				if(saleContractSubList.get(i).getId() == dataId){
					result  =true;
				}
			}
			if(result == false){
				WoodSaleContractSub aa = saleContractSubDao.find(dataId);
				deleteSubList.add(aa);
			}
		}
		for(WoodSaleContractSub s:deleteSubList){//删除对应数据
			saleContract.getSaleContractSubList().remove(s);
			s.setParentId(null);
			saleContractSubDao.delete(s);
		}
	}
}
