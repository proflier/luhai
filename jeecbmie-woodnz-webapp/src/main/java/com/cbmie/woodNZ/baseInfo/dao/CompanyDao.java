package com.cbmie.woodNZ.baseInfo.dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.baseInfo.entity.WoodBusinessCompany;
import com.cbmie.woodNZ.baseInfo.entity.WoodGoodsInfo;
 
/**
 * 商品DAO 
 */ 
@Repository
public class CompanyDao extends HibernateDao<WoodBusinessCompany, Long>{
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getTypes() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='类别' order by id asc";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getSpecies() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='材种'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setSpecies(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='材种' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getClasses() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='等级'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setClasses(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='等级' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getSpec() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='厚*宽'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setSpec(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='厚*宽' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getLength() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='长度'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setLength(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='长度' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> getBrand() {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='品牌'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WoodGoodsInfo> setBrand(String type) {
		String sql = "SELECT * FROM woodgoodsinfo WHERE class_name='品牌' and affiliated='"+type+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGoodsInfo.class);
		//sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
}  
 