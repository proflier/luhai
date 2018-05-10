package com.cbmie.baseinfo.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.baseinfo.dao.AffiBaseInfoDao;
import com.cbmie.baseinfo.dao.SequenceCodeDao;
import com.cbmie.baseinfo.entity.SequenceCode;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 编码生成service
 */
@Service
@Transactional
public class SequenceCodeService extends BaseService<SequenceCode, Long> {

	@Autowired
	private SequenceCodeDao sequenceCodeDao;
	
	@Autowired
	private AffiBaseInfoDao affiBaseInfoDao;

	@Override
	public HibernateDao<SequenceCode, Long> getEntityDao() {
		return sequenceCodeDao;
	}
	
	
	/**
	 * 获取下一个相应规则编码
	 * @param param
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Map<String, String > getNextCode(String param) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		SequenceCode sequenceCode = sequenceCodeDao.findUniqueBy("sequenceName", param);
		return constructCodeNext(transFormula(sequenceCode.getFormula(),param));
	}
	
	/**
	 * 获取当前相应规则编码
	 * @param param
	 * @return
	 */
	public String getCurrentCode(String param) {
		SequenceCode sequenceCode = sequenceCodeDao.findUniqueBy("sequenceName", param);
		return constructCodeCurrent(transFormula(sequenceCode.getFormula(),param));
	}
	
	/**
	 * 获取下个生成sequence
	 * @param param
	 * @return
	 */
	public Integer getNextSequence(String param){
		return sequenceCodeDao.getNextSequence(param);
	}
	

	/**
	 * 根据英文名查询，去重
	 * @param sequenceCode sequence英文名
	 * @return
	 */
	public SequenceCode findBySequenceCode(SequenceCode sequenceCode) {
		return sequenceCodeDao.findBySequenceCode(sequenceCode);
	}
	
	/**
	 * 根据英文名查询
	 * @param sequenceCode sequence英文名
	 * @return
	 */
	public SequenceCode findBySequenceCode(String code) {
		return sequenceCodeDao.findUniqueBy("sequenceName", code);
	}
	
	/**
	 * 更新sequence表
	 * @param param
	 * @param currentSquence
	 */
	public void updateSequence(String param,Integer currentSquence){
		sequenceCodeDao.updateSequence( param, currentSquence);
	}
	
	/**
	 * 获取下一个相应规则的code
	 * @param paramMap 公式组成map
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public  Map<String, String > constructCodeNext(Map<String , String > paramMap) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Map<String, String > returnMap  = new HashMap<String, String>();
		String returnStr= paramMap.get("formula");
		//获取下一个值
		Integer squence = sequenceCodeDao.getSequence(paramMap.get("param")) + 1;
    	if(returnStr.contains("DATE")){
    		//构建相应日期
    		Date d = new Date(); 
            SimpleDateFormat sdf = new SimpleDateFormat(paramMap.get("DATE"));
            String dateNowStr = sdf.format(d);
    		returnStr = returnStr.replace("DATE", dateNowStr);
        }
    	if(returnStr.contains("FLOW")){
    		String flowFormate = "";
    		for(int i=0;i<=Integer.valueOf(paramMap.get("FLOW"))-1;i++){
    			flowFormate = flowFormate + "0";
    		}
    		Format f1 = new DecimalFormat(flowFormate);
    		String flowStr = f1.format(squence);
    		returnStr = returnStr.replace("FLOW", flowStr);
        }
    	if(returnStr.contains("PREFIX")){
    		if(paramMap.get("PREFIX").equals("true")){
    			User currentUser = UserUtil.getCurrentUser();
    	    	String prefix = getCompany(currentUser.getOrganization()).getOrgBusiCode();
    	    	if(StringUtils.isNotBlank(prefix)){
    	    		returnStr = returnStr.replace("PREFIX", prefix);
    	    	}
    		}
        }
    	if(returnStr.contains("UNITCODE")){
    		String keyWord = paramMap.get("UNITCODE");
    		String[] strArray={"10230001"};
    		WoodAffiBaseInfo affi = affiBaseInfoDao.getByCustomerTypes(strArray).get(0);
    		PropertyDescriptor pd = new PropertyDescriptor(keyWord, WoodAffiBaseInfo.class);  
	        Method getMethod = pd.getReadMethod();
	        String unitCode =   (String) getMethod.invoke(affi);
	        returnStr = returnStr.replace("UNITCODE", unitCode);
	        returnMap.put("keyWord", keyWord);
        }
    	returnMap.put("returnStr", returnStr);
		return returnMap;
	}
	
	/**
	 * 获取当前相应规则的code
	 * @param paramMap 公式组成map
	 * @return
	 */
	public  String constructCodeCurrent(Map<String , String > paramMap){
		String returnStr= paramMap.get("formula");
		//获取下一个值
		Integer squence = sequenceCodeDao.getSequence(paramMap.get("param"));
    	if(returnStr.contains("DATE")){
    		//构建相应日期
    		Date d = new Date();  
            SimpleDateFormat sdf = new SimpleDateFormat(paramMap.get("DATE"));
            String dateNowStr = sdf.format(d);
    		returnStr = returnStr.replace("DATE", dateNowStr);
        }
    	if(returnStr.contains("FLOW")){
    		String flowFormate = "";
    		for(int i=0;i<=Integer.valueOf(paramMap.get("FLOW"))-1;i++){
    			flowFormate = flowFormate + "0";
    		}
    		Format f1 = new DecimalFormat(flowFormate);
    		String flowStr = f1.format(squence);
    		returnStr = returnStr.replace("FLOW", flowStr);
        }
		return returnStr;
	}
	
	
	/**
	 * 
	 * @param formula  LHZY-C(SA){DATE_yyyy}-{FLOW_3}    FKSQ{DATE_yyyyMMdd}{FLOW_3}
	 * @param param 
	 * @return
	 */
	public Map<String , String > transFormula(String formula,String param){
		Map<String , String > returnMap = new HashMap<String, String>();
//		Pattern pattern = Pattern.compile("(.*)\\{(.*?)\\}(.*)\\{(.*?)\\}"); //分组
		Pattern pattern = Pattern.compile("\\{(.*?)\\}"); //不分组，直接取值
		Matcher matcher = pattern.matcher(formula);
	    while(matcher.find()){
	      if(matcher.group(0).contains("DATE")){
	    	  formula =  formula.replace(matcher.group(0), "DATE");
	      }
	      if(matcher.group(0).contains("FLOW")){
	    	  formula =  formula.replace(matcher.group(0), "FLOW");
	      }
	      if(matcher.group(0).contains("PREFIX")){
	    	  formula =  formula.replace(matcher.group(0), "PREFIX");
	      }
	      if(matcher.group(0).contains("PREFIX")){
	    	  formula =  formula.replace(matcher.group(0), "PREFIX");
	      }
	      if(matcher.group(0).contains("UNITCODE")){
	    	  formula =  formula.replace(matcher.group(0), "UNITCODE");
	      }
	      returnMap.put(matcher.group(1).split("_")[0],  matcher.group(1).split("_")[1]);
	    }
	    returnMap.put("param", param);
	    returnMap.put("formula", formula);
		return returnMap;
	}
	


	
}
