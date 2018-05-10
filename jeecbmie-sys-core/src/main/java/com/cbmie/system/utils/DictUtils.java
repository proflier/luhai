package com.cbmie.system.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;

import com.cbmie.common.mapper.JsonMapper;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.system.dao.DictMainDao;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * 
 * @author
 * @version
 */
public class DictUtils {

	private static DictMainDao dictDao = SpringContextHolder.getBean(DictMainDao.class);

	protected static SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");

	/**字典list 缓存*/
	public static final String CACHE_DICT_MAP = "dictMap";
	/**字典code和name 缓存*/
	public static final String CACHE_DICT_SINGLE_MAP = "dictSingleMap";
	/**关联单位code和名称 缓存*/
	public static final String CACHE_CORP_MAP = "corpMap";
	/**用户账号和名称缓存*/
	public static final String CACHE_USER_NAME_MAP = "userNameMap";
	/**船编号和船名缓存*/
	public static final String CACHE_SHIP_NO_NAME = "shipNoNameMap";
	/**商品信息 缓存*/
	public static final String CACHE_GOODS_NAME = "goodsNameMap";
	/**港口信息 缓存*/
	public static final String CACHE_PORT_NAME = "portNameMap";
	

	
	/**
	 * 初始化字典list
	 */
	public static synchronized void initDictList() {
		Map<String, List<DictChild>> dictMap = (Map<String, List<DictChild>>) EhCacheUtils.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			dictMap = Maps.newHashMap();
			for (DictMain dictMain : dictDao.findAll()) {
				List<DictChild> dictChildList_ = Lists.newArrayList();
				for (DictChild dictChild : dictMain.getDictChild()) {
					// System.out.println(dictChild.getId() + "===" +
					// dictChild.getName());
					dictChildList_.add(dictChild);
				}
				dictMap.put(dictMain.getCode().toLowerCase().trim(), dictChildList_);
			}
			EhCacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
	}
	
	/**
	 * 将所有字典项以code和name为键值对的形式放入缓存，不含层级关系
	 */
	public static synchronized void initDictSingleCache() {
		Map<String, String> dictSingleMap = (Map<String, String>) EhCacheUtils.get(CACHE_DICT_SINGLE_MAP);
		if (dictSingleMap == null) {
			dictSingleMap = Maps.newHashMap();
			List<Object[]> dictSingleList = sessionFactory.getCurrentSession()
					.createSQLQuery("select a.code,a.name from dict_child a").list();
			for (Object[] obj : dictSingleList) {
				dictSingleMap.put(obj[0].toString(), obj[1].toString());
			}
			EhCacheUtils.put(CACHE_DICT_SINGLE_MAP, dictSingleMap);
		}
	}
	
	
	/**
	 * 初始化港口信息缓存
	 */
	public static synchronized void initPortCache() {
		Map<String, String> portMap = (Map<String, String>) EhCacheUtils.get(CACHE_PORT_NAME);
		if (portMap == null) {
			portMap = Maps.newHashMap();
			List<Object[]> corpList = sessionFactory.getCurrentSession()
					.createSQLQuery(
							" select bm,gkm from woodgk ")
					.list();
			for (Object[] obj : corpList) {
				portMap.put(obj[0].toString(), obj[1].toString());
			}
			EhCacheUtils.put(CACHE_PORT_NAME, portMap);
		}
	}
	
	/**
	 * 初始化关联单位缓存
	 */
	public static synchronized void initCorpCache() {
		Map<String, String> corpMap = (Map<String, String>) EhCacheUtils.get(CACHE_CORP_MAP);
		if (corpMap == null) {
			corpMap = Maps.newHashMap();
			List<Object[]> corpList = sessionFactory.getCurrentSession()
					.createSQLQuery(
							"select a.customer_code,a.customer_name from WOOD_AFFI_BASE_INFO a where 1=1 and status=1")
					.list();
			for (Object[] obj : corpList) {
				corpMap.put(obj[0].toString(), obj[1].toString());
			}
			EhCacheUtils.put(CACHE_CORP_MAP, corpMap);
		}
	}

