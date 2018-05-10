package com.cbmie.lh.permission.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.credit.dao.MortgageRegDao;
import com.cbmie.lh.credit.dao.PayApplyDao;
import com.cbmie.lh.credit.dao.PayMortgageRegDao;
import com.cbmie.lh.credit.dao.PayRegDao;
import com.cbmie.lh.credit.entity.MortgageReg;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.credit.entity.PayMortgageReg;
import com.cbmie.lh.credit.entity.PayReg;
import com.cbmie.lh.financial.dao.InputInvoiceSubDao;
import com.cbmie.lh.financial.dao.PaymentConfirmChildDao;
import com.cbmie.lh.financial.dao.PaymentConfirmDao;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
import com.cbmie.lh.financial.service.InputInvoiceService;
import com.cbmie.lh.financial.service.PaymentConfirmService;
import com.cbmie.lh.logistic.dao.FreightLetterDao;
import com.cbmie.lh.logistic.dao.HighwayContractDao;
import com.cbmie.lh.logistic.dao.InsuranceContractDao;
import com.cbmie.lh.logistic.dao.LhBillsDao;
import com.cbmie.lh.logistic.dao.OrderShipContractDao;
import com.cbmie.lh.logistic.dao.PortContractDao;
import com.cbmie.lh.logistic.dao.RailwayContractDao;
import com.cbmie.lh.logistic.dao.ShipTraceDao;
import com.cbmie.lh.logistic.dao.ShipmentsSettlementDao;
import com.cbmie.lh.logistic.dao.TransportSettlementDao;
import com.cbmie.lh.logistic.dao.WharfSettlementDao;
import com.cbmie.lh.logistic.entity.FreightLetter;
import com.cbmie.lh.logistic.entity.HighwayContract;
import com.cbmie.lh.logistic.entity.InsuranceContract;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.OrderShipContract;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.logistic.entity.RailwayContract;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.entity.ShipmentsSettlement;
import com.cbmie.lh.logistic.entity.TransportSettlement;
import com.cbmie.lh.logistic.entity.WharfSettlement;
import com.cbmie.lh.permission.dao.BusinessPerssionDao;
import com.cbmie.lh.permission.entity.BusinessPerssion;
import com.cbmie.lh.purchase.dao.PurchaseContractDao;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.dao.SaleContractDao;
import com.cbmie.lh.sale.dao.SaleDeliveryDao;
import com.cbmie.lh.sale.dao.SaleInvoiceDao;
import com.cbmie.lh.sale.dao.SaleSettlementDao;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.stock.dao.InStockDao;
import com.cbmie.lh.stock.dao.InventoryStockDao;
import com.cbmie.lh.stock.dao.OutStockDao;
import com.cbmie.lh.stock.dao.StockAllocationDao;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.InventoryStock;
import com.cbmie.lh.stock.entity.OutStock;
import com.cbmie.lh.stock.entity.StockAllocation;
import com.cbmie.system.dao.UserDao;
import com.cbmie.system.entity.CustomerPerssion;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 客户相关用户权限service
 * @param <T>
 */
@Service
@Transactional
public class BusinessPerssionService<T> extends BaseService<BusinessPerssion, Long> {
	
	@Autowired
	private BusinessPerssionDao businessPerssionDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PayApplyDao payApplyDao;
	
	@Autowired
	private MortgageRegDao mortgageRegDao;
	
	@Autowired
	private PayMortgageRegDao payMortgageRegDao;
	
	@Autowired
	private PayRegDao payRegDao;
	
	@Autowired 
	private LhBillsDao billsDao;
	
	@Autowired
	private PurchaseContractDao purchaseContractDao;
	
	@Autowired
	private SaleDeliveryDao saleDeliveryDao;
	
	@Autowired
	private SaleContractDao saleContractDao;
	
	@Autowired
	private SaleSettlementDao saleSettlementDao;

	@Autowired
	private SaleInvoiceDao invoiceDao;
	
	@Autowired
	private InStockDao inStockDao;
	
	@Autowired
	private OutStockDao outStockDao;
	
	@Autowired
	private ShipTraceDao shipTraceDao;
	
	@Autowired
	private OrderShipContractDao orderShipContractDao;//订船合同
	
	@Autowired 
	private InsuranceContractDao insuranceContractDao;//保险合同
	
