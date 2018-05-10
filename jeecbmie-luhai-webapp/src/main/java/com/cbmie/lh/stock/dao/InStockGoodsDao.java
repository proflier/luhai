package com.cbmie.lh.stock.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
import com.cbmie.lh.stock.entity.InStockGoods;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 入库商品
 */
@Repository
public class InStockGoodsDao extends HibernateDao<InStockGoods, Long>{

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static StringBuffer sql = new StringBuffer();
	static {
		/*
		 * 	select id ,goods_name,IFNULL((select info_name from lh_goodsinformation where info_code=goods_name),'')as goods_name_text,
				IFNULL(warehouse_name,'') as warehouse_name, IFNULL((select customer_name from wood_affi_base_info where customer_code=warehouse_name),'') as warehouse_name_text,
				instock_category, SUM(quantity) as quantity , IFNULL(units,'') from (
				select a.id as id ,a.goods_name as goods_name,a.warehouse_name as warehouse_name,a.instock_category as instock_category,a.quantity  as quantity,  a.units as units
							from lh_in_stock_goods a join lh_in_stock i on i.id = a.parent_id  where i.confirm ='1'
				UNION ALL
				SELECT o.id as id, o.goods_name as goods_name,o.port as warehouse_name,3 as instock_category,o.quantity*-1 as quantity ,o.units as units from  lh_sale_delivery_goods o join lh_out_stock b on b.id = o.out_stock_id where o.out_stock_id is not null and b.confirm='1'  
				) as total
			GROUP BY goods_name,warehouse_name
		 * */
		sql.append(" select id ,goods_name,IFNULL((select info_name from lh_goodsinformation where info_code=goods_name),'') as goods_name_text,IFNULL(warehouse_name,'') as warehouse_name,IFNULL((select customer_name from wood_affi_base_info where customer_code=warehouse_name),'') as warehouse_name_text,instock_category, SUM(quantity) as quantity , IFNULL(units,'') from ( ");
		sql.append(" select a.id as id ,a.goods_name as goods_name,a.warehouse_name as warehouse_name,a.instock_category as instock_category,a.quantity  as quantity , a.units as units from lh_in_stock_goods a ");
		sql.append(" join lh_in_stock i on i.id = a.parent_id  where i.confirm ='1' ");
		sql.append(" UNION ALL ");
		sql.append(" SELECT o.id as id, o.goods_name as goods_name,o.port as warehouse_name,3 as instock_category,o.quantity*-1 as quantity ,o.units as units from  lh_sale_delivery_goods o  join lh_out_stock b on b.id = o.out_stock_id where o.out_stock_id is not null and b.confirm='1'  ) as total ");
		sql.append(" where 1=1  ");
		sql.append(" and warehouse_name like :warehouseName ");
		sql.append(" and goods_name like :goodsName ");
		sql.append(" GROUP BY goods_name,warehouse_name ");
		
	}
	
	public Map<String, Object> getDataByParams(Map<String, Object> map) {
		int pageSize = map.containsKey("pageSize") ?Integer.parseInt(map.get("pageSize").toString()):-1;
		int pageIndex = map.containsKey("pageIndex") ?Integer.parseInt(map.get("pageIndex").toString()):0;
		
		String warehouseName = map.containsKey("warehouseName") ? "%" + map.get("warehouseName") + "%" : "%";
		String goodsName = map.containsKey("goodsName") ? "%" + map.get("goodsName") + "%" : "%";
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		sqlQuery.setParameter("warehouseName", warehouseName);
		sqlQuery.setParameter("goodsName", goodsName);
		
		sqlQuery.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			//商品编码、名称
			row_.put("goodsName", obj_array[1].toString());
			row_.put("goodsNameText", obj_array[2].toString());
			//仓库编码、名称
			row_.put("warehouseName", obj_array[3].toString());
			row_.put("warehouseNameText", obj_array[4].toString());
			//入库类型
			row_.put("instockCategory", obj_array[5].toString());
			//数量
			row_.put("quantity", obj_array[6].toString());
			//数量
			row_.put("units", obj_array[7].toString());
			data_rows.add(row_);
		}
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", data_rows);
		data.put("total", Integer.parseInt(getCountQuery(warehouseName,goodsName).uniqueResult().toString()));
		return data;
	}
	
	private SQLQuery getCountQuery(String warehouseName ,String goodsName) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_count = new StringBuffer("");
		sql_count.append("select count(*) from (");
		sql_count.append(sql);
		sql_count.append(") as abc");
		SQLQuery query_count = session.createSQLQuery(sql_count.toString());
		query_count.setParameter("warehouseName", warehouseName);
		query_count.setParameter("goodsName", goodsName);
		return query_count;
	}

	public List<LhBillsGoods> getByBillNo(String billNo) {
		String sql = "SELECT * FROM LH_BILLS WHERE bill_no='"+billNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(LhBills.class);
		List<LhBillsGoods> goodsList = ((LhBills) sqlQuery.list().get(0)).getGoodsList();
		return goodsList;
	}

	public Integer deleteByparentId(String parentId) {
//		String sql = "delete from lh_in_stock_goods a where a.parentId=:parentId";
//		this.createQuery(sql).setParameter("parentId", parentId).executeUpdate();
		String sql = "delete from lh_in_stock_goods where parent_id=?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, parentId);
		return sqlQuery.executeUpdate();
	}

	
	public List<Object[]> getCurrentStockGoods(String goodsName,String warehouseName){
		String sql = "select * from v_current_stock_goods where goods_name like ? and warehouse_name like ? ";
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser.getId()!=1){
			sql = sql + " and rel_login_names like ? ";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, goodsName);
		sqlQuery.setParameter(1, warehouseName);
		if(currentUser.getId()!=1){
			sqlQuery.setParameter(2, "%"+currentUser.getLoginName()+"%");
		}
		return sqlQuery.list();
	}

	public List<Object[]> getAllByDeliveryNo(String deliveryNo) {
		String sql = "select * from v_current_stock_goods where goods_name in (select lh_sale_delivery_goods.goods_name from lh_sale_delivery_goods join lh_sale_delivery on lh_sale_delivery.id = lh_sale_delivery_goods.sale_delivery_id where lh_sale_delivery.delivery_release_no =?) ";
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser.getId()!=1){
			sql = sql + " and rel_login_names like ? ";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, deliveryNo);
		if(currentUser.getId()!=1){
			sqlQuery.setParameter(1, "%"+currentUser.getLoginName()+"%");
		}
		return sqlQuery.list();
	}

	public List<Object[]> getAllBySaleNo(String saleNo) {
		String sql = "select * from v_current_stock_goods where goods_name in (select lh_sale_contract_goods.goods_name from lh_sale_contract_goods join lh_sale_contract on lh_sale_contract.id =lh_sale_contract_goods.salecontract_id where lh_sale_contract.contract_no =?) ";
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser.getId()!=1){
			sql = sql + " and rel_login_names like ? ";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, saleNo);
		if(currentUser.getId()!=1){
			sqlQuery.setParameter(1, "%"+currentUser.getLoginName()+"%");
		}
		return sqlQuery.list();
	}
}
