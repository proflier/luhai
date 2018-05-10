package com.cbmie.lh.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.stock.dao.InStockGoodsDao;
import com.cbmie.lh.stock.dao.InventoryStockDao;
import com.cbmie.lh.stock.entity.InStockGoods;
import com.cbmie.lh.stock.entity.InventoryStock;

/**
 * 仓储盘点
 */
@Service
@Transactional
public class InventoryStockService extends BaseService<InventoryStock, Long> {

	@Autowired
	private InventoryStockDao inventoryStockDao;
	
	@Autowired
	private InStockGoodsDao inStockGoodsDao;

	@Override
	public HibernateDao<InventoryStock, Long> getEntityDao() {
		return inventoryStockDao;
	}

	public InventoryStock findByNo(InventoryStock inventoryStock) {
		return inventoryStockDao.findByNo(inventoryStock);
	}


	public void saveInventory(InventoryStock inventoryStock) {
		InStockGoods inStockGoods = inStockGoodsDao.get(inventoryStock.getStockGoodsId());
		InStockGoods inStockGoodsDest = new InStockGoods();
		inStockGoodsDest = getterAndSetter(inStockGoodsDest,inStockGoods);
		inStockGoodsDest.setQuantity(inventoryStock.getInventoryQuantity());
		inStockGoodsDest.setInstockCategory("2");
		inStockGoodsDest.setUpdateDate(inventoryStock.getInventoryDate());
		inStockGoodsDao.getSession().save(inStockGoodsDest);
		inventoryStock.setNewStockGoodsId(inStockGoodsDest.getId());
		inventoryStockDao.save(inventoryStock);
	}
	
	public void updateInventory(InventoryStock inventoryStock) {
		InStockGoods inStockGoods = inStockGoodsDao.get(inventoryStock.getNewStockGoodsId());
		inStockGoods.setQuantity(inventoryStock.getInventoryQuantity());
		inStockGoodsDao.getSession().update(inStockGoods);
		inventoryStockDao.save(inventoryStock);
	}
	
	public void toSave(InventoryStock inventoryStock){
		if(inventoryStock.getId()!=null){
			updateInventory(inventoryStock);
		}else{
			saveInventory(inventoryStock);
		}
		
	}

	
	public InStockGoods getterAndSetter (InStockGoods dest,InStockGoods source){
		dest.setGoodsName(source.getGoodsName());
		dest.setInnerContractNo(source.getInnerContractNo());
		dest.setWarehouseName(source.getWarehouseName());
		dest.setUnits(source.getUnits());
		dest.setParentId(source.getParentId());
		dest.setUserId(source.getUserId());
		dest.setCompanyId(source.getCompanyId());
		dest.setDeptId(source.getDeptId());
		dest.setBillNo(source.getBillNo());
		return dest;
	}

	public void deleteInventory(InventoryStock inventoryStock) {
		inventoryStockDao.delete(inventoryStock);
		inStockGoodsDao.delete(inventoryStock.getNewStockGoodsId());
	}

}
