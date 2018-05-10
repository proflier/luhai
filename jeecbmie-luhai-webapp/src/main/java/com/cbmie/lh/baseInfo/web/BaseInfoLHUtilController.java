package com.cbmie.lh.baseInfo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
import com.cbmie.lh.baseInfo.entity.GoodsInformation;
import com.cbmie.lh.baseInfo.entity.GoodsType;
import com.cbmie.lh.baseInfo.entity.Signet;
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.lh.baseInfo.service.GoodsInformationService;
import com.cbmie.lh.baseInfo.service.GoodsTypeService;
import com.cbmie.lh.baseInfo.service.SignetService;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.service.ShipTraceService;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.service.DictMainService;
@Controller
@RequestMapping(value="/baseInfo/lhUtil")
public class BaseInfoLHUtilController extends BaseController {

	@Autowired
	private GoodsTypeService typeService;
	@Autowired
	private GoodsIndexService indexService;
	@Autowired
	private SignetService signetService;
	
	@Autowired
	private GoodsInformationService infoService;
	
	@Autowired
	private DictMainService dictMainService;
	
	@Autowired
	private ShipTraceService shipTraceService;
	
	
	/**
	 * 商品选项
	 * */
	@RequestMapping(value="goodsItems",method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsInformation> getSelectItemOfGoods(){
		return infoService.getAll();
	}
	
	/**
	 * 根据商品类别显示商品选项
	 * */
	@RequestMapping(value="goodsItemsByCode",method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsInformation> getSelectItemOfGoods(@RequestParam String code){
		return infoService.getListByCode(code);
	}
	
	/**
	 * 商品类别下拉for tree
	 * @return
	 */
	@RequestMapping(value="goodsInfoTree",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getGoodsInfoTree(){
		return infoService.getGoodsInfoTree();
	}
	
	/**
	 * 商品类别展示
	 * */
	@RequestMapping(value="goodsShow",method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsNameByCode(@RequestParam String code){
		GoodsInformation gt = infoService.getEntityByCode(code);
		if(gt!=null){
			return gt.getInfoName();
		}else{
			return null;
		}
	}
	
	/**
	 * 商品类别选项
	 * */
	@RequestMapping(value="goodsTypeItems",method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsType> getSelectItemOfGoodsType(){
		return typeService.getEffectGoodsType();
	}
	
	/**
	 * 商品类别展示
	 * */
	@RequestMapping(value="goodsTypeShow",method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsTypeNameByCode(@RequestParam String code){
		GoodsType gt = typeService.getEntityByCode(code);
		if(gt!=null){
			return gt.getTypeName();
		}else{
			return null;
		}
	}
	/**
	 * 商品指标选项
	 * */
	@RequestMapping(value="goodsIndexItems",method = RequestMethod.GET)
	@ResponseBody
	public List<GoodsIndex> getSelectItemOfGoodsIndex(){
		return indexService.getEffectGoodsIndex();
	}
	
	/**
	 * 商品类别展示
	 * */
	@RequestMapping(value="goodsIndexNameShow/{id}",method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsIndexNameById(@PathVariable("id") Long id){
		GoodsIndex index = indexService.get(id);
		if(index!=null){
			return index.getIndexName();
		}else{
			return null;
		}
	}
	
	/**
	 * 商品类别-英文名
	 * @param id
	 * @return
	 */
	@RequestMapping(value="goodsindexNameEShow/{id}",method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsIndexNameEById(@PathVariable("id") Long id){
		GoodsIndex index = indexService.get(id);
		if(index!=null){
			return index.getIndexNameE();
		}else{
			return null;
		}
	}
	
	/**
	 * 商品类别-单位
	 * @param id
	 * @return
	 */
	@RequestMapping(value="goodsUnithow/{id}",method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsUnitById(@PathVariable("id") Long id){
		GoodsIndex index = indexService.get(id);
		if(index!=null){
			return index.getUnit();
		}else{
			return null;
		}
	}
	
	/**
	 * 商品类别-单位-显示单位对应字典中文
	 * @param id
	 * @return
	 */
	@RequestMapping(value="goodsUnithowCode/{id}",method = RequestMethod.GET)
	@ResponseBody
	public String getGoodsUnitByIdCode(@PathVariable("id") Long id){
		String returnValue="";
		GoodsIndex index = indexService.get(id);
		if(index!=null){
			List<DictChild> dictChildList = dictMainService.findByCode("INDEXUNIT").getDictChild();
			for(DictChild dictChild:dictChildList){
				if(index.getUnit().equals(dictChild.getCode().toString())){
					returnValue = dictChild.getName();
				}
			}
			return returnValue;
		}else{
			return null;
		}
	}
	/**
	 * 商品类别选项
	 * */
	@RequestMapping(value="signetItems",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,String>> getSelectItemOfSignet(){
		String sql = "select a.signet_code,a.org_name,d.name from LH_SIGNET a left join organization b on a.orgId=b.id "
				+ " dict_child d on a.type_code=d.code left join dict_main c on c.id=d.pid "
				+ " where c.code='SIGNETTYPE'";
		List<Object[]> list = signetService.getEntityDao().createSQLQuery(sql).list();
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		for(Object[] objs : list){
			Map<String,String> result1 = new HashMap<String,String>();
			result1.put(objs[0].toString(), objs[2]!=null?objs[2].toString():""+"   "+objs[1]!=null?objs[1].toString():"");
			result.add(result1);
		}
		return result;
	}
	
	/**
	 * 印章展示
	 * */
	@RequestMapping(value="signetNameShow",method = RequestMethod.GET)
	@ResponseBody
	public String getSignetNameByCode(@RequestParam String code){
		Signet signet = signetService.getEntityByCode(code);
		if(signet!=null){
			return signet.getSignetCode();
		}else{
			return null;
		}
	}
	
	/**
	 * 印章集合
	 * */
	@RequestMapping(value="getSignetList",method = RequestMethod.GET)
	@ResponseBody
	public List<Signet> getSignetList(){
		List<Signet> signetList = signetService.getEffectSignet();
		return signetList;
	}
	
	/**
	 * 印章展示
	 * */
	@RequestMapping(value="getSignetNameByCode/{code}",method = RequestMethod.GET)
	@ResponseBody
	public String getSignNameByCode(@PathVariable("code") String code){
		Signet signet = signetService.getEntityByCode(code);
		return signet.getTypeName();
	}
	
	/**
	 * 通过采购合同号获取内部合同号
	 * */
	@RequestMapping(value="innerContractNoByPurchaseNo/{purchaseContractNo}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,String>> getInnerContractNoByContractNo(@PathVariable(value="purchaseContractNo") String purchaseContractNo){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		String sql = "select a.inner_Contract_No from LH_PURCHASE_CONTRACT a where a.state='1' and a.purchase_Contract_No in (:contractNos)";
		List<Object> list = signetService.getEntityDao().createSQLQuery(sql).setParameterList("contractNos", purchaseContractNo.split(",")).list();
		if(list!=null && list.size()>0){
			for(Object obj : list){
				if(obj!=null){
					Map<String,String> map = new HashMap<String,String>();
					map.put("innerNo", obj.toString());
					result.add(map);
				}
			}
		}
		return result;
	}
	
	/**
	 * 通过采购合同号获取船编号
	 * */
	@RequestMapping(value="shipNoByPurchaseNo/{purchaseContractNo}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,String>> getShipNoByContractNo(@PathVariable(value="purchaseContractNo") String purchaseContractNo){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		String sql = "select b.ship_No from LH_PURCHASE_CONTRACT a join LH_SHIP_TRACE b on a.inner_Contract_No=b.inner_Contract_No "
				+ " where a.purchase_Contract_No in (:contractNos)";
		List<Object> list = signetService.getEntityDao().createSQLQuery(sql).setParameterList("contractNos", purchaseContractNo.split(",")).list();
		if(list!=null && list.size()>0){
			for(Object obj : list){
				if(obj!=null){
					Map<String,String> map = new HashMap<String,String>();
					map.put("shipNo", obj.toString());
					result.add(map);
				}
			}
		}
		return result;
	}
	@RequestMapping(value="shipsByPurchaseNo/{purchaseContractNo}",method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> getShipsByContractNo(@PathVariable(value="purchaseContractNo") String purchaseContractNo){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		String sql = "select b.* from LH_PURCHASE_CONTRACT a join LH_SHIP_TRACE b on a.inner_Contract_No=b.inner_Contract_No "
				+ " where a.purchase_Contract_No in (:contractNos)";
		List<ShipTrace> list = signetService.getEntityDao().createSQLQuery(sql).addEntity(ShipTrace.class).setParameterList("contractNos", purchaseContractNo.split(",")).list();
		return list;
	}
	
	@RequestMapping(value="shipsByPurchaseInnerNo/{purchaseContractInnerNo}",method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> getShipsByContractInnerNo(@PathVariable(value="purchaseContractInnerNo") String purchaseContractInnerNo){
		String sql = " select * from LH_SHIP_TRACE a WHERE ";
		String sqlWhere = "";
		String[] innList = purchaseContractInnerNo.split(",");
		if(innList.length>0){
			for(String inn :innList){
				if(sqlWhere!=""){
					sqlWhere = sqlWhere +" or a.inner_contract_no like '%"+inn+"%' ";
				}else{
					sqlWhere = " a.inner_contract_no like '%"+inn+"%' ";
				}
			}
		}
		sql =sql + sqlWhere;
		List<ShipTrace> list = signetService.getEntityDao().createSQLQuery(sql).addEntity(ShipTrace.class).list();
		return list;
	}
	/**
	 * 获取船编号
	 * */
	@RequestMapping(value="allShipNo",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,String>> getShipNos(){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		String sql = "select b.ship_No from LH_SHIP_TRACE b order by b.id desc ";
		List<Object> list = signetService.getEntityDao().createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			for(Object obj : list){
				if(obj!=null){
					Map<String,String> map = new HashMap<String,String>();
					map.put("shipNo", obj.toString());
					result.add(map);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取船舶动态list
	 * */
	@RequestMapping(value="getAllShipTrack",method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> getAllShipTrack(){
		return shipTraceService.getAll();
	}
	
	/**
	 * 选择采购、销售合同跳转
	 */
	@RequestMapping(value = "selectContract", method = RequestMethod.GET)
	public String selectContract() {
		return "common/selectContract";
	}
	
	/**
	 * 获取采购销售商品平均单价页面
	 */
	@RequestMapping(value = "salePurchaseGoodsAvgPrice", method = RequestMethod.GET)
	public String salePurchaseGoodsAvgPrice() {
		return "common/salePurchaseGoodsAvgPrice";
	}
}
