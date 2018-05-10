package com.cbmie.baseinfo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;

/**
 * 关联信息__基本信息DAO
 */
@Repository
public class AffiBaseInfoDao extends HibernateDao<WoodAffiBaseInfo, Long> {

	@SuppressWarnings("unchecked")
	public List<WoodAffiBaseInfo> getByCustomerType(String num) {
		String sql = "SELECT * FROM wood_affi_base_info WHERE status = '1' AND customer_type in (?) ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class);
		sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	/**
	 * 查找一项符合关联单位类别的info
	 * */
	public List<WoodAffiBaseInfo> getByCustomerTypes(String[] cTypes) {
		String sql = "select * from wood_affi_base_info WHERE status = '1'  ";
		sql = sql + " and (1<>1 ";
		for(String cType : cTypes){
			sql = sql+" or customer_type like '%"+cType+"%' ";
		}
		sql = sql +")";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class);
		return sqlQuery.list();
	}

	public int getCodeByAffiBaseInfo(WoodAffiBaseInfo info) {
		String sql="";
		sql = "SELECT * FROM wood_affi_base_info WHERE 1=1 and customer_Code='"+info.getCustomerCode()+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class);
		return sqlQuery.list().size();
	}
	
	public WoodAffiBaseInfo findByNo(WoodAffiBaseInfo woodAffiBaseInfo){
		Criteria criteria = getSession().createCriteria(WoodAffiBaseInfo.class);
		if (woodAffiBaseInfo.getId() != null) {
			criteria.add(Restrictions.ne("id", woodAffiBaseInfo.getId()));
		}
		criteria.add(Restrictions.eq("customerCode", woodAffiBaseInfo.getCustomerCode()));
		return (WoodAffiBaseInfo)criteria.uniqueResult();
	}
	
	public void findEntityList(Page<WoodAffiBaseInfo> page,Map<String,Object> params){
		String sql = "select a.* from wood_affi_base_info a  LEFT JOIN  customer_perssion c on a.customer_code = c.customer_code  LEFT OUTER join user u on c.business_manager = u.LOGIN_NAME where 1=1 ";
		if(params.containsKey("customerCodeOrcustomerName")){
			String customerCodeOrcustomerName = params.get("customerCodeOrcustomerName").toString();
			sql = sql+" and  (a.customer_Code like '%"+customerCodeOrcustomerName+"%' or a.customer_Name like '%"+customerCodeOrcustomerName+"%')";
		}
		if(params.containsKey("customerType")){
			String customerType = params.get("customerType").toString();
			String[] types = customerType.split(",");
			for(String cType : types){
				sql = sql+" and a.customer_type like '%"+cType+"%' ";
			}
		}
		if(params.containsKey("Hperson")){
			String handlerPerson = params.get("Hperson").toString();
			sql = sql+" and u.name like '%"+handlerPerson +"%'";
		}
		if(params.containsKey("Rperson")){
			String rangesPerson = params.get("Rperson").toString();
			sql = sql+" and c.ranges like '%"+rangesPerson +"%'";
		}
		if(params.containsKey("userName")){
			String userName = params.get("userName").toString();
			sql = sql+" and ( LOCATE('"+userName+"',c.ranges) or c.business_manager='"+userName+"' or a.createrno='"+userName+"' or ( a.customer_type not like '%10230002%' and  a.customer_type not like '%10230003%' ) ) ";
		}
		
		sql = sql +" order by id desc ";
		page.setTotalCount(this.createSQLQuery(sql).list().size());
		List<WoodAffiBaseInfo> list = this.createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class).setFirstResult(page.getFirst() - 1).setMaxResults(page.getPageSize()).list();
		page.setResult(list);
	}
	public WoodAffiBaseInfo findByCustomerName(WoodAffiBaseInfo affiBaseInfo) {
		Criteria criteria = getSession().createCriteria(WoodAffiBaseInfo.class);
		if (affiBaseInfo.getId() != null) {
			criteria.add(Restrictions.ne("id", affiBaseInfo.getId()));
		}
		criteria.add(Restrictions.eq("customerName", affiBaseInfo.getCustomerName()));
		return (WoodAffiBaseInfo)criteria.uniqueResult();
	}
	public List<WoodAffiBaseInfo> getByCustomerTypes4UserPermission(String matchValue,String[] customerCode) {
		String sql = "select * from wood_affi_base_info WHERE status = '1'  ";
		sql = sql +" and customer_type like '%"+matchValue+"%' ";
		sql = sql +" and CUSTOMER_CODE in ( '" +StringUtils.join(customerCode, "','")+"')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class);
		return sqlQuery.list();
	}
	public List<WoodAffiBaseInfo> getAffiOurUnitOrCustomer() {
		User currentUser = UserUtil.getCurrentUser();
		String sql = " SELECT a.* FROM wood_affi_base_info a LEFT JOIN customer_perssion c ON a.customer_code = c.customer_code WHERE 1 = 1 ";
		if(currentUser.getId()!=1){
			sql = sql + " AND ((a.customer_type like '%10230001%' and a.customer_type not like '%10230003%') ";
			sql = sql +" OR (a.customer_type like '%10230003%' AND (LOCATE('"+currentUser.getLoginName()+"', c.ranges) OR c.business_manager = '"+currentUser.getLoginName()+"'))) ";
		}else{
			sql = sql +" and (a.customer_type like '%10230001%' or a.customer_type like '%10230003%') ";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class);
		return sqlQuery.list();
	}
	
	
	public List<Map<String, Object>> getOurUnitAndCustomer(Map<String, Object> params) {
		
		String sql = " SELECT a.id,a.customer_code,a.customer_name,a.customer_type,u.name,c.ranges FROM  wood_affi_base_info a ";
		sql = sql + " LEFT JOIN customer_perssion c ON a.customer_code = c.customer_code ";
		sql = sql + " LEFT OUTER JOIN USER u ON c.business_manager = u.LOGIN_NAME WHERE status = 1 and (a.customer_type like '%10230002%' or a.customer_type like '%10230003%') ";

		if(params.containsKey("customerCode")){
			String customerCode = params.get("customerCode").toString();
			sql = sql+" and a.customer_Code like '%"+customerCode+"%' ";
		}
		if(params.containsKey("customerName")){
			String customerName = params.get("customerName").toString();
			sql = sql+" and a.customer_Code like '%"+customerName+"%' ";
		}
		if(params.containsKey("customerType")){
			String customerType = params.get("customerType").toString();
			sql = sql +" and a.customer_type like '%"+customerType+"%' ";
		}
		if(params.containsKey("Hperson")){
			String handlerPerson = params.get("Hperson").toString();
			sql = sql+" and u.name like '%"+handlerPerson +"%'";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("id", obj_array[0].toString());
			//关联单位编码
			row_.put("customerCode", obj_array[1].toString());
			//关联单位名称
			row_.put("customerName", obj_array[2].toString());
			//关联单位类型
			row_.put("customerType", obj_array[3].toString());
			//业务经办人
			row_.put("Hperson", obj_array[4]!=null?obj_array[4].toString():"");
			//相关人员
			row_.put("ranges", obj_array[5]!=null?obj_array[5].toString():"");
			data_rows.add(row_);
		}
		return data_rows;
	}
}
