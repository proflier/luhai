package com.cbmie.woodNZ.importShips.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.importShips.dao.BookShipDao;
import com.cbmie.woodNZ.importShips.entity.BookShip;

/**
 * 定船合同审批service
 * @author linxiaopeng
 * 2016年7月5日
 */
@Service
@Transactional
public class BookShipService extends BaseService<BookShip, Long> {
	
	@Autowired
	private BookShipDao bookShipDao;

	@Override
	public HibernateDao<BookShip, Long> getEntityDao() {
		return bookShipDao;
	}
	
	public BookShip findByNo(BookShip bookShip) {
		return bookShipDao.findByNo(bookShip);
	}
}
