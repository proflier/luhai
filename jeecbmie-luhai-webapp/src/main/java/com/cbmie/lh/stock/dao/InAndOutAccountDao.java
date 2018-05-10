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
import com.cbmie.lh.stock.entity.InAndOutAccount;

/**
 * 进销存台账DAO
 */
@Repository
public class InAndOutAccountDao extends HibernateDao<InAndOutAccount, Long>{

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static StringBuffer sql = new StringBuffer();
	
	private static StringBuffer sqlSurplus = new StringBuffer();
	
	static {
		/*
		 * 	select * from (
		  select lh_in_stock_goods.id, 
					IFNULL(DATE_FORMAT( (
							if(lh_in_stock_goods.instock_category=0 or lh_in_stock_goods.instock_category=3,lh_in_stock.in_stock_date,#原始
							if(lh_in_stock_goods.instock_category=2,(select lh_inventory_stock.inventory_date from lh_inventory_stock where lh_inventory_stock.new_stock_goods_id=lh_in_stock_goods.id ),#盘库
							if(lh_in_stock_goods.instock_category=1 and lh_in_stock_goods.quantity>0,(select lh_stock_allocation.in_time from lh_stock_allocation where lh_stock_allocation.stock_add_id=lh_in_stock_goods.id),#调拨 调入
							if(lh_in_stock_goods.instock_category=1 and lh_in_stock_goods.quantity<0,(select lh_stock_allocation.out_time from lh_stock_allocation where lh_stock_allocation.stock_minus_id=lh_in_stock_goods.id),null))))
					) ,'%Y-%m-%d'),'') as date ,
				(select customer_name from wood_affi_base_info where customer_code=lh_in_stock_goods.warehouse_name) as warehouse_name, 
				lh_in_stock_goods.instock_category as instock_category,
				IFNULL(lh_in_stock.ship_no,'') as ship_no,
				(select info_name from lh_goodsinformation where info_code=lh_in_stock_goods.goods_name) as goods_name,
				if(lh_in_stock_goods.quantity>0,lh_in_stock_goods.quantity,0) as inStockQuantity,
				if(lh_in_stock_goods.quantity<0,lh_in_stock_goods.quantity*-1,0) as outStockQuantity,
				0 as surplusQuantity  
		 from lh_in_stock_goods  join lh_in_stock  on lh_in_stock_goods.parent_id= lh_in_stock.id  where lh_in_stock.confirm='1' 
//		 GROUP BY date,a.warehouse_name,a.goods_name,a.instock_category ,ship_no
		 union ALL 
		 select o.id, IFNULL(DATE_FORMAT(o.updaterdate ,'%Y-%m-%d'),'')  as date ,
		 (select customer_name from wood_affi_base_info where customer_code=o.port) as warehouse_name,4 as instock_category,IFNULL(o.voy,'') as ship_no, 
		(select info_name from lh_goodsinformation where info_code=o.goods_name)as goods_name,
		 0 as inStockQuantity, o.quantity as outStockQuantity,0 as surplusQuantity 
		 from lh_sale_delivery_goods o join lh_out_stock d on d.id=o.out_stock_id where o.out_stock_id is not null and d.confirm='1' 
		) as total
		 where 1=1 
		 * */
		sql.append("select * from (");
		sql.append(" select lh_in_stock_goods.id,");
		sql.append(" IFNULL(DATE_FORMAT(( ");
		sql.append(" if(lh_in_stock_goods.instock_category=0 or lh_in_stock_goods.instock_category=3,lh_in_stock.in_stock_date, ");
		sql.append(" if(lh_in_stock_goods.instock_category=2,(select lh_inventory_stock.inventory_date from lh_inventory_stock where lh_inventory_stock.new_stock_goods_id=lh_in_stock_goods.id ), ");
		sql.append(" if(lh_in_stock_goods.instock_category=1 and lh_in_stock_goods.quantity>0,(select lh_stock_allocation.in_time from lh_stock_allocation where lh_stock_allocation.stock_add_id=lh_in_stock_goods.id), ");
		sql.append(" if(lh_in_stock_goods.instock_category=1 and lh_in_stock_goods.quantity<0,(select lh_stock_allocation.out_time from lh_stock_allocation where lh_stock_allocation.stock_minus_id=lh_in_stock_goods.id),null)))) ");
		sql.append(" ) ,'%Y-%m-%d'),'') as date , ");
		sql.append(" (select customer_name from wood_affi_base_info where customer_code=lh_in_stock_goods.warehouse_name) as warehouse_name, lh_in_stock_goods.instock_category as instock_category,IFNULL(lh_in_stock.ship_no,'') as ship_no,");
		sql.append(" (select info_name from lh_goodsinformation where info_code=lh_in_stock_goods.goods_name) as goods_name,");
		sql.append(" if(lh_in_stock_goods.quantity>0,lh_in_stock_goods.quantity,0) as inStockQuantity,if(lh_in_stock_goods.quantity<0,lh_in_stock_goods.quantity*-1,0) as outStockQuantity,0 as surplusQuantity  ");
		sql.append(" from lh_in_stock_goods  join lh_in_stock  on lh_in_stock_goods.parent_id= lh_in_stock.id  where lh_in_stock.confirm='1' ");
//		sql.append(" GROUP BY date,a.warehouse_name,a.goods_name,a.instock_category,ship_no ");
		sql.append(" union ALL ");
		sql.append(" select o.id, IFNULL(DATE_FORMAT(d.out_stock_date ,'%Y-%m-%d'),'')  as date ,");
		sql.append(" (select customer_name from wood_affi_base_info where customer_code=o.port) as warehouse_name,4 as instock_category,IFNULL(o.voy,'') as ship_no, ");
		sql.append("(select info_name from lh_goodsinformation where info_code=o.goods_name)as goods_name,");
		sql.append(" 0 as inStockQuantity, o.quantity as outStockQuantity,0 as surplusQuantity ");
		sql.append(" from lh_sale_delivery_goods o join lh_out_stock d on d.id=o.out_stock_id where o.out_stock_id is not null and d.confirm='1' ");
		sql.append(") as total");
		sql.append(" where 1=1 ");
		sql.append(" and ship_no like :shipNo ");
		sql.append(" and warehouse_name like :warehouseName ");
		sql.append(" and goods_name like :goodsName ");
		sql.append(" and instock_category like :category ");
		sql.append(" and date >= :startTime ");
		sql.append(" and date<=  :endTime ");
		sql.append(" ORDER BY warehouse_name,date ");
		
		sqlSurplus.append("select (sum(total.inStockQuantity) - sum(total.outStockQuantity)) from (");
		sqlSurplus.append(" select a.id, IFNULL(DATE_FORMAT(a.updaterdate ,'%Y-%m-%d'),'')  as date ,");
		sqlSurplus.append(" (select customer_name from wood_affi_base_info where customer_code=a.warehouse_name) as warehouse_name, a.instock_category as instock_category,IFNULL(b.ship_no,'') as ship_no,");
		sqlSurplus.append(" (select info_name from lh_goodsinformation where info_code=a.goods_name) as goods_name,");
		sqlSurplus.append(" if(a.quantity>0,a.quantity,0) as inStockQuantity,if(a.quantity<0,a.quantity*-1,0) as outStockQuantity,0 as surplusQuantity  ");
		sqlSurplus.append(" from lh_in_stock_goods a join lh_in_stock b on a.parent_id= b.id  where b.confirm='1' ");
		sqlSurplus.append(" union ALL ");
		sqlSurplus.append(" select o.id, IFNULL(DATE_FORMAT(o.updaterdate ,'%Y-%m-%d'),'')  as date ,");
		sqlSurplus.append(" (select customer_name from wood_affi_base_info where customer_code=o.port) as warehouse_name,4 as instock_category,IFNULL(o.voy,'') as ship_no, ");
		sqlSurplus.append("(select info_name from lh_goodsinformation where info_code=o.goods_name)as goods_name,");
		sqlSurplus.append(" 0 as inStockQuantity, o.quantity as outStockQuantity,0 as surplusQuantity ");
		sqlSurplus.append(" from lh_sale_delivery_goods o join lh_out_stock d on d.id=o.out_stock_id where o.out_stock_id is not null and d.confirm='1' ");
		sqlSurplus.append(") as total");
		sqlSurplus.append(" where 1=1 ");
		sqlSurplus.append(" and warehouse_name = :warehouseName ");
		sqlSurplus.append(" and goods_name like :goodsName ");
		sqlSurplus.append(" and instock_category like :category ");
		sqlSurplus.append(" and date >= :startTime ");
		sqlSurplus.append(" and date<=  :endTime ");
		sqlSurplus.append(" GROUP BY warehouse_name ");
	}
	
