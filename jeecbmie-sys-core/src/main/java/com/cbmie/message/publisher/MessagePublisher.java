package com.cbmie.message.publisher;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.cbmie.message.event.SendEmailEvent;
import com.cbmie.message.event.SendSmsEvent;
@Component
public class MessagePublisher implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	public void publishSmsEvent(Object source,List<String> contents,List<String> mobiles){
		SendSmsEvent event = new SendSmsEvent(source);
		event.setContents(contents);
		event.setMobiles(mobiles);
		publishEvent(event);
	}
	
	public void publishEmailEvent(Object source,List<Map<String,String>> subject_contents,List<String> emails){
		SendEmailEvent event = new SendEmailEvent(source);
		event.setSubject_contents(subject_contents);
		event.setEmails(emails);
		publishEvent(event);
	}
	public void publishEvent(ApplicationEvent event){
		this.publisher.publishEvent(event);
	}
}
