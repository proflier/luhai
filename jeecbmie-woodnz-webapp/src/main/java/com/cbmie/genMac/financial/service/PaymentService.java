package com.cbmie.genMac.financial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.PaymentDao;
import com.cbmie.genMac.financial.entity.Expense;
import com.cbmie.genMac.financial.entity.Payment;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 付费service
 */
@Service
@Transactional
public class PaymentService extends BaseService<Payment, Long> {

	@Autowired
	private PaymentDao paymentDao;

	@Override
	public HibernateDao<Payment, Long> getEntityDao() {
		return paymentDao;
	}

	
	public void save(Expense expense, String paymentChildJson) {
		// 转成标准的json字符串
		paymentChildJson = StringEscapeUtils.unescapeHtml4(paymentChildJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<Payment> paymentList = new ArrayList<Payment>();
		try {
			JsonNode jsonNode = objectMapper.readTree(paymentChildJson);
			for (JsonNode jn : jsonNode) {
				Payment payment = objectMapper.readValue(jn.toString(), Payment.class);
				paymentList.add(payment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取commodity中Commodity持久化对象
		List<Payment> dataPaymentList = expense.getPaymentList();
		// 将数据库数据放入映射
		Map<Long, Payment> dataPaymentMap = new HashMap<Long, Payment>(); 
		for (Payment dataPayment : dataPaymentList) {
			dataPaymentMap.put(dataPayment.getId(), dataPayment);
		}
		// 排除没有发生改变的
		for (Payment dataPayment : dataPaymentList) {
			if (paymentList.contains(dataPayment)) {
				paymentList.remove(dataPayment);
				dataPaymentMap.remove(dataPayment.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (Payment payment : paymentList) {
			if (payment.getId() == null) {
				insert(expense.getId(), payment, dataPaymentList); // 新增 
			}
			Payment dataPayment = dataPaymentMap.get(payment.getId());
			if (dataPayment != null) {
				update(dataPayment, payment); // 修改
				dataPaymentMap.remove(payment.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, Payment> entry : dataPaymentMap.entrySet()) {
			dataPaymentList.remove(entry.getValue());
			paymentDao.delete(entry.getKey());
		}
	}

	private void update(Payment dataPayment, Payment paymentChild) {
		User currentUser = UserUtil.getCurrentUser();
		dataPayment.setDocumentNO(paymentChild.getDocumentNO());
		dataPayment.setPaymentChildType(paymentChild.getPaymentChildType());
		dataPayment.setExpenseIdentifyWay(paymentChild.getExpenseIdentifyWay());
		dataPayment.setImportContract(paymentChild.getImportContract());
		dataPayment.setExpenseNature(paymentChild.getExpenseNature());
		dataPayment.setBillingCustomer(paymentChild.getBillingCustomer());
		dataPayment.setOriginalCurrency(paymentChild.getOriginalCurrency());
		dataPayment.setDepartmentStaff(paymentChild.getDepartmentStaff());
		dataPayment.setPrepayCustomer(paymentChild.getPrepayCustomer());
		dataPayment.setUpdaterNo(currentUser.getLoginName());
		dataPayment.setUpdaterName(currentUser.getName());
		dataPayment.setUpdateDate(new Date());
	}
	
	private void insert(Long pid, Payment payment, List<Payment> dataPaymentList) {
		User currentUser = UserUtil.getCurrentUser();
		payment.setParentId(pid);
		payment.setCreaterNo(currentUser.getLoginName());
		payment.setCreaterName(currentUser.getName());
		payment.setCreateDate(new Date());
		dataPaymentList.add(payment);
	}
	
	
	
	
	
}
