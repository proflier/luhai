package com.cbmie.lh.sale.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleInvoiceOutRel;
@Repository
public class SaleInvoiceOutRelDao extends HibernateDao<SaleInvoiceOutRel, Long> {

	public List<Map<String,Object>> getOutStackList(Long invoiceId){
		//LH_SALE_INVOICEOUTREL 仓库、船名、出库单号、出库日期、商品名称、数量、运输方式
		String sql = "select a.id relId,b.id outstockDetailId, b.port port,b.voy voy,c.out_Stock_No outStockNo,c.out_Stock_Date outStockDate,b.goods_Name goodsNo,truncate(b.quantity,3) quantity,b.trans_Type transType, "
				+" (select customer_name from wood_affi_base_info where customer_code=b.port) as portView, "
				+" (select GROUP_CONCAT(ship_no,ship_name) from lh_ship_trace where ship_no=b.voy) as voyView, "
				+" (select info_name from lh_goodsinformation where info_code=b.goods_name) as goodsNoView, "
				+" (select name from dict_child where code=b.trans_Type) as transTypeView "
				+ " from "
				+ " LH_SALE_INVOICEOUTREL a join LH_SALE_DELIVERY_GOODS b on a.outstock_Detail_Id = b.id "
				+ " join LH_OUT_STOCK c on b.out_Stock_Id=c.id where a.invoice_Id=:invoiceId order by c.id asc";
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameter("invoiceId", invoiceId);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> result=query.list();
        return result;
	}
	
	public List<Map<String,Object>> getRemainOutStackList(String saleContractNo,Long invoiceId){
		//LH_SALE_SETTLEMENTOUTREL 仓库、船名、出库单号、出库日期、商品名称、数量、运输方式
		String sql = "select b.id id,b.port port,b.voy voy,c.out_Stock_No outStockNo,c.out_Stock_Date outStockDate,b.goods_Name goodsNo,b.quantity quantity,b.trans_Type transType from "
				+ " LH_SALE_DELIVERY_GOODS b  "
				+ " join LH_OUT_STOCK c on b.out_Stock_Id=c.id where c.confirm='1' and b.contract_No=:saleContractNo"
				+ "  order by c.id asc";
		//and not exists(select 1 from LH_SALE_INVOICEOUTREL a join LH_SALE_INVOICE d on a.invoice_Id=d.id "
		//+ " where a.outstock_Detail_Id=b.id and d.state<>'0' and a.invoice_Id<>:invoiceId)
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameter("saleContractNo", saleContractNo);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> result=query.list();
        return result;
	}
}
