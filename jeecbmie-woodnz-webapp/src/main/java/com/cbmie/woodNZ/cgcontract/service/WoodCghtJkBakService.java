package com.cbmie.woodNZ.cgcontract.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.cgcontract.dao.WoodCghtJkBakDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkBak;

/**
 * 采购合同-进口-记录 service
 */
@Service
@Transactional
public class WoodCghtJkBakService extends BaseService<WoodCghtJkBak, Long> {
	
	@Autowired
	private WoodCghtJkBakDao woodCghtJkBakDao;

	@Override
	public HibernateDao<WoodCghtJkBak, Long> getEntityDao() {
		return woodCghtJkBakDao;
	}
	
	public List<WoodCghtJkBak> findAll(Long id){
		List<WoodCghtJkBak> woodCghtJkBakList = woodCghtJkBakDao.findAll();
		List<WoodCghtJkBak> returnWoodCghtJkBakList = new ArrayList<WoodCghtJkBak>();
		if(woodCghtJkBakList.size()>0){
			Iterator<WoodCghtJkBak> it = woodCghtJkBakList.iterator();
			while (it.hasNext()) {
				WoodCghtJkBak woodCghtJkBak = (WoodCghtJkBak) it.next();
				if(woodCghtJkBak.getChangeRelativeId()!=null&&String.valueOf(id).equals(woodCghtJkBak.getChangeRelativeId())){
					returnWoodCghtJkBakList.add(woodCghtJkBak);
				}
			}
		}
		return returnWoodCghtJkBakList;
	}
	
}
