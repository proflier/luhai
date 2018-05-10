package com.cbmie.woodNZ.stock.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.woodNZ.stock.entity.StockStatistic;

@Repository
public class StockStatisticDao extends HibernateDao<StockStatistic, Long>{

	/*public Page<Map> search(Page page,List<PropertyFilter> filters){
		page.getResult().clear();
		StringBuffer sql = new StringBuffer();
		sql.append(" select e.warehouse,e.goodno,e.goodname,e.ruku,e.chuku,e.shengyu,f.totalshengyu from                                                                         ");
		sql.append(" (select c.warehouse,c.goodno,c.goodname,ifnull(c.realnumber,0) ruku,ifnull(d.realnumber,0) chuku,ifnull(c.realnumber,0)-ifnull(d.realnumber,0) shengyu from ");
		sql.append(" (select a.receive_Warehouse warehouse,b.goods_No goodno,max(b.goods_Name) goodname,sum(ifnull(b.real_Number,0)) realnumber                                  ");
		sql.append("  from wood_in_stock a  join wood_in_stock_goods b on a.id=b.PARENT_ID                                                                                       ");
		sql.append(" group by a.receive_Warehouse,b.goods_No) c                                                                                                                  ");
		sql.append(" left join                                                                                                                                                   ");
		sql.append(" (select a.warehouse warehouse,b.goods_No goodno,max(b.goods_Name) goodname,sum(ifnull(b.realsendPNum,0)) realnumber                                         ");
		sql.append("  from wood_out_stock a  join wood_out_stock_sub b on a.id=b.PARENT_ID                                                                                       ");
		sql.append(" group by a.warehouse,b.goods_No) d on (c.warehouse=d.warehouse and c.goodno=d.goodno)) e                                                                    ");
		sql.append(" left join                                                                                                                                                   ");
		sql.append(" (select c.warehouse,sum(ifnull(c.realnumber,0)-ifnull(d.realnumber,0)) totalshengyu from                                                                    ");
		sql.append(" (select a.receive_Warehouse warehouse,b.goods_No goodno,max(b.goods_Name) goodname,sum(real_Number) realnumber                                              ");
		sql.append("  from wood_in_stock a  join wood_in_stock_goods b on a.id=b.PARENT_ID                                                                                       ");
		sql.append(" group by a.receive_Warehouse,b.goods_No) c                                                                                                                  ");
		sql.append(" left join                                                                                                                                                   ");
		sql.append(" (select a.warehouse warehouse,b.goods_No goodno,max(b.goods_Name) goodname,sum(realsendPNum) realnumber                                                     ");
		sql.append("  from wood_out_stock a  join wood_out_stock_sub b on a.id=b.PARENT_ID                                                                                       ");
		sql.append(" group by a.warehouse,b.goods_No) d on (c.warehouse=d.warehouse and c.goodno=d.goodno)                                                                       ");
		sql.append(" group by c.warehouse) f on e.warehouse=f.warehouse                                                                                                          ");
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setFirstResult((page.getPageNo()-1)*page.getPageSize());
		sqlQuery.setMaxResults(page.getPageSize());
		List<Object[]> list = sqlQuery.list();
		for(Object[] objs:list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("warehouse", getValueFromArray(objs[0]));
			map.put("goodno", getValueFromArray(objs[1]));
			map.put("goodname", getValueFromArray(objs[2]));
			map.put("ruku", getValueFromArray(objs[3]));
			map.put("chuku", getValueFromArray(objs[4]));
			map.put("shengyu", getValueFromArray(objs[5]));
			map.put("totalshengyu", getValueFromArray(objs[6]));
			page.getResult().add(map);
		}
		return page;
	}
	private String getValueFromArray(Object obj){
		return obj==null?null:obj.toString();
	}*/
	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<StockStatistic> findPage(final Page<StockStatistic> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);
		Page<StockStatistic> result_page = findPage(page, criterions);
		fillTransientData(result_page.getResult(),filters);
		return result_page;
	}
	
	private void fillTransientData(List<StockStatistic> list,List<PropertyFilter> filters){
		Criterion[] criterions2 = buildCriterionByPropertyFilter(filters);
		if(list!=null && list.size()>0){
			Map<Long,Double> stock_records = new HashMap<Long,Double>();
			for(StockStatistic stock : list){
				Long wareHouseId =  stock.getWarehouse();
				Double storage = stock.getAmount();
				if(stock_records.containsKey(wareHouseId)){
				}else{
					Criteria c2 = createCriteria(criterions2);
					Criterion temp = Restrictions.eq("warehouse", wareHouseId);
					c2.add(temp);
					List<StockStatistic> list2 = c2.list();
					Double total=0.0;
					for(StockStatistic t : list2){
						total = t.getAmount()+total;
					}
					stock_records.put(wareHouseId, total);
				}
			}
			for(StockStatistic stock : list){
				Long wareHouseId =  stock.getWarehouse();
				stock.setTotalAmount(stock_records.get(wareHouseId));
			}
		}
	}
}