	@Autowired
	private HighwayContractDao highwayContractDao;//汽运合同
	
	@Autowired
	private FreightLetterDao freightLetterDao;//运价函
	
	@Autowired
	private RailwayContractDao railwayContractDao;//铁运合同

	@Autowired
	private PortContractDao portContractDao;//码头合同
	
	@Autowired
	private ShipmentsSettlementDao shipmentsSettlementDao;//船运结算
	
	@Autowired
	private WharfSettlementDao wharfSettlementDao;//码头结算
	
	@Autowired
	private PaymentConfirmChildDao paymentConfirmChildDao;
	
	@Autowired
	private PaymentConfirmDao paymentConfirmDao;
	@Autowired
	private PaymentConfirmService paymentConfirmService;
	
	@Autowired
	private InputInvoiceService InputInvoiceService;//进项发票
	
	@Autowired
	private InputInvoiceSubDao inputInvoiceSubDao;//进项发票
	
	@Autowired
	private StockAllocationDao stockAllocationDao;//仓储调拨
	
	@Autowired
	private InventoryStockDao inventoryStockDao;//盘库
	
	@Autowired
	private TransportSettlementDao transportSettlementDao;
	
	@Override
	public HibernateDao<BusinessPerssion, Long> getEntityDao() {
		return businessPerssionDao;
	}
	
	@Transactional(readOnly=false)
	public void save(BusinessPerssion businessPerssion) {
		businessPerssionDao.save(businessPerssion);
	}

	public Map<String, String> getUserNamesString(String relLoginNames) {
		if(StringUtils.isNotEmpty(relLoginNames)){
			String []params = relLoginNames.split(",");
			if(params.length>0){
				return businessPerssionDao.getUserNamesString(params);
			}
		}
		Map<String, String> returnValue = new HashMap<String, String>();
		returnValue.put("themeMemberIds", "");
		returnValue.put("themeMembers", "");
		return returnValue;
	}

	public List<BusinessPerssion> findByBusinessCode(String businessId, String businessCode) {
		return businessPerssionDao.findByBusinessCode(businessId,businessCode);
	}
	

	public String findVisible(String businessFlag) {
		User currentUser = UserUtil.getCurrentUser();
		String returnValue ="";
		List<String> returnVisibleList = businessPerssionDao.findVisible(businessFlag,currentUser.getId());
		if(returnVisibleList.size()>0){
			returnValue = StringUtils.join(returnVisibleList.toArray(), ",");
		}
		return returnValue;
	}

	/**
	 * 设置默认用户
	 * @param userId
	 * @param businessCode
	 * @param businessId
	 * @param businessFlag
	 */
	public void setCreateUser(Integer userId, String businessCode, Long businessId, String businessFlag) {
		BusinessPerssion businessPerssion = new BusinessPerssion();
		businessPerssion.setBusinessCode(businessCode);
		businessPerssion.setBusinessId(businessId.toString());
		businessPerssion.setBusinessFlag(businessFlag);
		businessPerssion.setLoginId(userId);
		businessPerssionDao.save(businessPerssion);
		businessPerssionDao.flush();
	}


