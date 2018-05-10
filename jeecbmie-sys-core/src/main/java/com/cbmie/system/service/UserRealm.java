package com.cbmie.system.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.cbmie.common.utils.security.Encodes;
import com.cbmie.system.entity.Permission;
import com.cbmie.system.entity.User;
import com.cbmie.system.entity.UserPasswordLog;
import com.cbmie.system.entity.UserRole;
import com.cbmie.system.utils.CaptchaException;
import com.cbmie.system.utils.PhonePermissionException;
import com.cbmie.system.utils.UserEffectException;
import com.cbmie.system.utils.UsernamePasswordCaptchaToken;
import com.google.common.base.Objects;

/**
 * 用户登录授权service(shrioRealm)
 */
@Service
@DependsOn({"userDao","permissionDao","rolePermissionDao"})
public class UserRealm extends AuthorizingRealm {

	/**
	 * 对不同操作系统的路径支持
	 */
	private static String SYSTEM_SEPARATOR = System.getProperty("file.separator");
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserPasswordLogService userPasswordLogService;
	
	@Autowired
	private CustomerPerssionService customerPerssionService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		User user = userService.getUser((String)authcToken.getPrincipal());
		if(user==null){
			throw new UnknownAccountException("用户名不存在，请重试！");
		}
		//设置登录次数、时间
		userService.updateUserLogin(user);
		if(user!=null){
			//验证码验证结果，用于区分手机端(无验证码)和PC端(有验证码)--update by cuiZhongQiang
			boolean captchaFlag = true;
			if (authcToken instanceof UsernamePasswordCaptchaToken) {
				captchaFlag = doCaptchaValidate(authcToken);
			}
			if (captchaFlag) {
				byte[] salt = Encodes.decodeHex(user.getSalt());
				ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
				//设置用户session
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute("user", user);
				//记录登录日志
				HttpServletRequest request = WebUtils.getHttpRequest(SecurityUtils.getSubject());
				logService.loginLog(request);
				String customerCode = "";
				List<String > customerCodeList = customerPerssionService.getCurrenCustomerCode(user.getLoginName());
				if(customerCodeList.size()>0){
					customerCode = StringUtils.join(customerCodeList.toArray(), ",");
				}
				//根据用户名设置用户相关客户
				session.setAttribute("customerCode", customerCode);
				return new SimpleAuthenticationInfo(shiroUser,user.getPassword(), ByteSource.Util.bytes(salt), getName());
			} else {
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.getUser(shiroUser.loginName);
		
		//把principals放session中 key=userId value=principals
		SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//赋予角色
		for(UserRole userRole:user.getUserRoles()){
			info.addRole(userRole.getRole().getName());
		}
		//赋予权限
		for(Permission permission:permissionService.getPermissions(user.getId())){
			if(StringUtils.isNotBlank(permission.getPermCode()))
			info.addStringPermission(permission.getPermCode());
		}
		
		
		return info;
	}
	
	/**
	 * 登陆校验
	 * @param token
	 * @return boolean
	 */
	protected boolean doCaptchaValidate(AuthenticationToken authcToken) {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		//保存用户名密码到log
		writeLoginPassword(token);
		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//验证码
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())){
			throw new CaptchaException("验证码错误！");
		}else if(captcha==null){//手机登陆权限校检 update by linXiaoPeng
			User user = userService.getUser((String)authcToken.getPrincipal());
			if(user!=null&&!doPhonePermission(user)){
				throw new PhonePermissionException("没有手机登陆权限！");
			}
			if(user!=null&&!doUserEffect(user)){
				throw new UserEffectException("账号已停用！");
			}
			
		}
		return true;
	}
	
	/**
	 * 保存登陆历史到数据库
	 * @param token
	 */
	private void writeLoginPassword(UsernamePasswordCaptchaToken token) {
		UserPasswordLog userPasswordLog = null;
		List<UserPasswordLog> logs = userPasswordLogService.getByUsername(token.getUsername());
		boolean addFlag = true;
		if(logs.size()>0){
			for(UserPasswordLog log: logs){
				if(log.getPassword().equals(String.valueOf(token.getPassword()))){
					userPasswordLogService.updateLogin(log);
					addFlag = false;
					break;
				}
			}
		}
		if(addFlag){
			userPasswordLog = new UserPasswordLog();
			userPasswordLog.setLoginName(token.getUsername());
			userPasswordLog.setPassword(String.valueOf(token.getPassword()));
			userPasswordLog.setLastVisit(new Date());
			userPasswordLog.setLoginCount(1);
			userPasswordLogService.save(userPasswordLog);
		}
	}

	/**
	 * 手机登陆权限校检
	 * @param token
	 * @return boolean
	 */
	protected boolean doPhonePermission(User user) {
		if(StringUtils.isNotBlank(user.getPhonePermission())&&"1".equals(user.getPhonePermission())){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 用户账号是否启用
	 */
	protected boolean doUserEffect(User user) {
		if(user.getLoginStatus()!=null&&1==user.getLoginStatus()){
			return true;
		}else{
			return false;
		}
		
	}
		

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@SuppressWarnings("static-access")
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(userService.HASH_ALGORITHM);
		matcher.setHashIterations(userService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Integer id;
		public String loginName;
		public String name;

		public ShiroUser(Integer id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public Integer getId(){
			return id;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}
	
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
 
}
