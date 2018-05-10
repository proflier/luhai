package com.cbmie.genMac.quartz;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.Email;
import com.cbmie.common.utils.PropertiesLoader;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.genMac.foreignTrade.entity.AgencyAgreement;
import com.cbmie.genMac.foreignTrade.entity.ImportContract;
import com.cbmie.genMac.foreignTrade.service.AgencyAgreementService;
import com.cbmie.genMac.foreignTrade.service.ImportContractService;
import com.cbmie.system.entity.Inform;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.InformService;
import com.cbmie.system.service.UserService;

public class MarginInformJob implements Job{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private ImportContractService importContractService;
	
	private AgencyAgreementService agencyAgreementService;
	
	private UserService userService;
	
	private InformService informService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Trigger trigger = context.getTrigger();
		String jobName = trigger.getJobKey().toString();
		int emails = 0;
		try {
			logger.debug("{} job start time:{}", new Object[]{jobName, DateUtils.getDateTime()});
			// 获取JobExecutionContext中的service对象
			SchedulerContext sc = context.getScheduler().getContext();
			importContractService = (ImportContractService) sc.get("importContractService");
			agencyAgreementService = (AgencyAgreementService) sc.get("agencyAgreementService");
			informService = (InformService) sc.get("informService");
			userService = (UserService) sc.get("userService");
			// 查出符合保证金催缴的进口合同
			List<ImportContract> importContractList = importContractService.findMarginInform();
			for (ImportContract importContract : importContractList) {
				int days = daysBetween(importContract.getOpenCreditDate());
				// 获取代理协议中的发函人
				AgencyAgreement agencyAgreement = agencyAgreementService.getNoLoad(importContract.getAgencyAgreementId());
				User user = userService.getNoLoad(agencyAgreement.getSentLetterPerson());
				// 以三种方式提醒发函人（邮件、短信、通知）
				// 首先查看通知表里是否存在该进口合同的通知，存在则修改剩余天数，不存在则新增通知
				PropertiesLoader p = new PropertiesLoader("javaMail.properties");
				String subject = StringUtils.replacePlaceholder(p.getProperty("marginSubject"), importContract.getContractNO());// 标题
				String content = StringUtils.replacePlaceholder(p.getProperty("marginContent"), agencyAgreement.getClient());// 内容
				Inform inform = informService.findBusinessInform("importContract", importContract.getId());
				if (inform == null) {
					insertInform(importContract.getId(), user.getName(), content, days);
				} else {
					inform.setResidueDays(days);
					inform.setUpdateDate(new Date());
					informService.update(inform);
				}
				sendEmail(p, subject, content, user.getEmail());
			}
			emails = importContractList.size();// 设置发送邮件个数
		} catch (Exception ex) {
			logger.error("{} job error", new Object[]{jobName, ex});
		} finally {
			logger.debug("{} job send {} emails end time:{}", new Object[]{jobName, emails, DateUtils.getDateTime()});
		}
	}
	
	/**
	 * 计算距离今天的天数 
	 */
	private int daysBetween (Date date) {
		Date now = new Date();
		return (int) ((date.getTime() - now.getTime()) / (1000 * 3600 * 24));
	}
	
	/**
	 * 发邮件
	 */
	private void sendEmail(PropertiesLoader p, String subject, String content, String emailaddr) {
		String mailhost = p.getProperty("ip");
		String mailfrom = p.getProperty("mailfrom");
		String mailusername = p.getProperty("username");
		String mailpassword = p.getProperty("password");

		Email sendemail = new Email();
		try {
			sendemail.sendMail(mailhost, mailfrom, emailaddr, subject, content, true, mailusername, mailpassword);
		} catch (SendFailedException se) {
			logger.error("Send email address errors", se);
		} catch (MessagingException me) {
			logger.error("Messaging exception", me);
		}
	}
	
	/**
	 * 新增通知
	 */
	private void insertInform(Long businessId, String name, String content, int days){
		Inform inform = new Inform();
		inform.setClassName("importContract");
		inform.setBusinessId(businessId);
		inform.setInformPerson(name);
		inform.setInformContent(content);
		inform.setResidueDays(days);
		inform.setCreateDate(new Date());
		informService.save(inform);
	}

}
