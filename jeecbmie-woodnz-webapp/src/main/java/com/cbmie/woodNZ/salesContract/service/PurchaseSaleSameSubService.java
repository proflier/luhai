package com.cbmie.woodNZ.salesContract.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkService;
import com.cbmie.woodNZ.salesContract.dao.PurchaseSaleSameSubDao;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSameSub;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * 采销同批子表service
 */
@Service
@Transactional
public class PurchaseSaleSameSubService extends BaseService<PurchaseSaleSameSub, Long> {

	@Autowired 
	private PurchaseSaleSameSubDao purchaseSaleSameSubDao;
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private WoodCghtJkService woodCghtJkService;

	@Override
	public HibernateDao<PurchaseSaleSameSub, Long> getEntityDao() {
		return purchaseSaleSameSubDao;
	}
	
	public void save(PurchaseSaleSame  purchaseSaleSame, String purchaseContractJson,String saleContractJson) {
		// 转成标准的json字符串
		saleContractJson = StringEscapeUtils.unescapeHtml4(saleContractJson);
		purchaseContractJson = StringEscapeUtils.unescapeHtml4(purchaseContractJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<WoodCghtJk> purchaseContractList = new ArrayList<WoodCghtJk>();
		List<WoodSaleContract> saleContractList = new ArrayList<WoodSaleContract>();
		try {
			//采购合同json转换
			JsonNode jsonNodePurchase = objectMapper.readTree(purchaseContractJson);
			for (JsonNode jn : jsonNodePurchase) {
				WoodCghtJk woodPurchaseContract = objectMapper.readValue(jn.toString(), WoodCghtJk.class);
				purchaseContractList.add(woodPurchaseContract);
			}
			//销售合同json转换
			JsonNode jsonNodeSale = objectMapper.readTree(saleContractJson);
			for (JsonNode jn : jsonNodeSale) {
				WoodSaleContract woodSaleContract = objectMapper.readValue(jn.toString(), WoodSaleContract.class);
				saleContractList.add(woodSaleContract);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			delete(purchaseSaleSame,purchaseContractList,saleContractList);
			save(purchaseSaleSame,purchaseContractList,saleContractList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//清空子表所有数据
	private void delete(PurchaseSaleSame purchaseSaleSame,
			List<WoodCghtJk> purchaseContractList,
			List<WoodSaleContract> saleContractList) {
		List<PurchaseSaleSameSub> subList = purchaseSaleSame.getSaleContractSubList();
		List<PurchaseSaleSameSub> deleteSubList = new ArrayList<PurchaseSaleSameSub>();;//保存要删除的数据
		for(PurchaseSaleSameSub sub:subList){
			deleteSubList.add(purchaseSaleSameSubDao.find(sub.getId()));
		}
		for(PurchaseSaleSameSub sub:deleteSubList){
			purchaseSaleSameSubDao.updateCxStateCancel(sub.getWoodSaleContract());
			purchaseSaleSameSubDao.updateCxStateCancel(sub.getWoodCghtJk());
			subList.remove(sub);
			sub.setParentId(null);
			purchaseSaleSameSubDao.delete(sub);
		}
	}

	private void save(PurchaseSaleSame purchaseSaleSame,
			List<WoodCghtJk> purchaseContractList,
				List<WoodSaleContract> saleContractList) {
		//采销同批有两种情况：一个采购合同对应多个销售合同； 一个销售合同对应多个采购合同
		try {
			if(purchaseContractList.size() == 1){
				for(WoodSaleContract saleContract:saleContractList){
					PurchaseSaleSameSub sub = new PurchaseSaleSameSub();
					sub.setParentId(purchaseSaleSame.getId());
					sub.setWoodCghtJk(purchaseContractList.get(0));
					sub.setWoodSaleContract(saleContract);
					purchaseSaleSameSubDao.save(sub);
					purchaseSaleSameSubDao.updateCxState(saleContract);
				} 
				purchaseSaleSameSubDao.updateCxStateCg(purchaseContractList.get(0));
			}
			else if(saleContractList.size() == 1){
				for(WoodCghtJk purchaseContract:purchaseContractList){
					PurchaseSaleSameSub sub = new PurchaseSaleSameSub();
					sub.setParentId(purchaseSaleSame.getId());
					sub.setWoodCghtJk(purchaseContract);
					sub.setWoodSaleContract(saleContractList.get(0));
					purchaseSaleSameSubDao.save(sub);
					purchaseSaleSameSubDao.updateCxStateCg(purchaseContract);
				}
				purchaseSaleSameSubDao.updateCxState(saleContractList.get(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<PurchaseSaleSameSub> findBySaleId(String saleId,String mainId) {
		return purchaseSaleSameSubDao.findBySaleId(saleId,mainId);
	}

	public List<PurchaseSaleSameSub> findByPurchaseId(String purchaseId,String mainId) {
		return purchaseSaleSameSubDao.findByPurchaseId(purchaseId,mainId);
	} 

	public List<PurchaseSaleSameSub> getBySaleId(Long saleId) {
		return purchaseSaleSameSubDao.getBySaleId(saleId);
	}

	public void updateCxStateCancel(WoodSaleContract woodSaleContract) {
		purchaseSaleSameSubDao.updateCxStateCancel(woodSaleContract);
	}

	public void updateCxStateCancel(WoodCghtJk c) {
		purchaseSaleSameSubDao.updateCxStateCancel(c);
	}
}
