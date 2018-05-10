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

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.reportForm.entity.DownstreamReport;

/**
 * 下游到单 log DAO
 */
@Repository
public class DownstreamReportDao extends HibernateDao<DownstreamReport, Long> {

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static StringBuffer sql = new StringBuffer();
	static {
		/*select 
			c.cg_hth,###
			c.cp_contract_no,##
			IFNULL(c.sale_contract_no,"") as sale_contract_no,###
			a.bill_no,###
			a.invoice_no,####
			a.express_no,####
			IFNULL(a.down_invoice_no,"") as down_invoice_no,####
			(select woodgoodsinfo.`name` from woodgoodsinfo where woodgoodsinfo.classes='1级' and woodgoodsinfo.`code` = substring(c.spbm, 1, 2)) as spbm,#商品名称
			a.createrdept,a.creatername,
			(select country_area.`name` FROM country_area WHERE id= a.area) AS area,
			(SELECT dict_child.`name` from dict_child where dict_child.id = a.currency ) as currency,
			a.forwarder_price,
			a.invoice_amount,
			a.no_box_day,
			a.number_units,
			a.numbers,
			(select wood_affi_base_info.customer_name from wood_affi_base_info WHERE id= a.our_unit) as our_unit,
			a.port_name,
			(select woodgk.gkm from woodgk where id= a.port_name) as port_name,
			a.remark,
			a.ship_company,
			a.ship_name,
			a.shipment_type,
			a.supplier,
			a.totalp,
			a.two_time_bill_no,
			a.voyage,
			a.down_stream_client,
			a.confirm,
			DATE_FORMAT(a.createrdate,'%Y-%m-%d') as createrdate,
			IFNULL(DATE_FORMAT(a.expect_port_date,'%Y-%m-%d'),"") as expect_port_date,
			IFNULL(DATE_FORMAT(a.give_bills_date,'%Y-%m-%d'),"") as give_bills_date,
			IFNULL(DATE_FORMAT(a.invoice_date,'%Y-%m-%d') ,"")as invoice_date,
			IFNULL(DATE_FORMAT(a.ship_date,'%Y-%m-%d'),"") as ship_date,
			IFNULL(DATE_FORMAT(a.true_to_date,'%Y-%m-%d'),"") as true_to_date,
			IFNULL(DATE_FORMAT(a.express_date,'%Y-%m-%d'),"") as express_date
			 from wood_downstream_bill a 
			join 
			(select b.spbm,b.cp_contract_no,b.parent_Id,b.cg_hth,b.sale_contract_no from wood_downstream_goods b) c
			on c.parent_Id = a.id
			where a.confirm = '1'
		 * */
		sql.append("select ");
		sql.append("c.cg_hth, ");
		sql.append("c.cp_contract_no, ");
		sql.append("IFNULL(c.sale_contract_no,'') as sale_contract_no, ");
		sql.append("a.bill_no, ");
		sql.append("a.invoice_no, ");
		sql.append("a.express_no, ");
		sql.append(" IFNULL(a.down_invoice_no,'') as down_invoice_no, ");
		sql.append("(select woodgoodsinfo.`name` from woodgoodsinfo where woodgoodsinfo.classes='1级' and woodgoodsinfo.`code` = substring(c.spbm, 1, 2)) as spbm, ");
		sql.append("(select country_area.`name` FROM country_area WHERE id= a.area) AS area, ");
		sql.append("(SELECT dict_child.`name` from dict_child where dict_child.id = a.currency ) as currency, ");
		sql.append("a.forwarder_price,c.cgje,a.no_box_day,");
		sql.append("(SELECT dict_child.`name` from dict_child where dict_child.id = a.number_units ) as number_units,");
		sql.append("a.numbers,");
		sql.append("(select wood_affi_base_info.customer_name from wood_affi_base_info WHERE id= a.our_unit) as our_unit, ");
		sql.append("(select woodgk.gkm from woodgk where id= a.port_name) as port_name, ");
		sql.append("(select wood_affi_base_info.customer_name from wood_affi_base_info WHERE id= a.supplier) as  supplier,");
		sql.append("a.two_time_bill_no,");
		sql.append(" IFNULL((select wood_affi_base_info.customer_name from wood_affi_base_info WHERE id= a.down_stream_client),'') as  down_stream_client, ");
		sql.append("DATE_FORMAT(a.createrdate,'%Y-%m-%d') as createrdate ");
		sql.append(" from wood_downstream_bill a join ");
		sql.append("(select b.spbm,b.cp_contract_no,b.parent_Id,b.cg_hth,b.sale_contract_no,b.cgje from wood_downstream_goods b) c ");
		sql.append("on c.parent_Id = a.id where a.confirm = '1' ");
		sql.append("and c.cg_hth like :cghth ");
		sql.append("and c.cp_contract_no like :cpContractNo ");
		sql.append("and a.bill_no like :billNo ");
		sql.append("and a.invoice_no like :invoiceNo ");
		sql.append("and a.express_no like :expressNo ");
	}
	