	/**
	 * 初始化用户名称和登陆名称缓存
	 */
	public static synchronized void initUserNameCache() {
		Map<String, String> userNameMap = (Map<String, String>) EhCacheUtils.get(CACHE_USER_NAME_MAP);
		if (userNameMap == null) {
			userNameMap = Maps.newHashMap();
			List<Object[]> userNameList = sessionFactory.getCurrentSession()
					.createSQLQuery(" select LOGIN_NAME,NAME from user ").list();
			for (Object[] obj : userNameList) {
				userNameMap.put(obj[0].toString(), obj[1].toString());
			}
			EhCacheUtils.put(CACHE_USER_NAME_MAP, userNameMap);
		}
	}

	/**
	 * 初始化船编号和船名缓存
	 */
	public static synchronized void initShipNoNameCache() {
		Map<String, String> shipNoNameMap = (Map<String, String>) EhCacheUtils.get(CACHE_SHIP_NO_NAME);
		if (shipNoNameMap == null) {
			shipNoNameMap = Maps.newHashMap();
			List<Object[]> userNameList = sessionFactory.getCurrentSession()
					.createSQLQuery(" select ship_no,ship_name from lh_ship_trace ").list();
			for (Object[] obj : userNameList) {
				shipNoNameMap.put(obj[0].toString(),obj[0].toString()+ obj[1].toString());
			}
			EhCacheUtils.put(CACHE_SHIP_NO_NAME, shipNoNameMap);
		}
	}
	
