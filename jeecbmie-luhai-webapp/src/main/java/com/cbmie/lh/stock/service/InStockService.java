package com.cbmie.lh.stock.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ShipTraceDao;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.stock.dao.InStockDao;
import com.cbmie.lh.stock.dao.InStockGoodsDao;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.InStockGoods;
import com.cbmie.lh.stock.entity.StockHistory;

/**
 * 入库
 * 
 */
@Service
@Transactional
public class InStockService extends BaseService<InStock, Long> {

	@Autowired
	private InStockDao inStockDao;
	
	@Autowired
	private InStockGoodsDao inStockGoodsDao;
	
	@Override
	public HibernateDao<InStock, Long> getEntityDao() {
		return inStockDao;
	}

	public InStock findByNo(InStock inStock) {
		return inStockDao.findByNo(inStock);
	}
	
	public InStock findByBillId(InStock inStock) {
		return inStockDao.findByBillId(inStock);
	}
	
	public InStock findByBillId(String billNo,long id) {
		return inStockDao.findByBillId(billNo,id);
	}
	
	public InStock findByBillNo(String billNo) {
		return inStockDao.findUniqueBy("billNo", billNo);
	}
	

	public boolean checkGoods(Long id) {
		boolean returnValue = true;
		List<InStockGoods> inStockGoodsList = get(id).getInStockGoodsList();
		if(inStockGoodsList.size()>0){
			for(InStockGoods inStockGoods :inStockGoodsList ){
				if(StringUtils.isBlank(inStockGoods.getWarehouseName())){
					returnValue = false;
				}
			}
		}
		return returnValue;
	}

	public String importExcel( MultipartFile multipartFile) throws IOException, IllegalAccessException, InvocationTargetException  {
		InputStream is = multipartFile.getInputStream();
		List<InStock> inStockList= new ArrayList<InStock>();
		//船编号集合
		List<String > shipNos = inStockDao.getAllShipNo();
		//商品编码集合
		List<String > goodsNos = inStockDao.getAllGoodsNo();
		//仓储编码集合
		List<String > wareHouseNos = inStockDao.getAllWarehouseNo();
		Map<String , Object> returnMap = ReadExcel.historyImportByPoi(is, 0,shipNos,goodsNos,wareHouseNos);
		//获取excel错误数据消息
		String msg = returnMap.get("msg").toString();
		if(StringUtils.isBlank(msg)){
			List<StockHistory> stockHistorys = (List<StockHistory>) returnMap.get("list");
			inStockList = transToRealStock(stockHistorys);
			for(InStock inStock:inStockList){
				List<InStockGoods> inStockGoodsList = new ArrayList<InStockGoods>();
				inStockGoodsList = inStock.getInStockGoodsList();
				inStock.setInStockGoodsList(null);
				save(inStock);
				for(InStockGoods inStockGoods :inStockGoodsList){
					inStockGoods.setParentId(inStock.getId().toString());
					inStockGoodsDao.save(inStockGoods);
				}
			}
			return "success";
		}else{
			return msg;
		}
		
}

	private List<InStock> transToRealStock(List<StockHistory> stockHistorys) throws IllegalAccessException, InvocationTargetException {
		List<InStock> returnList = new ArrayList<InStock>();
		List<InStockGoods> inStockGoodsList = null;
		InStock inStock = null;
		InStockGoods inStockGoods = null;
		List<String > shipNos = new ArrayList<String >();
		for(StockHistory stockHistory : stockHistorys){
			if(!shipNos.contains(stockHistory.getShipNo())){
				shipNos.add(stockHistory.getShipNo());
			}
		}
		for(String shipNo : shipNos){
			inStock = new InStock();
			inStockGoodsList = new ArrayList<InStockGoods>();
			double numbers = 0;
			for(StockHistory stockHistory : stockHistorys){
				if(shipNo.equals(stockHistory.getShipNo())){
					inStockGoods = new InStockGoods();
					BeanUtils.copyProperties(inStockGoods, stockHistory);
					inStockGoodsList.add(inStockGoods);
					numbers = numbers + inStockGoods.getQuantity();
					BeanUtils.copyProperties(inStock, stockHistory);
				}
			}
			inStock.setInStockGoodsList(inStockGoodsList);
			inStock.setNumbers(numbers);
			returnList.add(inStock);
		}
		return returnList;
	}

	public List<LhBills> selectBillsNoRepeict(String billNo) {
		return inStockDao.selectBillsNoRepeict(billNo);
	}
}
