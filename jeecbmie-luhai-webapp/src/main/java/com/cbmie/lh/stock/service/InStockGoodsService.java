package com.cbmie.lh.stock.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
import com.cbmie.lh.stock.dao.InStockGoodsDao;
import com.cbmie.lh.stock.entity.InStockGoods;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 入库商品
 */
@Service
@Transactional
public class InStockGoodsService extends BaseService<InStockGoods, Long> {

	@Autowired
	private InStockGoodsDao inStockGoodsDao;
	
	@Override
	public HibernateDao<InStockGoods, Long> getEntityDao() {
		return inStockGoodsDao;
	}

	public Map<String, Object> getCurrency(Map<String, Object> showParams) {
		return inStockGoodsDao.getDataByParams(showParams);
	}
	
	public List<Map<String,Object>> getCurrentStockGoods(Map<String, Object> showParams){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String warehouseName = showParams.containsKey("warehouseName") ? "%" + showParams.get("warehouseName") + "%" : "%";
		String goodsName = showParams.containsKey("goodsName") ? "%" + showParams.get("goodsName") + "%" : "%";
		List<Object[]> list = inStockGoodsDao.getCurrentStockGoods( goodsName, warehouseName);
		for(Object obj : list) {
			Object[] obj_array = (Object[])obj;
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			//商品编码、名称
			row_.put("goodsName", obj_array[1].toString());
			row_.put("goodsNameText", obj_array[2].toString());
			//仓库编码、名称
			row_.put("warehouseName", obj_array[3].toString());
			row_.put("warehouseNameText", obj_array[4].toString());
			//入库类型
			row_.put("instockCategory", obj_array[5].toString());
			//数量
			row_.put("quantity", obj_array[6].toString());
			//数量
			row_.put("units", obj_array[7].toString());
			//船次
			row_.put("shipNo", obj_array[8].toString());
			//船编号船名
			row_.put("shipNoAndName", obj_array[9].toString());
			row_.put("relLoginNames", obj_array[10].toString());
			result.add(row_);
		}
		return result;
	}

	/**
	 * 保存入库子表
	 * @param billNo 到单编号
	 * @param parentId	入库主表id
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void saveGood(String billNo,String parentId) throws IllegalAccessException, InvocationTargetException {
		List<LhBillsGoods> lhBillsGoodsList = new ArrayList<LhBillsGoods>();
		inStockGoodsDao.deleteByparentId(parentId);
		lhBillsGoodsList = inStockGoodsDao.getByBillNo(billNo);
		if(lhBillsGoodsList.size()>0){
			for(LhBillsGoods lhBillsGoods :lhBillsGoodsList){
				InStockGoods inStockGoods = new InStockGoods();
				BeanUtils.copyProperties(inStockGoods, lhBillsGoods);
				inStockGoods.setId(null);
				inStockGoods.setBillNo(billNo);
				inStockGoods.setParentId(parentId);
				inStockGoods.setInnerContractNo(lhBillsGoods.getInnerContractNo());
				inStockGoods.setGoodsName(lhBillsGoods.getGoodsName());
				inStockGoods.setQuantity(Double.valueOf(lhBillsGoods.getGoodsQuantity()));
//				inStockGoods.setUnits(lhBillsGoods.getGoodsUnit());
				inStockGoods.setUnits("10580003");//修为为默认单位为吨
				User currentUser = UserUtil.getCurrentUser();
				inStockGoods.setCreaterNo(currentUser.getLoginName());
				inStockGoods.setCreaterName(currentUser.getName());
				inStockGoods.setCreateDate(new Date());
				inStockGoods.setUpdateDate(new Date());
				inStockGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
				inStockGoodsDao.save(inStockGoods);
			}
		}
	}
	
	/**
	 * 保存入库子表
	 * @param billNo 到单编号
	 * @param parentId	入库主表id
	 * @param warehouseName 仓库编号
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void saveGood(String billNo,String parentId,String warehouseName) throws IllegalAccessException, InvocationTargetException {
		List<LhBillsGoods> lhBillsGoodsList = new ArrayList<LhBillsGoods>();
		inStockGoodsDao.deleteByparentId(parentId);
		lhBillsGoodsList = inStockGoodsDao.getByBillNo(billNo);
		if(lhBillsGoodsList.size()>0){
			for(LhBillsGoods lhBillsGoods :lhBillsGoodsList){
				InStockGoods inStockGoods = new InStockGoods();
				BeanUtils.copyProperties(inStockGoods, lhBillsGoods);
				inStockGoods.setId(null);
				inStockGoods.setBillNo(billNo);
				inStockGoods.setParentId(parentId);
				inStockGoods.setInnerContractNo(lhBillsGoods.getInnerContractNo());
				inStockGoods.setGoodsName(lhBillsGoods.getGoodsName());
				inStockGoods.setQuantity(Double.valueOf(lhBillsGoods.getGoodsQuantity()));
//				inStockGoods.setUnits(lhBillsGoods.getGoodsUnit());
				inStockGoods.setUnits("10580003");//修为为默认单位为吨
				inStockGoods.setWarehouseName(warehouseName);
				User currentUser = UserUtil.getCurrentUser();
				inStockGoods.setCreaterNo(currentUser.getLoginName());
				inStockGoods.setCreaterName(currentUser.getName());
				inStockGoods.setCreateDate(new Date());
				inStockGoods.setUpdateDate(new Date());
				inStockGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
				inStockGoodsDao.save(inStockGoods);
			}
		}
	}

	public List<InStockGoods> goodsList(String id) {
		return inStockGoodsDao.findBy("parentId", id);
	}
	
	public Integer deleteByparentId(String parentId){
		return inStockGoodsDao.deleteByparentId(parentId);
	}

	public List<Map<String, Object>> getAllByDeliveryNo(String deliveryNo) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<Object[]> list = inStockGoodsDao.getAllByDeliveryNo( deliveryNo);
		for(Object obj : list) {
			Object[] obj_array = (Object[])obj;
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			//商品编码、名称
			row_.put("goodsName", obj_array[1].toString());
			row_.put("goodsNameText", obj_array[2].toString());
			//仓库编码、名称
			row_.put("warehouseName", obj_array[3].toString());
			row_.put("warehouseNameText", obj_array[4].toString());
			//入库类型
			row_.put("instockCategory", obj_array[5].toString());
			//数量
			row_.put("quantity", obj_array[6].toString());
			//数量
			row_.put("units", obj_array[7].toString());
			//船次
			row_.put("shipNo", obj_array[8].toString());
			//船编号船名
			row_.put("shipNoAndName", obj_array[9].toString());
			result.add(row_);
		}
		return result;
	}

	public List<Map<String, Object>> getAllBySaleNo(String saleNo) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<Object[]> list = inStockGoodsDao.getAllBySaleNo( saleNo);
		for(Object obj : list) {
			Object[] obj_array = (Object[])obj;
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			//商品编码、名称
			row_.put("goodsName", obj_array[1].toString());
			row_.put("goodsNameText", obj_array[2].toString());
			//仓库编码、名称
			row_.put("warehouseName", obj_array[3].toString());
			row_.put("warehouseNameText", obj_array[4].toString());
			//入库类型
			row_.put("instockCategory", obj_array[5].toString());
			//数量
			row_.put("quantity", obj_array[6].toString());
			//数量
			row_.put("units", obj_array[7].toString());
			//船次
			row_.put("shipNo", obj_array[8].toString());
			//船编号船名
			row_.put("shipNoAndName", obj_array[9].toString());
			result.add(row_);
		}
		return result;
	}
	
}
