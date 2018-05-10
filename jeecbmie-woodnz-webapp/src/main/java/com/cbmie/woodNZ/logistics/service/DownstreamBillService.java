package com.cbmie.woodNZ.logistics.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.logistics.dao.BillsDao;
import com.cbmie.woodNZ.logistics.dao.BillsGoodsDao;
import com.cbmie.woodNZ.logistics.dao.DownstreamBillDao;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;
import com.cbmie.woodNZ.salesContract.dao.PurchaseSaleSameSubDao;
import com.cbmie.woodNZ.stock.dao.InStockDao;
import com.cbmie.woodNZ.stock.entity.InStock;


/**
 * 下游到单信息service
 */
@Service
@Transactional
public class DownstreamBillService extends BaseService<DownstreamBill, Long> {

	@Autowired 
	private DownstreamBillDao downstreamBillDao;
	
	@Autowired 
	private PurchaseSaleSameSubDao purchaseSaleSameSubDao;
	
	@Autowired 
	private BillsDao billsDao;
	
	@Autowired 
	private BillsGoodsDao billsGoodsDao;
	
	@Autowired
	private InStockDao inStockDao;
	
	@Override
	public HibernateDao<DownstreamBill, Long> getEntityDao() {
		return downstreamBillDao;
	}

	/**
	 * @param displayValue 可以支持“,”分割的字符串
	 * @return
	 */
	public List<WoodBills> searchBills(String displayValue) {
		List<WoodCghtJk> woodCghtJkList = new ArrayList<WoodCghtJk>();
		List<WoodBillsGoods> woodBillsGoodsList = new ArrayList<WoodBillsGoods>();
		List<WoodBills> woodBillsList = new ArrayList<WoodBills>();
		String[]ids = displayValue.split(",");
		if(ids.length>0){
			for(int i=0;i<ids.length;i++){
				woodCghtJkList.addAll(purchaseSaleSameSubDao.searchBySaleId(ids[i]));
			}
		}
		if(woodCghtJkList.size()>0){
			for(WoodCghtJk woodCghtJk:woodCghtJkList){
				woodBillsGoodsList.addAll(billsGoodsDao.getGoodsListByCGHTH(woodCghtJk.getHth()));
			}
		}
		if(woodBillsGoodsList.size()>0){
			for(WoodBillsGoods woodBillsGoods :woodBillsGoodsList){
				WoodBills woodBills = new WoodBills();
				woodBills = billsDao.get(Long.valueOf(woodBillsGoods.getParentId()));
				if(woodBillsList.contains(woodBills)){}else{
					woodBillsList.add(woodBills);
				}
			}
		}
		filterWoodBills(woodBillsList);
		return woodBillsList;
	}
	
	/**
	 * 根据已经确认入库的提单号进行筛选
	 */
	public void filterWoodBills(List<WoodBills>  returnList){
		List<InStock> instockList = inStockDao.findAllConfirm();
		List<String > billNo = new ArrayList<>();
		if(instockList.size()>0){
			for(InStock inStock:instockList){
				billNo.add(inStock.getBillId());
			}
		}
		Iterator<WoodBills> it = returnList.iterator();
		while (it.hasNext()) {
			WoodBills woodBills = (WoodBills) it.next();
			if(!billNo.contains(woodBills.getBillNo())){
				it.remove();
			}
		}
	}

	/**
	 * @param contractId
	 * @return
	 */
	public List<DownstreamBill> getInvoiceNoByConId(String contractId) {
		return downstreamBillDao.getInvoiceNoByConId(contractId);
	}
	
	public Page searchBills(Page page, Map<String, Object> filterMap) {
		//'ZH-HEYE-20160829-003'
		String sql = "select a.* from wood_downstream_bill as a where 1=1 "
				+ "and exists(select 1 from wood_downstream_goods as b where 1=1 and b.PARENT_ID = a.id and b.cp_Contract_No like :cpContractNo)";
		if(!filterMap.containsKey("cpContractNo")) {
			filterMap.put("cpContractNo", "");
		}
		
		downstreamBillDao.sqlSearch(page, sql, filterMap);
		return page;
	}

	public String getChose(Long id) {
		return downstreamBillDao.getChose(id);
	}

	public DownstreamBill findByNo(DownstreamBill downstreamBill) {
		return downstreamBillDao.findByNo(downstreamBill);
	}

}
