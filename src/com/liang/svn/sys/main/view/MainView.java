/**
 *
 */
package com.liang.svn.sys.main.view;

import com.liang.svn.comm.ui.CustSkinPanel;
import com.liang.svn.comm.ui.ViewDragHelper;
import com.liang.svn.comm.ui.ViewToolkit;
import com.liang.svn.comm.values.ValuesMgr;
import com.liang.svn.util.VersionDeploy;
import com.liang.util.ResourceUtil;
import com.liang.util.SwingUtil;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

/**
 * 系统的主窗体
 */
public class MainView extends JFrame implements ActionListener, MouseListener,ItemListener {

	private static final long serialVersionUID = 160140850862313179L;

	private Container conPanel;

	private JPanel headerPanel, headerTmpPanel1, headerTmpPanel2, footerPanel, leftPanel, rightPanel, svnSetPanel,mlysSetPanel,ftpSetPanel,verPubPanel;

	private JLabel sysMenuLabel, sysMinLabel, sysMaxLabel, sysCloseLabel;

	private JLabel topMenuSyLabel, topMenuXtglLabel, topMenuYwclLabel, topMenuXtbzLabel;

	private JLabel leftMenuYhglLabel,leftMenuMlysLabel, leftMenuJsglLabel, leftMenuCsglLabel;

	private JLabel svnTitle, svnUrlLabel, svnUserLabel, svnPwdLabel,svnSysLabel;

	private JLabel ftpTitle, ftpUrlLabel, ftpUserLabel, ftpPwdLabel,ftpSysLabel,ftpPortLabel,ftpProtclLabel,ftpUloadDirLabel,ftpLocalDirLabel,ftpShellLabel;

	private JLabel mlysTitle,mlysSysLabel,mlysLocalSrcLable,mlysRemoteSrcLable,mlysLocalWebLable,mlysRemoteWebLable,mlysLocalAppLable;

	private JLabel verPubTaskLabel,verPubSystemLabel;

	private Icon sysMenuIcon1, sysMenuIcon2, sysMinIcon1, sysMinIcon2, sysMaxIcon1, sysMaxIcon2, sysCloseIcon1, sysCloseIcon2;

	private Icon topMenuSyIcon1, topMenuSyIcon2, topMenuXtglIcon1, topMenuXtglIcon2, topMenuYwclIcon1, topMenuYwclIcon2, topMenuXtbzIcon1,
			topMenuXtbzIcon2;

	private Icon leftMenuYhglIcon1, leftMenuYhglIcon2, leftMenuJsglIcon1, leftMenuJsglIcon2, leftMenuCsglIcon1, leftMenuCsglIcon2,leftMenuMlysIcon1, leftMenuMlysIcon2,bcBtnIcon1;

	private static final String ICON_FLAG_01 = "01_", ICON_FLAG_02 = "02_";

	private JTextField svnUrlTf, svnUserTf, svnPwdTf;

	private JTextField mlysLocalSrcTf,mlysRemoteSrcTf,mlysLocalWebTf,mlysRemoteWebTf,mlysLocalAppTf;

	private JTextField ftpUrlTf, ftpUserTf, ftpPwdTf,ftpPortTf,ftpUploadDirTf,ftpLocalDirTf,ftpShellTf;

	private JTextField verPutTaskTf;

	private JComboBox svnSetSysJCombox,mlysSysJCombox,verPubJcombox,ftpProtclJcombox,ftpSysJcombox;

	private JButton svnSaveBtn,mlysSaveBtn, ftpSaveBtn,svnCloseBtn,verPubBtn;

	/**
	 * 构造器
	 */
	public MainView() {
		init();
		try {buildElems();}catch (Exception e){}
		fitTogether();
	}

	/**
	 * 初始化自己
	 */
	private void init() {
		this.setTitle(ValuesMgr.SYS_VALUES.MAIN_VIEW_TITLE);
		this.setSize(ValuesMgr.SYS_VALUES.MAIN_VIEW_WIDTH, ValuesMgr.SYS_VALUES.MAIN_VIEW_HEIGHT);
		this.setIconImage(ViewToolkit.createImageIcon(ValuesMgr.SYS_VALUES.SYSTEM_ICON_IMG).getImage());
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置圆角边框
		Shape shape = new RoundRectangle2D.Double(0, 0, ValuesMgr.SYS_VALUES.MAIN_VIEW_WIDTH, ValuesMgr.SYS_VALUES.MAIN_VIEW_HEIGHT, 9D, 9D);
		AWTUtilities.setWindowShape(this, shape);
		// 设置系统字体等
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		// UIManager.getLookAndFeelDefaults().put("defaultFont", new
		// Font("微软雅黑", Font.PLAIN, 12));
	}

