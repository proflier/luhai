package com.cbmie.lh.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.cbmie.accessory.entity.Accessory;
import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.activiti.service.ProcessEntityService;
import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.baseinfo.entity.WoodGk;
import com.cbmie.common.utils.ConvertUtils;
import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
import com.cbmie.lh.baseInfo.entity.GoodsInformation;
import com.cbmie.lh.baseInfo.entity.GoodsType;
import com.cbmie.lh.baseInfo.entity.Signet;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.lh.baseInfo.service.GoodsInformationService;
import com.cbmie.lh.baseInfo.service.GoodsTypeService;
import com.cbmie.lh.baseInfo.service.SignetService;
import com.cbmie.lh.feedback.entity.DiscussGroup;
import com.cbmie.lh.feedback.service.DiscussGroupService;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
import com.cbmie.lh.financial.entity.Serial;
import com.cbmie.lh.financial.service.InputInvoiceSubService;
import com.cbmie.lh.financial.service.PaymentConfirmChildService;
import com.cbmie.lh.financial.service.SerialService;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.service.ShipTraceService;
import com.cbmie.lh.sale.entity.SaleInvoiceSub;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
import com.cbmie.lh.sale.service.SaleDeliveryGoodsService;
import com.cbmie.lh.sale.service.SaleInvoiceOutRelService;
import com.cbmie.lh.sale.service.SaleInvoiceService;
import com.cbmie.lh.sale.service.SaleInvoiceSubService;
import com.cbmie.lh.sale.service.SaleSettlementOutRelService;
import com.cbmie.system.entity.CountryArea;
import com.cbmie.system.entity.Permission;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.PermissionService;
import com.cbmie.system.utils.OrgUserBean;
import com.cbmie.system.utils.OrgUserUtils;
import com.cbmie.system.utils.UserUtil;

/**
 * 自定义标签工具类
 * @author 
 * @version 
 */
public class MyTagUtil {
	
	private static BaseInfoUtilService bius = SpringContextHolder.getBean(BaseInfoUtilService.class);
	
	private static GoodsInformationService infoService = SpringContextHolder.getBean(GoodsInformationService.class);
	
	private static GoodsTypeService goodsTypeService = SpringContextHolder.getBean(GoodsTypeService.class);
	
	private static GoodsIndexService indexService = SpringContextHolder.getBean(GoodsIndexService.class);
	
	private static SaleDeliveryGoodsService outStockGoodsService = SpringContextHolder.getBean(SaleDeliveryGoodsService.class);
	
	private static SaleInvoiceSubService saleInvoiceSubService = SpringContextHolder.getBean(SaleInvoiceSubService.class);
	
	private static SignetService signetService = SpringContextHolder.getBean(SignetService.class);
	
	private static DiscussGroupService groupService = SpringContextHolder.getBean(DiscussGroupService.class);
	
	private static AccessoryService accessoryService = SpringContextHolder.getBean(AccessoryService.class);
	
	private static SaleSettlementOutRelService saleSettlementOutRelService = SpringContextHolder.getBean(SaleSettlementOutRelService.class);
	
	private static SaleInvoiceOutRelService saleInvoiceOutRelService = SpringContextHolder.getBean(SaleInvoiceOutRelService.class);
	
	private static SaleInvoiceService saleInvoiceService = SpringContextHolder.getBean(SaleInvoiceService.class);
	
	private static InputInvoiceSubService inputInvoiceSubService = SpringContextHolder.getBean(InputInvoiceSubService.class);
	
	private static SerialService serialService = SpringContextHolder.getBean(SerialService.class);
	
	private static ShipTraceService shipTraceService = SpringContextHolder.getBean(ShipTraceService.class);
	
	private static PermissionService permissionService = SpringContextHolder.getBean(PermissionService.class);
	
	private static PaymentConfirmChildService paymentConfirmChildService = SpringContextHolder.getBean(PaymentConfirmChildService.class);
	
	private static ProcessEntityService procEntityService = SpringContextHolder.getBean(ProcessEntityService.class);
	
	public static String getAffiBaseInfoByCode(String code){
		return bius.getAffiBaseInfoByCode(code);
	}
	
	public static CountryArea getCountryArea(Integer id){
		return bius.getCountryArea(id);
	}
	
	public static GoodsIndex getGoodsIndex(Long id) {
		return indexService.get(id);
	}
	
	public static GoodsInformation getGoodsInformationByCode(String code) {
		return infoService.getEntityByCode(code);
	}
	
	public static GoodsType getGoodsTypeByCode(String code) {
		return goodsTypeService.getEntityByCode(code);
	}
	
	public static WoodAffiBankInfo getAffiBankInfoById(Long id) {
		return bius.getAffiBankInfoById(id);
	}
	
	public static String unescapeHtml(String str) {
		for (int i = 0; i < 5; i++) {
			str = org.apache.commons.lang.StringEscapeUtils.unescapeHtml(str);
		}
		String[] special = new String[] { "&", "<", ">", "'", "\"" };
		for (String s : special) {
			if (str.contains(s)) {
				String escape = org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
				str = StringUtils.replace(str, s, escape);
			}
		}
		return str;
	}
	
	public static String escapeHtml(String str) {
		String[] special = new String[] { "&", "<", ">", "'", "\"" };
		for (String s : special) {
			if (str.contains(s)) {
				String escape = org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
				str = StringUtils.replace(str, s, escape);
			}
		}
		return str;
	}
	
