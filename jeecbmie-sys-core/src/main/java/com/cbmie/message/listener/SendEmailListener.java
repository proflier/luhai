package com.cbmie.message.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.message.event.SendEmailEvent;
import com.cbmie.message.service.SendEmailService;
@Component
@EnableAsync
public class SendEmailListener implements ApplicationListener<SendEmailEvent> {

	@Override
	@Async
	public void onApplicationEvent(SendEmailEvent event) {
		SendEmailService service = SpringContextHolder.getBean(SendEmailService.class);
		service.sendMail(event.getSubject_contents(),event.getEmails());
	}

}
