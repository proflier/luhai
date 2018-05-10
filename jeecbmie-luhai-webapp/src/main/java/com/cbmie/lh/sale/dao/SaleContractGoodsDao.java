package com.cbmie.lh.sale.dao;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
@Repository
public class SaleContractGoodsDao extends HibernateDao<SaleContractGoods, Long> {

	public void deleteEntityBySaleContractId(Long saleContractId){
		String hql = "delete from SaleContractGoods a where a.saleContractId=:saleContractId";
		this.createQuery(hql).setParameter("saleContractId", saleContractId).executeUpdate();
	}
	
	public void getSaleContractFGoodsFetchContract(Page<SaleContractGoods> page,Map<String,Object> queryParams){
		User currentUser = UserUtil.getCurrentUser();
		
		String sql = "select a.* from LH_SALE_CONTRACT_GOODS a join LH_SALE_CONTRACT b on a.SALECONTRACT_ID=b.id ";
				sql = sql + " where b.closed_Flag='0' and b.state='1' and b.change_state='1' ";//
				if(currentUser!=null&&currentUser.getId()!=1){
					sql = sql + " and ( b.createrno like'%"+currentUser.getLoginName()+"%' or b.rel_login_names like '%"+currentUser.getLoginName() +"%' ) ";//权限新增
				}
		if(!queryParams.isEmpty()){
			if(queryParams.containsKey("saleContractNo")){
				sql = sql+" and b.contract_No like '%"+queryParams.get("saleContractNo")+"%'";
			}
		}
		sql = sql+" order by contract_No desc";
		SQLQuery query = this.createSQLQuery(sql);
		page.setTotalCount(query.list().size());
		query.addEntity(SaleContractGoods.class);
		query.setFirstResult((page.getPageNo()-1)*page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setResult(query.list());
	}
}
