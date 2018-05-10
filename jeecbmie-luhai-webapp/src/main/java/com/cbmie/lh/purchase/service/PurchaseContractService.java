package com.cbmie.lh.purchase.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.purchase.dao.PurchaseContractDao;
import com.cbmie.lh.purchase.dao.PurchaseContractGoodsDao;
import com.cbmie.lh.purchase.dao.PurchaseGoodsIndexDao;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.purchase.entity.PurchaseGoodsIndex;
import com.cbmie.lh.sale.dao.SaleContractGoodsDao;
import com.cbmie.lh.sale.dao.SaleContractGoodsIndexDao;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.entity.SaleContractGoodsIndex;
import com.cbmie.system.utils.DictUtils;

/**
 * 采购合同-进口service
 */
@Service
@Transactional
public class PurchaseContractService extends BaseService<PurchaseContract, Long> {

	@Autowired
	private PurchaseContractDao purchaseContractDao;
	
//	@Autowired
//	private PuchaseSaleRelationDao puchaseSaleRelationDao;
	
	
	@Autowired
	private SaleContractGoodsDao saleContractGoodsDao;
	
	@Autowired
	private PurchaseContractGoodsDao purchaseContractGoodsDao;
	
	@Autowired
	private SaleContractGoodsIndexDao saleContractGoodsIndexDao;
	
	@Autowired
	private PurchaseGoodsIndexDao purchaseGoodsIndexDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bus;
	
	
	@Override
	public HibernateDao<PurchaseContract, Long> getEntityDao() {
		return purchaseContractDao;
	}

	public PurchaseContract findByContractNo(PurchaseContract purchaseContract) {
		return purchaseContractDao.findByContractNo(purchaseContract);
	}
	
	public PurchaseContract findByContractNo(String  contractNo) {
		return purchaseContractDao.findByContractNo(contractNo);
	}

	public PurchaseContract findByInnerContractNo(PurchaseContract purchaseContract) {
		return purchaseContractDao.findByInnerContractNo(purchaseContract);
	}
	
	public PurchaseContract findByInnerContractNo(String innerContractNo) {
		return purchaseContractDao.findByInnerContractNo(innerContractNo);
	}
	
	public String getSequence(String param){
		Integer squence = purchaseContractDao.getSequence(param) + 1;
//        Format f1 = new DecimalFormat("0000");
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String dateNowStr = sdf.format(d);
//        return dateNowStr+ "-" + f1.format(squence);
        return dateNowStr+ "-" + squence;
	}
	
	
	public String getNextSequence(String param){
		Integer squence = purchaseContractDao.getNextSequence(param);
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String dateNowStr = sdf.format(d);
        return dateNowStr+ "-" + squence;
	}

	public List<PurchaseContract> getAllBak(Long sourceId) {
		return purchaseContractDao.getAllBak(sourceId);
	}

	public List<PurchaseContract> getPurchaseContractBak(long sourceId) {
		return purchaseContractDao.findBy("sourceId", purchaseContractDao.get(sourceId).getSourceId());
	}

//	public List<SaleContract> getSaleRelation(String purchaseNo) {
//		List<PuchaseSaleRelation> puchaseSaleRelations = new ArrayList<PuchaseSaleRelation>();
//		List<SaleContract> saleContracts = new ArrayList<SaleContract>();
//		SaleContract saleContract = null;
//		puchaseSaleRelations = puchaseSaleRelationDao.findBy("purchaseContractNo", purchaseNo);
//		for(PuchaseSaleRelation puchaseSaleRelation : puchaseSaleRelations){
//			saleContract = new SaleContract();
//			saleContract = saleContractDao.findEffectByNo(puchaseSaleRelation.getSaleContractNo());
//			saleContracts.add(saleContract);
//		}
//		return saleContracts;
//	}

