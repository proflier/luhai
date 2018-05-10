package com.cbmie.lh.purchase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.lh.purchase.dao.PurchaseContractGoodsDao;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.purchase.entity.PurchaseGoodsIndex;

/**
 * 采购合同-进口-子表信息service
 */
@Service
@Transactional
public class PurchaseContractGoodsService extends BaseService<PurchaseContractGoods, Long> {

	@Autowired
	private PurchaseContractGoodsDao purchaseContractGoodsDao;
	
	@Autowired
	private GoodsIndexService indexService;

	@Override
	public HibernateDao<PurchaseContractGoods, Long> getEntityDao() {
		return purchaseContractGoodsDao;
	}

	public List<PurchaseContractGoods> getGoodsList(long purchaseContractId) {
		return purchaseContractGoodsDao.findBy("purchaseContractId", purchaseContractId);
	}
	
	public List<PurchaseContractGoods> getContractGodds(long id) {
		List<PurchaseContractGoods> purchaseContractGoodsList = getGoodsList(id);
		List<PurchaseContractGoods> returnList = new ArrayList<PurchaseContractGoods>();
		for (PurchaseContractGoods purchaseContractGoods : purchaseContractGoodsList) {
			returnList.add(converGoods(purchaseContractGoods));
		}
		return returnList;
	}
	
	public PurchaseContractGoods converGoods(PurchaseContractGoods purchaseContractGoods) {
		List<PurchaseGoodsIndex> purchaseGoodsIndexList = purchaseContractGoods.getPurchaseGoodsIndexList();
		StringBuffer ss = new StringBuffer();

		ss.append("<table width='98%' class='tableClass'><tr><th>指标</th><th>约定值</th><th>拒收值</th><th>扣罚条款</th></tr>");
		for (PurchaseGoodsIndex purchaseGoodsIndex : purchaseGoodsIndexList) {
			ss.append("<tr><td>" + indexService.get(Long.valueOf(purchaseGoodsIndex.getIndexName())).getIndexName()
					+ "</td><td>" + purchaseGoodsIndex.getConventions() + "</td><td>"
					+ purchaseGoodsIndex.getRejectionValue() + "</td><td>" + purchaseGoodsIndex.getTerms()
					+ "</td></tr>");
		}
		ss.append("</table>");
		purchaseContractGoods.setIndicatorInformation(String.valueOf(ss));
		return purchaseContractGoods;
	}
	
}
