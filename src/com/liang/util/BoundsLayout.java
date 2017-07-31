package com.liang.util;

/**
 * @author liang wei
 * @description 用一句话描述下该文件的作用
 * @date 2017/6/29 17:32
 */
import javax.swing.*;
import java.awt.*;

/*
 * 2015-06-14
 */

public class BoundsLayout {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //设置panel的layout以及sieze
        JPanel jpanel = new JPanel();
        System.out.println("default PreferredSize is " + jpanel.getPreferredSize());
        System.out.println("default Size is " + jpanel.getSize());
        jpanel.setLayout(null);
        System.out.println("In null layout, the PreferredSize is " + jpanel.getPreferredSize());
        System.out.println("In null layout, the Size is " + jpanel.getSize());
        jpanel.setPreferredSize(new Dimension(400, 400));
        //添加按钮
        JButton button11 = new JButton("setBounds");
        JButton button12 = new JButton("setLocationAndSetSize");

        button11.setBounds(30, 100, 20, 20);
        button12.setBounds(50,100, 20, 20);
        //button12.setLocation(250, 250);
        //button12.setSize(100, 100);

        jpanel.add(button11);
        jpanel.add(button12);

        // 设置窗体属性
        JFrame frame = new JFrame("setBoundsDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
