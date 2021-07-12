package com.shanjupay.merchant.service;

import com.alibaba.fastjson.JSON;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author chase
 * @date 2021/5/6 14:57
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${sms.effectiveTime}")
    private String effectiveTime;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取短信验证码
     *
     * @param phone
     * @return
     */
    @Override
    public String sendMsg(String phone) throws BusinessException {
        String url = smsUrl + "/generate?name=sms&effectiveTime=" + effectiveTime;
        log.info("调用短信微服务发送验证码:url:{}", url);
        Map<String, Object> body = new HashMap<>();
        body.put("mobile", phone);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        Map responseMap = null;
        try {
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            log.info("调用短信微服务发送验证码返回值:{}", JSON.toJSONString(exchange));
            responseMap = exchange.getBody();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
//            throw new RuntimeException("发送验证码出错");
            throw new BusinessException(CommonErrorCode.E_100107);
        }
        if (responseMap == null || responseMap.get("result") == null) {
            throw new BusinessException(CommonErrorCode.E_100107);
        }
        Map resultMap = (Map) responseMap.get("result");
        return resultMap.get("key").toString();
    }


    @Override
    public void checkVerifyCode(String verifyKey, String verifyCode) throws BusinessException {
        String url = smsUrl + "/verify?name=sms&verificationCode=" + verifyCode + "&verificationKey=" + verifyKey;
        Map responseMap = null;
        try {
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, Map.class);
            responseMap = exchange.getBody();
            log.info("检验验证码响应内容:{}", JSON.toJSONString(responseMap));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            throw new BusinessException(CommonErrorCode.E_100102);
        }
        if (responseMap == null || responseMap.get("result") == null || !(Boolean) responseMap.get("result")) {
//            throw new RuntimeException("验证码错误");
            throw new BusinessException(CommonErrorCode.E_100102);
        }
    }
}
