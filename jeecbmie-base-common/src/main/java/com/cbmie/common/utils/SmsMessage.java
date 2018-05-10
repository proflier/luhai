package com.cbmie.common.utils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SmsMessage {
	private String serviceUrl;
	private String account;
	private String passwd;
	private Logger logger = LoggerFactory.getLogger(getClass());
	/*public SmsMessage(){
		serviceUrl = "http://yxt.bbn.com.cn/eums/rpc/";
		account = "zhongjiancaijituan";
		passwd = "888888";
	}*/
	public SmsMessage(){
		PropertiesLoader p = new PropertiesLoader("smsplatform.properties");
		serviceUrl = p.getProperty("azServiceUrl");
		account = p.getProperty("azAccount");
		passwd = p.getProperty("azPasswd");
	}
	
	public void sendSms(List<String> contents,List<String> mobiles){
		if(contents==null || contents.size()==0){
			return;
		}else{
			for(String temp : contents){
				for(int send_count=10;send_count>0;send_count--){
					boolean flag = sendSms(temp,mobiles);
					if(flag){
						break ;
					}
						
				}
				
			}
		}
	}
	public boolean sendSms(String content,List<String> mobiles){
		if(mobiles==null || mobiles.size()==0){
			logger.info("mobile is null");
			return false;
		}
		try {
			int reqId = 1;
			//获取登录令牌
			JSONObject reqJson = new JSONObject();
			reqJson.put("id", reqId++);
			reqJson.put("jsonrpc", "2.0");
			reqJson.put("method", "genLoginToken");
			JSONArray params = new JSONArray();
			params.add(account);
			params.add(passwd); 
			reqJson.put("params", params);
			
			String resStr = postJson(serviceUrl + "power/authService", reqJson.toString());
			JSONObject resJson = JSONObject.fromObject(resStr);
			if(resJson.has("result")){
				JSONObject resultJson = resJson.getJSONObject("result");
				if(resultJson.getBoolean("result")){
					//令牌
					String loginToken = resultJson.getString("others");
					//发送短信 
					reqJson = new JSONObject();
					reqJson.put("id", reqId++);
					reqJson.put("jsonrpc", "2.0");
					reqJson.put("method", "save");
					
					JSONObject smsJson = new JSONObject();
					smsJson.put("businessType", "短信发送");		//业务类型，用于统计
					smsJson.put("sendText", content);		//短信内容
					smsJson.put("sendTo", StringUtils.join(mobiles, ","));		//接收人姓名，可以多个，用半角逗号分隔
					smsJson.put("toDetail", StringUtils.join(mobiles, ","));		//接收号码，可以多个，用半角逗号分隔
					smsJson.put("sendFrom", "人力资源系统");
					
					params = new JSONArray();
					params.add(smsJson);
					reqJson.put("params", params);
					
					JSONObject authJson = new JSONObject();
					authJson.put("loginToken", loginToken);
					reqJson.put("auth", authJson);
					
					resStr = postJson(serviceUrl + "smsSave", reqJson.toString());
					System.out.println(resStr);
					resJson = JSONObject.fromObject(resStr);
					resultJson = resJson.getJSONObject("result");
					if(resultJson.getBoolean("result")){
						System.out.println("send success "+ resultJson.getString("msg"));
						return true;
					}
					else {
						System.out.println("send fail: " + resultJson.getString("msg"));
						return false;
					}
				}
				else {
					System.out.println("get loginToken fail:" + resultJson.getString("msg"));
					return false;
				}
			}
			else {
				System.out.println("get loginToken fail");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("send fail");
			return false;
		}	
	}
	/*public static void main(String[] args) {
		SmsMessage deal = new SmsMessage();
		List<String> contents = new ArrayList<String>();
		contents.add("您有一条来自ces的关于公出申请的待办任务，请及时登录系统办理。(中国建材集团人力资源管理系统)");
		List<String> mobiles = new ArrayList<String>();
		mobiles.add("15201597225");
		deal.sendSms(contents, mobiles);
		
	}*/
	public static String postJson(String url, String jsonStr) throws Exception{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonStr, "utf-8");
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		HttpEntity  httpEntity = response.getEntity();
		 if (httpEntity != null) {
			 post.abort();
	     }
		if(statusCode == 200){
			ByteArrayOutputStream resHolder = new ByteArrayOutputStream();
			httpEntity.writeTo(resHolder);
			resHolder.flush();
	    	return resHolder.toString("utf-8");
		}
		else {
			throw new Exception("服务异常");
		}
	}
}
