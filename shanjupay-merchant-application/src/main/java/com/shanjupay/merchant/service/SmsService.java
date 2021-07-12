package com.shanjupay.merchant.service;

import com.shanjupay.common.domain.BusinessException;

public interface SmsService {

    /**
     * 获取短信验证码
     *
     * @param phone
     * @return
     */
    String sendMsg(String phone) throws BusinessException;

    /**
     * 校验验证码，抛出异常则验证无效
     *
     * @param verifyKey
     * @param verifyCode
     */
    void checkVerifyCode(String verifyKey, String verifyCode) throws BusinessException;
}
