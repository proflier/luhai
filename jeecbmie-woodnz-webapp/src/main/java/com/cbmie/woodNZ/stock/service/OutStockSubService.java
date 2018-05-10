package com.cbmie.woodNZ.stock.service;

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
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.stock.dao.InStockOutStockRelationDao;
import com.cbmie.woodNZ.stock.dao.OutStockDao;
import com.cbmie.woodNZ.stock.dao.OutStockSubDao;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.cbmie.woodNZ.stock.entity.InStockOutStockRelation;
import com.cbmie.woodNZ.stock.entity.OutStock;
import com.cbmie.woodNZ.stock.entity.OutStockSub;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 出库子表信息service
 */
@Service
@Transactional
public class OutStockSubService extends BaseService<OutStockSub, Long> {

	@Autowired 
	private OutStockSubDao outstockSubDao;
	
	@Autowired 
	private OutStockDao outstockDao;
	
	@Autowired
	private InStockService inStockService;
	
	@Autowired 
	private InStockOutStockRelationDao inStockOutStockRelationDao;
	

	@Override
	public HibernateDao<OutStockSub, Long> getEntityDao() {
		return outstockSubDao;
	}
	
	
	/**
	 * 
	 * @param outStock
	 * @param outStockJson
	 */
	public void save(OutStock outStock, String outStockSubJson) {
		// 转成标准的json字符串
		outStockSubJson = StringEscapeUtils.unescapeHtml4(outStockSubJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<OutStockSub> outStockSubList = new ArrayList<OutStockSub>();
		try {
			JsonNode jsonNode = objectMapper.readTree(outStockSubJson);
			for (JsonNode jn : jsonNode) {
				OutStockSub outStockSub = objectMapper.readValue(jn.toString(), OutStockSub.class);
				outStockSubList.add(outStockSub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		save(outStock,outStockSubList);
		delete(outStock,outStockSubList);
	}
	

	public void save(OutStock outStock,List<OutStockSub> outStockSubList){//新增或修改	
		for(OutStockSub outStockSub : outStockSubList) {
			try {
				if(outStockSub.getId() == null){//新增 
					User currentUser = UserUtil.getCurrentUser();
					outStockSub.setParentId(outStock.getId());
					outStockSub.setCreaterNo(currentUser.getLoginName());
					outStockSub.setCreaterName(currentUser.getName());
					outStockSub.setCreateDate(new Date());
					outstockSubDao.save(outStockSub);
					if(outStock.getDeliveryId() != null){
						updateRelation(outStockSub);
					}
					if(outStock.getDownBillsId() != null){
						updateRelation4Down(outStockSub);
					}
				}else if(outStockSub.getId() != null){//修改
					User currentUser = UserUtil.getCurrentUser();
					outStockSub.setUpdaterName(currentUser.getName());
					outStockSub.setUpdateDate(new Date());
					outStockSub.setUpdaterNo(currentUser.getLoginName());
					outStockSub.setUpdaterDept(currentUser.getOrganization().getOrgName());
					outstockSubDao.save(outStockSub);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	private void updateRelation(OutStockSub outStockSub) {
		//*************向出库关联表插入数据   start***************//
		List<DeliveryStockRelation> deliveryStockRelationList = 
				outstockDao.getDeliverStockBydeliveryId(Long.valueOf(outStockSub.getRealSalesDeliveryGoodsId()));//通过放货子表id返回入库中间表集合；
		for(DeliveryStockRelation deliveryStockRelation:deliveryStockRelationList){
			InStockOutStockRelation relation = new InStockOutStockRelation();
			relation.setInStockGoods(deliveryStockRelation.getInStockGoods());//入库商品表id
			relation.setOutStockSub(outStockSub);//出库商品表id
			inStockOutStockRelationDao.save(relation);
		}
		//*************向出库关联表插入数据   end***************//
	}
	
	private void updateRelation4Down(OutStockSub outStockSub) {
		//*************向出库关联表插入数据   start***************//
		InStock inStock = 
				inStockService.get(Long.valueOf(outStockSub.getInStockId()));//通过入库主表id返回入库子表集合；
		List<InStockGoods> subList = inStock.getInStockGoods();
		for(InStockGoods inStockGoods:subList){
			if(inStockGoods.getGoodsNo().equals(outStockSub.getGoodsNo())){//找到对应入库子表
				InStockOutStockRelation relation = new InStockOutStockRelation();
				relation.setInStockGoods(inStockGoods);//入库商品表id
				relation.setOutStockSub(outStockSub);//出库商品表id
				inStockOutStockRelationDao.save(relation);
			}
		}
		//*************向出库关联表插入数据   end***************//
	}

	public void delete(OutStock outStock,List<OutStockSub> outStockSubList){//删除
		List<OutStockSub> dataSubList = outStock.getSubList();//修改前的数据
        List<OutStockSub> deleteSubList = new ArrayList<OutStockSub>();//保存要删除的数据
		for(OutStockSub sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
			Long dataId = sub.getId();
			Boolean result = false;
			for(int i=0;i<outStockSubList.size();i++){
				if(outStockSubList.get(i).getId() == dataId){
					result  =true;
				}
			}
			if(result == false){
				OutStockSub aa = outstockSubDao.find(dataId);
				deleteSubList.add(aa);
			}
		}
		for(OutStockSub s:deleteSubList){//删除对应数据
			outStock.getSubList().remove(s);
			s.setParentId(null);
			outstockSubDao.delete(s);
		}
	}


	public List<OutStockSub> getOutStockByMainId(Long id) {
		return outstockSubDao.getOutStockByMainId(id);
	}
 
}