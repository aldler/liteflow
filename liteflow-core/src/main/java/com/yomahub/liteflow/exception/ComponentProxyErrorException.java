
package com.yomahub.liteflow.exception;

/**
 * 组件代理错误异常
 *
 * @author Yun
 */
public class ComponentProxyErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 异常信息 */
	private String message;

	public ComponentProxyErrorException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
