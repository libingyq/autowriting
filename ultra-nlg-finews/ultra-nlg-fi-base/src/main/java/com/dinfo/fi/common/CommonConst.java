package com.dinfo.fi.common;

/**
 * <p>Date:2017/8/8</p>
 * <p>Module:</p>
 * <p>Description: Spark任务发送组件公用常量</p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */
public class CommonConst {

    public static final String HttpUrlPrefix = "http://";
    public static final String HttpHeaderContentType = "Content-Type";
    public static final String HttpHeaderAccept = "Accept";
    public static final String HttpTypeJsonApplication = "application/json";

    public static final Integer SUC_CODE = 200;
    public static final Integer FAIL_CODE = 500;

    public static final String GetDirAll = "/*";
    public static final String LIB = "/lib";
    public static final String OneSpace = " ";
    public static final String HdfsUrlPrefix = "hdfs://";
    public static final String ClassParam = "--class";
    public static final String JarParam = "--jar";
    public static final String ArgParam = "--arg";
    public static final String Comma = ",";
    public static final String Sprit = "/";
    public static final String QuestionMark = "?";
    public static final String AndMark = "&";
    public static final String equalMark = "=";

    public static final String PropertiesFile = "--properties-file";
    public static final String LogParam = "1><LOG_DIR>/App.stdout 2><LOG_DIR>/App.stderr";

    public static final String StagingBaseDir = "/httpSendStaging/.sparkStaging/";

    public static final String HADOOP_COMMON_HOME_SHARE_HADOOP_COMMON = "{{HADOOP_COMMON_HOME}}/share/hadoop/common";
    public static final String HADOOP_HDFS_HOME_SHARE_HADOOP_HDFS = "{{HADOOP_HDFS_HOME}}/share/hadoop/hdfs";
    public static final String HADOOP_MAPRED_HOME_SHARE_HADOOP_MAPREDUCE = "{{HADOOP_MAPRED_HOME}}/share/hadoop/mapreduce";
    public static final String HADOOP_YARN_HOME_SHARE_HADOOP_YARN = "{{HADOOP_YARN_HOME}}/share/hadoop/yarn";
}
