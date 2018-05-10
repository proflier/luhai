package com.cbmie.genMac.financial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.SaleInvoiceSubDao;
import com.cbmie.genMac.financial.entity.SaleInvoice;
import com.cbmie.genMac.financial.entity.SaleInvoiceSub;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.stock.entity.OutStock;
import com.cbmie.woodNZ.stock.entity.OutStockSub;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
@Transactional
public class SaleInvoiceSubService extends BaseService<SaleInvoiceSub, Long> {
	@Autowired
	private SaleInvoiceSubDao subDao;
	@Override
	public HibernateDao<SaleInvoiceSub, Long> getEntityDao() {
		return subDao;
	}
	
	/**
	 * 
	 * @param outStock
	 * @param outStockJson
	 */
	public void save(SaleInvoice saleInvoice, String saleInvoiceSubJson) {
		// 转成标准的json字符串
		saleInvoiceSubJson = StringEscapeUtils.unescapeHtml4(saleInvoiceSubJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<SaleInvoiceSub> subList = new ArrayList<SaleInvoiceSub>();
		try {
			JsonNode jsonNode = objectMapper.readTree(saleInvoiceSubJson);
			for (JsonNode jn : jsonNode) {
				SaleInvoiceSub sub = objectMapper.readValue(jn.toString(), SaleInvoiceSub.class);
				subList.add(sub);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		save(saleInvoice,subList);
		delete(saleInvoice,subList);
	}
	
	public void save(SaleInvoice saleInvoice,List<SaleInvoiceSub> sublist){//新增或修改	
		for(SaleInvoiceSub sub : sublist) {
			if(sub.getId() == null){//新增
				User currentUser = UserUtil.getCurrentUser();
				sub.setParentId(saleInvoice.getId());
				sub.setCreaterNo(currentUser.getLoginName());
				sub.setCreaterName(currentUser.getName());
				sub.setCreateDate(new Date());
			}else if(sub.getId() != null){//修改
				User currentUser = UserUtil.getCurrentUser();
				sub.setUpdaterNo(currentUser.getLoginName());
				sub.setUpdaterName(currentUser.getName());
			}
			subDao.save(sub);	
		}
	}
	
	
	public void delete(SaleInvoice saleInvoice,List<SaleInvoiceSub> sublist){//删除
		String sql = "from SaleInvoiceSub a where a.parentId=:parentId";
		List<SaleInvoiceSub> dataSubList = subDao.createQuery(sql).setParameter("parentId", saleInvoice.getId()).list();
		 saleInvoice.getSubs().clear();
		 saleInvoice.getSubs().addAll(dataSubList);
        List<SaleInvoiceSub> deleteSubList = new ArrayList<SaleInvoiceSub>();//保存要删除的数据
		for(SaleInvoiceSub sub:dataSubList){//如果修改前的数据里面没有现有数据，说明页面上做了删除操作
			Long dataId = sub.getId();
			Boolean result = false;
			for(int i=0;i<sublist.size();i++){
				if(sublist.get(i).getId().longValue() == dataId.longValue()){
					result  =true;
				}
			}
			if(result == false){
				SaleInvoiceSub aa = subDao.find(dataId);
				deleteSubList.add(aa);
			}
		}
		for(SaleInvoiceSub s:deleteSubList){//删除对应数据
			saleInvoice.getSubs().remove(s);
			s.setParentId(null);
			subDao.delete(s);
		}
	}
}
