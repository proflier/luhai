package com.cbmie.woodNZ.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.logistics.dao.DownstreamContainerDao;
import com.cbmie.woodNZ.logistics.entity.DownstreamContainer;


/**
 * 下游到单子表- 箱单service
 */
@Service
@Transactional
public class DownstreamContainerService extends BaseService<DownstreamContainer, Long> {

	@Autowired 
	private DownstreamContainerDao downstreamContainerDao;

	@Override
	public HibernateDao<DownstreamContainer, Long> getEntityDao() {
		return downstreamContainerDao;
	}

	/**
	 * @param id
	 * @return
	 */
	public List<DownstreamContainer> getByParentId(String id) {
		return downstreamContainerDao.getByParentId(id);
	}

	/**
	 * 
	 * @param downstreamContainerList
	 */
	public void saveList(List<DownstreamContainer> downstreamContainerList,String parentId) {
		for(DownstreamContainer downstreamContainer :downstreamContainerList){
			downstreamContainer.setId(null);
			downstreamContainer.setParentId(parentId);
			downstreamContainerDao.save(downstreamContainer);
		}
	}

	/**
	 * @param parentId
	 */
	public void deleteByParentId(String parentId) {
		downstreamContainerDao.deleteByParentId(parentId);
	}

	/**
	 * @param downstreamContainer
	 * @param cpContractNo
	 * @param spbm
	 * @param cgdj
	 */
	public void setCGDJ(DownstreamContainer downstreamContainer, String cpContractNo, String spbm, String cgdj) {
		String spbmBegin = spbm.substring(0, 8);
		if(cpContractNo.equals(downstreamContainer.getCpContractNo())&&
				spbmBegin.equals(downstreamContainer.getGoodsNo().substring(0, 8))){
			downstreamContainer.setPurchasePrice(Double.valueOf(cgdj));
			downstreamContainer.setTotalPrice(Double.valueOf(cgdj)*downstreamContainer.getNum());
			downstreamContainerDao.save(downstreamContainer);
		}
	}
}
