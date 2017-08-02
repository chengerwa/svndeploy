package com.liang.util;

/**
 * @author liang wei
 * @description �ļ�����
 * @date 2017/6/27 17:54
 */

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class FileUtil {
    public void WriteListToFile(String filePath, List<String> data) throws Exception {
        try {
            FileWriter fw = new FileWriter(filePath, false);
            BufferedWriter bw = new BufferedWriter(fw);
            int leng = data.size();
            for (int i = 0; i < leng; i++) {
                bw.write(data.get(i)+"\r\n");
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void WriteStringToFile(String filePath) {
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println("http://www.jb51.net");// ���ļ���д���ַ���
            ps.append("http://www.jb51.net");// �����еĻ���������ַ���
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void WriteStringToFile3(String filePath) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath));
            pw.println("abc ");
            pw.println("def ");
            pw.println("hef ");
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void WriteStringToFile4(String filePath) {
        try {
            RandomAccessFile rf = new RandomAccessFile(filePath, "rw");
            rf.writeBytes("op\r\n");
            rf.writeBytes("app\r\n");
            rf.writeBytes("hijklllll");
            rf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteStringToFile5(String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            String s = "http://www.jb51.netl";
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String getRunPath(Class cls){
        return cls.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    /**
     * 查找文件夹的特诊为mask的子文件夹
     * @param dirPath
     * @param mask
     * @return
     */
    public String getSubDirPath(String dirPath,String mask){
        String des = "";
        File f = new File(dirPath);
        List<String> list = new ArrayList<String>();
        new FileUtil().listAllDir(f,list);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).endsWith(mask)){
                des = list.get(i);
                break;
            }
        }
        return des;
    }
    public static void main(String[] args)throws Exception {
        //
        String s1 = "E:\\soft\\ideaworkspace\\yr\\payment\\modules/webapps/payment/WEB-INF/classes/com/sinosoft/payment/blsvr/jf/BLPrpJPayBank.class";
        String s2 = "E:\\soft\\ideaworkspace\\yr\\payment\\modules/webapps/payment/compensate/UIPayInvoicePrint.jsp";
        zip (s1, "E:\\soft\\ideaworkspace\\yr\\0731\\test.jar");
        zip (s2, "E:\\soft\\ideaworkspace\\yr\\0731\\test.jar");
        //String tmpFile = "E:\\soft\\ideaworkspace\\yr\\0731\\claim.jar";
        //unZip(tmpFile,"E:\\soft\\ideaworkspace\\yr\\0731");
        //System.out.println(new FileUtil().getSubDirPath("E:\soft\ideaworkspace\yr\payment.jar","WEB-INF"));
    }
    static class MyFilter implements FilenameFilter{
        private String type;
        public MyFilter(String type){
            this.type = type;
        }
        public boolean accept(File dir,String name){
            return name.endsWith(type);
        }
    }
    public  void listAllFile(File f,List<String> list){
        if(f!=null){
            if(f.isDirectory()){
                File[] fileArray=f.listFiles();
                if(fileArray!=null){
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        listAllFile(fileArray[i],list);
                    }
                }
            }
            else{
                list.add(f.getAbsolutePath());
            }
        }
    }
    public  void listAllDir(File f,List<String> list){
        if(f!=null){
            if(f.isDirectory()){
                File[] fileArray=f.listFiles();
                if(fileArray!=null){
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        list.add(f.getAbsolutePath());
                        listAllDir(fileArray[i],list);
                    }
                }
            }
        }
    }
    /**
     * 得到当前jar包运行同级目录
     * */
    public String getSameLevelPath(Class cls) throws IOException
    {
        String jarWholePath = cls.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        String jarPath = new File(jarWholePath).getParentFile().getAbsolutePath();
        return jarPath;
    }
    public static void copyFile(InputStream fis, File target) {
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(fis);
            fos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                fis.close();
            }catch (Exception e){}
            try{
                fos.close();
            }catch (Exception e){}
        }
    }
    private static void copyFile(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(source));
            fos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                fis.close();
            }catch (Exception e){}
            try{
                fos.close();
            }catch (Exception e){}
        }
    }


    /**
     * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件
     * @param sourceDir
     * @param zipFile
     */

    public static void zip(String sourceDir, String zipFile) {

        OutputStream os;

        try {

            os = new FileOutputStream(zipFile);

            BufferedOutputStream bos = new BufferedOutputStream(os);

            ZipOutputStream zos = new ZipOutputStream(bos);

            File file = new File(sourceDir);

            String basePath = null;

            if (file.isDirectory()) {

                basePath = file.getPath();

            } else {//直接压缩单个文件时，取父目录

                basePath = file.getParent();

            }

            zipFile(file, basePath, zos);

            zos.closeEntry();

            zos.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * 功能：执行文件压缩成zip文件
     * @param source
     * @param basePath  待压缩文件根目录
     * @param zos
     */

    private static void zipFile(File source, String basePath,

                                ZipOutputStream zos) {

        File[] files = new File[0];

        if (source.isDirectory()) {

            files = source.listFiles();

        } else {

            files = new File[1];

            files[0] = source;

        }

        String pathName;//存相对路径(相对于待压缩的根目录)

        byte[] buf = new byte[1024];

        int length = 0;

        try {

            for (File file : files) {

                if (file.isDirectory()) {

                    pathName = file.getPath().substring(basePath.length() + 1)

                            + "/";

                    zos.putNextEntry(new ZipEntry(pathName));

                    zipFile(file, basePath, zos);

                } else {

                    pathName = file.getPath().substring(basePath.length() + 1);

                    InputStream is = new FileInputStream(file);

                    BufferedInputStream bis = new BufferedInputStream(is);

                    zos.putNextEntry(new ZipEntry(pathName));

                    while ((length = bis.read(buf)) > 0) {

                        zos.write(buf, 0, length);

                    }

                    is.close();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    /**
     * 功能：解压 zip 文件，只能解压 zip 文件
     * @param zipfile
     * @param destDir
     */

    public static void unZip(String zipfile, String destDir) throws Exception {

        destDir = destDir.endsWith("\\") ? destDir : destDir + "\\";

        byte b[] = new byte[1024];

        int length;

        ZipFile zipFile;

        try {

            zipFile = new ZipFile(new File(zipfile));

            Enumeration enumeration = zipFile.getEntries();

            ZipEntry zipEntry = null;

            while (enumeration.hasMoreElements()) {

                zipEntry = (ZipEntry) enumeration.nextElement();

                File loadFile = new File(destDir + zipEntry.getName());

                if (zipEntry.isDirectory()) {

                    loadFile.mkdirs();

                } else {

                    if (!loadFile.getParentFile().exists()){

                        loadFile.getParentFile().mkdirs();

                    }

                    OutputStream outputStream = new FileOutputStream(loadFile);

                    InputStream inputStream = zipFile.getInputStream(zipEntry);

                    while ((length = inputStream.read(b)) > 0)

                        outputStream.write(b, 0, length);

                }

            }

        } catch (IOException e) {

            e.printStackTrace();
            throw e;
        }

    }
}
