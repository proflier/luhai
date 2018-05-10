package com.cbmie.lh.sale.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.sale.entity.SaleSettlementOutRel;
import com.cbmie.lh.sale.service.SaleSettlementGoodsService;
import com.cbmie.lh.sale.service.SaleSettlementOutRelService;
import com.cbmie.lh.sale.service.SaleSettlementService;
@Controller
@RequestMapping("sale/settlementOutRel")
public class SaleSettlementOutRelController extends BaseController {

	@Autowired
	private SaleSettlementOutRelService relService;
	@Autowired
	private SaleSettlementService settlementService;
	@Autowired
	private SaleSettlementGoodsService settlementGoodsService;
	
//	@RequestMapping(value="listOutsForSettlement/{settlementId}")
//	@ResponseBody
//	public List<Map<String,Object>> getOutStackList(@PathVariable(value="settlementId") Long settlementId){
//		List<Map<String,Object>> outStackList = relService.getOutStackList(settlementId);
//		return outStackList;
//	}
	
	@RequestMapping(value="listOutsForSettlement/{settlementId}")
	@ResponseBody
	public Map<String,Object> getOutStackList(@PathVariable(value="settlementId") Long settlementId){
		Map<String, Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> outStackList = relService.getOutStackList(settlementId);
		List<Map<String,Object>> footer = new ArrayList<Map<String,Object>>();
		Map<String,Object> footerMap = new HashMap<String,Object>();
		BigDecimal quantity = new BigDecimal(0);
		for(Map<String,Object> outStack : outStackList) {
			quantity = quantity.add(new BigDecimal(outStack.get("quantity").toString()));
		}
		footerMap.put("quantity", quantity.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
		footer.add(footerMap);
		
		result.put("total", outStackList.size());
		result.put("rows", outStackList);
		result.put("footer", footer);
		return result;
	}
	
	@RequestMapping(value="getOutsSelectItem/{saleContractNo}/{settlementId}")
	@ResponseBody
	public List<Map<String,Object>> getRemainOutStackList(@PathVariable(value="saleContractNo") String saleContractNo,@PathVariable(value="settlementId") Long settlementId){
		return relService.getRemainOutStackList(saleContractNo,settlementId);
	}
	
	@RequestMapping(value="toOutsSelectPage/{settlementId}")
	public String toOutStockSelect(@PathVariable(value="settlementId") Long settlementId,Model model){
		SaleSettlement saleSettlement = settlementService.get(settlementId);
		model.addAttribute("saleContractNo", saleSettlement.getSaleContractNo());
		model.addAttribute("settlementId", settlementId);
		return "sale/saleOutsSelectPage";
	}
	
	@RequestMapping(value = "saveRel/{settlementId}")
	@ResponseBody
	public String changeRelationDepts(@PathVariable("settlementId") Long settlementId,@RequestBody List<Long> outStockIds){
		SaleSettlement saleSettlement = settlementService.get(settlementId);
		List<SaleSettlementOutRel> rels = saleSettlement.getOutRelList();
		for(int i=0;i<rels.size();i++){
			SaleSettlementOutRel cur = rels.get(i);
			boolean keepFlag = false;
			a: for(int j=0;j<outStockIds.size();j++){
				if(cur.getId().longValue()==outStockIds.get(j).longValue()){
					outStockIds.remove(j);
					keepFlag = true;
					break a;
				}
			}
			if(!keepFlag){
				rels.remove(cur);
				relService.delete(cur);
				i--;
			}
		}
		if(outStockIds!=null && outStockIds.size()>0){
			for(Long outId : outStockIds){
				SaleSettlementOutRel rel = new SaleSettlementOutRel();
				rel.setSettlementId(settlementId);
				rel.setOutstockDetailId(outId);
				saleSettlement.getOutRelList().add(rel);
			}
		}
		settlementService.update(saleSettlement);
		return "success";
	}
	
}
