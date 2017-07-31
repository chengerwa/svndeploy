/**
 *
 */
package com.liang.svn.comm.values;

/**
 * 公共常量类管理类
 */
public final class ValuesMgr {
	/**
	 * 定义系统级常量容器
	 */
	public static final SysValues SYS_VALUES = new SysValues();

	/**
	 * 定义业务逻辑级常量容器
	 */
	public static final BusiValues BUSI_VALUES = new BusiValues();

	/**
	 * 定义异常信息级常量容器
	 */
	public static final ExceValues EXCE_VALUES = new ExceValues();
}