	public void updatePermission4Purchase(long id, String themeMemberIds) {
		PurchaseContract purchaseContract = purchaseContractDao.get(id);
		purchaseContract.setRelLoginNames(themeMemberIds);
		purchaseContractDao.save(purchaseContract);
		//开证申请
		List<PayApply> payApplyList = payApplyDao.findBy("inteContractNo", purchaseContract.getInnerContractNo());
		if(payApplyList.size()>0){
			for(PayApply payApply :payApplyList){
				payApply.setRelLoginNames(themeMemberIds);
				payApplyDao.save(payApply);
			}
		}
		//开证登记
		List<PayReg> payRegList = payRegDao.findBy("inteContractNo", purchaseContract.getInnerContractNo());
		if(payRegList.size()>0){
			for(PayReg payReg :payRegList){
				payReg.setRelLoginNames(themeMemberIds);
				payRegDao.save(payReg);
			}
		}
		//上游到单
		List<LhBills> billList = billsDao.findByContractNo(purchaseContract.getInnerContractNo());
		if(billList.size()>0){
			for(LhBills lhBills :billList){
				lhBills.setRelLoginNames(themeMemberIds);
				billsDao.save(lhBills);
				//押汇登记
				List<MortgageReg> mortgageRegList = mortgageRegDao.findBy("woodBillId", lhBills.getBillNo());
				if(mortgageRegList.size()>0){
					for(MortgageReg mortgageReg :mortgageRegList){
						mortgageReg.setRelLoginNames(themeMemberIds);
						mortgageRegDao.save(mortgageReg);
					}
				}
				//付汇登记
				List<PayMortgageReg> payMortgageRegList=payMortgageRegDao.findBy("woodBillId", lhBills.getBillNo());
				if(payMortgageRegList.size()>0){
					for(PayMortgageReg payMortgageReg :payMortgageRegList){
						payMortgageReg.setRelLoginNames(themeMemberIds);
						payMortgageRegDao.save(payMortgageReg);
					}
				}
				//入库
				List<InStock> instockList = inStockDao.findBy("billNo", lhBills.getBillNo());
				if(instockList.size()>0){
					for(InStock inStock:instockList){
						inStock.setRelLoginNames(themeMemberIds);
						inStockDao.save(inStock);
					}
				}
			}
		}
		//进项发票
		List<InputInvoiceSub> InputInvoiceSubList = inputInvoiceSubDao.findBy("purchaseOrderNumber", purchaseContract.getInnerContractNo());
		if(InputInvoiceSubList.size()>0){
			for(InputInvoiceSub inputInvoiceSub : InputInvoiceSubList){
				inputInvoiceSub.setRelLoginNames(themeMemberIds);
				inputInvoiceSubDao.save(inputInvoiceSub);
				InputInvoiceService.saveRelLoginNames(inputInvoiceSub);
			}
		}
		
	}

	public void updatePermission4Sale(long id, String themeMemberIds) {
		SaleContract saleContract = saleContractDao.get(id);
		saleContract.setRelLoginNames(themeMemberIds);
		saleContractDao.save(saleContract);
		List<SaleDelivery> saleDeliveryList = saleDeliveryDao.findBy("saleContractNo", saleContract.getContractNo());
		if(saleDeliveryList.size()>0){
			for(SaleDelivery saleDelivery :saleDeliveryList){
				saleDelivery.setRelLoginNames(themeMemberIds);
				saleDeliveryDao.save(saleDelivery);
				//出库
				List<OutStock> outStockList = outStockDao.findBy("deliveryNo", saleDelivery.getDeliveryReleaseNo());
				if(outStockList.size()>0){
					for(OutStock outStock :outStockList){
						outStock.setRelLoginNames(themeMemberIds);
						outStockDao.save(outStock);
					}
				}
			}
		}
		//销售结算
		List<SaleSettlement> saleSettlementList = saleSettlementDao.findBy("saleContractNo",  saleContract.getContractNo());
		if(saleSettlementList.size()>0){
			for(SaleSettlement saleSettlement : saleSettlementList){
				saleSettlement.setRelLoginNames(themeMemberIds);
				saleSettlementDao.save(saleSettlement);
			}
		}
		//销售发票
		List<SaleInvoice> saleInvoiceList = invoiceDao.findBy("saleContractNo", saleContract.getContractNo());
		if(saleInvoiceList.size()>0){
			for(SaleInvoice saleInvoice:saleInvoiceList){
				saleInvoice.setRelLoginNames(themeMemberIds);
				invoiceDao.save(saleInvoice);
			}
		}
		//付款申请
//		List<PaymentConfirmChild> paymentConfirmChildList =  paymentConfirmChildDao.findBy("code", saleContract.getContractNo());
//		if(paymentConfirmChildList.size()>0){
//			for(PaymentConfirmChild paymentConfirmChild:paymentConfirmChildList){
//				paymentConfirmChild.setRelLoginNames(themeMemberIds);
//				paymentConfirmChildDao.save(paymentConfirmChild);
//			}
//		}
//		List<PaymentConfirm> paymentConfirmList = paymentConfirmDao.findByChildCode(saleContract.getContractNo());
//		for(PaymentConfirm paymentConfirm :paymentConfirmList){
//			paymentConfirmService.saveBusinessPermission(paymentConfirm);
//		}
		updatePaymentConfirm(saleContract.getContractNo(), themeMemberIds);
	}
	
