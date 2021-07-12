package com.shanjupay.merchant;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author chase
 * @date 2021/5/6 11:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testHtml() {
        String url = "http://www.baidu.com";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        System.out.println(body);
    }

    @Test
    public void testGetSmsCode() {
        String url = "http://localhost:56085/sailing/generate?name=sms&effectiveTime=600";//验证码过期时间为600秒
        String phone = "13434343434";
        log.info("调用短信服务发送验证码：{}", url);
        // 请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("mobile", phone);
        // 请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置数据格式为json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // 封装请求参数
        HttpEntity httpEntity = new HttpEntity(requestBody, httpHeaders);
        Map responseMap = null;
        try {
            // post请求
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            //
            log.info("调用短信微服务发送验证码：返回值{}", JSON.toJSONString(exchange));
            // 获取响应
            responseMap = exchange.getBody();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        // 取出body中的result数据
        if (responseMap != null && responseMap.get("result") != null) {
            Map resultMap = (Map) responseMap.get("result");
            String key = resultMap.get("key").toString();
            System.out.println(key);
        }
    }
}