	public List<Map<String,Object>> getSaleShip(Map<String, Object> showParams) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String contractNo = showParams.containsKey("contractNo") ? "%" + showParams.get("contractNo") + "%" : "%";
		List<Object[]> list = purchaseContractDao.getSaleShip( contractNo);
		for(Object obj : list) {
			Object[] obj_array = (Object[])obj;
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			/**品名*/
			row_.put("goodsName", obj_array[1].toString());
			/** 类别 **/
			row_.put("goodsCategory", obj_array[2].toString());
			/** 数量 **/
			row_.put("goodsQuantity", obj_array[3].toString());
			/** 单价 **/
			row_.put("price", obj_array[4].toString());
			/** 金额 **/
			row_.put("amount", obj_array[5].toString());
			/** 合同编号* */
			row_.put("contractNo", obj_array[6].toString());
			/** 卖受人* */
			row_.put("seller", obj_array[7].toString());
			/** 买受人* */
			row_.put("purchaser", obj_array[8].toString());
			/**船名*/
			row_.put("ship", obj_array[9].toString());
			
			row_.put("goodsNameView", obj_array[10].toString());
			
			row_.put("sellerView", obj_array[11].toString());
			
			row_.put("purchaserView", obj_array[12].toString());
			result.add(row_);
		}
		return result;
	}

	public void saveSaleGoods(String purchaseNo, String saleGoodsIds) {
		PurchaseContract purchaseContract = purchaseContractDao.findNoRuleByContractNo(purchaseNo);
		PurchaseContractGoods purchaseContractGoods = null;
		SaleContractGoods saleContractGoods = null;
		List<SaleContractGoodsIndex> saleGoodsIndexList = null;
		PurchaseGoodsIndex purchaseGoodsIndex = null;
		String[] str = new String[100];
		str = saleGoodsIds.split(",");
		for(int i=0;i<str.length;i++){
			saleContractGoods = new SaleContractGoods();
			saleContractGoods = saleContractGoodsDao.get(Long.valueOf(str[i]).longValue());
			purchaseContractGoods = new PurchaseContractGoods();
			BeanUtils.copyProperties(saleContractGoods, purchaseContractGoods);
			purchaseContractGoods.setId(null);
			purchaseContractGoods.setPurchasePrice(saleContractGoods.getPrice());
			purchaseContractGoods.setSaleContractGoodsId(saleContractGoods.getId());
			purchaseContractGoods.setPurchaseContractId(purchaseContract.getId());
			purchaseContractGoodsDao.save(purchaseContractGoods);
			saleGoodsIndexList = new ArrayList<SaleContractGoodsIndex>();
			saleGoodsIndexList = saleContractGoodsIndexDao.findBy("saleContractGoodsId", saleContractGoods.getId());
			for(SaleContractGoodsIndex saleContractGoodsIndex : saleGoodsIndexList){
				purchaseGoodsIndex = new PurchaseGoodsIndex();
				BeanUtils.copyProperties(saleContractGoodsIndex, purchaseGoodsIndex);
				purchaseGoodsIndex.setId(null);
				purchaseGoodsIndex.setParentId(String.valueOf(purchaseContractGoods.getId()));
				purchaseGoodsIndexDao.save(purchaseGoodsIndex);
			}
		}
	}

	public String getRelLoginNames(List<String> purchaseArray) {
		if(purchaseArray.size()>0){
			return purchaseContractDao.getRelLoginNames(purchaseArray);
		}
		return "";
	}
	
	/**
	 * 
	 * @param contractNo
	 * @return
	 */
	public Map<String, Object> exportPDF(String contractNo) {
		PurchaseContract purchaseContract = findByInnerContractNo(contractNo);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (purchaseContract != null) {
			List<PurchaseContractGoods> pcgList = purchaseContract.getPurchaseContractGoodsList();
			for (PurchaseContractGoods pcg : pcgList) {
				mainContentSB.append("品名：" + DictUtils.getGoodsInfoName(pcg.getGoodsName()) + "<br/>");
				mainContentSB.append("数量：" + pcg.getGoodsQuantity() + "<br/>");
				mainContentSB.append("采购单价：" + pcg.getPurchasePrice() + "<br/>");
			}
			mainContentSB.append("结算条款：" + DictUtils.getDictSingleName(purchaseContract.getSettlementTerms()) + "<br/>");
			mainContentSB.append("备注：" + purchaseContract.getComment());
			businessManager = purchaseContract.getBusinessManager();
			if (purchaseContract.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(purchaseContract.getProcessInstanceId());
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("innerContractNo", contractNo);
		//合同编号
		data.put("contractNo", purchaseContract.getPurchaseContractNo());
		//申请部门
		data.put("createrDept", purchaseContract.getCreaterDept());
		//申请人
		data.put("createrName", purchaseContract.getCreaterName());
		//业务经办人
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		//申请日期
		data.put("createDate", purchaseContract.getCreateDate());
		//合同类型
		data.put("contractCategory", "外贸".equals(DictUtils.getDictSingleName(purchaseContract.getContractCategory())) ? "国际采购合同" : "国内采购合同");
		//合同金额
		data.put("amount", Double.parseDouble(purchaseContract.getContractAmount()));
		//币种
		data.put("currency", DictUtils.getDictSingleName(purchaseContract.getCurrency()));
		//合同期限
		data.put("contracTermt", purchaseContract.getDeliveryStartDate());
		data.put("contracTermtEnd", purchaseContract.getDeliveryTerm());
		//单位全称
		WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(purchaseContract.getDeliveryUnit());
		data.put("signAffi", affi.getCustomerName());
		//联系方式
		data.put("phone", affi.getPhoneContact());
		//地址
		data.put("address", affi.getAddress());
		//主要内容
		data.put("mainContent", mainContentSB.toString());
		//合同审查
		data.put("traceList", traceList);
		
		Map<String, Object> export = new HashMap<String, Object>();
		export.put("title", "订船合同" + StringUtils.replaceEach(purchaseContract.getInnerContractNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}
}
