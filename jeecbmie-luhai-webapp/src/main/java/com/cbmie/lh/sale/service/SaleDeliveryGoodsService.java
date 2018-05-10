package com.cbmie.lh.sale.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
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
import com.cbmie.lh.sale.dao.SaleDeliveryGoodsDao;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;

/**
 * 销售放货申请商品
 */
@Service
@Transactional
public class SaleDeliveryGoodsService extends BaseService<SaleDeliveryGoods, Long> {

	@Autowired
	private SaleDeliveryGoodsDao salesDeliveryGoods;

	@Override
	public HibernateDao<SaleDeliveryGoods, Long> getEntityDao() {
		return salesDeliveryGoods;
	}
	
	public void deleteEntityBySaleDeliveryId(Long saleDeliveryId){
		salesDeliveryGoods.deleteEntityBySaleDeliveryId(saleDeliveryId);
	}
	
	public List<Map<String,Object>> getOutStockGoodsList(String saleContractNo){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object[]> list = salesDeliveryGoods.getOutStockGoodsList(saleContractNo);
		for(Object[] objs : list){
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("contractNo", objs[0].toString());
			temp.put("receiptCode", objs[1]==null?"":objs[1].toString());
			temp.put("goodsName", objs[2]==null?"":objs[2].toString());
			temp.put("voy",  objs[3]==null?"":objs[3].toString());
			if(objs[4]!=null){
				Date outDate = (Date) objs[4];
				temp.put("outStockDate", sf.format(outDate));
			}else{
				temp.put("outStockDate", "");
			}
			temp.put("quantity", objs[5]==null?"":objs[5].toString());
			temp.put("port", objs[6]==null?"":objs[6].toString());
			result.add(temp);
		}
		return result;
	}
	
	/**
	 * 
	 * @param deliveryNo
	 * @param parentId
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void saveGood(String deliveryNo,String parentId) throws IllegalAccessException, InvocationTargetException {
		List<SaleDeliveryGoods> saleDeliveryGoodsList = new ArrayList<SaleDeliveryGoods>();
		salesDeliveryGoods.deleteByparentId(parentId);
		saleDeliveryGoodsList = salesDeliveryGoods.getByDeliveryNo(deliveryNo);
		if(saleDeliveryGoodsList.size()>0){
			for(SaleDeliveryGoods saleDeliveryGoods :saleDeliveryGoodsList){
				SaleDeliveryGoods outStockGoods = new SaleDeliveryGoods();
				BeanUtils.copyProperties(outStockGoods, saleDeliveryGoods);
				outStockGoods.setId(null);
				outStockGoods.setSaleDeliveryId(null);
				outStockGoods.setOutStockId(Long.valueOf(parentId));
				salesDeliveryGoods.save(outStockGoods);
			}
		}
	}
}
