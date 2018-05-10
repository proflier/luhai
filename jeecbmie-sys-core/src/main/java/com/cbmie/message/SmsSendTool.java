package com.cbmie.message;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cbmie.common.utils.PropertiesLoader;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.message.entity.SmsStateRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Component(value="smsSendTool")
@Scope(value="singleton")
public class SmsSendTool {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected SessionFactory sessionFactory;
	
	private String serviceUrl;
	private String account;
	private String passwd;
	
	public SmsSendTool(){
		PropertiesLoader p = new PropertiesLoader("smsplatform.properties");
		serviceUrl = p.getProperty("azServiceUrl");
		account = p.getProperty("azAccount");
		passwd = p.getProperty("azPasswd");
	}
	
	public void sendSms(List<String> contents,List<String> mobiles){
		if(contents==null || contents.size()==0){
			logger.info(" contents is null ");
			return;
		}else{
			for(String temp : contents){
				sendSms(temp,mobiles);
			}
		}
	}
	public void retrievalSend(List<SmsStateRecord> records){
		if(records==null || records.size()<1){
			return;
		}
		try {
			int reqId = 1;
			String resStr = getLoginToken(reqId);
			reqId++;
			JSONObject resJson = JSONObject.fromObject(resStr);
			if(resJson.has("result")){
				JSONObject resultJson = resJson.getJSONObject("result");
				if(resultJson.getBoolean("result")){
					//令牌
					String loginToken = resultJson.getString("others");
					//发送短信 
					for(SmsStateRecord record : records){
						sendDetail(loginToken,reqId,record,null,null);
						reqId++;
					}
				}
				else {
					logger.error(" get loginToken fail");
				}
			}
			else {
				logger.error(" get loginToken fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("send fail");
		}	
	}
	//获取登录令牌json
	private String getLoginToken(int reqId) throws Exception{
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", reqId);
		reqJson.put("jsonrpc", "2.0");
		reqJson.put("method", "genLoginToken");
		JSONArray params = new JSONArray();
		params.add(account);
		params.add(passwd); 
		reqJson.put("params", params);
		String resStr = postJson(serviceUrl + "power/authService", reqJson.toString());
		return resStr;
	}
	private void sendDetail(String loginToken,int reqId,SmsStateRecord record_exist,String content,String mobile) throws Exception{
		SmsStateRecord record = new SmsStateRecord();
		if(record_exist==null){
			record.setContent(content);
			record.setMobile(mobile);
		}else{
			record = record_exist;
			content = record_exist.getContent();
			mobile = record_exist.getMobile();
		}
		record.setSendTime(new Date());
		sessionFactory.getCurrentSession().saveOrUpdate(record);
		JSONObject reqJson = new JSONObject();
		reqJson.put("id", reqId);
		reqJson.put("jsonrpc", "2.0");
		reqJson.put("method", "save");
		
		JSONObject smsJson = new JSONObject();
		smsJson.put("businessType", "短信发送");		//业务类型，用于统计
		smsJson.put("sendText", content);		//短信内容
		smsJson.put("sendTo", mobile);		//接收人姓名，可以多个，用半角逗号分隔
		smsJson.put("toDetail", mobile);		//接收号码，可以多个，用半角逗号分隔
		smsJson.put("sendFrom", "人力资源系统");
		
		JSONArray params = new JSONArray();
		params.add(smsJson);
		reqJson.put("params", params);
		
		JSONObject authJson = new JSONObject();
		authJson.put("loginToken", loginToken);
		reqJson.put("auth", authJson);
		
		String resStr = postJson(serviceUrl + "smsSave", reqJson.toString());
		JSONObject resJson = JSONObject.fromObject(resStr);
		JSONObject resultJson = resJson.getJSONObject("result");
		if(resultJson.getBoolean("result")){
			record.setState("1");
		}else {
			logger.error("send fail: " + resultJson.getString("msg"));
		}
		sessionFactory.getCurrentSession().saveOrUpdate(record);
	}
	public boolean sendSms(String content,List<String> mobiles){
		boolean flag = true;
		if(mobiles==null || mobiles.size()==0){
			logger.info(" mobiles is null ");
			flag = false;
			return flag;
		}
		try {
			int reqId = 1;
			String resStr = getLoginToken(reqId);
			reqId++;
			JSONObject resJson = JSONObject.fromObject(resStr);
			if(resJson.has("result")){
				JSONObject resultJson = resJson.getJSONObject("result");
				if(resultJson.getBoolean("result")){
					//令牌
					String loginToken = resultJson.getString("others");
					//发送短信 
					for(String mobile : mobiles){
						if(StringUtils.isBlank(mobile)){
							logger.error(" mobile is null ");
							continue;
						}
						sendDetail(loginToken,reqId,null,content,mobile);
						reqId++;
					}
				}else {
					logger.error(" get loginToken fail");
					flag = false;
				}
			}
			else {
				logger.error(" get loginToken fail");
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("send fail");
			flag = false;
		}	
		return flag;
	}
	public static String postJson(String url, String jsonStr) throws Exception{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonStr, "utf-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		HttpEntity  httpEntity = response.getEntity();
		if(statusCode == 200){
			ByteArrayOutputStream resHolder = new ByteArrayOutputStream();
			httpEntity.writeTo(resHolder);
			resHolder.flush();
	    	return resHolder.toString("utf-8");
		}
		else {
			 if (httpEntity != null) {
				 post.abort();
		     }
			throw new Exception("服务异常");
		}
	}
}
