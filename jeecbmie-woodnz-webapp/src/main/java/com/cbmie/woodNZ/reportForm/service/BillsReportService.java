package com.cbmie.woodNZ.reportForm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.baseinfo.dao.AffiBaseInfoDao;
import com.cbmie.system.utils.JXLExcel;
import com.cbmie.baseinfo.dao.GkDao;
import com.cbmie.woodNZ.baseInfo.dao.GoodsInfoDao;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;
import com.cbmie.woodNZ.reportForm.dao.BillsReportDao;
import com.cbmie.woodNZ.reportForm.entity.BillsReport;

/**
 * 上游到单 log service
 */
@Service
@Transactional
public class BillsReportService extends BaseService<BillsReport, Long> {

	@Autowired
	private BillsReportDao billsReportDao;
	
	@Autowired
	private GoodsInfoDao goodsInfoDao;
	
	@Autowired
	private AffiBaseInfoDao affiBaseInfoDao; 
	 
	@Autowired
	private GkDao woodGkDao;

	@Override
	public HibernateDao<BillsReport, Long> getEntityDao() {
		return billsReportDao;
	}

	/**
	 * @param response
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void excelLog(HttpServletResponse response, List<BillsReport> list) {
		JXLExcel jxl = new JXLExcel();
		// excel名称
		String excelName = "log.xls";
		// 设置标题
		String[] columnNames = new String[] { "综合合同号","品名","上游合同号","上游客户","下游客户","货物描述","采购数量","实际到单数量",
				"采购总金额","上游发票号","集装箱数量","提单号","目的港","装船日期","到港日期","收到正本日期"};
		jxl.setColumnNames(columnNames);
		// 设置属性名称
		String[] dbColumnNames = new String[] { "cpContractNo", "cateforyName","contractNo","supplier","downStreamClient","goodsDesc","sl","sl",
				"invoiceAmount","invoiceNo","containerNumber","billNo","portName","shipDate","expectPortDate","giveBillsDate"};
		jxl.setDbColumnNames(dbColumnNames);
		// 执行
		jxl.exportExcel(response, list, excelName);
	}

	
	/**
	 * @param page
	 * @return
	 */
	public List<BillsReport> setReportList(Page<WoodBills> page) {
		List<BillsReport> billReportList = new ArrayList<BillsReport>();
		List<WoodBills> billList = page.getResult();
		for(WoodBills bill :billList ){
			List<WoodBillsGoods> goodsList = bill.getGoodsList();;
			if(goodsList.size()>0){
				billReportList.addAll(setReportList(bill ,goodsList));
			}
		}
		return billReportList;
	}
	
	
	/**
	 * 根据每个上游到单设置 报表
	 * @param bill
	 * @param goodsList
	 * @return
	 */
	private List<BillsReport> setReportList(WoodBills bill ,List<WoodBillsGoods> goodsList) {
		List<BillsReport> returnList = new ArrayList<BillsReport>();
		BillsReport billReport = null;
		for(WoodBillsGoods billGoods:goodsList){
			billReport = setDownReport(bill,billGoods);
			returnList.add(billReport);
		}
		return returnList;
	}

	/**
	 * @param bill
	 * @param woodBillsGoods
	 * @return
	 */
	private BillsReport setDownReport(WoodBills bill, WoodBillsGoods woodBillsGoods) {
		BillsReport billReport = new BillsReport();
		billReport.setCpContractNo(woodBillsGoods.getCpContractNo());//综合合同号
		billReport.setCateforyName(woodBillsGoods.getSpmc());//商品名称
		billReport.setContractNo(woodBillsGoods.getCghth());//上游合同号(从采购子表中获取)
		if(StringUtils.isNotBlank(bill.getSupplier())){
			billReport.setSupplier(affiBaseInfoDao.get(Long.valueOf(bill.getSupplier()))
					.getCustomerName());//供应商(上游客户)
		}
		billReport.setGoodsDesc(bill.getRemark());//货物描述
		billReport.setSl(Double.valueOf(bill.getNumbers()).toString());//采购数量(获取货物明细中的数量)
		billReport.setSl(Double.valueOf(bill.getNumbers()).toString());//实际到单数量
		billReport.setInvoiceAmount(bill.getInvoiceAmount());//采购总金额
		billReport.setInvoiceNo(bill.getInvoiceNo());//上游发票号
		billReport.setContainerNumber(0);//集装箱数量
		if(StringUtils.isNotBlank(bill.getPortName())){//目的港
			billReport.setPortName(woodGkDao.get(Long.valueOf(bill.getPortName()))
				.getGkm());
		}
		if(StringUtils.isNotBlank(bill.getBillNo())){
			billReport.setBillNo(bill.getBillNo());//提单号
			String downCustomer = billsReportDao.getDownCustomer(bill.getBillNo());
			if(!downCustomer.equals("")){
				downCustomer = affiBaseInfoDao.get(Long.valueOf(downCustomer)).getCustomerName();
				billReport.setDownStreamClient(downCustomer);//下游客户
			}else{
				billReport.setDownStreamClient("");//下游客户
			}
		}
		billReport.setShipDate(bill.getShipDate()==null?null:bill.getShipDate());//装船日期
		billReport.setExpectPortDate(bill.getExpectPortDate()==null?null:bill.getExpectPortDate());//到港日期
		billReport.setGiveBillsDate(bill.getGiveBillsDate()==null?null:bill.getGiveBillsDate());//收到正本日期
		return billReport;
	}

	/**
	 * 根据商品编码获取品名
	 * @param code 商品编码
	 * @return
	 */
	public String getCategoryName(String code){
		String returnStr = "";
		if(StringUtils.isNotBlank(code)&&code.length()>=2){
					returnStr = goodsInfoDao.getByCode(code.substring(0, 2),1,null);
		}
		return returnStr;
	}

	public List<BillsReport> getBillReportList(List<PropertyFilter> filters) {
		List<BillsReport> billReportList = new ArrayList<BillsReport>();
		List<WoodBills> billList = billsReportDao.getBillReportList(filters);
		for(WoodBills bill :billList ){
			List<WoodBillsGoods> goodsList = bill.getGoodsList();;
			if(goodsList.size()>0){
				billReportList.addAll(setReportList(bill ,goodsList));
			}
		} 
		return billReportList;
	}
}
