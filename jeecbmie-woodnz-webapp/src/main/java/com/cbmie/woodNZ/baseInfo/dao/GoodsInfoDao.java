package com.cbmie.woodNZ.baseInfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.woodNZ.baseInfo.entity.WoodGoodsInfo;
 
/**
 * 商品DAO 
 */ 
@Repository
public class GoodsInfoDao extends HibernateDao<WoodGoodsInfo, Long>{
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getTypes() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='1级' order by id asc";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getSpecies() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='2级'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setSpecies(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='2级' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getClasses() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='3级'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setClasses(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='3级' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getSpec() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='4级'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setSpec(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='4级' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getLength() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='5级'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setLength(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='5级' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getBrand() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='6级'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setBrand(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE classes='6级' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	

	public int getCodeByGoodsInfo(WoodGoodsInfo info) {
		String sql="";
		if(info.getAffiliated() == null){
			 sql = "SELECT * FROM woodgoodsinfo WHERE classes='"+info.getClasses()+"' and affiliated is null "
					+ " and code='"+info.getCode()+"'";
		}else {
			 sql = "SELECT * FROM woodgoodsinfo WHERE classes='"+info.getClasses()+"' and affiliated='"+info.getAffiliated()+"'"
					+ " and code='"+info.getCode()+"'";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		return sqlQuery.list().size();
	}
	
	public String getByCode(String queryStr , int level ,String affiliated) {
		String sql = "SELECT name FROM woodgoodsinfo WHERE code = ? and classes=?";
		if (StringUtils.isNotBlank(affiliated)) {
			sql += " and affiliated= ?";
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		if (StringUtils.isNotBlank(affiliated)) {
			sqlQuery.setParameter(2, affiliated);
		}
		sqlQuery.setParameter(0, queryStr);
		sqlQuery.setParameter(1, level+"级");
		return (String)sqlQuery.uniqueResult();
}

	public Double getHssByFourClassCode(String code,String affiliated) {
		SQLQuery sqlQuery;
		try {
			String sql = "SELECT hss FROM woodgoodsinfo WHERE classes='4级' and affiliated='"+affiliated+"' and code='"+code+"'";
			sqlQuery = getSession().createSQLQuery(sql);
			return (Double)sqlQuery.uniqueResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Double.valueOf(code);
	}
	
	public Double getHssByFiveClassCode(String code,String affiliated) {
		String sql = "SELECT hss FROM woodgoodsinfo WHERE classes='5级' and affiliated='"+affiliated+"' and code='"+code+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return (Double)sqlQuery.uniqueResult();
	}
}  
 