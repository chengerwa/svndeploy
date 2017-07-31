package com.liang.util;

import javax.swing.*;
import java.util.List;

/**
 * @author liang wei
 * @description 用一句话描述下该文件的作用
 * @date 2017/6/29 18:12
 */
public class SwingUtil {
    public static void fillSelect(JComboBox jcomboBox, List<String> optionList){
        int len = optionList.size();
        for (int i = 0; i < len; i++) {
            jcomboBox.addItem(optionList.get(i));
        }
    }
}
