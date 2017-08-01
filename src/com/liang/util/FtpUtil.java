package com.liang.util;

import com.jcraft.jsch.*;

import java.io.*;


public class FtpUtil {

    /**
     * 利用JSch包实现SFTP下载、上传文件
     * @param ip 主机IP
     * @param user 主机登陆用户名
     * @param psw  主机登陆密码
     * @param port 主机ssh2登陆端口，如果取默认值，传-1
     * @param orgFile 原文件
     * @param destFile 目标文件
     */
    public static void upload(String ip, String user, String psw ,int port,String orgFile,String destFile,String protocl) throws Exception{
        Session session = null;
        Channel channel = null;
        JSch jsch = new JSch();
        if(port <=0){
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        }else{
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip ,port);
        }
        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);

        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel(protocl);
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;


            //进入服务器指定的文件夹
            //sftp.mkdir(destFile);
            sftp.cd(destFile);
            copyFile(sftp,new File(orgFile),sftp.pwd());
//            //列出服务器指定的文件列表
//            Vector v = sftp.ls("*.txt");
//            for(int i=0;i<v.size();i++){
//                System.out.println(v.get(i));
//            }

            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了

//            File oFile = new File(orgFile);
//            OutputStream outstream = sftp.put(oFile.getName());
//            InputStream instream = new FileInputStream(oFile);
//
//            byte b[] = new byte[1024];
//            int n;
//            while ((n = instream.read(b)) != -1) {
//                outstream.write(b, 0, n);
//            }
//
//            outstream.flush();
//            outstream.close();
//            instream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }
    public static void copyFile(ChannelSftp sftp, File file, String pwd) {

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            try {
                try {
                    String fileName = file.getName();
                    sftp.cd(pwd);
                    System.out.println("正在创建目录:" + sftp.pwd() + "/" + fileName);
                    sftp.mkdir(fileName);
                    System.out.println("目录创建成功:" + sftp.pwd() + "/" + fileName);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                pwd = pwd + "/" + file.getName();
                try {

                    sftp.cd(file.getName());
                } catch (SftpException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int i = 0; i < list.length; i++) {
                copyFile(sftp, list[i], pwd);
            }
        } else {

            try {
                sftp.cd(pwd);

            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("正在复制文件:" + file.getAbsolutePath());
            InputStream instream = null;
            OutputStream outstream = null;
            try {
                outstream = sftp.put(file.getName());
                instream = new FileInputStream(file);

                byte b[] = new byte[1024];
                int n;
                try {
                    while ((n = instream.read(b)) != -1) {
                        outstream.write(b, 0, n);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (SftpException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    outstream.flush();
                    outstream.close();
                    instream.close();

                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                }
            }
        }
    }
}
