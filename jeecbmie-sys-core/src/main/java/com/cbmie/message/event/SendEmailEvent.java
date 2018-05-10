package com.cbmie.message.event;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

public class SendEmailEvent extends ApplicationEvent {

	private static final long serialVersionUID = -9128287559875847022L;
	private List<Map<String,String>> subject_contents;
	private List<String> emails;

	public SendEmailEvent(Object source) {
		super(source);
	}

	public List<Map<String, String>> getSubject_contents() {
		return subject_contents;
	}

	public void setSubject_contents(List<Map<String, String>> subject_contents) {
		this.subject_contents = subject_contents;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

}
