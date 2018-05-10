package com.cbmie.genMac.stock.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.logistics.entity.SendGoodsGoods;
import com.cbmie.genMac.logistics.service.SendGoodsService;
import com.cbmie.genMac.stock.dao.PlanStockDao;
import com.cbmie.genMac.stock.dao.PlanStockDetailDao;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.cbmie.woodNZ.stock.service.InStockService;
import com.cbmie.genMac.stock.entity.PlanStock;
import com.cbmie.genMac.stock.entity.PlanStockDetail;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 盘库service
 */
@Service
@Transactional
public class PlanStockService extends BaseService<PlanStock, Long> {
	
	@Autowired
	private PlanStockDao planStockDao;
	
	@Autowired
	private PlanStockDetailDao planStockDetailDao;
	
	@Autowired
	private SendGoodsService sendGoodsService;
	
	@Autowired
	private InStockService inStockService;

	@Override
	public HibernateDao<PlanStock, Long> getEntityDao() {
		return planStockDao;
	}
	
	public boolean planStock() {
		// 找出入库
		List<InStock> inStockList = inStockService.getAll();
		// 找出生效的出库
		List<SendGoods> sendGoodsList = sendGoodsService.findBy("state", "生效");
		
		// 出库map<入库单号, 出库对象集合(一个入库单可能对应多个出库单)>
		Map<String, List<SendGoods>> outMap = new HashMap<String, List<SendGoods>>();
		// 放入→出库map
		for (SendGoods sendGoods : sendGoodsList) {
			if (outMap.get(sendGoods.getInStockId()) == null) {
				List<SendGoods> sendGoodsMapList = new ArrayList<SendGoods>();
				sendGoodsMapList.add(sendGoods);
				outMap.put(sendGoods.getInStockId(), sendGoodsMapList);
			} else {
				outMap.get(sendGoods.getInStockId()).add(sendGoods);
			}
		}
		// 实例化盘库对象
		PlanStock ps = new PlanStock();
		User currentUser = UserUtil.getCurrentUser();
		ps.setCreaterNo(currentUser.getLoginName());
		ps.setCreaterName(currentUser.getName());
		ps.setCreaterDept(currentUser.getOrganization().getOrgName());
		ps.setCreateDate(new Date());
		// 保存主表
		planStockDao.save(ps);
		// 循环迭代入库
		for (InStock inStock : inStockList) {
			// key(商品名称/规格型号),value(盘库明细)
			Map<String, PlanStockDetail> psdMap = new HashMap<String, PlanStockDetail>();
			// 入库商品
			List<InStockGoods> inStockGoodsList = inStock.getInStockGoods();
			for (InStockGoods inStockGoods : inStockGoodsList) {
				// 构造盘库明细
				PlanStockDetail psd = generateDetailByIn(inStock, inStockGoods);
				psdMap.put(psd.getGoodsNameSpecification(), psd);
			}
			// 根据入库单号获取出库单集合
			List<SendGoods> sendGoodsMapList = outMap.get(inStock.getInStockId());
			if (sendGoodsMapList != null) {
				for (SendGoods sendGoods : sendGoodsMapList) {
					// 出库商品
					for (SendGoodsGoods sendGoodsGoods : sendGoods.getSendGoodsGoods()) {
						PlanStockDetail psd = psdMap.get(sendGoodsGoods.getGoodsCategory() + "/" + sendGoodsGoods.getSpecification());
						if (psd == null) {
							PlanStockDetail psdNew = generateDetailByOut(inStock, sendGoods, sendGoodsGoods);
							psdMap.put(psdNew.getGoodsNameSpecification(), psdNew);
						} else {
							psd.setSendGoodsNo(sendGoods.getSendGoodsNo());
							psd.setSendGoodsAmount(sendGoodsGoods.getAmount());
							psd.setSendDate(sendGoods.getSendDate());
						}
					}
				}
			}
			// 把盘库明细置入盘库
			for (Entry<String, PlanStockDetail> entry : psdMap.entrySet()) {
				ps.getPlanStockDetail().add(entry.getValue());
				// 保存子表
				entry.getValue().setParentId(ps.getId());
				planStockDetailDao.save(entry.getValue());
			}
		}
		return true;
	}
	
	private PlanStockDetail generateDetailByIn(InStock inStock, InStockGoods inStockGoods) {
		PlanStockDetail psd = new PlanStockDetail();
//		psd.setGoodsNameSpecification(inStockGoods.getGoodsCategory() + "/" + inStockGoods.getSpecification());
//		psd.setInvoiceNo(inStock.getInvoiceNo());
//		psd.setInStockId(inStock.getInStockId());
//		psd.setInStockAmount(inStockGoods.getAmount());
		psd.setInStockDate(inStock.getInStockDate());
		return psd;
	}
	
	private PlanStockDetail generateDetailByOut(InStock inStock, SendGoods sendGoods, SendGoodsGoods sendGoodsGoods) {
		PlanStockDetail psd = new PlanStockDetail();
		psd.setGoodsNameSpecification(sendGoodsGoods.getGoodsCategory() + "/" + sendGoodsGoods.getSpecification());
		psd.setInvoiceNo(sendGoods.getInvoiceNo());
		psd.setInStockId(inStock.getInStockId());
		psd.setInStockAmount(0d);
		psd.setInStockDate(inStock.getInStockDate());
		psd.setSendGoodsNo(sendGoods.getSendGoodsNo());
		psd.setSendGoodsAmount(sendGoodsGoods.getAmount());
		psd.setSendDate(sendGoods.getSendDate());
		return psd;
	}
}
