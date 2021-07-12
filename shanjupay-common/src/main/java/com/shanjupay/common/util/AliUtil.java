package com.shanjupay.common.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * TODO
 *
 * @author chase
 * @date 2021/5/10 10:45
 */
public class AliUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(AliUtil.class);


    /**
     * 工具方法，上传文件
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucket
     * @param bytes
     */
    public static void upload2Ali(String accessKeyId, String accessKeySecret, String bucket, byte[] bytes, String fileName) {
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        //创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
        //上传文件: 存储空间（bucketName） 文件名称（objectName）
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ossClient.putObject(bucket, fileName, byteArrayInputStream);
        //关闭ossClient
        ossClient.shutdown();
    }

}
