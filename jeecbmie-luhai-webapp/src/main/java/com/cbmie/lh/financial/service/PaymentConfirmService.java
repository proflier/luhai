package com.cbmie.lh.financial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.Reflections;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.financial.dao.PaymentConfirmDao;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
import com.cbmie.lh.utils.MyTagUtil;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;

@Service
@Transactional
public class PaymentConfirmService extends BaseService<PaymentConfirm, Long> {
	
	@Autowired
	private PaymentConfirmDao confirmDao;
	
	@Autowired
	private PaymentConfirmChildService paymentConfirmChildService;
	
	@Override
	public HibernateDao<PaymentConfirm, Long> getEntityDao() {
		return confirmDao;
	}

	public PaymentConfirm findByConfirmNo(PaymentConfirm paymentConfirm) {
		return confirmDao.findByConfirmNo(paymentConfirm);
	}

	public List<WoodAffiBaseInfo> getListNoYH() {
		User currentUser = UserUtil.getCurrentUser();
		return confirmDao.getListNoYH(currentUser.getLoginName());
	}
	
	public void exportPdf(Map<String, Object> data, PaymentConfirm pc) {
		List<String> fieldNames = Reflections.getFiledNames(pc);
		for (String fieldName : fieldNames) {
			Object value = Reflections.getFieldValue(pc, fieldName);
			// "子表数据"和"需要页面进行js二次请求获取的数据"需要特殊处理
			if (fieldName.equals("paymentConfirmChildList")) {
				List<PaymentConfirmChild> pccList = pc.getPaymentConfirmChildList();
				List<PaymentConfirmChild> rPccList = new ArrayList<PaymentConfirmChild>();
				for (int i = 0; i < pccList.size(); i++) {
					PaymentConfirmChild pcc = new PaymentConfirmChild();
					String feeCategory = DictUtils.getDictLabelByCode(pccList.get(i).getFeeCategory(), "feeCategory", "");
					pcc.setFeeCategory(feeCategory);
					String paymentType = pccList.get(i).getPaymentType();
					if (StringUtils.isNotBlank(paymentType)) {
						if (paymentType.equals("sale")) {
							paymentType = "销售合同";
						} else if (paymentType.equals("kehu")) {
							paymentType = "客户";
						} else {
							paymentType = "船编号";
						}
					}
					pcc.setPaymentType(paymentType == null ? "" : paymentType);
					pcc.setCode(paymentConfirmChildService.digest(pccList.get(i)));
					pcc.setShareAmount(pccList.get(i).getShareAmount());
					rPccList.add(pcc);
					value = rPccList;
				}
			} else if (fieldName.equals("receiveUnitName")) {//收款单位名称
				value = MyTagUtil.getAffiBaseInfoByCode(String.valueOf(value));
			} else if (fieldName.equals("remitBank")) {//银行
				value = MyTagUtil.getAffiBankInfoById(Long.valueOf(String.valueOf(value))).getBankName();
			} else if (fieldName.equals("currency")) {//币种
				value = DictUtils.getDictLabelByCode(String.valueOf(value), "currency", "");
			} else if (fieldName.equals("payContent")) {//付款内容
				value = DictUtils.getDictLabelByCode(String.valueOf(value), "paymentContent", "");
			} else if (fieldName.equals("payType")) {//付款方式
				value = DictUtils.getDictLabelByCode(String.valueOf(value), "paymentMethod", "");
			} else if (fieldName.equals("payUnit")) {//付款单位名称
				value = MyTagUtil.getAffiBaseInfoByCode(String.valueOf(value));
			} else if (fieldName.equals("businessManager")) {//业务经办人
				User user = (User)MyTagUtil.getUserByLoginName(String.valueOf(value));
				if(user!=null){
					value = user.getName();
					data.put("businessManagerDept", user.getOrganization().getOrgName());
				}
				
			}
			data.put(fieldName, value);
		}
	}

	public String getNOYH(String loginName) {
		return confirmDao.getNOYH(loginName);
	}

	public void saveBusinessPermission(PaymentConfirm paymentConfirm) {
		List<PaymentConfirmChild> paymentConfirmChildList = paymentConfirm.getPaymentConfirmChildList();
		String returnValue ="";
		if(paymentConfirmChildList.size()>0){
			for(PaymentConfirmChild paymentConfirmChild :paymentConfirmChildList){
				if(StringUtils.isNotBlank(paymentConfirmChild.getRelLoginNames())){
					if(StringUtils.isNotBlank(returnValue)){
						returnValue = returnValue +","+paymentConfirmChild.getRelLoginNames();
					}else{
						returnValue = paymentConfirmChild.getRelLoginNames();
					}
				}
			}
			paymentConfirm.setRelLoginNames(returnValue);
			confirmDao.save(paymentConfirm);
		}
	}
}
