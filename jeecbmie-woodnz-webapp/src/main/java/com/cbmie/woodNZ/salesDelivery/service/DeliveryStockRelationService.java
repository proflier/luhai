package com.cbmie.woodNZ.salesDelivery.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.woodNZ.salesDelivery.dao.DeliveryStockRelationDao;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;
import com.cbmie.woodNZ.stock.dao.InStockDao;
import com.cbmie.woodNZ.stock.dao.InStockGoodsDao;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.InStockGoods;

/**
 * 放货库存关系service
 * @author linxiaopeng
 * 2016年8月10日
 */
@Service
@Transactional
public class DeliveryStockRelationService extends BaseService<DeliveryStockRelation, Long> {
	
	@Autowired
	private DeliveryStockRelationDao deliveryStockRelationDao;
	
	@Autowired
	private InStockGoodsDao inStockGoodsDao;
	
	@Autowired
	private InStockDao inStockDao;


	@Override
	public HibernateDao<DeliveryStockRelation, Long> getEntityDao() {
		return deliveryStockRelationDao;
	}

	/**
	 * @param realSalesDeliveryGoodsJson
	 */
	public void saveRelation(SalesDelivery salesDelivery) {
		List<RealSalesDeliveryGoods> realSalesDeliveryGoodsList = salesDelivery.getRealSalesDeliveryGoodsList();
		for(RealSalesDeliveryGoods realSalesDeliveryGoods : realSalesDeliveryGoodsList){
			Double amount = realSalesDeliveryGoods.getAmount();
			String  warehouse = realSalesDeliveryGoods.getWarehouse();
			String goodsNo = realSalesDeliveryGoods.getGoodsNo();
			List<InStockGoods> inStockGoodsList = findByGoodNoAndWarehouse(warehouse, goodsNo);
			for(InStockGoods inStockGoods : inStockGoodsList){
				amount = amount - inStockGoods.getNum();
				DeliveryStockRelation deliveryStockRelation = new DeliveryStockRelation();
				deliveryStockRelation.setInStockGoods(inStockGoods);
				deliveryStockRelation.setRealSalesDeliveryGoods(realSalesDeliveryGoods);
				deliveryStockRelationDao.save(deliveryStockRelation);
				if(amount > 0){
					inStockGoods.setHaveOver(false);
					inStockGoodsDao.save(inStockGoods);
				}else if(amount<0){
					break;
				}else if(amount == 0){
					inStockGoods.setHaveOver(false);
					inStockGoodsDao.save(inStockGoods);
					break;
				}
			}
		}
	}
	
	public List<InStockGoods>  findByGoodNoAndWarehouse(String  warehouse, String goodsNo){
		List<InStock> inStockList = inStockDao.findByReceiveWarehouse(warehouse);
		List<InStockGoods> inStockGoodsList = new ArrayList<InStockGoods>();
		List<InStockGoods> returnList = new ArrayList<InStockGoods>();
		for(InStock inStock : inStockList){
			inStockGoodsList.addAll(inStock.getInStockGoods());
		}
		if(inStockGoodsList.size()>0){
			Iterator<InStockGoods> it = inStockGoodsList.iterator();
			while (it.hasNext()) {
				InStockGoods inStockGoods = (InStockGoods) it.next();
				if(StringUtils.isNotBlank(inStockGoods.getGoodsNo())&&goodsNo.equals(inStockGoods.getGoodsNo())&&inStockGoods.isHaveOver()){
					returnList.add(inStockGoods);
				}else{
					it.remove();
				}
			}
		}
		return returnList;
	}

	/**
	 * @param realSalesDeliveryGoods
	 */
	public void deleteByRealGoods(RealSalesDeliveryGoods realSalesDeliveryGoods) {
		List<DeliveryStockRelation> deliveryStockRelationList = deliveryStockRelationDao.getAllInStockGoodsByRealGoods(realSalesDeliveryGoods.getId());
		for(DeliveryStockRelation deliveryStockRelation : deliveryStockRelationList){
			InStockGoods inStockGoods = deliveryStockRelation.getInStockGoods();
			inStockGoods.setHaveOver(true);
			inStockGoodsDao.save(inStockGoods);
		}
		deliveryStockRelationDao.deleteByRealGoods(realSalesDeliveryGoods.getId());
	}
	
	
}
