package com.liang.svn.util;

import com.liang.util.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author liang wei
 * @description 用一句话描述下该文件的作用
 * @date 2017/6/28 16:40
 */
public class VersionDeploy {
    public String deploy(String taskName,String systemCode){
        String ppName = systemCode;
        String rtMsg = "";
        String localUploadDir = "";//升级包解压后的webapp目录
        boolean successFlag = true;
        try {
            SvnFileGet svn = new SvnFileGet(ppName);
            List<String> fileList = svn.getLogByMessage(taskName,true);
            //加工处理path
            fileList = new SvnFileUrlDeal().getLocalFileList(fileList,ppName);
            //输出到本地目录，以便后续打增量包
            String localPath = ResourceUtil.getValue(ppName+"_ftpLocalDir")+"/"+taskName;
            File f = new File(localPath);
            f.mkdirs();
            System.out.println(localPath+"---"+localPath.replace("\\\\","//"));
            System.out.println(localPath+"---"+localPath.replaceAll("\\\\","//"));
            //清单文件
            String filePath = localPath+"/"+ppName+"_"+taskName+".txt";
            new FileUtil().WriteListToFile(filePath,fileList);
            if(fileList.size() == 0){
                System.out.println("没有要打包的数据");
                return "未找到需要升级的文件清单";
            }
            //执行打包
            String targetDirPath = localPath;
            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始生成"+targetDirPath+"/"+ppName+".jar...");
            File targetDir = new File(targetDirPath);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            Manifest manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            Process proc = null;
            String localJarPath = targetDirPath+"/"+ppName+".jar";
            String[] cmd = {"cmd", "/k", localPath.substring(0,1)+":& cd "+ResourceUtil.getValue(ppName+"_localAppUrl")+" & jar -cvf "+localJarPath+ " @"+filePath};
            System.out.println(Arrays.toString( cmd ));
            proc=Runtime.getRuntime().exec(cmd);
            InputStream stdin = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin,"GBK");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<output></output>");
            while ((line = br.readLine()) != null && !"".equals(br.readLine().trim())) {
                System.out.println(line);
            }
            proc.destroy();
            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> jar包生成完毕。");
            //解压
            while(!new File(localJarPath).exists()){
                Thread.sleep(2000);
            }
            String tmpUploadDir = targetDirPath+"/"+ppName+"_"+taskName;
            File uploadTmpDir  = new File(tmpUploadDir);
            uploadTmpDir.mkdirs();
            String[] cmds = {"cmd", "/k", localPath.substring(0,1)+":& cd "+tmpUploadDir+" & jar xvf "+localJarPath};
            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始解压"+ Arrays.toString(cmds));
            proc=Runtime.getRuntime().exec(cmds);
            stdin = proc.getInputStream();
            isr = new InputStreamReader(stdin,"GBK");
            br = new BufferedReader(isr);
            line = null;
            System.out.println("<output></output>");
            while ((line = br.readLine()) != null && !"".equals(line) && line.length() > 10) {
                System.out.println(line);
            }
            proc.destroy();
            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> jar解压完毕。");
            localUploadDir = new File(new FileUtil().getSubDirPath(tmpUploadDir,"META-INF")).getParent();
            //上传路径直接修改为meta-info 同级目录
//            if(null == localUploadDir || "".equals(localUploadDir)){
//                //maven 项目
//                localUploadDir = new File(new FileUtil().getSubDirPath(tmpUploadDir,"target")).getParent();
//            }modify 2017/08/02，上传路径直接修改为解压文件路径
            //localUploadDir = tmpUploadDir;
        }catch(Exception e){
            successFlag = false;
            rtMsg = "发布失败:"+e.getMessage();
            e.printStackTrace();
        }finally {
            try {
                if(!successFlag){
                    return rtMsg;
                }
                //清理服务器上传文件夹
                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 文件已成功上传到服务器");
                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始执行脚本服务，升级文件");
                //执行shell
                String ftpIp = ResourceUtil.getValue(ppName+"_ftpip");
                String ftpUser = ResourceUtil.getValue(ppName+"_ftpuser");
                String ftpPwd = ResourceUtil.getValue(ppName+"_ftppwd");
                int ftpPort = Integer.parseInt(ResourceUtil.getValue(ppName+"_ftpport"));
                ShellUtil shellUtil = new ShellUtil(ftpIp,ftpUser,ftpPwd);
                ShellUtil.charset = "GBK";
                shellUtil.exec("rm -rf "+ResourceUtil.getValue(ppName+"_ftpuploadDir")+"/*");
                //开始上传 增量升级文件
                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 上传增量包到服务器。"+ ResourceUtil.getValue(ppName+"_ftpip")+" "+ResourceUtil.getValue(ppName+"_ftpuploadDir"));

                File uploadFile = new File (localUploadDir);
                File[] fileList = uploadFile.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if(fileList[i].getName().endsWith("META-INF")){
                        continue;
                    }
                    FtpUtil.upload(ftpIp,
                            ftpUser,
                            ftpPwd,
                            ftpPort,
                            fileList[i].getAbsolutePath(),
                            ResourceUtil.getValue(ppName+"_ftpuploadDir"),
                            ResourceUtil.getValue(ppName+"_ftpprotocol"));
                }

                ShellUtil.charset = "GBK";
                shellUtil.exec(ResourceUtil.getValue(ppName+"_ftpshell"));
                rtMsg = "发布成功";
            }catch (Exception e){rtMsg = "发布失败:"+e.getMessage();}
        }
        return rtMsg;
    }

    public static void main(String[] args) {
        try{
            InputStreamReader isr = null;
            BufferedReader br = null;
            String line = null;
            String[] cmds = {"cmd", "/k", "E:& cd E:\\soft\\ideaworkspace\\yr\\updateFiles/payment9.4/payment_payment9.4 & jar xvf E:\\soft\\ideaworkspace\\yr\\updateFiles/payment9.4/payment.jar"};
            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始解压"+ Arrays.toString(cmds));
            InputStream stdin = null;
            Process proc=Runtime.getRuntime().exec(cmds);
            stdin = proc.getInputStream();
            isr = new InputStreamReader(stdin,"GBK");
            br = new BufferedReader(isr);
            line = null;
            System.out.println("<output></output>");
            while ((line = br.readLine()) != null && !"".equals(line.trim()) && line.length()>10) {
                System.out.println( line );
                System.out.println("------00000-------");
            }
            System.out.println("-----|||||||||||||||||||||||||||||||||||||||||||||---");
            System.out.println("-------------");
            proc.destroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
