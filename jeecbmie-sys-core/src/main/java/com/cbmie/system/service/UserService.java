package com.cbmie.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.utils.security.Digests;
import com.cbmie.common.utils.security.Encodes;
import com.cbmie.system.dao.UserDao;
import com.cbmie.system.dao.RoleDao;
import com.cbmie.system.dao.UserRoleDao;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.entity.UserRole;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.OrgUserUtils;

/**
 * 用户service
 */
@Service
@Transactional
public class UserService extends BaseService<User, Integer> {
	
	/**加密方法*/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;	//盐长度

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public HibernateDao<User, Integer> getEntityDao() {
		return userDao;
	}

	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void save(User user) {
		entryptPassword(user);
		user.setCreateDate(DateUtils.getSysTimestamp());
		userDao.save(user);
//		activitiIdentityService.synActivitiUser(user);
		EhCacheUtils.remove(OrgUserUtils.ORGUSERBEAN);
		EhCacheUtils.remove(DictUtils.CACHE_USER_NAME_MAP );
	}
	
	
	public void update(User user){
		userDao.save(user);
		userDao.getSession().flush();
//		activitiIdentityService.synActivitiUser(user);
		EhCacheUtils.remove(OrgUserUtils.ORGUSERBEAN);
		EhCacheUtils.remove(DictUtils.CACHE_USER_NAME_MAP );
	}
	
	public void updateImg(User user){
		userDao.updateImg(user);
	}
	/**
	 * 修改密码
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updatePwd(User user) {
		entryptPassword(user);
		userDao.save(user);
		userDao.getSession().flush();
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void delete(Integer id){
		if(!isSupervisor(id)){
			String loginname = getUser(id).getLoginName();
//			activitiIdentityService.deleteActivitiUser(loginname);
			userDao.delete(id);
			EhCacheUtils.remove(OrgUserUtils.ORGUSERBEAN);
			EhCacheUtils.remove(DictUtils.CACHE_USER_NAME_MAP );
		}
	}
	
	/**
	 * 按登录名查询用户
	 * @param loginName
	 * @return 用户对象
	 */
	public User getUser(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	/**
	 * 按id查询用户
	 * @param id
	 * @return 用户对象
	 */
	public User getUser(Integer id) {
		return userDao.findUniqueBy("id", id);
	}
	
	/**
	 * 按用户状态查询
	 * @param status 1启用、0停用
	 * @return
	 */
	public List<User> getUsersByStatus(Integer status){
		return userDao.getUsersByStatus(status);
	}
	
	/**
	 * 判断是否超级管理员
	 * @param id
	 * @return boolean
	 */
	private boolean isSupervisor(Integer id) {
		return id == 1;
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 验证原密码是否正确
	 * @param user
	 * @param oldPwd
	 * @return
	 */
	public boolean checkPassword(User user,String oldPassword){
		byte[] salt =Encodes.decodeHex(user.getSalt()) ;
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户登录
	 * @param user
	 */
	public void updateUserLogin(User user){
		user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
		user.setPreviousVisit(user.getLastVisit());
		user.setLastVisit(DateUtils.getSysTimestamp());
		userDao.save(user);
		userDao.getSession().flush();
	}
	
	
	/**
	 *获取业务员信息（用户角色为业务员）
	 * @param num
	 * @return
	 */
	public List<User> getSupplier(String num) {
		return userDao.getSupplier(num);
	}
	
	/**
	 * 修改用户所在部门
	 */
	public void updateUserOrg(User user, Organization organization){
		user.setOrganization(organization);
		userDao.save(user);
		userDao.getSession().flush();
	}

	/**
	 * 
	 * @param id 角色id
	 * @return
	 */
	public List<User> getUserInRole(Integer id) {
		return userRoleDao.getUserInRole(id);
	}

	public List<Integer> searchUserIds(Integer roleId) {
		return userRoleDao.searchUserIds(roleId);
	}

	public void updateUserInRole(Integer roleId, List<Integer> oldList, List<String> newList) {
		//删除
		for(int i=0,j=oldList.size();i<j;i++){
			if(!newList.contains(oldList.get(i).toString())){
				userRoleDao.deleteFromRole(roleId,oldList.get(i));
			}
		}
		
		//添加
		for(int i=0,j=newList.size();i<j;i++){
			if(!oldList.contains(Integer.valueOf(newList.get(i)))){
				userRoleDao.save(buildUserRole(roleId,newList.get(i)));
			}
		}
	}

	private UserRole buildUserRole(Integer roleId, String userId) {
		UserRole userRole = new UserRole();
		userRole.setRole(roleDao.get(roleId));
		User user = userDao.get(Integer.valueOf(userId));
		userRole.setUser(user);
		userRole.setRelationDepts(user.getOrganization().getId().toString());
		return userRole;
	}

	public List<User> getUsersByIds(Integer[] ids){
		return userDao.getUsersByIds(ids);
	}
	
	public List<User> getUsersByLoginNames(String[] loginNames){
		return userDao.getUsersByLoginNames(loginNames);
	}
	
	public User getUserByLoginName(String loginName){
		return userDao.getUserByLoginName(loginName);
	}

	public void findEntityList(Page<User> page, Map<String, Object> params) {
		 userDao.findEntityList(page,params);
	}

	public List<Long> getCurrentRelation4Affi(String loginName) {
		return  userDao.getIdRelation4Affi(loginName);
	}
	
	public List<User> getEffectUsers(){
		return userDao.findBy("loginStatus", 1);
	}

	public List<User> getUsersByNameLike(String userName) {
		return  userDao.getUsersByNameLike(userName);
	}

	public List<Long> getCurrentRelation4Purchase(String loginName) {
		return  userDao.getIdRelation4Purchase(loginName);
	}

	public List<Long> getCurrentRelation4Sale(String loginName) {
		return  userDao.getCurrentRelation4Sale(loginName);
	}

	public List<Long> getCurrentRelation4OrderShip(String loginName) {
		return  userDao.getCurrentRelation4OrderShip(loginName);
	}

	public List<Long> getCurrentRelation4Insurance(String loginName) {
		return  userDao.getCurrentRelation4Insurance(loginName);
	}

	public List<Long> getCurrentRelation4Highway(String loginName) {
		return  userDao.getCurrentRelation4Highway(loginName);
	}

	public List<Long> getCurrentRelation4Railway(String loginName) {
		return  userDao.getCurrentRelation4Railway(loginName);
	}

	public List<Long> getCurrentRelation4Port(String loginName) {
		return  userDao.getCurrentRelation4Port(loginName);
	}
}