	public static User getUserByLoginName(String loginName) {
		return bius.getUserByLoginName(loginName);
	}
	
	public static User getUserById(Integer id) {
		return bius.getUserById(id);
	}
	
	public static List<Map<String,Object>> getOutStockGoodsList(String saleContractNo) {
		return outStockGoodsService.getOutStockGoodsList(saleContractNo);
	}
	
	public static List<SaleInvoiceSub> getSaleInvoiceSubService(Long saleInvoiceId) {
		return saleInvoiceSubService.getOldListByPid(saleInvoiceId);
	}
	
	public static List<WoodGk> gkList() {
		return bius.gkList(null);
	}
	
	public static String getGKMByCode(String code) {
		return bius.getGKMByCode(code);
	}
	
	public static Signet getEntityByCode(String code) {
		return signetService.getEntityByCode(code);
	}
	
	public static String getOrgNameById(Integer orgId) {
		return bius.getOrgNameById(orgId);
	}
	
	public static DiscussGroup getDiscussGroup(Long id) {
		return groupService.get(id);
	}
	
	public static List<Accessory> getAccessory(String accParentId, String accParentEntity){
		return accessoryService.getListByPidAndEntity(accParentId, accParentEntity);
	}
	
	public static List<Map<String,Object>> getOutStackList(Long settlementId) {
		return saleSettlementOutRelService.getOutStackList(settlementId);
	}
	
	public static List<Map<String,Object>> getOutStackListSale(Long invoiceId) {
		return saleInvoiceOutRelService.getOutStackList(invoiceId);
	}
	
	public static List<SaleSettlementGoods> SaleInvoicepubList(Long invoiceId) {
		return saleInvoiceService.SaleInvoicepubList(invoiceId);
	}
	
	public static List<InputInvoiceSub> getInputInvoiceSubBySaleNo(String saleConctractNo) {
		return inputInvoiceSubService.getInputInvoiceSubBySaleNo(saleConctractNo);
	}
	
	public static List<Serial> getSerialBySaleNo(String saleConctractNo) {
		return serialService.getSerialBySaleNo(saleConctractNo);
	}
	
	public static String replaceHtml(String html) {
		return StringUtils.replaceHtml(html);
	}
	
	public static ShipTrace getShipByNo(String shipNo) {
		return shipTraceService.getShipByNo(shipNo);
	}
	
	public static Boolean shiro(String permCode) {
		User user = UserUtil.getCurrentUser();
		List<Permission> pList = permissionService.getPermissions(user.getId());
		for (Permission permission : pList) {
			if (permCode.equals(permission.getPermCode())) {
				return true;
			}
		}
		return false;
	}
	
	public static String digest(PaymentConfirmChild pcc) {
		return paymentConfirmChildService.digest(pcc);
	}
	
	public static List<Map<String,String>> getAllProcIns() {
		return procEntityService.getAllProcIns();
	}
	
	public static String getCompanyUser(String mandatary) throws Exception {
		List<OrgUserBean> orgUserBeanList = OrgUserUtils.getOrgUserList();
		orgUserBeanList = ConvertUtils.deepCopy(orgUserBeanList);
		Map<String, OrgUserBean> orgUserBeanListMap = new HashMap<String, OrgUserBean>();
		for (OrgUserBean oub : orgUserBeanList) {
			orgUserBeanListMap.put(oub.getId(), oub);
		}
		for (OrgUserBean oub : orgUserBeanList) {
			if (orgUserBeanListMap.get(oub.getPid()) != null) {
				OrgUserBean parent = orgUserBeanListMap.get(oub.getPid());
				parent.getOrgUserBeanList().add(oub);
			}
		}
		OrgUserBean rootObj = new OrgUserBean();
		for (Entry<String, OrgUserBean> entry : orgUserBeanListMap.entrySet()) {
			for (OrgUserBean oub : entry.getValue().getOrgUserBeanList()) {
				if (orgUserBeanListMap.get(oub.getId()) != null) {
					oub = orgUserBeanListMap.get(oub.getId());
				}
			}
			if (StringUtils.isBlank(entry.getValue().getPid()) || orgUserBeanListMap.get(entry.getValue().getPid()) == null) {
				BeanUtils.copyProperties(rootObj, entry.getValue());
			}
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<tree id='mandatary' name='mandatary' caption='" + rootObj.getName() + "' title='受委托人'>");
		getCompanyUserRecursive(rootObj.getOrgUserBeanList(), sb, mandatary);
		sb.append("</tree>");
		return sb.toString();
	}
	
	private static void getCompanyUserRecursive(List<OrgUserBean> list, StringBuffer sb, String mandatary) {
		for (OrgUserBean oub : list) {
			boolean checkboxFlag = true;
			boolean checked = false;
			if (oub.getOrgUserBeanList().size() > 0 || !oub.getType().equals("2")) {
				checkboxFlag = false;
			}
			if (checkboxFlag) {
				if (oub.getLoginName().equals(mandatary)) {
					checked = true;
				}
			}
			sb.append("<item id='" + oub.getId() + "' caption='" + oub.getName() + "' value='" + oub.getLoginName() + "' checkbox='" + checkboxFlag + "' checked='" + checked + "' checkboxhref='checkTreeItem(\"" + oub.getId() + "\");'>");
			getCompanyUserRecursive(oub.getOrgUserBeanList(), sb, mandatary);
			sb.append("</item>");
		}
	}
	
}
