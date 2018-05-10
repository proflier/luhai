package com.cbmie.lh.relation.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.credit.service.PayApplyService;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
import com.cbmie.lh.feedback.entity.ThemeMember;
import com.cbmie.lh.feedback.service.FeedbackThemeService;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.financial.service.PaymentConfirmService;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.service.LhBillsService;
import com.cbmie.lh.logistic.service.ShipTraceService;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.service.PurchaseContractService;
import com.cbmie.lh.relation.service.RelationService;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.service.SaleContractService;
import com.cbmie.lh.sale.service.SaleDeliveryService;
import com.cbmie.lh.sale.service.SaleInvoiceService;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.OutStock;
import com.cbmie.lh.stock.service.InStockService;
import com.cbmie.lh.stock.service.OutStockService;
import com.cbmie.system.entity.User;

/**
 * 摸块之间的关联显示
 */
@Controller
@RequestMapping("realtion")
public class RelationController extends BaseController {

	@Autowired
	private RelationService relationService;

	@Autowired
	private InStockService inStockService;
	
	@Autowired
	private FeedbackThemeService themeService;

	@Autowired
	private OutStockService outStockService;

	@Autowired
	private SaleDeliveryService saleDeliveryService;

	@Autowired
	private SaleContractService saleContractService;

	@Autowired
	private PurchaseContractService purchaseContractService;

	@Autowired
	private LhBillsService lhBillsService;

	@Autowired
	private ShipTraceService shipTraceService;

	@Autowired
	private SaleInvoiceService saleInvoiceService;

	@Autowired
	private PaymentConfirmService paymentConfirmService;

	@Autowired
	private PayApplyService payApplyService;

	
	
	@RequestMapping(value = "detail/{type}/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, @PathVariable("type") String type,Model model) {
		model.addAttribute("relation", "1");
		if("inStock".equals(type)){
			model.addAttribute("inStock", inStockService.get(id));
			return "stock/inStockDetail";
		} else if("feeBack".equals(type)){
			FeedbackTheme feedbackTheme = themeService.get(id);
			getMember(feedbackTheme);
			model.addAttribute("feedbackTheme", feedbackTheme);
			model.addAttribute("action", "view");
			return "feedback/themeDetailRelation";
		}else if("outStock".equals(type)){
			model.addAttribute("outStock", outStockService.get(id));
			model.addAttribute("action", "view");
			return "stock/outStockForm";
		} else if("saleDelivery".equals(type)){
			model.addAttribute("saleDelivery", saleDeliveryService.get(id));
			model.addAttribute("action", "view");
			return "sale/saleDeliveryForm";
		} else if("saleContract".equals(type)){
			model.addAttribute("saleContract", saleContractService.get(id));
			model.addAttribute("action", "view");
			return "sale/saleContractDetail";
		} else if("purchaseContract".equals(type)){
			model.addAttribute("purchaseContract", purchaseContractService.get(id));
			return "purchase/contractDetail";
		} else if("shipTrace".equals(type)){
			model.addAttribute("shipTrace", shipTraceService.get(id));
			model.addAttribute("action", "view");
			return "logistic/shipTraceForm";
		} else if("saleInvoice".equals(type)){
			model.addAttribute("saleInvoice", saleInvoiceService.get(id));
			model.addAttribute("action", "view");
			return "sale/saleInvoiceForm";
		} else if("paymentConfirm".equals(type)){
			model.addAttribute("paymentConfirm", paymentConfirmService.get(id));
			return "financial/paymentConfirmDetail";
		}  else if("payApply".equals(type)){
			model.addAttribute("payApply", payApplyService.get(id));
			return "credit/payApplyDetail";
		}else if("lhBills".equals(type)){
			LhBills lhBills = lhBillsService.get(id);
			model.addAttribute("lhBills", lhBills);
			model.addAttribute("purchaseContractIdsOld", lhBills.getPurchaseContractIds());
			model.addAttribute("action", "view");
			return "logistic/billsForm";
		}else{
			return "";
		}
	}

	/**
	 * 根据部门id获取相关用户
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "userDetail/{orgId}", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findByOrgId(@PathVariable("orgId") Integer orgId) {
		return relationService.findUserByOrg(orgId);
	}

	@RequestMapping(value = "toPaymentDetail", method = RequestMethod.GET)
	public String toPaymentDetail() {
		return "relationDetail/paymentListDetail";
	}

	/**
	 * 根据innerContractNo获取到单列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "billsDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<LhBills> findBillByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findByInnerContractNo(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取付款列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "paymentDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<PaymentConfirm> findPayByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findPayByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取开证列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "creditDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<PayApply> findCreditByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findCreditByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取销售列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "saleDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleContract> findSaleByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findSaleByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取采购列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "purchaseDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseContract> findPurchaseByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findPurchaseByInner(innerContractNos);
	}
	
	/**
	 * 根据innerContractNo获取反馈列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "feeBackDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<FeedbackTheme> findFeeBackByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findFeeBackByInner(innerContractNos);
	}
	

	/**
	 * 根据innerContractNo获取入库列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "inStockDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<InStock> findInStockByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findInStockByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取放货列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "deliveryStockDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleDelivery> findDeliveryByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findDeliveryByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取出库列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "outStockDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<OutStock> findOutStockByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findOutStockByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取船舶动态列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "shipTrackDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> findShipTrackByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findShipTrackByInner(innerContractNos);
	}

	/**
	 * 根据innerContractNo获取销售发票列表
	 * 
	 * @param innerContractNos
	 * @return
	 */
	@RequestMapping(value = "saleInvoiceDetail/{innerContractNos}", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleInvoice> findSaleInvoiceByInner(@PathVariable("innerContractNos") String innerContractNos) {
		return relationService.findSaleInvoiceByInner(innerContractNos);
	}
	
	public void getMember(FeedbackTheme feedbackTheme){
		 String themeMemberIds = "";
		 String themeMembers = "";
		 String themeMemberKeyIds = "";
		 String themeMemberKeys = "";
		List<ThemeMember> members = feedbackTheme.getMembers();
		for(ThemeMember member : members){
			themeMemberIds = themeMemberIds+member.getUser().getId()+",";
			themeMembers = themeMembers + member.getUser().getName()+",";
			if("1".equals(member.getKeyFlag())){
				themeMemberKeyIds = themeMemberKeyIds+member.getUser().getId()+",";
				themeMemberKeys = themeMemberKeys+member.getUser().getName()+",";
			}
		}
		feedbackTheme.setThemeMemberIds(themeMemberIds.equals("")?"":themeMemberIds.substring(0, themeMemberIds.length()-1));
		feedbackTheme.setThemeMembers(themeMembers.equals("")?"":themeMembers.substring(0, themeMembers.length()-1));
		feedbackTheme.setThemeMemberKeyIds(themeMemberKeyIds.equals("")?"":themeMemberKeyIds.substring(0, themeMemberKeyIds.length()-1));
		feedbackTheme.setThemeMemberKeys(themeMemberKeys.equals("")?"":themeMemberKeys.substring(0, themeMemberKeys.length()-1));
	}

}