	public Map<String, Object> getDataQuery(Map<String, Object> map) {
		int pageSize = Integer.parseInt(map.get("pageSize").toString());
		int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
		
		String cghth = map.containsKey("cghth") ? "%" + map.get("cghth") + "%" : "%";
		String cpContractNo = map.containsKey("cpContractNo") ? "%" + map.get("cpContractNo") + "%" : "%";
		String billNo = map.containsKey("billNo") ? "%" + map.get("billNo") + "%" : "%";
		String invoiceNo = map.containsKey("invoiceNo") ? "%" + map.get("invoiceNo") + "%" : "%";
		String expressNo = map.containsKey("expressNo") ? "%" + map.get("expressNo") + "%" : "%";
		
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		sqlQuery.setParameter("cghth", cghth);
		sqlQuery.setParameter("cpContractNo", cpContractNo);
		sqlQuery.setParameter("billNo", billNo);
		sqlQuery.setParameter("invoiceNo", invoiceNo);
		sqlQuery.setParameter("expressNo", expressNo);
		
		sqlQuery.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//采购合同号
			row_.put("cghth", obj_array[0].toString());
			//综合合同号
			row_.put("cpContractNo", obj_array[1].toString());
			//销售合同号
			row_.put("saleContarteNo", obj_array[2].toString());
			//到单号
			row_.put("billNo", obj_array[3].toString());
			//上游发票号
			row_.put("invoiceNo", obj_array[4].toString());
			//快递号
			row_.put("expressNo", obj_array[5].toString());
			//下游发票号
			row_.put("downInvoiceNo", obj_array[6].toString());
			//商品名称
			row_.put("goodName", obj_array[7].toString());
			//区域
			row_.put("area", obj_array[8].toString());
			//币种
			row_.put("currency", obj_array[9].toString());
			//包干费单价
			row_.put("forwarderPrice", obj_array[10].toString());
			//发票金额
			row_.put("invoiceAmount", obj_array[11].toString());//需要修改
			//免箱期天数
			row_.put("noBoxDay", obj_array[12].toString());
			//数量单位
			row_.put("numberUnits", obj_array[13].toString());
			//数量
			row_.put("numbers", obj_array[14].toString());
			//我司单位
			row_.put("ourUnit", obj_array[15].toString());
			//目的港
			row_.put("portName", obj_array[16].toString());
			//供应商
			row_.put("supplier", obj_array[17].toString());
			//二次换单提单号
			row_.put("twoTimeBillNo", obj_array[18].toString());
			//下游客户
			row_.put("downStreamClient", obj_array[19].toString());
			//登记时间
			row_.put("createDate", obj_array[20].toString());
			
			
			data_rows.add(row_);
		}
		
		
		List<Map<String, Object>> data_footer = new ArrayList<Map<String, Object>>();
		Map<String, Object> data_footer_row1 = getTotal(cghth,cpContractNo,billNo,invoiceNo,expressNo);
		data_footer_row1.put("cghth", "合计：");
		data_footer.add(data_footer_row1);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", data_rows);
		data.put("total", Integer.parseInt(getCountQuery(cghth,cpContractNo,billNo,invoiceNo,expressNo).uniqueResult().toString()));
		data.put("footer", data_footer);
		return data;
	}
	
	private SQLQuery getCountQuery(String cghth ,String cpContractNo ,String billNo,String invoiceNo,String expressNo) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_count = new StringBuffer("");
		sql_count.append("select count(*) from (");
		sql_count.append(sql);
		sql_count.append(") as abc");
		SQLQuery query_count = session.createSQLQuery(sql_count.toString());
		query_count.setParameter("cghth", cghth);
		query_count.setParameter("cpContractNo", cpContractNo);
		query_count.setParameter("billNo", billNo);
		query_count.setParameter("invoiceNo", invoiceNo);
		query_count.setParameter("expressNo", expressNo);
		return query_count;
	}
	
	public Map<String, Object> getTotal(String cghth ,String cpContractNo ,String billNo,String invoiceNo,String expressNo) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sql_total = new StringBuffer("");
		sql_total.append("select cast(sum(numbers) AS decimal(15,2)) as numbers, ");
		sql_total.append("cast(sum(cgje) AS decimal(15,2)) as cgje ");
		sql_total.append("from (");
		sql_total.append(sql);
		sql_total.append(") as abc");
		SQLQuery query_total = session.createSQLQuery(sql_total.toString());
		query_total.setParameter("cghth", cghth);
		query_total.setParameter("cpContractNo", cpContractNo);
		query_total.setParameter("billNo", billNo);
		query_total.setParameter("invoiceNo", invoiceNo);
		query_total.setParameter("expressNo", expressNo);
		
		Map<String, Object> row_ = new HashMap<String, Object>();
		for(Object obj : query_total.list()) {
			Object[] obj_array = (Object[])obj;
			row_.put("numbers", obj_array[0]);
			row_.put("invoiceAmount", obj_array[1]);
		}
		return row_;
	}

}
