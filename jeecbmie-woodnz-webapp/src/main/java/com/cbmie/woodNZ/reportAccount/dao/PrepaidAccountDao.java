package com.cbmie.woodNZ.reportAccount.dao;

import java.text.DecimalFormat;
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
public class PrepaidAccountDao {
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static StringBuffer sql = new StringBuffer();
	static {		
		sql.append(" select  customernam,round(sum(days),0) ,currency,ROUND(sum(price),2),cpcontractno from ");
		sql.append(" (select  ifnull(customer_name,'') as customernam,ifnull(DATEDIFF(CURDATE() , DATE(confirmDate)),0) as days ,ifnull(cgbz,'') as currency,payMoneyXiao-price as price,ifnull(cp_contract_no,'') as cpcontractno"); 
		sql.append(" from (select  company_name,cp_contract_no,price,ifnull(payMoneyXiao ,0) as payMoneyXiao,cgbz,confirmDate ");
		sql.append(" from(select company_name,cp_contract_no, sum(price) as price,cgbz ,hth ");
		sql.append(" from wood_in_stock i,");
		sql.append(" (select ss.cp_contract_no,( ifnull(ss.num ,0) * ifnull(purchase_price,0)) as price,jk.cgbz,ss.parent_id,jk.hth");
		sql.append(" from   wood_in_stock_goods ss  left join wood_cght_jk jk on cp_contract_no = jk.inter_contract_no )sub ");
		sql.append(" where i.id =sub.parent_id and confirm='1' group by company_name,cp_contract_no,cgbz) ins left join  ");
		sql.append(" (  select  contractNo,sum(payMoneyXiao) as payMoneyXiao,confirmDate from  wood_payment_confirm  where state='生效' group by contractNo) s  on ins.hth= s.contractNo)a ");
		sql.append(" left join wood_affi_base_info b on a.company_name = b.id )aa");
		sql.append(" where aa.cpContractNo like :cpContractNo ");
		sql.append(" group by cpcontractno,customernam,currency order by customernam,currency,cpcontractno");
		
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
		
		String cpContractNo = map.containsKey("cpContractNo") ? "%" + map.get("cpContractNo") + "%" : "%";
		
//		if(StringUtils.isNotEmpty(orderby)) {
//			sql.append("order by " + orderby + " ");
//		}
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		 
		sqlQuery.setParameter("cpContractNo", cpContractNo);
		sqlQuery.setFirstResult(pageIndex*pageSize).setMaxResults(pageSize);
		
		String currencysql=" select currency,year,month,rateUS,rateUN,rateNZ from exchange_rate where currency='人民币' order by year desc ,month+0 desc";
		SQLQuery currencysqlQuery = sessionFactory.getCurrentSession().createSQLQuery(currencysql.toString());
		double rateUS=0,rateUN=0,rateNZ=0;
		 DecimalFormat df = new DecimalFormat("0.00");  
		 DecimalFormat dfnum = new DecimalFormat("#");  
		if(currencysqlQuery.list()!=null && currencysqlQuery.list().size()>0){
			Object[] obj_array = (Object[])currencysqlQuery.list().get(0);			
			rateUS= Double.parseDouble(obj_array[3].toString());
		//	rateUN= Double.parseDouble(obj_array[4].toString());
			rateNZ= Double.parseDouble(obj_array[5].toString());
		}
		 //dict_child表的字典编码值 20人民币   21 美元   119新西兰元
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			 
	        			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//客户名称
			row_.put("customername", obj_array[0].toString());
			//账龄
			row_.put("days", dfnum.format(obj_array[1]));
			//币种
			row_.put("currency", obj_array[2].toString());
			String submoney =obj_array[3].toString();
			
			//币种是人民币
			if("20".equals(obj_array[2].toString())&&!"".equals(submoney)){
				//人民币余额
				String rmbmoney =obj_array[3].toString();
				row_.put("rmbmoney", df.format(Double.parseDouble(rmbmoney)));
				//美元余额
				if(rateUS==0)
					row_.put("usmoney","");
				else{
				double usmoney = Double.parseDouble(rmbmoney)*rateUS;
				row_.put("usmoney", df.format(usmoney));
				}
				//纽币余额
				if(rateNZ==0)
					row_.put("nzmoney","");
				else{
				double nzmoney = Double.parseDouble(rmbmoney)*rateNZ;
				row_.put("nzmoney", df.format(nzmoney));
				}
			}
			//币种是美元
			if("21".equals(obj_array[2].toString())&&!"".equals(submoney)){
				//人民币余额
				if(rateUS==0)
					row_.put("rmbmoney","");
				else{
				double rmbmoney = Double.parseDouble(obj_array[3].toString())/rateUS;
				row_.put("rmbmoney", df.format(rmbmoney));
				}
				//美元余额 
				row_.put("usmoney", df.format(Double.parseDouble(obj_array[3].toString())));
				//纽币余额.
				if(rateUS==0)
					row_.put("nzmoney","");
				else{
				double nzmoney = Double.parseDouble(obj_array[3].toString())*rateNZ/rateUS;
				row_.put("nzmoney", df.format(nzmoney)); 
				}
			}
			//币种是纽币
			if("119".equals(obj_array[2].toString())&&!"".equals(submoney)){
				//人民币余额
				if(rateNZ==0)
					row_.put("rmbmoney","");
				else{
				double rmbmoney = Double.parseDouble(obj_array[3].toString())/rateNZ;
				row_.put("rmbmoney", df.format(rmbmoney));
				}
				//美元余额
				if(rateNZ==0)
					row_.put("usmoney","");
				else{
				double usmoney = Double.parseDouble(obj_array[3].toString())*rateUS/rateNZ;
				row_.put("usmoney", df.format(usmoney));
				}
				//纽币余额 
				row_.put("nzmoney", df.format(Double.parseDouble(obj_array[3].toString()))); 
			}
			else {
				//余额
				row_.put("submoney", df.format(Double.parseDouble(obj_array[3].toString())));
			}
			 
			//综合合同号
			row_.put("cpContractNo", obj_array[4].toString());
		
			data_rows.add(row_);
		}
		
		
	 
		Map<String, Object> data = new HashMap<String, Object>(); 
		data.put("rows", data_rows);
		data.put("total", Integer.parseInt(getCountQuery().setParameter("cpContractNo", cpContractNo).uniqueResult().toString()));

 
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
	
 
}
