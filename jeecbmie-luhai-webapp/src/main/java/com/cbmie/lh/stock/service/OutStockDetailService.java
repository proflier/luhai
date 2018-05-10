package com.cbmie.lh.stock.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.stock.dao.OutStockDetailDao;
import com.cbmie.lh.stock.entity.OutStockDetail;

/**
 * 出库 
 */
@Service
@Transactional
public class OutStockDetailService extends BaseService<OutStockDetail, Long> {

	@Autowired
	private OutStockDetailDao outStockDetailDao;
	
	@Override
	public HibernateDao<OutStockDetail, Long> getEntityDao() {
		return outStockDetailDao;
	}


	public void importExcel( MultipartFile multipartFile) throws IOException  {
					InputStream is = multipartFile.getInputStream();
					List<OutStockDetail> outStockDetailList= ReadExcel.importByPoi(is, 0);
					outStockDetailDao.delByOutStockId();
					for(OutStockDetail outStockDetail:outStockDetailList){
						save(outStockDetail);
					}
	}
	
}