	public void updatePermission4InStock(long id, String themeMemberIds) {
		InStock inStock = inStockDao.get(id);
		inStock.setRelLoginNames(themeMemberIds);
		inStockDao.save(inStock);
	}
	
	public void updatePermission4OutStock(long id, String themeMemberIds) {
		OutStock outStock = outStockDao.get(id);
		outStock.setRelLoginNames(themeMemberIds);
		outStockDao.save(outStock);
	}
	
	public void updatePermission4ShipTrace(long id, String themeMemberIds) {
		ShipTrace shipTrace = shipTraceDao.get(id);
		shipTrace.setRelLoginNames(themeMemberIds);
		shipTraceDao.save(shipTrace);
		//付款
		updatePaymentConfirm(shipTrace.getShipNo(), themeMemberIds);
		//仓储调拨
		List<StockAllocation> stockAllocationList = stockAllocationDao.findBy("shipNo", shipTrace.getShipNo());
		if(stockAllocationList.size()>0){
			for(StockAllocation stockAllocation : stockAllocationList){
				stockAllocation.setRelLoginNames(themeMemberIds);
				stockAllocationDao.save(stockAllocation);
			}
		}
		//盘库
		List<InventoryStock> inventoryStockList = inventoryStockDao.findBy("shipNo", shipTrace.getShipNo());
		if(inventoryStockList.size()>0){
			for(InventoryStock inventoryStock : inventoryStockList){
				inventoryStock.setRelLoginNames(themeMemberIds);
				inventoryStockDao.save(inventoryStock);
			}
		}
	}
	
	public void updatePermission4OrderShipContract(long id, String themeMemberIds) {
		OrderShipContract orderShipContract = orderShipContractDao.get(id);
		orderShipContract.setRelLoginNames(themeMemberIds);
		orderShipContractDao.save(orderShipContract);
		List<ShipmentsSettlement> shipmentsSettlementList = shipmentsSettlementDao.findBy("contractNo", orderShipContract.getInnerContractNo());
		if(shipmentsSettlementList.size()>0){
			for(ShipmentsSettlement shipmentsSettlement:shipmentsSettlementList){
				shipmentsSettlement.setRelLoginNames(themeMemberIds);
				shipmentsSettlementDao.save(shipmentsSettlement);
			}
		}
	}
	
	public void updatePermission4InsuranceContract(long id, String themeMemberIds) {
		InsuranceContract insuranceContract = insuranceContractDao.get(id);
		insuranceContract.setRelLoginNames(themeMemberIds);
		insuranceContractDao.save(insuranceContract);
	}
	
	public void updatePermission4HighwayContract(long id, String themeMemberIds) {
		HighwayContract highwayContract = highwayContractDao.get(id);
		highwayContract.setRelLoginNames(themeMemberIds);
		highwayContractDao.save(highwayContract);
	}

	public void updatePermission4FreightLetter(long id, String themeMemberIds) {
		FreightLetter freightLetter = freightLetterDao.get(id);
		freightLetter.setRelLoginNames(themeMemberIds);
		freightLetterDao.save(freightLetter);
	}

	public void updatePermission4RailwayContract(long id, String themeMemberIds) {
		RailwayContract railwayContract = railwayContractDao.get(id);
		railwayContract.setRelLoginNames(themeMemberIds);
		railwayContractDao.save(railwayContract);
	}

	public void updatePermission4PortContract(long id, String themeMemberIds) {
		PortContract portContrac = portContractDao.get(id);
		portContrac.setRelLoginNames(themeMemberIds);
		portContractDao.save(portContrac);
		List<WharfSettlement> wharfSettlementList = wharfSettlementDao.findBy("wharf", portContrac.getContractNo());
		if(wharfSettlementList.size()>0){
			for(WharfSettlement wharfSettlement:wharfSettlementList){
				wharfSettlement.setRelLoginNames(themeMemberIds);
				wharfSettlementDao.save(wharfSettlement);
			}
		}
	}
	
