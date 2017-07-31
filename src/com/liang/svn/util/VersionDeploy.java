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
        InputStream stdin = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String ppName = systemCode;
        String rtMsg = "";
        String localUploadDir = "";//升级包解压后的webapp目录
        try {
            SvnFileGet svn = new SvnFileGet(ppName);
            List<String> fileList = svn.getLogByMessage(taskName,true);
            //加工处理path
            fileList = new SvnFileUrlDeal().getLocalFileList(fileList,ppName);
            //输出到本地目录，以便后续打增量包
            String localPath = ResourceUtil.getValue(ppName+"_ftpLocalDir");
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
            Process procJar = null;
            Process procUnJar = null;
            String localJarPath = targetDirPath+"/"+ppName+".jar";
            String[] cmd = {"cmd", "/k", localPath.substring(0,1)+":& cd "+ResourceUtil.getValue(ppName+"_newWebUrl")+" & jar -cvf "+localJarPath+ " @"+filePath};
            System.out.println(Arrays.toString( cmd ));
            procJar=Runtime.getRuntime().exec(cmd);
            try {
                procJar = Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stdin = procJar.getInputStream();
            procJar.getOutputStream().close();//加上这2句即可解决
            procJar.getErrorStream().close();
            isr = new InputStreamReader(stdin,"GBK");
            br = new BufferedReader(isr);

            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> jar包生成完毕。");
            //解压
            String tmpUploadDir = targetDirPath+"/"+ppName+"_"+taskName;
            File uploadTmpDir  = new File(tmpUploadDir);
            uploadTmpDir.mkdirs();
            String[] cmds = {"cmd", "/k", localPath.substring(0,1)+":& cd "+tmpUploadDir+" & jar xvf "+localJarPath};
            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始解压"+ Arrays.toString(cmds));
            procUnJar=Runtime.getRuntime().exec(cmds);
            procUnJar.getOutputStream().close();//加上这2句即可解决
            procUnJar.getErrorStream().close();

            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> jar解压完毕。");
            localUploadDir = new File(new FileUtil().getSubDirPath(tmpUploadDir,"WEB-INF")).getParent();
            if(null == localUploadDir || "".equals(localUploadDir)){
                //maven 项目
                localUploadDir = new File(new FileUtil().getSubDirPath(tmpUploadDir,"target")).getParent();
            }
        }catch(Exception e){
            rtMsg = "发布失败:"+e.getMessage();
            e.printStackTrace();
        }finally {
            if(null != stdin){
                try {
                    stdin.close();
                }catch (Exception e){rtMsg = "发布失败:"+e.getMessage();}
            }
            if(null != isr){
                try {
                    isr.close();
                }catch (Exception e){rtMsg = "发布失败:"+e.getMessage();}
            }
            if(null != br){
                try {
                    br.close();
                }catch (Exception e){rtMsg = "发布失败:"+e.getMessage();}
            }
            try {
                //开始上传 增量jar
                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 上传增量包到服务器。"+ ResourceUtil.getValue(ppName+"_ftpip")+" "+ResourceUtil.getValue(ppName+"_ftpuploadDir"));
                String ftpIp = ResourceUtil.getValue(ppName+"_ftpip");
                String ftpUser = ResourceUtil.getValue(ppName+"_ftpuser");
                String ftpPwd = ResourceUtil.getValue(ppName+"_ftppwd");
                int ftpPort = Integer.parseInt(ResourceUtil.getValue(ppName+"_ftpport"));
                FtpUtil.upload(ftpIp,
                        ftpUser,
                        ftpPwd,
                        ftpPort,
                        localUploadDir,
                        ResourceUtil.getValue(ppName+"_ftpuploadDir"),
                        ResourceUtil.getValue(ppName+"_ftpprotocol"));
                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 文件已成功上传到服务器");
                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始执行停服务，升级文件");
                //执行shell
                ShellUtil shellUtil = new ShellUtil(ftpIp,ftpUser,ftpPwd);
                ShellUtil.charset = "GBK";
                shellUtil.exec(ResourceUtil.getValue(ppName+"_ftpshell"));
                rtMsg = "发布成功";
            }catch (Exception e){rtMsg = "发布失败:"+e.getMessage();}
        }
        return rtMsg;
    }

    public static void main(String[] args) {
        new VersionDeploy().deploy("TASK-31471","spay-manage");
    }
}
