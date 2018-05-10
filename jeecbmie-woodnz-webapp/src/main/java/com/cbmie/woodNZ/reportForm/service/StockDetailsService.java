package com.cbmie.woodNZ.reportForm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.system.utils.JXLExcel;
import com.cbmie.woodNZ.reportForm.dao.StockDetailsDao;
import com.cbmie.woodNZ.reportForm.entity.StockDetail;


@Service
@Transactional
public class StockDetailsService {

	@Autowired 
	private StockDetailsDao stockDetailsDao;
	
	public Map<String, Object> search(Map<String, Object> map) {
		return stockDetailsDao.getDataQuery(map);
	}
	
	
	/**
	 * @param response
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void excelLog(HttpServletResponse response, List<StockDetail> list) {
		JXLExcel jxl = new JXLExcel();
		// excel名称
		String excelName = "log.xls";
		// 设置标题
		String[] columnNames = new String[] { "商品名称","贸易类型","贸易方式","仓库","实物量单位","业务实存数量","币种",
				"库存合计","进价","运杂费","1个月以内","1-3个月","3-6个月","6个月以上"};
		jxl.setColumnNames(columnNames);
		// 设置属性名称
		String[] dbColumnNames = new String[] { "goodname", "tradetype", "tradeway", "warehouse", "unit", "realstock", "currency",
				"instockfee", "price", "transpot", "mouth","season","secondSeason","sixMouth" };
		jxl.setDbColumnNames(dbColumnNames);
		// 执行
		jxl.exportExcel(response, list, excelName);
	}
}
