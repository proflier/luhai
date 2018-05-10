package com.cbmie.lh.sale.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleSettlementOutRel;
@Repository
public class SaleSettlementOutRelDao extends HibernateDao<SaleSettlementOutRel, Long> {
	
	public List<Map<String,Object>> getOutStackList(Long settlementId){
		//LH_SALE_SETTLEMENTOUTREL 仓库、船名、出库单号、出库日期、商品名称、数量、运输方式
		String sql = "select a.id relId,b.id outstockDetailId, b.port port,b.voy voy,c.out_Stock_No outStockNo,c.out_Stock_Date outStockDate,b.goods_Name goodsNo,b.quantity quantity,b.trans_Type transType, "
				+" (select wood_affi_base_info.customer_name from wood_affi_base_info where wood_affi_base_info.customer_code=b.port ) as portView , "
				+" (select CONCAT(lh_ship_trace.ship_no,lh_ship_trace.ship_name) from lh_ship_trace where lh_ship_trace.ship_no=b.voy ) as voyView , "
				+" (select lh_goodsinformation.info_name from lh_goodsinformation where lh_goodsinformation.info_code = b.goods_Name) as goodsNoView ,"
				+" (select dict_child.name from dict_child WHERE dict_child.code = b.trans_Type) as transTypeView "
				+" from "
				+ " LH_SALE_SETTLEMENTOUTREL a join LH_SALE_DELIVERY_GOODS b on a.outstock_Detail_Id = b.id "
				+ " join LH_OUT_STOCK c on b.out_Stock_Id=c.id where a.settlement_Id=:settlementId order by c.id asc";
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameter("settlementId", settlementId);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> result=query.list();
        return result;
        
	}
	
	public List<Map<String,Object>> getRemainOutStackList(String saleContractNo,Long settlementId){
		//LH_SALE_SETTLEMENTOUTREL 仓库、船名、出库单号、出库日期、商品名称、数量、运输方式
		String sql = "select b.id id,b.port port,b.voy voy,c.out_Stock_No outStockNo,c.out_Stock_Date outStockDate,b.goods_Name goodsNo,b.quantity quantity,b.trans_Type transType from "
				+ " LH_SALE_DELIVERY_GOODS b  "
				+ " join LH_OUT_STOCK c on b.out_Stock_Id=c.id where c.confirm='1' and b.contract_No=:saleContractNo"
				+ " and not exists(select 1 from LH_SALE_SETTLEMENTOUTREL a join LH_SALE_SETTLEMENT d on a.settlement_Id=d.id "
				+ " where a.outstock_Detail_Id=b.id and d.state<>'0' and a.settlement_Id<>:settlementId) order by c.id asc";
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameter("saleContractNo", saleContractNo).setParameter("settlementId", settlementId);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> result=query.list();
        return result;
	}
}
