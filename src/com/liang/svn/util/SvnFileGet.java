package com.liang.svn.util;

import com.liang.util.ResourceUtil;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author liang wei
 * @description 获取升级文件清单
 * @date 2017/6/27 16:26
 */
public class SvnFileGet {

    private static SVNRepository repository = null;

    static {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
    public SvnFileGet(String ppName){
        try {
            //加载properties资源文件
            String snvUrl = ResourceUtil.getValue(ppName+"_svnUrl");
            String svnUser = ResourceUtil.getValue(ppName+"_svnUser");
            String svnPwd = ResourceUtil.getValue(ppName+"_svnPwd");
            try {
                // 身份验证
                ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnUser, svnPwd);
                repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(snvUrl));
                repository.setAuthenticationManager(authManager);
            } catch (SVNException e) {
                throw new RuntimeException("repository 对象为空！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public SvnFileGet(String url, String username, String password) {
        if (!url.trim().isEmpty() && !username.trim().isEmpty() && !password.trim().isEmpty()) {
            try {
                // 身份验证
                ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
                repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
                repository.setAuthenticationManager(authManager);
            } catch (SVNException e) {
                throw new RuntimeException("repository 对象为空！");
            }
        }
    }

    /**
     * 根据注释的信息查找内容
     *
     * @param message
     * @param isLike  是否模糊查询
     * @return
     * @throws SVNException
     */
    public List<String> getLogByMessage(final String message,final boolean isLike) throws SVNException {
        long startRevision = 0;
        long endRevision = -1;// 表示最后一个版本
        // String[] 为过滤的文件路径前缀，为空表示不进行过滤
        final List<String> history = new ArrayList<String>();
        //这里自己修改下，有BUG
        repository.log(new String[] {}, startRevision, endRevision, true, true, new ISVNLogEntryHandler() {
            @Override
            public void handleLogEntry(SVNLogEntry svnlogentry) throws SVNException {
                if (
                        (message.equals(svnlogentry.getMessage()) && !isLike)
                                ||
                                (svnlogentry.getMessage().contains(message) && isLike)
                        ) {
                    // getChangedPaths为提交的历史记录MAP key为文件名，value为文件详情
                    Map changedPaths = svnlogentry.getChangedPaths();
                    // 追加了所有的数据。
                    history.addAll(changedPaths.keySet());
                }
            }
        });

        // 去除重复
        HashSet h = new HashSet(history);
        history.clear();
        history.addAll(h);

        //去除文件夹，非文件类型
        List<String> newLogByMessage = new ArrayList<String>();
        for (String removeNoPoint : history) {
            if (removeNoPoint.contains(".")) {
                newLogByMessage.add(removeNoPoint);
            }
        }
        return newLogByMessage;
    }

    public static void main(String[] args) {
        InputStream stdin = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String ppName = "spay-business";
        try {
            SvnFileGet svn = new SvnFileGet("spay-business");
            List<String> fileList = svn.getLogByMessage("TASK-31471",true);
            //加工处理path
            fileList = new SvnFileUrlDeal().getLocalFileList(fileList,ppName);
            for (int i = 0; i < fileList.size(); i++) {
                System.out.println(fileList.get(i));
            }
//            //加工处理path
//            fileList = new SvnFileUrlDeal().getLocalFileList(fileList,ppName);
//            //输出到本地目录，以便后续打增量包
//            String localPath = ResourceUtil.getValue("udateFileTxtLocation");
//            System.out.println(localPath+"---"+localPath.replace("\\\\","//"));
//            System.out.println(localPath+"---"+localPath.replaceAll("\\\\","//"));
//            //清单文件
//            String filePath = localPath+"/"+ppName+new DateUtil().getDateTime(DateUtil.YEAR_TO_DAY)+".txt";
//            new FileUtil().WriteListToFile(filePath,fileList);
//            if(fileList.size() == 0){
//                return;
//            }
//            //执行打包
//            String targetDirPath = localPath;
//            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始生成"+targetDirPath+"/"+ppName+".jar...");
//            File targetDir = new File(targetDirPath);
//            if (!targetDir.exists()) {
//                targetDir.mkdirs();
//            }
//
//            Manifest manifest = new Manifest();
//            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
//            Process procJar = null;
//            Process procUnJar = null;
//            String localJarPath = targetDirPath+"/"+ppName+".jar";
//            String cmd = "jar -cvf "+localJarPath+ " @"+filePath;
//            System.out.println(cmd);
//            procJar=Runtime.getRuntime().exec(cmd);
//            stdin = procJar.getInputStream();
//            isr = new InputStreamReader(stdin);
//            br = new BufferedReader(isr);
//            String line = null;
//            System.out.println("<output></output>");
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            procJar.destroy();
//            //解压
//            cmd = "unzip  "+localJarPath + " -o -d "+targetDirPath+"/uploadTmp";
//            System.out.println(cmd);
//            procUnJar=Runtime.getRuntime().exec(cmd);
//            stdin = procUnJar.getInputStream();
//            isr = new InputStreamReader(stdin);
//            br = new BufferedReader(isr);
//            line = null;
//            System.out.println("<output></output>");
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            procUnJar.destroy();
//            System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> jar包生成完毕。");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(null != stdin){
                try {
                    stdin.close();
                }catch (Exception e){}
            }
            if(null != isr){
                try {
                    isr.close();
                }catch (Exception e){}
            }
            if(null != br){
                try {
                    br.close();
                }catch (Exception e){}
            }
            try {
                //开始上传 增量jar
//                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 上传增量包到服务器。"+ResourceUtil.getValue(ppName+"_ip")+" "+ResourceUtil.getValue(ppName+"_uploadDir"));
//                FtpUtil.upload(ResourceUtil.getValue(ppName+"_ip"),
//                        ResourceUtil.getValue(ppName+"_user"),
//                        ResourceUtil.getValue(ppName+"_pwd"),
//                        Integer.parseInt(ResourceUtil.getValue(ppName+"_port")),
//                        "E:\\soft\\ideaworkspace\\yr\\升级版本\\20170621\\uploadTmp\\E_\\soft\\ideaworkspace\\yr\\payment",
//                        ResourceUtil.getValue(ppName+"_uploadDir"),
//                        ResourceUtil.getValue(ppName+"_protocol"));
//                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 文件已成功上传到服务器");
//                System.out.println(DateUtil.getDateTime(DateUtil.YEAR_TO_SECOND)+"*** --> 开始执行停服务，升级文件");
            }catch (Exception e){}
        }

    }

}
