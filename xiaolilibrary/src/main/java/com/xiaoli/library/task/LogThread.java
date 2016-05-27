package com.xiaoli.library.task;


import com.xiaoli.library.utils.FileUtils;

/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName:LogThread
 * @date 2015-12-21
 * @Description:日志写入线程实现类
 */
public class LogThread implements Runnable {

    /**
     * 存储目录 E:\\test\\
     */
    private String directory;

    /**
     *存储文件名 user.txt
     */
    private String fileName;

    /**
     *存储内容
     */
    private String fileContent;

    /**
     *
     * @param _directory 存储目录
     * @param _fileName 存储文件名
     * @param _fileContent 存储内容
     */
    public LogThread(String _directory,String _fileName, String _fileContent){
        this.directory = _directory;
        this.fileName = _fileName;
        this.fileContent = _fileContent;
    }
    @Override
    public void run() {
        FileUtils.writeAppend(this.directory,this.fileName,fileContent);
    }
}
