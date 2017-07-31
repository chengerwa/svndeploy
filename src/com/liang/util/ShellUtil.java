package com.liang.util;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ShellUtil {

    private Connection conn;
    /** 远程机器IP */
    private String ip;
    /** 用户名 */
    private String osUsername;
    /** 密码 */
    private String password;
    public static String charset = Charset.defaultCharset().toString();

    private static final int TIME_OUT = 1000 * 5 * 60;

    /**
     * 构造函数
     * @param ip
     * @param usr
     * @param pasword
     */
    public ShellUtil(String ip, String usr, String pasword) {
        this.ip = ip;
        this.osUsername = usr;
        this.password = pasword;
    }


    /**
     * 登录
     * @return
     * @throws IOException
     */
    private boolean login() throws IOException {
        conn = new Connection(ip);
        conn.connect();
        return conn.authenticateWithPassword(osUsername, password);
    }

    /**
     * 执行脚本
     *
     * @param cmds
     * @return
     * @throws Exception
     */
    public int exec(String cmds) throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        int ret = -1;
        try {
            if (login()) {
                //cmds = "sh /weblogic/wls1036_x64/user_projects/domains/appdomain/test.sh";
                // Open a new {@link Session} on this connection
                Session session = conn.openSession();
                // Execute a command on the remote machine.
                session.requestPTY("bash");
                // 打开一个Shell
                session.startShell();
                // 准备输入命令
                PrintWriter out = new PrintWriter(session.getStdin());
                // 输入待执行命令

                out.println(cmds);

                out.println("exit");

                // 6. 关闭输入流

                out.close();
                //session.execCommand(cmds);

                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);

                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);

                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);

                System.out.println(session.getExitStatus()+"outStr=" + outStr);
                System.out.println("outErr=" + outErr);

                ret = session.getExitStatus();
            } else {
                throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stdOut != null) {
                stdOut.close();
            }
            if (stdErr != null) {
                stdErr.close();
            }
        }
        return ret;
    }

    /**
     * @param in
     * @param charset
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public static void main(String args[]) throws Exception {
        ShellUtil.test();
       // ShellUtil executor = new ShellUtil("192.168.234.123", "root", "beebank");
        // 执行myTest.sh 参数为java Know dummy
      //  System.out.println(executor.exec("/home/IFileGenTool /load_data.sh t_users myDataBase01"));

    }
    public static void test() throws Exception{
        String ppName = "spay-manage";
        ShellUtil.charset = "GBK";
        String ftpIp = ResourceUtil.getValue(ppName+"_ftpip");
        String ftpUser = ResourceUtil.getValue(ppName+"_ftpuser");
        String ftpPwd = ResourceUtil.getValue(ppName+"_ftppwd");
        ShellUtil shellUtil = new ShellUtil(ftpIp,ftpUser,ftpPwd);
        shellUtil.exec(ResourceUtil.getValue(ppName+"_ftpshell"));
    }
}
