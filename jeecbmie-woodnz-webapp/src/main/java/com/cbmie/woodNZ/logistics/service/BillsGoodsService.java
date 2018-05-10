package com.cbmie.woodNZ.logistics.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.logistics.dao.BillsGoodsDao;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;
import com.cbmie.woodNZ.logistics.entity.WoodBillsSub;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 到单子表- 货物明细service
 */
@Service
@Transactional
public class BillsGoodsService extends BaseService<WoodBillsGoods, Long> {

	@Autowired 
	private BillsGoodsDao billsGoodsDao;

	@Override
	public HibernateDao<WoodBillsGoods, Long> getEntityDao() {
		return billsGoodsDao;
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	public List<WoodBillsGoods> getByParentId(Long id) {
		return billsGoodsDao.getGoodsListByParentId(id);
	}
	
	public void save(WoodBills woodBills, String billGoodsJson) {
		// 转成标准的json字符串
		billGoodsJson = StringEscapeUtils.unescapeHtml4(billGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<WoodBillsGoods> billGoodsList = new ArrayList<WoodBillsGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(billGoodsJson);
			for (JsonNode jn : jsonNode) {
				WoodBillsGoods woodBillsGoods = objectMapper.readValue(jn.toString(), WoodBillsGoods.class);
				Double cgdj = Double.valueOf(woodBillsGoods.getCgdj());
				Double cgsl = Double.valueOf(woodBillsGoods.getSl());
				Double cgje = cgdj*cgsl;
				String cgjeString = String.format("%.2f", cgje);//保留2位小数
				woodBillsGoods.setCgje(cgjeString);//动态计算 总价=数量*采购价,保留三位小数
				billGoodsList.add(woodBillsGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//delete(woodBills,billGoodsList);
		save(woodBills,billGoodsList);
	}
	

	public void save(WoodBills woodBills,List<WoodBillsGoods> woodBillsGoodsList){//新增或修改	
		try {
			for(WoodBillsGoods woodBillsGoods : woodBillsGoodsList) {
				woodBillsGoods.setParentId(woodBills.getId().toString());
				billsGoodsDao.save(woodBillsGoods);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void delete(WoodBills woodBills,List<WoodBillsGoods> woodBillsGoodsList){//删除
		try {
			Long mainId = woodBills.getId();
			List<WoodBillsGoods> goodsList= billsGoodsDao.getGoodsListByParentId(mainId);
			for(WoodBillsGoods g:goodsList){
				billsGoodsDao.delete(g);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
