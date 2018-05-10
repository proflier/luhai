package com.cbmie.message.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.cbmie.message.event.SendSmsEvent;
import com.cbmie.message.service.SendSmsService;
import com.cbmie.common.utils.SpringContextHolder;
@Component
@EnableAsync
public class SendSmsListener implements ApplicationListener<SendSmsEvent> {

	@Override
	@Async
	public void onApplicationEvent(SendSmsEvent event) {
		SendSmsService service = SpringContextHolder.getBean(SendSmsService.class);
		service.doSend(event.getContents(), event.getMobiles());
	}
}