	public void updatePaymentConfirm(String code ,String themeMemberIds){
		List<PaymentConfirmChild> paymentConfirmChildList =  paymentConfirmChildDao.findBy("code", code);
		if(paymentConfirmChildList.size()>0){
			for(PaymentConfirmChild paymentConfirmChild:paymentConfirmChildList){
				paymentConfirmChild.setRelLoginNames(themeMemberIds);
				paymentConfirmChildDao.save(paymentConfirmChild);
			}
		}
		List<PaymentConfirm> paymentConfirmList = paymentConfirmDao.findByChildCode(code);
		for(PaymentConfirm paymentConfirm :paymentConfirmList){
			paymentConfirmService.saveBusinessPermission(paymentConfirm);
		}
	}

	public void updatePermission4HightOrRail(long id, String themeMemberIds) {
		TransportSettlement transportSettlement = transportSettlementDao.get(id);
		transportSettlement.setRelLoginNames(themeMemberIds);
		transportSettlementDao.save(transportSettlement);
	}

	/**
	 * 保存用户相关采购
	 * @param loginName
	 * @param ids 变化的ids
	 */
	public void saveRealtion4Purchase(String loginName, String[] ids) {
		//获取当前用户拥有权限的采购
		List<String> purchaseIds = purchaseContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(purchaseIds, ids).get("addString");
		List<String > deleteString =  getChanges(purchaseIds, ids).get("deleteString");
		for(String add: addString){
			PurchaseContract purchaseContract = new PurchaseContract();
			purchaseContract  = purchaseContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(purchaseContract.getRelLoginNames())){
				themeMemberIds = purchaseContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			updatePermission4Purchase(Long.valueOf(add), themeMemberIds);
		}
		for(String delete: deleteString){
			PurchaseContract purchaseContract = new PurchaseContract();
			purchaseContract = purchaseContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = purchaseContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			updatePermission4Purchase(Long.valueOf(delete), themeMemberIds);
		}
	}

	/**
	 * 保存用户相关销售
	 * @param loginName
	 * @param ids 变化后的ids
	 */
	public void saveRealtion4Sale(String loginName, String[] ids) {
		//获取当前用户拥有权限的销售
		List<String> saleIds = saleContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(saleIds, ids).get("addString");
		List<String > deleteString =  getChanges(saleIds, ids).get("deleteString");
		for(String add: addString){
			SaleContract saleContract = new SaleContract();
			saleContract  = saleContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(saleContract.getRelLoginNames())){
				themeMemberIds = saleContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			updatePermission4Sale(Long.valueOf(add), themeMemberIds);
		}
		for(String delete: deleteString){
			SaleContract saleContract = new SaleContract();
			saleContract = saleContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = saleContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			updatePermission4Sale(Long.valueOf(delete), themeMemberIds);
		}
	}
	
	/**
	 * 保存用户相关订船合同
	 * @param loginName
	 * @param ids 变化后的ids
	 */
	public void saveRealtion4OrderShip(String loginName, String[] ids) {
		//获取当前用户拥有权限的销售
		List<String> saleIds = orderShipContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(saleIds, ids).get("addString");
		List<String > deleteString =  getChanges(saleIds, ids).get("deleteString");
		for(String add: addString){
			OrderShipContract orderShipContract = new OrderShipContract();
			orderShipContract  = orderShipContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(orderShipContract.getRelLoginNames())){
				themeMemberIds = orderShipContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			updatePermission4OrderShipContract(Long.valueOf(add), themeMemberIds);
		}
		for(String delete: deleteString){
			OrderShipContract orderShipContract = new OrderShipContract();
			orderShipContract = orderShipContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = orderShipContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			updatePermission4OrderShipContract(Long.valueOf(delete), themeMemberIds);
		}
		
	}
	
	/**
	 * 保存用户相关保险合同
	 * @param loginName
	 * @param ids 变化后的ids
	 */
	public void saveRealtion4Insurance(String loginName, String[] ids) {
		//获取当前用户拥有权限的销售
		List<String> saleIds = insuranceContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(saleIds, ids).get("addString");
		List<String > deleteString =  getChanges(saleIds, ids).get("deleteString");
		for(String add: addString){
			InsuranceContract insuranceContract = new InsuranceContract();
			insuranceContract  = insuranceContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(insuranceContract.getRelLoginNames())){
				themeMemberIds = insuranceContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			insuranceContract.setRelLoginNames(themeMemberIds);
			insuranceContractDao.save(insuranceContract);
		}
		for(String delete: deleteString){
			InsuranceContract insuranceContract = new InsuranceContract();
			insuranceContract = insuranceContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = insuranceContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			insuranceContract.setRelLoginNames(themeMemberIds);
			insuranceContractDao.save(insuranceContract);
		}
	}
	
