package com.cbmie.message.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.message.SmsSendTool;
@Service
public class SendSmsService {

	@Transactional
	public void doSend(List<String> contents,List<String> mobiles){
		SmsSendTool smsSendTool =SpringContextHolder.getBean("smsSendTool");
		smsSendTool.sendSms(contents, mobiles);
	}
}
