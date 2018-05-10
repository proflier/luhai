package com.cbmie.woodNZ.reportForm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockDetailsDao {
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static StringBuffer sql = new StringBuffer();
	static {
		/*select abc.goodname,abc.tradetype, abc.tradeWay,abc.warehouse,abc.unit,abc.realStock,abc.currency,abc.inStockFee,abc.price,abc.transpot,
			abc.mouth,abc.season,sum(abc.secondSeason) as secondSeason,abc.sixMouth  
			from (
			select  (select woodgoodsinfo.`name` from woodgoodsinfo where woodgoodsinfo.classes='1级' and woodgoodsinfo.`code` = substring(a.goods_no, 1, 2)) as goodname,#商品名称
			(SELECT dict_child.`name` from dict_child where dict_child.id = b.htlb ) as tradetype,#贸易类型
			'' as tradeWay,#贸易方式
			(select woodck.company_name from woodck where woodck.id = d.warehouse) as warehouse,#仓库
			(SELECT dict_child.`name` from dict_child where dict_child.id = a.number_units ) as unit,#实物量单位
			sum(a.num-c.s_amount) as realStock,#业务实存数量 入库量-出库量
			(SELECT dict_child.`name` from dict_child where dict_child.id = a.currency ) as currency,#币种
			sum(a.total_price) as inStockFee,#库存合计 进价+杂运费
			sum(a.total_price) as price,#进价
			0 as transpot,#杂运费
			if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3))) as mouth_level,
			if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=0,sum(a.total_price),0) as mouth,
			if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=1,sum(a.total_price),0) as season,
			if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=2,sum(a.total_price),0) as secondSeason,
			if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=3,sum(a.total_price),0) as sixMouth
			from wood_in_stock_goods as a
			join (
						select instock.id ,instock.receive_warehouse as warehouse from wood_in_stock instock
						) d
			on d.id = a.parent_id
			join wood_cght_jk b 
			on b.inter_contract_no = a.cp_contract_no
			join (
				select outsub.cp_contract_no,
							sum(outsub.amount) as s_amount
							from wood_out_stock_sub outsub 
							group by outsub.cp_contract_no) c 
			on c.cp_contract_no = a.cp_contract_no
			where 1=1
			and length(a.goods_no) = 13
			and substring(a.goods_no, 1, 2) <> 99
			and a.cp_contract_no <> '' 
			#and a.cp_contract_no like :'ht'
			group by substring(a.goods_no, 1, 2), b.htlb,warehouse,unit,a.currency,mouth_level) as abc 
			group by abc.warehouse ,abc.goodname, tradetype,abc.unit,abc.currency
		 * */
		sql.append("select abc.goodname,abc.tradetype, abc.tradeWay,abc.warehouse,abc.unit,");
		sql.append("abc.realStock,abc.currency,abc.inStockFee,abc.price,abc.transpot,abc.mouth,abc.season,sum(abc.secondSeason) ,abc.sixMouth  from (");
		sql.append("select ");
		sql.append("(select woodgoodsinfo.`name` from woodgoodsinfo where woodgoodsinfo.classes='1级' and woodgoodsinfo.`code` = substring(a.goods_no, 1, 2)) as goodname,");
		sql.append("(SELECT dict_child.`name` from dict_child where dict_child.id = b.htlb ) as tradetype,");
		sql.append(" '无' as tradeway,");
		sql.append("(select woodck.company_name from woodck where woodck.id = d.warehouse) as warehouse,");
		sql.append("(SELECT dict_child.`name` from dict_child where dict_child.id = a.number_units ) as unit,");
		sql.append("sum(a.num-c.s_amount) as realstock,");
		sql.append("(SELECT dict_child.`name` from dict_child where dict_child.id = a.currency ) as currency,");
		sql.append("sum(a.total_price) as instockfee,sum(a.total_price) as price,0 as transpot,");
		sql.append("if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3))) as mouth_level,");
		sql.append("if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=0,sum(a.total_price),0) as mouth,");
		sql.append("if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=1,sum(a.total_price),0) as season,");
		sql.append("if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=2,sum(a.total_price),0) as secondSeason,");
		sql.append("if(if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<1 ,0 , if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<4,1,if(TIMESTAMPDIFF(MONTH,a.createrdate,DATE(now()))<7,2,3)))=3,sum(a.total_price),0) as sixMouth ");
		sql.append("from wood_in_stock_goods as a ");
		sql.append("join (select instock.id as id , instock.receive_warehouse as warehouse from wood_in_stock instock) d on d.id = a.parent_id ");
		sql.append("join wood_cght_jk b on b.inter_contract_no = a.cp_contract_no ");
		sql.append("join (select outsub.cp_contract_no,sum(amount) as s_amount from wood_out_stock_sub outsub group by outsub.cp_contract_no) c on c.cp_contract_no = a.cp_contract_no ");
		sql.append("where 1=1 ");
		sql.append("and length(a.goods_no) = 13 ");
		sql.append("and substring(a.goods_no, 1, 2) <> 99 ");
		sql.append("and a.cp_contract_no <> '' ");
		sql.append("and d.warehouse like :receiveWarehouse ");
		sql.append("group by substring(a.goods_no, 1, 2), b.htlb,warehouse,unit,a.currency,mouth_level ");
		sql.append(" ) as abc  group by abc.warehouse ,abc.goodname,abc.tradeway, abc.tradetype,abc.unit,abc.currency ");
	}
	
	public Map<String, Object> getDataQuery(Map<String, Object> map) {
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
		String receiveWarehouse = map.containsKey("receiveWarehouse") ? "%" + map.get("receiveWarehouse") + "%" : "%";
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("receiveWarehouse", receiveWarehouse);
		sqlQuery.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//商品名称
			row_.put("goodname", obj_array[0].toString());
			//贸易类型
			row_.put("tradetype", obj_array[1].toString());
			//贸易方式
			row_.put("tradeway", obj_array[2].toString());
			//仓库
			row_.put("warehouse", obj_array[3].toString());
			//数量单位
			row_.put("unit", obj_array[4].toString());
			//业务实存数量
			row_.put("realstock", obj_array[5].toString());
			//币种
			row_.put("currency", obj_array[6].toString());
			//库存合计
			row_.put("instockfee", obj_array[7].toString());
			//进价
			row_.put("price", obj_array[8].toString());
			//运杂费
			row_.put("transpot", obj_array[9].toString());
			row_.put("mouth", obj_array[10].toString());
			row_.put("season", obj_array[11].toString());
			row_.put("secondSeason", obj_array[12].toString());
			row_.put("sixMouth", obj_array[13].toString());
			data_rows.add(row_);
		}
		
		
		List<Map<String, Object>> data_footer = new ArrayList<Map<String, Object>>();
		Map<String, Object> data_footer_row1 = getTotal();
		data_footer_row1.put("goodname", "合计：");
		data_footer.add(data_footer_row1);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", data_rows);
		data.put("total", Integer.parseInt(getCountQuery().setParameter("receiveWarehouse", receiveWarehouse).uniqueResult().toString()));
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
		return query_count;
	}
	
	public Map<String, Object> getTotal() {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_total = new StringBuffer("");
		sql_total.append("select cast(sum(realstock) AS decimal(15,2)) as realstock,");
		sql_total.append("cast(sum(instockfee) AS decimal(15,2)) as instockfee, ");
		sql_total.append("cast(sum(price) AS decimal(15,2)) as price,");
		sql_total.append("cast(sum(transpot) AS decimal(15,2)) as transpot ");
		sql_total.append("from (");
		sql_total.append(sql);
		sql_total.append(") as abc");
		SQLQuery query_total = session.createSQLQuery(sql_total.toString());
		query_total.setParameter("receiveWarehouse", "%");
		
		Map<String, Object> row_ = new HashMap<String, Object>();
		for(Object obj : query_total.list()) {
			Object[] obj_array = (Object[])obj;
			row_.put("realstock", obj_array[0]);
			row_.put("instockfee", obj_array[1]);
			row_.put("price", obj_array[2]);
			row_.put("transpot", obj_array[3]);
		}
		return row_;
	}
}
