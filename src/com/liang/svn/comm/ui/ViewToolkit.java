/**
 *
 */
package com.liang.svn.comm.ui;

import com.liang.svn.comm.values.ValuesMgr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 视图辅助类
 */
public class ViewToolkit {

	/**
	 * 获取图标对象
	 */
	public static ImageIcon createImageIcon(String resTitle) {
		return new ImageIcon(ViewToolkit.class.getClass().getResource(ValuesMgr.SYS_VALUES.RESOURCES_PATH + resTitle));
	}

	/**
	 * 设置背景色 new Color(240, 240, 240)
	 */
	public static void setBackColor(Color color, Container con) {
		for (int i = 0; i < con.getComponentCount(); i++) {
			Component temp = con.getComponent(i);
			if (!(temp instanceof JButton) && !(temp instanceof JComboBox)) {
				temp.setBackground(color);
				if (temp instanceof Container) {
					Container sub = (Container) temp;
					setBackColor(color, sub);
				}
			}
		}
	}

	/**
	 * 显示提示信息对话框
	 */
	public static void showMsgInfo(Component comp, String tipInfo) {
		JOptionPane pane = new JOptionPane("  " + tipInfo);
		pane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = pane.createDialog(comp, ValuesMgr.SYS_VALUES.ABOUT_DIALOG_TITLE);
		setBackColor(new Color(240, 240, 240), dialog);
		dialog.show();
	}

	/**
	 * 显示确认信息对话框
	 */
	public static int showConfInfo(Component comp, String tipInfo) {
		JOptionPane pane = new JOptionPane("  " + tipInfo, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialog = pane.createDialog(comp, ValuesMgr.SYS_VALUES.ABOUT_DIALOG_TITLE);
		setBackColor(new Color(240, 240, 240), dialog);
		dialog.show();
		Object rtnValue = pane.getValue();
		if (null != rtnValue && rtnValue instanceof Integer) {
			return ((Integer) rtnValue).intValue();
		} else {
			return JOptionPane.CLOSED_OPTION;
		}
	}

	/**
	 * 创建文本框对象
	 */
	public static JTextField buildTxtField(String title, int colSize) {
		JTextField tmpTxtField = new JTextField(colSize);
		tmpTxtField.setToolTipText(title);
		return tmpTxtField;
	}

	/**
	 * buildTxtField 创建下拉选择框对象
	 */
	public static JComboBox buildComBox(String title, String[] values, boolean isEdit) {
		JComboBox tmpComBox = new JComboBox(values);
		tmpComBox.setToolTipText(title);
		tmpComBox.setEditable(isEdit);
		return tmpComBox;
	}

	/**
	 * 在JLabel上绘制文字
	 */
	public static ImageIcon createTxtImageIcon(String resTitle, String txtInfo) {
		ImageIcon imageIcon = ViewToolkit.createImageIcon(resTitle);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(ViewToolkit.class.getClass().getResource(ValuesMgr.SYS_VALUES.RESOURCES_PATH + resTitle)); // 将File对象转换成BufferedImage对象
		} catch (IOException e) {
			e.printStackTrace();
		}

		Graphics2D g = bufferedImage.createGraphics();
		g.setColor(Color.white); // 设置画笔颜色
		g.setFont(new Font("宋体", Font.PLAIN, 13));

		g.drawString(txtInfo, 8, imageIcon.getIconHeight() - 10);// 向缓冲图像里写文字,
		// 6,11可酌情改动

		Image image = Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());// 将BufferedImage对象转换成Image对象

		return new ImageIcon(image);
	}
}
