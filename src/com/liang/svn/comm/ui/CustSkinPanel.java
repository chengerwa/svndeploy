/**
 *
 */
package com.liang.svn.comm.ui;

import com.liang.svn.comm.values.ValuesMgr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * 皮肤面板
 */
public class CustSkinPanel extends JPanel {

	private static final long serialVersionUID = -1142694233560441425L;

	private Image skinImg;

	private int width, height;

	private boolean isDialog;

	/**
	 * 默认构造器
	 */
	public CustSkinPanel() {
	}

	/**
	 * 构造对话框面板
	 */
	public CustSkinPanel(boolean isDialog) {
		this.isDialog = isDialog;
	}

	/**
	 * 构造贴图或绘制面板
	 */
	public CustSkinPanel(String resName, int width, int height) {
		this.width = width;
		this.height = height;
		try {
			if (null != resName && !"".equals(resName)) {
				skinImg = ImageIO.read(this.getClass().getResource(ValuesMgr.SYS_VALUES.RESOURCES_PATH + resName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建图片面板
	 */
	public void createImagePanel(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (0 != width) {
			g2d.drawImage(skinImg, 0, 0, width, height, null);
		} else {
			g2d.drawImage(skinImg, 0, 0, null);
		}
	}

	/**
	 * 创建色彩面板
	 */
	public void createColorPanel(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// Paint p = new GradientPaint(0.0f, 0.0f, new Color(255, 0, 51, 80),
		// 500,
		// 600, new Color(255, 204, 253, 255), true);
		Paint p = new GradientPaint(0.0f, 0.0f, new Color(0xF0FAFB), getWidth(), 0, new Color(0xAFDBF6), true);
		g2d.setPaint(p);

		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.setColor(new Color(0x9FCFEB));

		Shape shape = null;

		width = 0 == width ? super.getWidth() : width;
		height = 0 == height ? super.getWidth() : height;
		// shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1,
		// 5.0D, 5.0D);// 绘制窗体边框
		shape = new Rectangle2D.Double(0, 0, width - 1, height - 2);// 绘制窗体边框
		g2d.draw(shape);
	}

	/**
	 * 绘制白面板
	 */
	public void createDialogPanel(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// Paint p = new GradientPaint(0.0f, 0.0f, new Color(255, 0, 51, 80),
		// 500,
		// 600, new Color(255, 204, 253, 255), true);
		Paint p = new GradientPaint(0.0f, 0.0f, new Color(0xF0FAFB), 0, getHeight(), new Color(0xF0FAFB), true);
		g2d.setPaint(p);

		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.setColor(new Color(0x9FCFEB));

		Shape shape = null;

		width = 0 == width ? super.getWidth() : width;
		height = 0 == height ? super.getWidth() : height;
		// shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 2,
		// 5.0D, 5.0D);// 绘制窗体边框
		shape = new Rectangle2D.Double(0, 0, width - 1, height - 2);// 绘制窗体边框
		g2d.draw(shape);
	}

	/**
	 * 复写渲染方法
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isDialog) {
			createDialogPanel(g);
		} else {
			if (null != skinImg) {
				createImagePanel(g);
			} else {
				createColorPanel(g);
			}
		}
	}
}