	/**
	 * 保存用户相关汽运合同
	 * @param loginName
	 * @param ids 变化的ids
	 */
	public void saveRealtion4Highway(String loginName, String[] ids) {
		//获取当前用户拥有权限的销售
		List<String> saleIds = highwayContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(saleIds, ids).get("addString");
		List<String > deleteString =  getChanges(saleIds, ids).get("deleteString");
		for(String add: addString){
			HighwayContract highwayContract = new HighwayContract();
			highwayContract  = highwayContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(highwayContract.getRelLoginNames())){
				themeMemberIds = highwayContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			highwayContract.setRelLoginNames(themeMemberIds);
			highwayContractDao.save(highwayContract);
		}
		for(String delete: deleteString){
			HighwayContract highwayContract = new HighwayContract();
			highwayContract = highwayContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = highwayContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			highwayContract.setRelLoginNames(themeMemberIds);
			highwayContractDao.save(highwayContract);
		}
		
	}
	
	public void saveRealtion4Railway(String loginName, String[] ids) {
		//获取当前用户拥有权限的销售
		List<String> saleIds = railwayContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(saleIds, ids).get("addString");
		List<String > deleteString =  getChanges(saleIds, ids).get("deleteString");
		for(String add: addString){
			RailwayContract railwayContract = new RailwayContract();
			railwayContract  = railwayContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(railwayContract.getRelLoginNames())){
				themeMemberIds = railwayContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			railwayContract.setRelLoginNames(themeMemberIds);
			railwayContractDao.save(railwayContract);
		}
		for(String delete: deleteString){
			RailwayContract railwayContract = new RailwayContract();
			railwayContract = railwayContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = railwayContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			railwayContract.setRelLoginNames(themeMemberIds);
			railwayContractDao.save(railwayContract);
		}
	}
	
	public void saveRealtion4Port(String loginName, String[] ids) {
		//获取当前用户拥有权限的销售
		List<String> saleIds = portContractDao.findHavePermission(loginName);
		List<String > addString =  getChanges(saleIds, ids).get("addString");
		List<String > deleteString =  getChanges(saleIds, ids).get("deleteString");
		for(String add: addString){
			PortContract portContract = new PortContract();
			portContract  = portContractDao.get(Long.valueOf(add));
			String themeMemberIds = "";
			if(StringUtils.isNotBlank(portContract.getRelLoginNames())){
				themeMemberIds = portContract.getRelLoginNames()+","+loginName;
			}else{
				themeMemberIds = loginName;
			}
			portContract.setRelLoginNames(themeMemberIds);
			portContractDao.save(portContract);
		}
		for(String delete: deleteString){
			PortContract portContract = new PortContract();
			portContract = portContractDao.get(Long.valueOf(delete));
			String themeMemberIds = "";
			String ranges = portContract.getRelLoginNames();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			themeMemberIds = StringUtils.join(rageList, ",");
			portContract.setRelLoginNames(themeMemberIds);
			portContractDao.save(portContract);
		}
	}
	
	/**
	 * 获取变化的ids
	 * @param sourceIds 原始相关ids
	 * @param changeIds 变化后的ids
	 * @return
	 */
	private Map<String , List<String > > getChanges (List<String> sourceIds,String[] changeIds){
		 Map<String , List<String > >  returnMap = new HashMap<String , List<String > >();
		List<String> addString = new ArrayList<String>();
		List<String> deleteString = new ArrayList<String>();
		List<String> updateString = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		//两个集合不判断空，自动过滤，最后变更数据
		for (String purchaseId : sourceIds) {
			map.put(String.valueOf(purchaseId), 1);
		}
		if(changeIds!=null){
			for (String string : changeIds) {
				if (map.get(string) != null) {
					updateString.add(string);
					map.put(string, 2);
				} else {
					addString.add(string);
				}
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				deleteString.add(entry.getKey());
			}
		}
		returnMap.put("addString", addString);
		returnMap.put("deleteString", deleteString);
		return returnMap;
	}

}
