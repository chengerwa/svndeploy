package com.liang.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liang wei
 * @description 用一句话描述下该文件的作用
 * @date 2017/6/28 8:44
 */
public class ResourceUtil {
    private static Map<String,String> resourceMap = new HashMap<String,String>();
    static {
        //创建资源配置文件
        String saveDir = "C:/Users/Public/Ver";
        File file = new File(saveDir);
        if(!file.exists()){
            file.mkdirs();
        }
        //
        String configFile = saveDir+"/appconfig.properties";
        resourceMap.put("configFile",configFile);
        file = new File(configFile);
        if(!file.exists()){
            try{
                System.out.println("不存在，创建");
                file.createNewFile();
                InputStream fis =new ResourceUtil().getClass().getResourceAsStream("/resources/appconfig.properties");
                FileUtil.copyFile(fis,file);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        loadConig(configFile);
    }
    public static void loadConig(String configFile) {
        InputStream is= null;
        Properties ps = new Properties();
        Reader rd = null;
        try {
            is = new FileInputStream(new File(configFile));
            rd = new InputStreamReader(is, "UTF-8");
            ps.load(rd);
            Enumeration e = ps.propertyNames();
            while (e.hasMoreElements()){
                String key = e.nextElement().toString();
                resourceMap.put(key,ps.getProperty(key));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                }catch (Exception ex){}
            }
            if (rd != null) {
                try {
                    rd.close();
                }catch (Exception ex){}
            }
        }
    }
    public static void init(){
        try {
            //加载resouces 下面的所有properties文件
            String srcPath =   Thread.currentThread().getContextClassLoader().getResource("").getPath();
            File sourceFile = new File(srcPath+"/resources");
            if(sourceFile.isDirectory()){
                File[]  files = sourceFile.listFiles();
                for (int i = 0; i < files.length; i++) {
                    String filePath = files[i].getAbsolutePath();
                    if(filePath.endsWith(".properties")){
                        Properties ps = new Properties();
                        Reader rd = null;
                        try {
                            rd = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
                            ps.load(rd);
                            Enumeration e = ps.propertyNames();
                            while (e.hasMoreElements()){
                                String key = e.nextElement().toString();
                                resourceMap.put(key,ps.getProperty(key));
                            }
                        }catch (Exception e){
                            throw e;
                        }finally {
                            if (rd != null) {
                                rd.close();
                            }
                        }
                    }
                }
            }else{
                throw new  Exception("未找到资源文件位置"+srcPath+"/resources");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getValue(String key) throws Exception{
        String value = "";
        if(resourceMap.containsKey(key)){
            value = resourceMap.get(key);
        }else{
            throw new RuntimeException("资源文件中未找到key值为"+key+"的配置!");
        }
        return value;
    }
    public static void storeValue(String key,String value) throws Exception{
        InputStream fis =new FileInputStream(ResourceUtil.getValue("configFile"));
        Properties prop = new Properties();
        prop.load(fis);
        fis.close();
        ///保存属性到b.properties文件
        String filePath = ResourceUtil.getValue("configFile");
        FileOutputStream fos = new FileOutputStream(filePath);
        prop.setProperty(key, value);
        prop.store(fos, "Update '" + key + "' value");
        //prop.store(oFile, "modify date "+ DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND));
        fos.close();
        prop.clear();
        ResourceUtil.resourceMap.put(key,value);
    }
    public static void main(String[] args) {
        new ResourceUtil();
    }
}
