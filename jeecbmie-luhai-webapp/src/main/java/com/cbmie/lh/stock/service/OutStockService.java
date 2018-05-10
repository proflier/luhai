package com.cbmie.lh.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.dao.GoodsInformationDao;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.cbmie.lh.stock.dao.OutStockDao;
import com.cbmie.lh.stock.entity.OutStock;

/**
 * 出库 
 */
@Service
@Transactional
public class OutStockService extends BaseService<OutStock, Long> {

	@Autowired
	private OutStockDao outStockDao;
	
	@Autowired
	private BaseInfoUtilService baseInfoUtilService;
	
	@Autowired
	private GoodsInformationDao goodsInformationDao;
	

	@Override
	public HibernateDao<OutStock, Long> getEntityDao() {
		return outStockDao;
	}

	public OutStock findByNo(OutStock inStock) {
		return outStockDao.findByNo(inStock);
	}

	public Map<String, Object> checkGoods(Long id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		boolean checked = true;
		String msg = "";
		List<SaleDeliveryGoods> salesDeliveryGoodsList = get(id).getSalesDeliveryGoodsList();
		if(salesDeliveryGoodsList.size()>0){
			for(SaleDeliveryGoods saleDeliveryGoods :salesDeliveryGoodsList ){
				if(StringUtils.isBlank(saleDeliveryGoods.getPort())){
					checked = false;
					msg = "货物未选择仓库！";
					break;
				}
				if(StringUtils.isBlank(saleDeliveryGoods.getVoy())){
					checked = false;
					msg = "货物船信息为空！";
					break;
				}
				if(outStockDao.getAllQuantity(saleDeliveryGoods)>outStockDao.getMaxQuantity(saleDeliveryGoods)){
					msg = "仓库："+baseInfoUtilService.getAffiBaseInfoByCode(saleDeliveryGoods.getPort())+goodsInformationDao.getEntityByCode(saleDeliveryGoods.getGoodsName())+"数量超出最大限制！";
					checked = false;
					break;
				}
			}
		}else{
			msg = "出库货物为空，请检查！";
			checked = false;
		}
		returnMap.put("returnValue", checked);
		returnMap.put("msg", msg);
		return returnMap;
	}
	public List<Map<String,Object>> stockTrack(Map<String, Object> showParams){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String goodsName = showParams.containsKey("goodsName") ? "%" + showParams.get("goodsName") + "%" : "%";
		String deliveryNo = showParams.containsKey("deliveryNo") ? "%" + showParams.get("deliveryNo") + "%" : "%";
		String contractNo = showParams.containsKey("contractNo") ? "%" + showParams.get("contractNo") + "%" : "%";
		String receiptCode = showParams.containsKey("receiptCode") ? "%" + showParams.get("receiptCode") + "%" : "%";
		List<Object[]> list = outStockDao.stockTrack(  goodsName, deliveryNo, contractNo, receiptCode);
		for(Object obj : list) {
			Object[] obj_array = (Object[])obj;
			Map<String, Object> row_ = new HashMap<String, Object>();
			row_.put("date", obj_array[0].toString());
			row_.put("port", obj_array[1].toString());
			row_.put("customer", obj_array[2].toString());
			row_.put("contractNo", obj_array[3].toString());
			row_.put("contractQuantity", obj_array[4].toString());
			row_.put("goodsName", obj_array[5].toString());
			row_.put("deliveryNo", obj_array[6].toString());
			row_.put("quantity", obj_array[7].toString());
			row_.put("shipNo", obj_array[8].toString());
			result.add(row_);
		}
		return result;
	}
}
