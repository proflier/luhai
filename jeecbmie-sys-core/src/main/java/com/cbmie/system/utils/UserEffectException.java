package com.cbmie.system.utils;

import org.apache.shiro.authc.AuthenticationException;
/**
 * 用户账号可以状态
 * @author admin
 *
 */
public class UserEffectException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UserEffectException() {
		super();
	}

	public UserEffectException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserEffectException(String message) {
		super(message);
	}

	public UserEffectException(Throwable cause) {
		super(cause);
	}
}