	public Map<String, Object> getDataByParams(Map<String, Object> map) {
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
		
		String shipNo = map.containsKey("shipNo") ? "%" + map.get("shipNo") + "%" : "%";
		String warehouseName = map.containsKey("warehouseName") ? "%" + map.get("warehouseName") + "%" : "%";
		String goodsName = map.containsKey("goodsName") ? "%" + map.get("goodsName") + "%" : "%";
		String category = map.containsKey("category") ? "%" + map.get("category") + "%" : "%";
		String	startTime = map.containsKey("startTime")?map.get("startTime").toString():"0";
		String	endTime = map.containsKey("endTime")?map.get("endTime").toString():"9999-12-31";
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		sqlQuery.setParameter("shipNo", shipNo);
		sqlQuery.setParameter("warehouseName", warehouseName);
		sqlQuery.setParameter("goodsName", goodsName);
		sqlQuery.setParameter("category", category);
		sqlQuery.setParameter("startTime", startTime);
		sqlQuery.setParameter("endTime", endTime);
		
		sqlQuery.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			//日期
			row_.put("date", obj_array[1].toString());
			//仓库
			row_.put("warehouse", obj_array[2].toString());
			//单据类别
			row_.put("category", obj_array[3].toString());
			//船次
			row_.put("voyage", obj_array[4].toString());
			//物料名称
			row_.put("goodsName", obj_array[5].toString());
			//入库吨数
			row_.put("inStockQuantity", obj_array[6].toString());
			//出库吨数
			row_.put("outStockQuantity", obj_array[7].toString());
			//结余吨数
			row_.put("surplusQuantity", obj_array[8].toString());
			data_rows.add(row_);
		}
		
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", data_rows);
		data.put("total", Integer.parseInt(getCountQuery(shipNo,warehouseName,goodsName,category,startTime,endTime).uniqueResult().toString()));
		return data;
	}
	
	private SQLQuery getCountQuery(String shipNo ,String warehouseName ,String goodsName,String category,String startTime,String endTime) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_count = new StringBuffer("");
		sql_count.append("select count(*) from (");
		sql_count.append(sql);
		sql_count.append(") as abc");
		SQLQuery query_count = session.createSQLQuery(sql_count.toString());
		query_count.setParameter("shipNo", shipNo);
		query_count.setParameter("warehouseName", warehouseName);
		query_count.setParameter("goodsName", goodsName);
		query_count.setParameter("category", category);
		query_count.setParameter("startTime", startTime);
		query_count.setParameter("endTime", endTime);
		return query_count;
	}
	
	public Double getSurplusQuantity(Map<String, Object> map){
		String warehouseName = map.get("warehouseName").toString();
		String goodsName = map.containsKey("goodsName") ? "%" + map.get("goodsName") + "%" : "%";
		String category = map.containsKey("category") ? "%" + map.get("category") + "%" : "%";
		String	startTime = map.containsKey("startTime")?map.get("startTime").toString():"0";
		String	endTime = map.containsKey("endTime")?map.get("endTime").toString():"9999-12-31";
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlSurplus.toString());
		
		sqlQuery.setParameter("warehouseName", warehouseName);
		sqlQuery.setParameter("goodsName", goodsName);
		sqlQuery.setParameter("category", category);
		sqlQuery.setParameter("startTime", startTime);
		sqlQuery.setParameter("endTime", endTime);
		
		return (Double) sqlQuery.uniqueResult();
	}
	
}
