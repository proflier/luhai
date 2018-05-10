package com.cbmie.woodNZ.report.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbmie.woodNZ.report.entity.ProfitDetails;

@Repository
public class ProfitDetailsDao {
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static StringBuffer sql = new StringBuffer();
	static {
		sql.append("select substring(a.goods_no, 1, 2) as goodType,substring(a.goods_no, 1, 2) as goodName,a.cp_contract_no as contract,");
		sql.append("b.htlb as tradeType,'' as tradeWay,a.number_units as unit,sum(c.s_amount) as salesQuantity,a.currency as currency,");
		sql.append("sum(c.s_money) as income,sum(a.total_price) as price,0 as transpot,sum(c.s_money) - sum(a.total_price) as profit ");
		sql.append("from wood_in_stock_goods as a ");
		sql.append("join wood_cght_jk b on b.inter_contract_no = a.cp_contract_no ");
		sql.append("join (select outsub.cp_contract_no,sum(amount) as s_amount,sum(outsub.money) as s_money from wood_out_stock_sub outsub group by outsub.cp_contract_no) c on c.cp_contract_no = a.cp_contract_no ");
		sql.append("where 1=1 ");
		sql.append("and length(a.goods_no) = 13 ");
		sql.append("and substring(a.goods_no, 1, 2) <> 99 ");
		sql.append("and a.cp_contract_no <> '' ");
		sql.append("and a.cp_contract_no like :interContractNo ");
		sql.append("group by a.cp_contract_no,substring(a.goods_no, 1, 2),b.htlb,a.currency,a.number_units ");
	}
	
	public Map<String, Object> getDataQuery(Map<String, Object> map) {
		String sort = map.get("sort") == null ? "" : map.get("sort").toString();
		String order = map.get("order") == null ? "" : map.get("order").toString();
		int page = Integer.parseInt(map.get("page").toString());
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		int pageNumber = Integer.parseInt(map.get("pageNumber").toString());
		String orderby = map.get("orderby") == null ? "" : map.get("orderby").toString();
		int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
		int rows = Integer.parseInt(map.get("rows").toString());
		
		String interContractNo = map.containsKey("interContractNo") ? "%" + map.get("interContractNo") + "%" : "%";
		
//		if(StringUtils.isNotEmpty(orderby)) {
//			sql.append("order by " + orderby + " ");
//		}
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("interContractNo", interContractNo);
		sqlQuery.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//商品类别
			row_.put("goodType", obj_array[0].toString());
			//商品名称
			row_.put("goodName", obj_array[1].toString());
			//综合合同号
			row_.put("contract", obj_array[2].toString());
			//贸易类型
			row_.put("tradeType", obj_array[3].toString());
			//贸易方式
			row_.put("tradeWay", obj_array[4].toString());
			//数量单位
			row_.put("unit", obj_array[5].toString());
			//销售数量
			row_.put("salesQuantity", obj_array[6].toString());
			//币种
			row_.put("currency", obj_array[7].toString());
			//主营业务收入（销售价）
			row_.put("income", obj_array[8].toString());
			//进价
			row_.put("price", obj_array[9].toString());
			//运杂费
			row_.put("transpot", obj_array[10].toString());
			//商品利润
			row_.put("profit", obj_array[11].toString());
			data_rows.add(row_);
		}
		
		
		List<Map<String, Object>> data_footer = new ArrayList<Map<String, Object>>();
		Map<String, Object> data_footer_row1 = getTotal();
		data_footer_row1.put("goodType", "合计：");
//		data_footer_row1.put("salesQuantity", 100);
//		data_footer_row1.put("income", 200);
//		data_footer_row1.put("price", 300);
//		data_footer_row1.put("transpot", 400);
//		data_footer_row1.put("profit", 500);
//		Map<String, Object> data_footer_row2 = new HashMap<String, Object>();
//		data_footer_row2.put("goodType", "合计：");
//		data_footer_row2.put("salesQuantity", 100);
//		data_footer_row2.put("income", 200);
//		data_footer_row2.put("price", 300);
//		data_footer_row2.put("transpot", 400);
//		data_footer_row2.put("profit", 500);
		data_footer.add(data_footer_row1);
//		data_footer.add(data_footer_row2);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", data_rows);
		data.put("total", Integer.parseInt(getCountQuery().setParameter("interContractNo", interContractNo).uniqueResult().toString()));
		data.put("footer", data_footer);
		return data;
	}
	
	private SQLQuery getCountQuery() {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_count = new StringBuffer("");
		sql_count.append("select count(*) from (");
		sql_count.append(sql);
		sql_count.append(") as abc");
		SQLQuery query_count = session.createSQLQuery(sql_count.toString());
//		int count = Integer.parseInt(query_count.uniqueResult().toString());
		return query_count;
	}
	
	public Map<String, Object> getTotal() {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_total = new StringBuffer("");
		sql_total.append("select cast(sum(salesQuantity) AS decimal(15,2)) as salesQuantity,");
		sql_total.append("cast(sum(income) AS decimal(15,2)) as income,");
		sql_total.append("cast(sum(price) AS decimal(15,2)) as price,");
		sql_total.append("cast(sum(transpot) AS decimal(15,2)) as transpot,");
		sql_total.append("cast(sum(profit) AS decimal(15,2)) as profit ");
		sql_total.append("from (");
		sql_total.append(sql);
		sql_total.append(") as abc");
		SQLQuery query_total = session.createSQLQuery(sql_total.toString());
		query_total.setParameter("interContractNo", "%");
		
		Map<String, Object> row_ = new HashMap<String, Object>();
		for(Object obj : query_total.list()) {
			Object[] obj_array = (Object[])obj;
			row_.put("salesQuantity", obj_array[0]);
			row_.put("income", obj_array[1]);
			row_.put("price", obj_array[2]);
			row_.put("transpot", obj_array[3]);
			row_.put("profit", obj_array[4]);
		}
		return row_;
	}
	
	/**
	 * 用于测试
	 * @return
	 */
	public Map<String, Object> testNamedNativeQuery() {
		Session session = sessionFactory.getCurrentSession();
		List<ProfitDetails> list = session.createSQLQuery(ProfitDetails.sql).addEntity(ProfitDetails.class).list();
		return null;
	}
}
