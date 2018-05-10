package com.cbmie.lh.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.financial.dao.PaymentConfirmChildDao;
import com.cbmie.lh.financial.dao.PaymentTypeRelationDao;
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
import com.cbmie.lh.financial.entity.PaymentTypeRelation;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.service.ShipTraceService;
import com.cbmie.lh.sale.service.SaleContractService;
import com.cbmie.lh.utils.MyTagUtil;

@Service
@Transactional
public class PaymentConfirmChildService extends BaseService<PaymentConfirmChild, Long> {
	@Autowired
	private PaymentConfirmChildDao confirmDao;
	
	@Autowired
	private PaymentTypeRelationDao paymentTypeRelationDao;
	
	@Autowired
	private ShipTraceService shipTraceService;
	
	@Autowired
	private SaleContractService contractService;

	@Override
	public HibernateDao<PaymentConfirmChild, Long> getEntityDao() {
		return confirmDao;
	}

	public PaymentTypeRelation getPaymentRelation(String value) {
		return paymentTypeRelationDao.findUniqueBy("payType", value);
	}

	public Integer deleteByPid(Long pid) {
		return confirmDao.deleteByPid(pid);
	}
	
	/**
	 * 摘要
	 */
	public String digest(PaymentConfirmChild pcc) {
		String code = pcc.getCode();
		if (StringUtils.isNotBlank(code)) {
			String paymentType = pcc.getPaymentType();
			if (StringUtils.isNotBlank(paymentType)) {
				if (paymentType.equals("ship")) {
					ShipTrace shipTrace = shipTraceService.getShipByNo(code);
					code = (shipTrace == null ? "" : shipTrace.getNoAndName());
				} else if (paymentType.equals("sale")) {
					code = contractService.getContractNoCustomer(code);
				} else if (paymentType.equals("kehu")) {
					code = MyTagUtil.getAffiBaseInfoByCode(code);
				}
			} else {
				code = "";
			}
		}
		return code == null ? "" : code;
	}

}
