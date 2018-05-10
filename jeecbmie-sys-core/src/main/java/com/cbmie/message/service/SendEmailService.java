package com.cbmie.message.service;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cbmie.common.utils.Email;
import com.cbmie.common.utils.PropertiesLoader;
@Service
public class SendEmailService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String mailhost;
	private String mailfrom;
	private String mailusername;
	private String mailpassword;
	private String pcURL;
	private String mobileURL;
	public void initProperties(){
		PropertiesLoader p = new PropertiesLoader("javaMail.properties");
		mailhost = p.getProperty("ip");
		mailfrom = p.getProperty("mailfrom");
		mailusername = p.getProperty("username");
		mailpassword = p.getProperty("password");
		pcURL = p.getProperty("pcURL");
		mobileURL = p.getProperty("mobileURL");
	}
	/**
	 * contents 中包含subject 主题 content 内容
	 * */
	public void sendMail(List<Map<String,String>> subject_contents,List<String> emails) {
		if(subject_contents.size()==0 || emails.size()==0){
			return ;
		}
		initProperties();
		for(Map<String,String> map : subject_contents){
			String content = map.get("content");
			String subject = map.get("subject");
			if(StringUtils.isBlank(content) && StringUtils.isBlank(subject)){
				continue;
			}
			for(String email : emails){
				doSend(content, subject, email);
			}
		}
	}

	public void doSend(String content, String subject, String emailaddr) {
		

		Email sendemail = new Email();
		try {
			sendemail.sendMail(mailhost, mailfrom, emailaddr, subject,
					content + " 请登录工作平台（" + pcURL + "）办理。", true, mailusername, mailpassword);
		} catch (SendFailedException se) {
			logger.error("Send email address errors", se);
		} catch (MessagingException me) {
			logger.error("Messaging exception", me);
		}
	}
}