	/**
	 * 初始化商品名称 缓存
	 */
	public static synchronized void initGoodsNameCache() {
		Map<String, String> goodsNameMap = (Map<String, String>) EhCacheUtils.get(CACHE_GOODS_NAME);
		if (goodsNameMap == null) {
			goodsNameMap = Maps.newHashMap();
			List<Object[]> goodsNameList = sessionFactory.getCurrentSession()
					.createSQLQuery(" select info_code,info_name from LH_GOODSINFORMATION ").list();
			for (Object[] obj : goodsNameList) {
				goodsNameMap.put(obj[0].toString(),obj[1].toString());
			}
			EhCacheUtils.put(CACHE_GOODS_NAME, goodsNameMap);
		}
	}
	
	
	/**
	 * 获取字典项的名字
	 * 
	 * @param id
	 *            字典项的ID值
	 * @param code
	 *            字典项的大类CODE
	 * @param defaultValue
	 *            默认值，如果根据ID和CODE没有查到任何职则返回默认值
	 * @return
	 */
	public static String getDictLabel(String id, String code, String defaultValue) {
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(id)) {
			for (DictChild dict : getDictList(code)) {
				if (id.equals(dict.getId().toString())) {
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取多个字典项ID的名称，用“,”号分割
	 * 
	 * @param ids
	 *            字典项ID，多个用“,”号分割
	 * @param code
	 *            字典项的大类CODE
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static String getDictLabels(String ids, String code, String defaultValue) {
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(ids)) {
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(ids, ",")) {
				valueList.add(getDictLabel(value, code, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String code, String defaultLabel) {
		if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(label)) {
			for (DictChild dict : getDictList(code)) {
				if (label.equals(dict.getName())) {
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}


	/**
	 * 根据大类CODE获取字典项，如果缓存中不存在改CODE值将重新查询
	 * 
	 * @param code
	 * @return
	 */
	public static synchronized List<DictChild> getDictList(String code) {
		if(StringUtils.isBlank(code)) {
			return Lists.newArrayList();
		}
		initDictList();
		Map<String, List<DictChild>> dictMap = (Map<String, List<DictChild>>) EhCacheUtils.get(CACHE_DICT_MAP);
		List<DictChild> dictList = dictMap.get(code.toLowerCase().trim());
		// System.out.println("缓存==================================" + code);
		// for(DictChild dictChild : dictList) {
		// System.out.println("id = " + dictChild.getId() + "; name = " +
		// dictChild.getName());
		// }
		if (dictList == null) {
			dictList = Lists.newArrayList();
		}
		return dictList;
	}


	/**
	 * 根据船编号获取船名
	 * 
	 * @param shipNo
	 * @return
	 */
	public static synchronized String getShipName(String shipNo) {
		if(StringUtils.isBlank(shipNo)) {
			return "";
		}
		initShipNoNameCache();
		return ((Map<String, String>) EhCacheUtils.get(DictUtils.CACHE_SHIP_NO_NAME)).get(shipNo);
	}

	/**
	 * 根据字典项code值获取字典项name值
	 * 
	 * @param code
	 * @return
	 */
	public static synchronized String getDictSingleName(String code) {
		if(StringUtils.isBlank(code)) {
			return "";
		}
		initDictSingleCache();
		return ((Map<String, String>) EhCacheUtils.get(DictUtils.CACHE_DICT_SINGLE_MAP)).get(code);
	}

	/**
	 * 根据关联单位code获取name
	 * 
	 * @param code
	 *            关联单位code
	 * @return 关联单位名称
	 */
	public static synchronized String getCorpName(String code) {
		if(StringUtils.isBlank(code)) {
			return "";
		}
		initCorpCache();
		return ((Map<String, String>) EhCacheUtils.get(DictUtils.CACHE_CORP_MAP)).get(code);
	}
	
	/**
	 * 根据港口bm获取gkm
	 * 
	 * @param bm
	 *            港口编码
	 * @return 港口名称
	 */
	public static synchronized String getPortName(String bm) {
		if(StringUtils.isBlank(bm)) {
			return "";
		}
		initPortCache();
		return ((Map<String, String>) EhCacheUtils.get(DictUtils.CACHE_PORT_NAME)).get(bm);
	}
	

	/**
	 * 根据登陆名 获取用户名
	 * @param loginName
	 * @return
	 */
	public static synchronized String getUserNameByLoginName(String loginName) {
		if(StringUtils.isBlank(loginName)) {
			return "";
		}
		initUserNameCache();
		return ((Map<String, String>) EhCacheUtils.get(DictUtils.CACHE_USER_NAME_MAP)).get(loginName);
	}
	
	/**
	 * 根据商品code获取名称
	 * @param infoCode
	 * @return
	 */
	public static synchronized String getGoodsInfoName(String infoCode) {
		if(StringUtils.isBlank(infoCode)) {
			return "";
		}
		initGoodsNameCache();
		return ((Map<String, String>) EhCacheUtils.get(DictUtils.CACHE_GOODS_NAME)).get(infoCode);
	}

	/**
	 * 返回字典列表（JSON）
	 * 
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type) {
		return JsonMapper.nonEmptyMapper().toJson(getDictList(type));
	}

	/**
	 * 获取字典项的名字
	 * 
	 * @param childCode
	 *            字典项的编码
	 * @param pcode
	 *            字典项的大类CODE
	 * @param defaultValue
	 *            默认值，如果根据ID和CODE没有查到任何职则返回默认值
	 * @return
	 */
	public static String getDictLabelByCode(String childCode, String pcode, String defaultValue) {
		if (StringUtils.isNotBlank(childCode) && StringUtils.isNotBlank(pcode)) {
			for (DictChild dict : getDictList(pcode)) {
				if (childCode.equals(dict.getCode())) {
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取字典项的名字
	 * 
	 * @param childCodes
	 *            字典项的编码，多个用“,”号分割
	 * @param pcode
	 *            字典项的大类CODE
	 * @param defaultValue
	 *            默认值，如果没有查到任何值则返回默认值
	 * @return
	 */
	public static String getDictLabelsByCodes(String childCodes, String pcode, String defaultValue) {
		if (StringUtils.isNotBlank(childCodes) && StringUtils.isNotBlank(pcode)) {
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(childCodes, ",")) {
				for (DictChild dict : getDictList(pcode)) {
					if (value.equals(dict.getCode())) {
						valueList.add(dict.getName());
					}
				}
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}
}
