package com.cbmie.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面返回公共类
 * @author linxiaopeng
 * 2016年7月22日
 */
public class ReturnData {

	/**
	 * 
	 * @param returnFlag 返回成功失败标志
	 * @param returnId 返回上前实体的id
	 * @return
	 */
	public  Map<String, Object>  setReturnData(String returnFlag, Long returnId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", returnFlag);
		map.put("returnId", returnId);
		return map;
	}


}
