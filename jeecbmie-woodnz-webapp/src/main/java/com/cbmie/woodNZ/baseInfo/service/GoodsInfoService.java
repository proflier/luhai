package com.cbmie.woodNZ.baseInfo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.baseInfo.dao.GoodsInfoDao;
import com.cbmie.woodNZ.baseInfo.entity.WoodGoodsInfo;


/**
 * 商品信息service
 */
@Service
@Transactional(readOnly = true)
public class GoodsInfoService extends BaseService<WoodGoodsInfo, Long> {

	@Autowired
	private GoodsInfoDao goodsInfoDao;

	@Override
	public HibernateDao<WoodGoodsInfo, Long> getEntityDao() {
		return goodsInfoDao;
	}

	public List<WoodGoodsInfo> getTypes(){
		return goodsInfoDao.getTypes();
	}
	
	public List<WoodGoodsInfo> getSpecies(){
		return goodsInfoDao.getSpecies();
	}
	
	public List<WoodGoodsInfo> setSpecies(String type){
		return goodsInfoDao.setSpecies(type);
	}
	
	public List<WoodGoodsInfo> getClasses(){
		return goodsInfoDao.getClasses();
	}
	
	public List<WoodGoodsInfo> setClasses(String type){
		return goodsInfoDao.setClasses(type);
	}
	
	public List<WoodGoodsInfo> getSpec(){
		return goodsInfoDao.getSpec();
	}
	
	public List<WoodGoodsInfo> setSpec(String type){
		return goodsInfoDao.setSpec(type);
	}
	
	public List<WoodGoodsInfo> getLength(){
		return goodsInfoDao.getLength();
	}
	
	public List<WoodGoodsInfo> setLength(String type){
		return goodsInfoDao.setLength(type);
	}
	
	public List<WoodGoodsInfo> getBrand(){
		return goodsInfoDao.getBrand();
	}
	
	public List<WoodGoodsInfo> setBrand(String type){
		return goodsInfoDao.setBrand(type);
	}
	
	public String getNameByCode(String code){
		String returnStr = "";
		String queryStr ="";
		String affiliated = null;
		String returnQuery = "";
		if(StringUtils.isNotBlank(code)){
			if(code.length()>=2){
				for(int i=0;i<code.length()/2;i++){
					if(i!=5){
						queryStr = code.substring(0+2*i, 2+2*i);
					}else{
						queryStr =code.substring(10);
					}
					if(i!=0){
						if(StringUtils.isNotBlank(affiliated)){
							returnQuery = goodsInfoDao.getByCode(queryStr,1+i,affiliated);
						}else{
							returnQuery = "";
						}
					}else{
						returnQuery = goodsInfoDao.getByCode(queryStr,1+i,affiliated);
						affiliated = returnQuery;
					}
					if(StringUtils.isNotBlank(returnQuery)){
						returnStr += returnQuery + " ";
					}
				}
			}
		}
		return returnStr;
	}

	//校验相同商品级别和所属级别下的商品编码是否唯一
	public boolean validateGoodsNo(WoodGoodsInfo info) {
		if(goodsInfoDao.getCodeByGoodsInfo(info)>0)
			return false;//编码重复
		else 
			return true;//编码不重复
	}

	public Double getHssByFourCode(String code,String affiliated) {
		return goodsInfoDao.getHssByFourClassCode(code,affiliated);
	}
	
	public Double getHssByFiveCode(String code,String affiliated) {
		return goodsInfoDao.getHssByFiveClassCode(code,affiliated);
	}
}