	/**
	 * 构建窗口元素
	 */
	public void buildElems() throws  Exception {
		conPanel = this.getContentPane();
		conPanel.setLayout(new BorderLayout(0, 0));

		headerPanel = new JPanel();
		headerPanel.setPreferredSize(new Dimension(ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_WIDTH, ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_HEIGHT));
		headerPanel.setLayout(new BorderLayout());
		new ViewDragHelper(this, headerPanel);
		//最大，最小，关闭panel
		headerTmpPanel1 = new CustSkinPanel(ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_IMG_01, ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_WIDTH_01,
				ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_HEIGHT_01);
		headerTmpPanel1.setPreferredSize(new Dimension(ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_WIDTH_01,
				ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_HEIGHT_01));
		headerTmpPanel1.setLayout(new FlowLayout(2, 0, 0));
		//menu log panel
		headerTmpPanel2 = new CustSkinPanel(ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_IMG_02, ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_WIDTH_02,
				ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_HEIGHT_02);
		headerTmpPanel2.setPreferredSize(new Dimension(ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_WIDTH_02,
				ValuesMgr.SYS_VALUES.MAIN_VIEW_HEADER_HEIGHT_02));
		headerTmpPanel2.setLayout(new FlowLayout(0, 0, 0));
		//底部log栏
		footerPanel = new CustSkinPanel(ValuesMgr.SYS_VALUES.MAIN_VIEW_FOOTER_IMG, ValuesMgr.SYS_VALUES.MAIN_VIEW_FOOTER_WIDTH,
				ValuesMgr.SYS_VALUES.MAIN_VIEW_FOOTER_HEIGHT);
		footerPanel.setPreferredSize(new Dimension(ValuesMgr.SYS_VALUES.MAIN_VIEW_FOOTER_WIDTH, ValuesMgr.SYS_VALUES.MAIN_VIEW_FOOTER_HEIGHT));
		footerPanel.setLayout(new FlowLayout(2, 1, 0));
		new ViewDragHelper(this, footerPanel);

		leftPanel = new CustSkinPanel(ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_IMG, 169, 550);
		leftPanel.setLayout(new FlowLayout(0, 0, 0));
		leftPanel.setPreferredSize(new Dimension(169, 550));

		rightPanel = new CustSkinPanel(null, ValuesMgr.SYS_VALUES.MAIN_VIEW_FOOTER_WIDTH - 169, 550);
		//SVN 参数配置panel
		svnSetPanel = new CustSkinPanel(null, 450, 200);
		svnSetPanel.setPreferredSize(new Dimension(450, 200));
		svnSetPanel.setLayout(new GridBagLayout());
		//目录映射panel
		mlysSetPanel  = new CustSkinPanel(null, 450, 200);
		mlysSetPanel.setPreferredSize(new Dimension(450, 200));
		mlysSetPanel.setLayout(new GridBagLayout());
		//FTP 设置panel
		ftpSetPanel = new CustSkinPanel(null, 450, 300);
		ftpSetPanel.setPreferredSize(new Dimension(450, 300));
		ftpSetPanel.setLayout(new GridBagLayout());

		//版本发布参数配置panel
		verPubPanel = new CustSkinPanel(null, 450, 200);
		verPubPanel.setPreferredSize(new Dimension(450, 200));
		verPubPanel.setLayout(null);

		sysMenuIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_SYS_MENU_IMG);
		sysMenuIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_SYS_MENU_IMG);

		sysMinIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_MIN_BTN_IMG);
		sysMinIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_MIN_BTN_IMG);

		sysMaxIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_MAX_BTN_IMG);
		sysMaxIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_MAX_BTN_IMG);

		sysCloseIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_CLOSE_BTN_IMG);
		sysCloseIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_CLOSE_BTN_IMG);

		topMenuSyIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_SY);
		topMenuSyIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_SY);

		topMenuXtglIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_XTGL);
		topMenuXtglIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_XTGL);

		topMenuYwclIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_YWCL);
		topMenuYwclIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_YWCL);

		topMenuXtbzIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_XTBZ);
		topMenuXtbzIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_TOP_MENU_IMG_XTBZ);
		//左侧第1个菜单
		leftMenuYhglIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_YHGL);

		leftMenuYhglIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_YHGL);

		//左侧第2个菜单
		leftMenuMlysIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_MLYS);
		leftMenuMlysIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_MLYS);

		//左侧第3个菜单
		leftMenuJsglIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_JSGL);
		leftMenuJsglIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_JSGL);

		//左侧第4个菜单
		leftMenuCsglIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_CSGL);
		leftMenuCsglIcon2 = ViewToolkit.createImageIcon(ICON_FLAG_02 + ValuesMgr.SYS_VALUES.MAIN_VIEW_LEFT_MENU_IMG_CSGL);

		//保存按钮
		bcBtnIcon1 = ViewToolkit.createImageIcon(ICON_FLAG_01 + ValuesMgr.SYS_VALUES.MAIN_VIEW_COMMON_BTN_SAVE);
		sysMenuLabel = new JLabel(sysMenuIcon1);
		sysMenuLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sysMenuLabel.setToolTipText(ValuesMgr.SYS_VALUES.MAIN_VIEW_SYS_MENU_TIP);
		sysMenuLabel.addMouseListener(this);

		sysMinLabel = new JLabel(sysMinIcon1);
		sysMinLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sysMinLabel.setToolTipText(ValuesMgr.SYS_VALUES.MAIN_VIEW_MIN_BTN_TIP);
		sysMinLabel.addMouseListener(this);

		sysMaxLabel = new JLabel(sysMaxIcon1);
		sysMaxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sysMaxLabel.setToolTipText(ValuesMgr.SYS_VALUES.MAIN_VIEW_MAX_BTN_TIP);
		sysMaxLabel.addMouseListener(this);

		sysCloseLabel = new JLabel(sysCloseIcon1);
		sysCloseLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sysCloseLabel.setToolTipText(ValuesMgr.SYS_VALUES.MAIN_VIEW_CLOSE_BTN_TIP);
		sysCloseLabel.addMouseListener(this);

		topMenuSyLabel = new JLabel(topMenuSyIcon1);
		topMenuSyLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		topMenuSyLabel.addMouseListener(this);

		topMenuXtglLabel = new JLabel(topMenuXtglIcon1);
		topMenuXtglLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		topMenuXtglLabel.addMouseListener(this);

		topMenuYwclLabel = new JLabel(topMenuYwclIcon1);
		topMenuYwclLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		topMenuYwclLabel.addMouseListener(this);

		topMenuXtbzLabel = new JLabel(topMenuXtbzIcon1);
		topMenuXtbzLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		topMenuXtbzLabel.addMouseListener(this);

		leftMenuYhglLabel = new JLabel(leftMenuYhglIcon1);
		leftMenuYhglLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		leftMenuYhglLabel.addMouseListener(new MenuListener(rightPanel,svnSetPanel));

		leftMenuMlysLabel = new JLabel(leftMenuMlysIcon1);
		leftMenuMlysLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		leftMenuMlysLabel.addMouseListener(new MenuListener(rightPanel,mlysSetPanel));

		leftMenuJsglLabel = new JLabel(leftMenuJsglIcon1);
		leftMenuJsglLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		leftMenuJsglLabel.addMouseListener(new MenuListener(rightPanel,ftpSetPanel));

		leftMenuCsglLabel = new JLabel(leftMenuCsglIcon1);
		leftMenuCsglLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		leftMenuCsglLabel.addMouseListener(new MenuListener(rightPanel,verPubPanel));

		//SVN设置 Jabel
		svnTitle = new JLabel("SVN 设置");
		svnUrlLabel = new JLabel("URL:");
		svnUserLabel = new JLabel("用户名:");
		svnPwdLabel = new JLabel("密 码:");
		svnSysLabel = new JLabel("系 统:");

		svnUrlTf = new JTextField(25);
		svnUserTf = new JTextField(25);
		svnPwdTf = new JTextField(25);

		svnSaveBtn = new Button(1);
		svnSaveBtn.addActionListener(this);
		//目录映射
		mlysTitle = new JLabel("SVN与本地文件映射");
		mlysSysLabel = new JLabel("系统:");
		mlysRemoteSrcLable = new JLabel("Java路径(SVN):");
		mlysLocalSrcLable = new JLabel("本地class路径");
		mlysRemoteWebLable = new JLabel("Web目录(SVN):");
		mlysLocalWebLable = new JLabel("Web本地目录:");
        mlysLocalAppLable = new JLabel("本地应用目录:");

		mlysLocalSrcTf = new JTextField(25);
		mlysRemoteSrcTf = new JTextField(25);
		mlysLocalWebTf = new JTextField(25);
		mlysRemoteWebTf = new JTextField(25);
		mlysLocalAppTf = new JTextField(25);

		mlysSaveBtn = new Button(1);
		mlysSaveBtn.addActionListener(this);

		//FTP 设置
		ftpProtclLabel = new JLabel("ftp 传输协议:");
		ftpTitle = new JLabel("ftp 参数设置");
		ftpPortLabel = new JLabel("端  口:");
		ftpPwdLabel = new JLabel("密  码:");
		ftpUserLabel = new JLabel("用户名:");
		ftpSysLabel = new JLabel("系  统:");
		ftpUrlLabel = new JLabel("Ip  :");
		ftpUloadDirLabel = new JLabel("上传目录:");
		ftpLocalDirLabel = new JLabel("升级文件存储:");
		ftpShellLabel = new JLabel("上传后执行sh文件:");


		ftpPortTf = new JTextField(25);
		ftpUserTf = new JTextField(25);
		ftpPwdTf = new JTextField(25);
		ftpUrlTf = new JTextField(25);
		ftpUploadDirTf = new JTextField(25);
		ftpLocalDirTf = new JTextField(25);
		ftpShellTf = new JTextField(25);
		ftpSaveBtn = new Button(1);
		ftpSaveBtn.addActionListener(this);
		//ftp 设置

		//版本发布 label
		verPubTaskLabel = new JLabel("任 务 号:");
		verPubSystemLabel = new JLabel("发布系统:");
		verPutTaskTf = new JTextField(25);

		verPubBtn = new Button(1);
		verPubBtn.addActionListener(this);
		// 数据读取初始化  start
		svnSetSysJCombox = new JComboBox();
		String[] systemCodes = ResourceUtil.getValue("system_codes").split(",");
		SwingUtil.fillSelect(svnSetSysJCombox,Arrays.asList(systemCodes));
		svnSetSysJCombox.addItemListener(this);

		mlysSysJCombox = new JComboBox();
		SwingUtil.fillSelect(mlysSysJCombox,Arrays.asList(systemCodes));
		mlysSysJCombox.addItemListener(this);

		verPubJcombox = new JComboBox();
		SwingUtil.fillSelect(verPubJcombox,Arrays.asList(systemCodes));

		ftpSysJcombox = new JComboBox();
		SwingUtil.fillSelect(ftpSysJcombox,Arrays.asList(systemCodes));
		ftpSysJcombox.addItemListener(this);
		initSvnData();
		initMlysData();
		initFtpData();
		 //数据读取初始化end

	}

	/**
	 * 组装拼接界面
	 */
	public void fitTogether() {
		headerTmpPanel1.add(sysMenuLabel);
		headerTmpPanel1.add(sysMinLabel);
		headerTmpPanel1.add(sysMaxLabel);
		headerTmpPanel1.add(sysCloseLabel);
		headerTmpPanel1.add(Box.createHorizontalStrut(7));

		headerTmpPanel2.add(Box.createHorizontalStrut(185));
		//headerTmpPanel2.add(topMenuSyLabel);
		//headerTmpPanel2.add(topMenuXtglLabel);
		//headerTmpPanel2.add(topMenuYwclLabel);
		headerTmpPanel2.add(topMenuXtbzLabel);

		headerPanel.add(BorderLayout.NORTH, headerTmpPanel1);
		headerPanel.add(BorderLayout.CENTER, headerTmpPanel2);

		leftPanel.add(leftMenuYhglLabel);
		leftPanel.add(leftMenuMlysLabel);
		leftPanel.add(leftMenuJsglLabel);
		leftPanel.add(leftMenuCsglLabel);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 2;
		constraints.weighty = 6;
		//snv
		add(svnSetPanel, svnTitle, constraints, 0, 1, 2, 1);

		add(svnSetPanel, svnSysLabel, constraints, 0, 2, 1, 1);
		add(svnSetPanel, svnSetSysJCombox, constraints, 1, 2, 1, 1);

		add(svnSetPanel, svnUrlLabel, constraints, 0, 3, 1, 1);
		add(svnSetPanel, svnUrlTf, constraints, 1, 3, 1, 1);

		add(svnSetPanel, svnUserLabel, constraints, 0, 4, 1, 1);
		add(svnSetPanel, svnUserTf, constraints, 1, 4, 1, 1);

		add(svnSetPanel, svnPwdLabel, constraints, 0, 5, 1, 1);
		add(svnSetPanel, svnPwdTf, constraints, 1, 5, 1, 1);

		add(svnSetPanel, svnSaveBtn, constraints, 0, 6, 2, 1);
		rightPanel.add(BorderLayout.CENTER, svnSetPanel);
		//snv
		//目录映射
		constraints.weighty = 8;
		add(mlysSetPanel,mlysTitle , constraints, 0, 1, 2, 1);

		add(mlysSetPanel, mlysSysLabel, constraints, 0, 2, 1, 1);
		add(mlysSetPanel, mlysSysJCombox, constraints, 1, 2, 1, 1);

		add(mlysSetPanel, mlysLocalAppLable, constraints, 0, 3, 1, 1);
		add(mlysSetPanel, mlysLocalAppTf, constraints, 1, 3, 1, 1);

		add(mlysSetPanel, mlysLocalSrcLable, constraints, 0, 4, 1, 1);
		add(mlysSetPanel, mlysLocalSrcTf, constraints, 1, 4, 1, 1);

		add(mlysSetPanel, mlysRemoteSrcLable, constraints, 0, 5, 1, 1);
		add(mlysSetPanel, mlysRemoteSrcTf, constraints, 1, 5, 1, 1);

		add(mlysSetPanel, mlysLocalWebLable, constraints, 0, 6, 1, 1);
		add(mlysSetPanel, mlysLocalWebTf, constraints, 1, 6, 1, 1);

		add(mlysSetPanel, mlysRemoteWebLable, constraints, 0, 7, 1, 1);
		add(mlysSetPanel, mlysRemoteWebTf, constraints, 1, 7, 1, 1);

		add(mlysSetPanel, mlysSaveBtn, constraints, 0, 8, 2, 1);
		//ftp
		constraints.weighty = 11;
		int rowIndex = 1;
		add(ftpSetPanel, ftpTitle, constraints, 0, rowIndex, 2, 1);
		rowIndex++;

		add(ftpSetPanel, ftpSysLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpSysJcombox, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpUrlLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpUrlTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpProtclLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpProtclJcombox, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpPortLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpPortTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpUserLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpUserTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpPwdLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpPwdTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpUloadDirLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpUploadDirTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpLocalDirLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpLocalDirTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpShellLabel, constraints, 0, rowIndex, 1, 1);
		add(ftpSetPanel, ftpShellTf, constraints, 1, rowIndex, 1, 1);
		rowIndex++;

		add(ftpSetPanel, ftpSaveBtn, constraints, 0, rowIndex, 2, 1);

		//ver
		verPubSystemLabel.setBounds(new Rectangle(50, 20, 60, 20));
		verPubJcombox.setBounds(new Rectangle(150, 20, 150, 25));

		verPubTaskLabel.setBounds(new Rectangle(50, 50, 60, 20));
		verPutTaskTf.setBounds(new Rectangle(150, 50, 150, 25));

		verPubBtn.setBounds(new Rectangle(180, 120, 66, 30));

		verPubPanel.add(verPubBtn);
		verPubPanel.add(verPubSystemLabel);
		verPubPanel.add(verPubTaskLabel);
		verPubPanel.add(verPutTaskTf);
		verPubPanel.add(verPubJcombox);

		conPanel.add(BorderLayout.NORTH, headerPanel);
		conPanel.add(BorderLayout.WEST, leftPanel);
		conPanel.add(BorderLayout.CENTER, rightPanel);
		// conPanel.add(BorderLayout.CENTER, new
		// JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel));
		conPanel.add(BorderLayout.SOUTH, footerPanel);

	}

	// -------------------------------事件处理

	/**
	 * 处理顶部菜单选择
	 */
	private void dealMouseClickEffect(JLabel selLabel) {
		if (selLabel.equals(topMenuSyLabel)) {
			topMenuSyLabel.setIcon(topMenuSyIcon2);
			topMenuXtglLabel.setIcon(topMenuXtglIcon1);
			topMenuYwclLabel.setIcon(topMenuYwclIcon1);
			topMenuXtbzLabel.setIcon(topMenuXtbzIcon1);
		} else if (selLabel.equals(topMenuXtglLabel)) {
			topMenuXtglLabel.setIcon(topMenuXtglIcon2);
			topMenuSyLabel.setIcon(topMenuSyIcon1);
			topMenuYwclLabel.setIcon(topMenuYwclIcon1);
			topMenuXtbzLabel.setIcon(topMenuXtbzIcon1);
		} else if (selLabel.equals(topMenuYwclLabel)) {
			topMenuYwclLabel.setIcon(topMenuYwclIcon2);
			topMenuXtglLabel.setIcon(topMenuXtglIcon1);
			topMenuSyLabel.setIcon(topMenuSyIcon1);
			topMenuXtbzLabel.setIcon(topMenuXtbzIcon1);
		} else if (selLabel.equals(topMenuXtbzLabel)) {
			topMenuXtbzLabel.setIcon(topMenuXtbzIcon2);
			topMenuYwclLabel.setIcon(topMenuYwclIcon1);
			topMenuXtglLabel.setIcon(topMenuXtglIcon1);
			topMenuSyLabel.setIcon(topMenuSyIcon1);
		}
	}

	/**
	 * 设置系统菜单选择效果
	 */
	private void dealMouseOverAndOutEffect(JLabel selLabel, int type) {
		if (selLabel.equals(sysMenuLabel)) {
			if (0 == type) {
				sysMenuLabel.setIcon(sysMenuIcon2);
			} else if (1 == type) {
				sysMenuLabel.setIcon(sysMenuIcon1);
			}
		} else if (selLabel.equals(sysMaxLabel)) {
			if (0 == type) {
				sysMaxLabel.setIcon(sysMaxIcon2);
			} else if (1 == type) {
				sysMaxLabel.setIcon(sysMaxIcon1);
			}
		} else if (selLabel.equals(sysMinLabel)) {
			if (0 == type) {
				sysMinLabel.setIcon(sysMinIcon2);
			} else if (1 == type) {
				sysMinLabel.setIcon(sysMinIcon1);
			} else if (2 == type) {
				this.setState(JFrame.ICONIFIED);
			}
		} else if (selLabel.equals(sysCloseLabel)) {
			if (0 == type) {
				sysCloseLabel.setIcon(sysCloseIcon2);
			} else if (1 == type) {
				sysCloseLabel.setIcon(sysCloseIcon1);
			} else if (2 == type) {
				System.exit(0);
			}
		} else if (selLabel.equals(topMenuSyLabel)) {
			if (0 == type) {
				topMenuSyLabel.setIcon(topMenuSyIcon2);
			} else if (1 == type) {
				topMenuSyLabel.setIcon(topMenuSyIcon1);
			}
		} else if (selLabel.equals(topMenuXtglLabel)) {
			if (0 == type) {
				topMenuXtglLabel.setIcon(topMenuXtglIcon2);
			} else if (1 == type) {
				topMenuXtglLabel.setIcon(topMenuXtglIcon1);
			}
		} else if (selLabel.equals(topMenuYwclLabel)) {
			if (0 == type) {
				topMenuYwclLabel.setIcon(topMenuYwclIcon2);
			} else if (1 == type) {
				topMenuYwclLabel.setIcon(topMenuYwclIcon1);
			}
		} else if (selLabel.equals(topMenuXtbzLabel)) {
			if (0 == type) {
				topMenuXtbzLabel.setIcon(topMenuXtbzIcon2);
			} else if (1 == type) {
				topMenuXtbzLabel.setIcon(topMenuXtbzIcon1);
			}
		} else if (selLabel.equals(leftMenuYhglLabel)) {
			if (0 == type) {
				leftMenuYhglLabel.setIcon(leftMenuYhglIcon2);
			} else if (1 == type) {
				leftMenuYhglLabel.setIcon(leftMenuYhglIcon1);
			}
		} else if (selLabel.equals(leftMenuJsglLabel)) {
			if (0 == type) {
				leftMenuJsglLabel.setIcon(leftMenuJsglIcon2);
			} else if (1 == type) {
				leftMenuJsglLabel.setIcon(leftMenuJsglIcon1);
			}
		} else if (selLabel.equals(leftMenuCsglLabel)) {
			if (0 == type) {
				leftMenuCsglLabel.setIcon(leftMenuCsglIcon2);
			} else if (1 == type) {
				leftMenuCsglLabel.setIcon(leftMenuCsglIcon1);
			}
		} else if (selLabel.equals(leftMenuMlysLabel)) {
			if (0 == type) {
				leftMenuMlysLabel.setIcon(leftMenuMlysIcon2);
			} else if (1 == type) {
				leftMenuMlysLabel.setIcon(leftMenuMlysIcon1);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			dealMouseOverAndOutEffect((JLabel) e.getSource(), 2);
		} else{
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			dealMouseOverAndOutEffect((JLabel) e.getSource(), 0);
		} else {
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			dealMouseOverAndOutEffect((JLabel) e.getSource(), 1);
		} else {
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			if(e.getSource() == svnSaveBtn){
				svnSave();
			}else if(e.getSource() == mlysSaveBtn){
				mlysSave();
			}else if(e.getSource() == ftpSaveBtn){
				ftpSave();
			}else if(e.getSource() == verPubBtn){
				verPubBtn.setEnabled(false);
				verPub();
				verPubBtn.setEnabled(true);
			}
		}
	}

	/**
	 * @param cp          需要添加的组件
	 * @param constraints
	 * @param r           指控件位于第几行。
	 * @param c           指控件位于第几列。
	 * @param w           指控件需要占几列。
	 * @param h           指控件需要占几行。
	 */
	public void add(JPanel panel, Component cp, GridBagConstraints constraints, int c, int r, int w, int h)

	{
		constraints.gridx = c;

		constraints.gridy = r;

		constraints.gridwidth = w;

		constraints.gridheight = h;

		//按照网格组布局方式排列组件

		panel.add(cp, constraints);  //这个地方是调用的父类的add方法

	}
	public void svnSave(){
		try {
			String svnUrl = svnUrlTf.getText().trim();
			String svnUser = svnUserTf.getText().trim();
			String svnPwd = svnPwdTf.getText().trim();
			if(null == svnUrl || "".equals(svnUrl)){
				JOptionPane.showMessageDialog(svnSetPanel,"请输入SVN地址");
				return;
			}
			if(null == svnUser || "".equals(svnUser)){
				JOptionPane.showMessageDialog(svnSetPanel,"请输入SVN用户名");
				return;
			}
			if(null == svnPwd || "".equals(svnPwd)){
				JOptionPane.showMessageDialog(svnSetPanel,"请输入SVN密码");
				return;
			}
			String svnSystemCode = svnSetSysJCombox.getSelectedItem().toString();
			ResourceUtil.storeValue(svnSystemCode+"_svnUrl",svnUrl);
			ResourceUtil.storeValue(svnSystemCode+"_svnUser",svnUser);
			ResourceUtil.storeValue(svnSystemCode+"_svnPwd",svnPwd);
			JOptionPane.showMessageDialog(ftpSetPanel,"保存成功");
		}catch (Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ftpSetPanel,"保存失败:"+ex.getMessage());
		}
	}
	public void mlysSave(){
		try {

			String mlysLocalSrc = mlysLocalSrcTf.getText().trim();
			String mlysRemoteSrc = mlysRemoteSrcTf.getText().trim();
			String mlysLocalWeb = mlysLocalWebTf.getText().trim();
			String mlysRemoteWeb = mlysRemoteWebTf.getText().trim();
			String mlysLocalApp = mlysLocalAppTf.getText().trim();
			if(null == mlysLocalSrc || "".equals(mlysLocalSrc)){
				JOptionPane.showMessageDialog(mlysSetPanel,"请输入本地class文件的相对路径");
				return;
			}
			if(null == mlysRemoteSrc || "".equals(mlysRemoteSrc)){
				JOptionPane.showMessageDialog(mlysSetPanel,"请输入svn对应的java源码目录");
				return;
			}
			if(null == mlysLocalWeb || "".equals(mlysLocalWeb)){
				JOptionPane.showMessageDialog(mlysSetPanel,"请输入本地web目录");
				return;
			}
			if(null == mlysRemoteWeb || "".equals(mlysRemoteWeb)){
				JOptionPane.showMessageDialog(mlysSetPanel,"请输入SVN对应的web目录");
				return;
			}
			if(null == mlysLocalApp || "".equals(mlysLocalApp)){
				JOptionPane.showMessageDialog(mlysSetPanel,"请输入本地应用目录");
				return;
			}
			String svnSystemCode = mlysSysJCombox.getSelectedItem().toString();
			ResourceUtil.storeValue(svnSystemCode+"_oldSrcUrl",mlysRemoteSrc);
			ResourceUtil.storeValue(svnSystemCode+"_newSrcUrl",mlysLocalSrc);

			ResourceUtil.storeValue(svnSystemCode+"_oldWebUrl",mlysRemoteWeb);
			ResourceUtil.storeValue(svnSystemCode+"_newWebUrl",mlysLocalWeb);
			ResourceUtil.storeValue(svnSystemCode+"_localAppUrl",mlysLocalApp);

			JOptionPane.showMessageDialog(mlysSetPanel,"保存成功");
		}catch (Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(mlysSetPanel,"保存失败:"+ex.getMessage());
		}
	}
	public void ftpSave(){
		try {
			String ftpUrl = ftpUrlTf.getText().trim();
			String ftpUser = ftpUserTf.getText().trim();
			String ftpPwd = ftpPwdTf.getText().trim();
			String ftpPort = ftpPortTf.getText().trim();
			String ftpUploadDir = ftpUploadDirTf.getText().trim();
			String ftpShell = ftpShellTf.getText().trim();
			String ftpProtcl = ftpProtclJcombox.getSelectedItem().toString();
			String systemCode = ftpSysJcombox.getSelectedItem().toString();
			String ftpLocalDir = ftpLocalDirTf.getText().toString();
			if(null == ftpUrl || "".equals(ftpUrl) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"Ftp ip地址不能为空");
				return;
			}
			if(null == ftpUser || "".equals(ftpUser) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"Ftp 用户名不能为空");
				return;
			}
			if(null == ftpPwd || "".equals(ftpPwd) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"Ftp 密码不能为空");
				return;
			}
			if(null == ftpLocalDir || "".equals(ftpLocalDir) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"升级文件本地存储目录不能为空");
				return;
			}
			if(null == ftpPort || "".equals(ftpPort) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"Ftp 端口不能为空");
				return;
			}else{
				try {
					Integer.parseInt(ftpPort);
				}catch (Exception ex){
					JOptionPane.showMessageDialog(ftpSetPanel,"Ftp 端口不是数字!");
					return;
				}
			}
			if(null == ftpUploadDir || "".equals(ftpUploadDir) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"Ftp 上传地址不能为空");
				return;
			}
			if(null == ftpShell || "".equals(ftpShell) ){
				JOptionPane.showMessageDialog(ftpSetPanel,"sh文件不能为空");
				return;
			}
			ResourceUtil.storeValue(systemCode+"_ftpip",ftpUrl);
			ResourceUtil.storeValue(systemCode+"_ftpuser",ftpUser);
			ResourceUtil.storeValue(systemCode+"_ftppwd",ftpPwd);
			ResourceUtil.storeValue(systemCode+"_ftpport",ftpPort);
			ResourceUtil.storeValue(systemCode+"_ftpuploadDir",ftpUploadDir);
			ResourceUtil.storeValue(systemCode+"_ftpprotocol",ftpProtcl);
			ResourceUtil.storeValue(systemCode+"_ftpLocalDir",ftpLocalDir);
			ResourceUtil.storeValue(systemCode+"_ftpshell",ftpShell);
			JOptionPane.showMessageDialog(ftpSetPanel,"保存成功");
		}catch (Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ftpSetPanel,"保存失败:"+ex.getMessage());
		}
	}
	class MenuListener implements  MouseListener{
		private JPanel target ;
		private JPanel showPanel ;
		public MenuListener(JPanel target,JPanel showPanel){
			this.target = target;
			this.showPanel = showPanel;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JLabel) {
				dealMouseOverAndOutEffect((JLabel) e.getSource(), 2);
			} else {
			}
			//右边面板重新加载
			target.removeAll();
			target.add(showPanel);
			target.updateUI();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof JLabel) {
				dealMouseOverAndOutEffect((JLabel) e.getSource(), 0);
			} else {
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof JLabel) {
				dealMouseOverAndOutEffect((JLabel) e.getSource(), 1);
			} else {
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		public JPanel getTarget() {
			return target;
		}

		public void setTarget(JPanel target) {
			this.target = target;
		}

		public JPanel getShowPanel() {
			return showPanel;
		}

		public void setShowPanel(JPanel showPanel) {
			this.showPanel = showPanel;
		}
	}
	public void verPub(){
		String taskNo = verPutTaskTf.getText().trim();
		if(taskNo.isEmpty()){
			JOptionPane.showMessageDialog(verPubPanel,"请填写需要发布的任务号");
			return;
		}
		int choice = JOptionPane.showConfirmDialog(verPubPanel,"确认要发布版本"+taskNo+" 吗?");
		if(0 == choice){
			JOptionPane.showMessageDialog(verPubPanel,new VersionDeploy().deploy(taskNo,verPubJcombox.getSelectedItem().toString()));
		}
	}
	public void initSvnData(){
		String svnSystemCode = svnSetSysJCombox.getSelectedItem().toString();
		try {
			svnUrlTf.setText(ResourceUtil.getValue(svnSystemCode+"_svnUrl"));
		}catch (Exception e){
			svnUrlTf.setText("");
		}
		try {
			svnUserTf.setText(ResourceUtil.getValue(svnSystemCode+"_svnUser"));
		}catch (Exception e){
			svnUserTf.setText("");
		}
		try {
			svnPwdTf.setText(ResourceUtil.getValue(svnSystemCode+"_svnPwd"));
		}catch (Exception e){
			svnPwdTf.setText("");
		}
	}
	public void initMlysData(){
		String mlysSystemCode = mlysSysJCombox.getSelectedItem().toString();
		try {
			mlysLocalSrcTf.setText(ResourceUtil.getValue(mlysSystemCode+"_newSrcUrl"));
		}catch (Exception e){
			mlysLocalSrcTf.setText("");
		}
		try {
			mlysRemoteSrcTf.setText(ResourceUtil.getValue(mlysSystemCode+"_oldSrcUrl"));
		}catch (Exception e){
			mlysRemoteSrcTf.setText("");
		}
		try {
			mlysLocalWebTf.setText(ResourceUtil.getValue(mlysSystemCode+"_newWebUrl"));
		}catch (Exception e){
			mlysLocalWebTf.setText("");
		}
		try {
			mlysRemoteWebTf.setText(ResourceUtil.getValue(mlysSystemCode+"_oldWebUrl"));
		}catch (Exception e){
			mlysRemoteWebTf.setText("");
		}
        try {
            mlysLocalAppTf.setText(ResourceUtil.getValue(mlysSystemCode+"_localAppUrl"));
        }catch (Exception e){
            mlysLocalAppTf.setText("");
        }
	}
	public void initFtpData(){
		ftpProtclJcombox = new JComboBox();
		ftpProtclJcombox.addItem("sftp");
		ftpProtclJcombox.addItem("ftp");
		String ftpSystemCode = ftpSysJcombox.getSelectedItem().toString();
		try {
			String protocl = ResourceUtil.getValue(ftpSystemCode+"_ftpprotocol");
			if("sftp".equals(protocl)){
				ftpProtclJcombox.setSelectedIndex(0);
			}else{
				ftpProtclJcombox.setSelectedIndex(1);
			}
		}catch (Exception e){
			ftpUploadDirTf.setText("");
		}
		try {
			ftpUploadDirTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftpuploadDir"));
		}catch (Exception e){
			ftpUploadDirTf.setText("");
		}
		try {
			ftpLocalDirTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftpLocalDir"));
		}catch (Exception e){
			ftpLocalDirTf.setText("");
		}
		try {
			ftpUrlTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftpip"));
		}catch (Exception e){
			ftpUrlTf.setText("");
		}
		try {
			ftpUserTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftpuser"));
		}catch (Exception e){
			ftpUserTf.setText("");
		}
		try {
			ftpPwdTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftppwd"));
		}catch (Exception e){
			ftpPwdTf.setText("");
		}
		try {
			ftpPortTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftpport"));
		}catch (Exception e){
			ftpPortTf.setText("");
		}
		try {
			ftpShellTf.setText(ResourceUtil.getValue(ftpSystemCode+"_ftpshell"));
		}catch (Exception e){
			ftpShellTf.setText("");
		}
	}
	class Button extends JButton{
		private Icon icon ;
		public Button(int buttonType){
			super();
			switch (buttonType){
				case 1 :{
					super.setIcon(bcBtnIcon1);
					super.setPreferredSize(new Dimension(66,30));
				}
			}
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == svnSetSysJCombox){
			initSvnData();
		}else if(e.getSource() == mlysSysJCombox){
			initMlysData();
		}else if(e.getSource() == ftpSysJcombox){
			initFtpData();
		}
	}
}
