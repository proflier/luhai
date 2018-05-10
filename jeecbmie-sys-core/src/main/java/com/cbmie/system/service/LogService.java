package com.cbmie.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.mapper.JsonMapper;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.system.dao.LogDao;
import com.cbmie.system.entity.Log;
import com.cbmie.system.utils.IPUtil;
import com.cbmie.system.utils.JXLExcel;
import com.cbmie.system.utils.UserUtil;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 日志service
 */
@Service
@Transactional(readOnly = true)
public class LogService extends BaseService<Log, Integer> {

	@Autowired
	private LogDao logDao;

	@Override
	public HibernateDao<Log, Integer> getEntityDao() {
		return logDao;
	}

	/**
	 * 批量删除日志
	 * 
	 * @param idList
	 */
	@Transactional(readOnly = false)
	public void deleteLog(List<Integer> idList) {
		logDao.deleteBatch(idList);
	}

	/**
	 * 导出excel
	 */
	@Transactional(readOnly = false)
	public void excelLog(HttpServletResponse response, List<Log> list) {
		JXLExcel jxl = new JXLExcel();
		// excel名称
		String excelName = "log.xls";
		// 设置标题
		String[] columnNames = new String[] { "操作编码", "操作用户名称", "日志类型", "系统类型", "浏览器类型", "IP地址", "物理地址", "执行时间", "详细描述",
				"请求参数", "日志生成时间" };
		jxl.setColumnNames(columnNames);
		// 设置属性名称
		String[] dbColumnNames = new String[] { "operationCode", "creater", "type", "os", "browser", "ip", "mac",
				"executeTime", "description", "requestParam", "createDate" };
		jxl.setDbColumnNames(dbColumnNames);
		// 执行
		jxl.exportExcel(response, list, excelName);
	}
	
	/**
	 * 根据用户名分组，相应时间段的计算操作数量
	 */
	public List<Map<String,Object>> countLog(int days) {
		return logDao.countLog(days);
	}
	
	public void loginLog(HttpServletRequest request) {
		String requestRri = request.getRequestURI();
		String uriPrefix = request.getContextPath();
		String operationCode=StringUtils.substringAfter(requestRri,uriPrefix);	//操作编码
		String requestParam=(new JsonMapper()).toJson(request.getParameterMap());	//请求参数
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent")); 
		String os=userAgent.getOperatingSystem().getName();	//获取客户端操作系统
		String browser=userAgent.getBrowser().getName();	//获取客户端浏览器
		
		Log log=new Log();
		log.setOs(os);
		log.setBrowser(browser);
		log.setIp(IPUtil.getIpAddress(request));
		log.setOperationCode(operationCode);
		log.setExecuteTime(1);
		log.setCreater(UserUtil.getCurrentUser().getName());
		log.setCreaterLoginName(UserUtil.getCurrentUser().getLoginName());
		log.setCreateDate(DateUtils.getSysTimestamp());
		log.setRequestParam(requestParam);
		
		save(log);
	}

}
