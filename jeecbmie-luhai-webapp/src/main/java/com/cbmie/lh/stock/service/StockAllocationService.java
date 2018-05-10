package com.cbmie.lh.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ShipTraceDao;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.stock.dao.InStockGoodsDao;
import com.cbmie.lh.stock.dao.StockAllocationDao;
import com.cbmie.lh.stock.entity.InStockGoods;
import com.cbmie.lh.stock.entity.StockAllocation;

/**
 * 仓储调拨
 * 
 */
@Service
@Transactional
public class StockAllocationService extends BaseService<StockAllocation, Long> {

	@Autowired
	private StockAllocationDao stockAllocationDao;

	@Autowired
	private InStockGoodsDao inStockGoodsDao;
	
	@Autowired
	private ShipTraceDao shipTraceDao;
	
	@Override
	public HibernateDao<StockAllocation, Long> getEntityDao() {
		return stockAllocationDao;
	}

	public StockAllocation findByNo(StockAllocation stockAllocation) {
		return stockAllocationDao.findByNo(stockAllocation);
	}
	
	public void delAllocation(StockAllocation stockAllocation){
		stockAllocationDao.delete(stockAllocation.getId());
		inStockGoodsDao.delete(stockAllocation.getStockAddId());
		inStockGoodsDao.delete(stockAllocation.getStockMinusId());
	}

	public void saveAllocation(StockAllocation stockAllocation) {
		InStockGoods inStockGoods = inStockGoodsDao.get(stockAllocation.getSourceStockGoodsId());
		InStockGoods inStockGoodsMinus = new InStockGoods();
		inStockGoodsMinus = getterAndSetter(inStockGoodsMinus,inStockGoods);
		inStockGoodsMinus.setQuantity(stockAllocation.getAllocationQuantity()*-1);
		inStockGoodsMinus.setWarehouseName(stockAllocation.getSourceWarehouseCode());
		inStockGoodsMinus.setInstockCategory("1");
		inStockGoodsMinus.setUpdateDate(stockAllocation.getUpdateDate());
		inStockGoodsDao.getSession().save(inStockGoodsMinus);
		stockAllocation.setStockMinusId(inStockGoodsMinus.getId());
		stockAllocationDao.save(stockAllocation);
	}
	
	public InStockGoods getterAndSetter (InStockGoods dest,InStockGoods source){
		dest.setGoodsName(source.getGoodsName());
		dest.setInnerContractNo(source.getInnerContractNo());
		dest.setUnits(source.getUnits());
		dest.setParentId(source.getParentId());
		dest.setUserId(source.getUserId());
		dest.setCompanyId(source.getCompanyId());
		dest.setDeptId(source.getDeptId());
		dest.setBillNo(source.getBillNo());
		return dest;
	}

	public void updateAllocation(StockAllocation stockAllocation) {
		InStockGoods inStockGoods = inStockGoodsDao.get(stockAllocation.getSourceStockGoodsId());
		InStockGoods inStockGoodsMinus =inStockGoodsDao.get(stockAllocation.getStockMinusId());
		inStockGoodsMinus.setQuantity(stockAllocation.getAllocationQuantity()*-1);
		inStockGoodsMinus.setWarehouseName(stockAllocation.getSourceWarehouseCode());
		inStockGoodsMinus.setUpdateDate(stockAllocation.getUpdateDate());
		inStockGoodsMinus = getterAndSetter(inStockGoodsMinus,inStockGoods);
		inStockGoodsDao.getSession().update(inStockGoodsMinus);
		stockAllocationDao.save(stockAllocation);
	}
	
	public void inAllocation(StockAllocation stockAllocation){
		InStockGoods inStockGoods = inStockGoodsDao.get(stockAllocation.getSourceStockGoodsId());
		InStockGoods inStockGoodsAdd = new InStockGoods();
		if(stockAllocation.getStockAddId()!=null){
			inStockGoodsAdd = inStockGoodsDao.get(stockAllocation.getStockAddId()); 
			inStockGoodsAdd.setQuantity(stockAllocation.getReceiveQuantity());
			inStockGoodsAdd.setWarehouseName(stockAllocation.getDestWarehouseCode());
			inStockGoodsAdd.setUpdateDate(stockAllocation.getUpdateDate());
			inStockGoodsAdd = getterAndSetter(inStockGoodsAdd,inStockGoods);
			inStockGoodsDao.getSession().update(inStockGoodsAdd);
		}else{
			inStockGoodsAdd = getterAndSetter(inStockGoodsAdd,inStockGoods);
			inStockGoodsAdd.setQuantity(stockAllocation.getReceiveQuantity());
			inStockGoodsAdd.setWarehouseName(stockAllocation.getDestWarehouseCode());
			inStockGoodsAdd.setInstockCategory("1");
			inStockGoodsAdd.setUpdateDate(stockAllocation.getUpdateDate());
			inStockGoodsDao.getSession().save(inStockGoodsAdd);
			stockAllocation.setStockAddId(inStockGoodsAdd.getId());
		}
		stockAllocationDao.save(stockAllocation);
	}
	
	
	public void toSave(StockAllocation stockAllocation){
		if(stockAllocation.getId()!=null){
			updateAllocation(stockAllocation);
		}else{
			saveAllocation(stockAllocation);
		}
	}
	
	public List<ShipTrace> getInnnerShipTrace() {
		return shipTraceDao.findBy("tradeCategory", "10850003");
	}

}
