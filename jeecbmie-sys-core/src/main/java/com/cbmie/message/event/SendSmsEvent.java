package com.cbmie.message.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

public class SendSmsEvent extends ApplicationEvent {

	private static final long serialVersionUID = 4949129156650865518L;

	private List<String> contents;
	private List<String> mobiles;
	
	public SendSmsEvent(Object source) {
		super(source);
	}
	
	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public List<String> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<String> mobiles) {
		this.mobiles = mobiles;
	}
}
