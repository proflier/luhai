package com.cbmie.woodNZ.reportForm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.baseinfo.dao.AffiBaseInfoDao;
import com.cbmie.system.utils.JXLExcel;
import com.cbmie.baseinfo.dao.GkDao;
import com.cbmie.woodNZ.baseInfo.dao.GoodsInfoDao;
import com.cbmie.woodNZ.reportForm.dao.DownstreamReportDao;
import com.cbmie.woodNZ.reportForm.entity.DownstreamReport;

/**
 * 下游到单 log service
 */
@Service
@Transactional
public class DownstreamReportService extends BaseService<DownstreamReport, Long> {

	@Autowired
	private DownstreamReportDao downstreamReportDao;
	
	@Autowired
	private GoodsInfoDao goodsInfoDao;
	
	@Autowired
	private AffiBaseInfoDao affiBaseInfoDao;
	
	@Autowired
	private GkDao woodGkDao;

	@Override
	public HibernateDao<DownstreamReport, Long> getEntityDao() {
		return downstreamReportDao;
	}

	/**
	 * @param response
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void excelLog(HttpServletResponse response, List<DownstreamReport> list) {
		JXLExcel jxl = new JXLExcel();
		// excel名称
		String excelName = "log.xls";
		// 设置标题
		String[] columnNames = new String[] { "采购合同号","综合合同号","销售合同号","到单号"," 上游发票号", "快递号","下游发票号","商品名称","区域","币种","包干费单价",
				"发票金额","免箱期天数","数量单位","数量","我司单位","目的港","供应商","二次换单提单号","下游客户","登记时间"
				};
		jxl.setColumnNames(columnNames);
		// 设置属性名称
		String[] dbColumnNames = new String[] { "cghth","cpContractNo","saleContarteNo","billNo","invoiceNo","expressNo","downInvoiceNo",
				"goodName","area","currency","forwarderPrice","invoiceAmount","noBoxDay","numberUnits","numbers","ourUnit","portName",
				"supplier","twoTimeBillNo","downStreamClient","createDate"};
		jxl.setDbColumnNames(dbColumnNames);
		// 执行
		jxl.exportExcel(response, list, excelName);
	}

	public Map<String, Object> search(Map<String, Object> map) {
		return downstreamReportDao.getDataQuery(map);
	}
	
	

}
