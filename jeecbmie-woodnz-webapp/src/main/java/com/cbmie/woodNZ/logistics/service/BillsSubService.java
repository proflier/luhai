package com.cbmie.woodNZ.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.logistics.dao.BillsSubDao;
import com.cbmie.woodNZ.logistics.entity.WoodBillsSub;


/**
 * 到单子表- 箱单service
 */
@Service
@Transactional(readOnly = true)
public class BillsSubService extends BaseService<WoodBillsSub, Long> {

	@Autowired 
	private BillsSubDao billsSubDao;

	@Override
	public HibernateDao<WoodBillsSub, Long> getEntityDao() {
		return billsSubDao;
	}

	/**
	 * @param id
	 * @return
	 */
	public List<WoodBillsSub> getByParentId(Long id) {
		return billsSubDao.getByParentId(id);
	}

	
//	/**
//	 * 
//	 * @param outStock
//	 * @param outStockJson
//	 */
//	public void save(WoodBills bills, WoodBillsSub sub) {
//		// 转成标准的json字符串
//		create(bills,sub);
//		//delete(bills,outStockSubList);
//	}
//	
//
//	public void create(WoodBills bills,WoodBillsSub sub){//新增或修改	
//			if(outStockSub.getId() == null){//新增
//				User currentUser = UserUtil.getCurrentUser();
//				outStockSub.setParentId(outStock.getId());
//				outStockSub.setCreaterNo(currentUser.getLoginName());
//				outStockSub.setCreaterName(currentUser.getName());
//				outStockSub.setCreateDate(new Date());
//			}else if(outStockSub.getId() != null){//修改
//				User currentUser = UserUtil.getCurrentUser();
//				outStockSub.setUpdaterNo(currentUser.getLoginName());
//				outStockSub.setUpdaterName(currentUser.getName());
//			}
//			outstockSubDao.save(outStockSub);	
//	}
//	
//	
//	public void delete(OutStock outStock,List<OutStockSub> outStockSubList){//删除
//		List<OutStockSub> dataSubList = outStock.getSubList();//修改前的数据
//        List<OutStockSub> deleteSubList = new ArrayList<OutStockSub>();//保存要删除的数据
//		for(OutStockSub sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
//			Long dataId = sub.getId();
//			Boolean result = false;
//			for(int i=0;i<outStockSubList.size();i++){
//				if(outStockSubList.get(i).getId() == dataId){
//					result  =true;
//				}
//			}
//			if(result == false){
//				OutStockSub aa = outstockSubDao.find(dataId);
//				deleteSubList.add(aa);
//			}
//		}
//		for(OutStockSub s:deleteSubList){//删除对应数据
//			outStock.getSubList().remove(s);
//			s.setParentId(null);
//			outstockSubDao.delete(s);
//		}
//	}
}
