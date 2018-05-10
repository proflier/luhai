package com.cbmie.message.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbmie.common.utils.SpringContextHolder;
public class SmsRecordPollJob implements Job{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("begin sms retrieval");
		SmsRecordPoll smsRecordPoll = SpringContextHolder.getBean("smsRecordPoll");
		smsRecordPoll.retrievalSend();
	}
}
