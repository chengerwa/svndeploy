/**
 *
 */
package com.liang.svn;

import com.liang.svn.sys.main.view.MainView;

import javax.swing.*;

/**
 * 系统入口类
 */
public class SysMain {

	/**
	 * 入口方法
	 */
	public static void main(String[] args) {
//		Font font = new Font("宋体",Font.PLAIN,12);
//		FontUIResource fontRes = new FontUIResource(font);
//		for (Enumeration<Object> keys = UIManager.getDefaults().keys();
//			 keys.hasMoreElements(); ) {
//			Object key = keys.nextElement();
//			Object value = UIManager.get(key);
//			if (value instanceof FontUIResource) {
//				UIManager.put(key, fontRes);
//			}
//		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e){}
		new MainView().show();
	}
}
