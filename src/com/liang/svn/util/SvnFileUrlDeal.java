package com.liang.svn.util;

import com.liang.util.ResourceUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liang wei
 * @description
 * @date 2017/6/27 17:22
 */
public class SvnFileUrlDeal {
    public List<String> getLocalFileList(List<String> rePosityList,String ppName) throws  Exception{
        List<String> fileList = new ArrayList<String>();
        if (rePosityList != null && rePosityList.size() > 0) {
            int fileSize = rePosityList.size();
            for (int i = 0; i < fileSize; i++) {
                String filePath = rePosityList.get(i);
                if(filePath.endsWith(".java")){
                    fileList.add(filePath.replaceFirst(ResourceUtil.getValue(ppName+"_oldSrcUrl"),ResourceUtil.getValue(ppName+"_newSrcUrl")).replace(".java",".class").replaceFirst( "/","" ));
                }else{
                    fileList.add(filePath.replaceFirst(ResourceUtil.getValue(ppName+"_oldWebUrl"),ResourceUtil.getValue(ppName+"_newWebUrl")).replaceFirst( "/","" ));
                }
            }
        }
        //����ڲ���
        addExtSubClass(ppName,fileList);
        return fileList;
    }
    /**
     * ����ڲ���
     * @param fileList
     */
    public List<String> addExtSubClass(String ppName,List<String> fileList)throws Exception{
        int size = fileList.size();
        for (int i = 0; i < size; i++) {
            File f = new File(ResourceUtil.getValue(ppName+"_newWebUrl")+"/"+fileList.get(i));
            if(!f.getName().endsWith(".class")){
                continue;
            }
            File[] subFiles = f.getParentFile().listFiles();
            int subSize = subFiles.length;
            for (int j = 0; j < subSize; j++) {
                String subFileName = subFiles[j].getName();
                if(subFileName.startsWith(f.getName().substring(0,f.getName().indexOf(".class"))+"$")){
                    fileList.add(subFiles[j].getAbsolutePath());
                }
            }

        }
        return fileList;
    }
}
