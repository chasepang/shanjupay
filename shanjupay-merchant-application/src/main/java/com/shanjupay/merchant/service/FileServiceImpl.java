package com.shanjupay.merchant.service;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.AliUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author chase
 * @date 2021/5/10 10:37
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${oss.ali.url}")
    private String aliUrl;
    @Value("${oss.ali.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.ali.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.ali.bucket}")
    private String bucket;

    @Override
    public String upload(byte[] bytes, String fileName) {
        try {
            AliUtil.upload2Ali(accessKeyId, accessKeySecret, bucket, bytes, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        //返回文件名称
        return aliUrl + fileName;
    }
}
