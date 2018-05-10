package com.cbmie.lh.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
import com.cbmie.lh.stock.dao.InStockNoticeDao;
import com.cbmie.lh.stock.entity.InStockNotice;
import com.cbmie.lh.stock.entity.InStockNoticeGoods;
import com.cbmie.system.utils.DictUtils;

/**
 * 入库通知
 * 
 */
@Service
@Transactional
public class InStockNoticeService extends BaseService<InStockNotice, Long> {

	@Autowired
	private InStockNoticeDao inStockNoticeDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Override
	public HibernateDao<InStockNotice, Long> getEntityDao() {
		return inStockNoticeDao;
	}

	public InStockNotice findByNo(InStockNotice inStockNotice) {
		return inStockNoticeDao.findByNo(inStockNotice);
	}

	public InStockNotice findByInner(String innerContractNo) {
		return inStockNoticeDao.findUniqueBy("innerContractNo", innerContractNo);
	}

	public Map<String, Object> exportPDF(String innerContractNo) {
		InStockNotice inStockNotice = findByInner(innerContractNo);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> goodsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		if (inStockNotice != null) {
			List<InStockNoticeGoods> oscsList = inStockNotice.getInStockNoticeGoodsList();
			for(InStockNoticeGoods inStockNoticeGoods :oscsList){
				Map<String, Object> goodsMap = new HashMap<String, Object>();
				goodsMap.put("goodsNameView", inStockNoticeGoods.getGoodsNameView());
				goodsMap.put("warehouseNameView", inStockNoticeGoods.getWarehouseNameView());
				goodsMap.put("quantity", inStockNoticeGoods.getQuantity());
				goodsMap.put("quantityCIQ", inStockNoticeGoods.getQuantityCIQ());
				goodsMap.put("unitsView", inStockNoticeGoods.getUnitsView());
				goodsMap.put("transportCategoryView", inStockNoticeGoods.getTransportCategoryView());
				goodsMap.put("shipNo", inStockNoticeGoods.getShipNo());
				goodsList.add(goodsMap);
			}
			if (inStockNotice.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(inStockNotice.getProcessInstanceId());
			}
//			data.put("osc", osc);
			//内部合同号
			data.put("innerContractNo", innerContractNo);
			data.put("purchaseContractNo", inStockNotice.getPurchaseContractNo());
			data.put("noticeNo", inStockNotice.getNoticeNo());
			data.put("deliveryUnitView", inStockNotice.getDeliveryUnitView());
			data.put("setUnitView", inStockNotice.getSetUnitView());
			data.put("deliveryModeView", inStockNotice.getDeliveryModeView());
			//申请部门
			data.put("createrDept", inStockNotice.getCreaterDept());
			//申请人
			data.put("createrName", inStockNotice.getCreaterName());
			//业务经办人
			data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
			//申请日期
			data.put("createDate", inStockNotice.getCreateDate());
			data.put("goodsList", goodsList);
			//合同审查
			data.put("traceList", traceList);
		}
		
		Map<String, Object> export = new HashMap<String, Object>();
		export.put("title", "入库通知" + StringUtils.replaceEach(inStockNotice.getNoticeNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}

}
