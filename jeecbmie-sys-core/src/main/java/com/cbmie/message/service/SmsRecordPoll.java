package com.cbmie.message.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.message.SmsSendTool;
import com.cbmie.message.entity.SmsStateRecord;
@Component(value="smsRecordPoll")
public class SmsRecordPoll {
	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	private SmsSendTool smsSendTool;
	private void setInvalidState(){
		String sql = "update Sms_State_Record a set a.state='3' where a.state='0' and a.create_Time<DATE_SUB(now(),INTERVAL 1 DAY)";
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	private List<SmsStateRecord> getRecords(){
		String sql = "select * from Sms_State_Record a where a.state='0' ";
		return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(SmsStateRecord.class).list();
	}
	@Transactional
	public void retrievalSend(){
		setInvalidState();
		List<SmsStateRecord> records =  getRecords();
		if(records==null || records.size()<1){
			return;
		}else{
			smsSendTool.retrievalSend(records);
		}
	}
}
