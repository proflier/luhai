package com.cbmie.lh.stock.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.purchase.dao.PurchaseContractGoodsDao;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.stock.dao.InStockNoticeGoodsDao;
import com.cbmie.lh.stock.entity.InStockNoticeGoods;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 入库通知商品
 */
@Service
@Transactional
public class InStockNoticeGoodsService extends BaseService<InStockNoticeGoods, Long> {

	@Autowired
	private InStockNoticeGoodsDao inStockNoticeGoodsDao;
	
	@Autowired
	private PurchaseContractGoodsDao purchaseContractGoodsDao;
	
	
	@Override
	public HibernateDao<InStockNoticeGoods, Long> getEntityDao() {
		return inStockNoticeGoodsDao;
	}

	public List<InStockNoticeGoods> goodsList(String id) {
		return inStockNoticeGoodsDao.findBy("inStockNoticeId", id);
	}

	public void saveGoods(String innerContractNo, String inStockNoticeId) throws IllegalAccessException, InvocationTargetException {
		List<PurchaseContractGoods> purchaseContractGoodsList = new ArrayList<PurchaseContractGoods>();
		inStockNoticeGoodsDao.deleteByNoticeId(inStockNoticeId);
		purchaseContractGoodsList = purchaseContractGoodsDao.getByInnerContractNo(innerContractNo);
		if(purchaseContractGoodsList.size()>0){
			for(PurchaseContractGoods purchaseContractGoods :purchaseContractGoodsList){
				InStockNoticeGoods inStockNoticeGoods = new InStockNoticeGoods();
				BeanUtils.copyProperties(inStockNoticeGoods, purchaseContractGoods);
				inStockNoticeGoods.setId(null);
				inStockNoticeGoods.setInStockNoticeId(inStockNoticeId);
				inStockNoticeGoods.setInnerContractNo(innerContractNo);
				inStockNoticeGoods.setGoodsName(purchaseContractGoods.getGoodsName());
				inStockNoticeGoods.setQuantity(Double.valueOf(purchaseContractGoods.getGoodsQuantity()));
				inStockNoticeGoods.setUnits("10580003");//修为为默认单位为吨
				User currentUser = UserUtil.getCurrentUser();
				inStockNoticeGoods.setCreaterNo(currentUser.getLoginName());
				inStockNoticeGoods.setCreaterName(currentUser.getName());
				inStockNoticeGoods.setCreateDate(new Date());
				inStockNoticeGoods.setUpdateDate(new Date());
				inStockNoticeGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
				inStockNoticeGoodsDao.save(inStockNoticeGoods);
			}
		}
		
	}

}
