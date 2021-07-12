package com.shanjupay.transaction.api;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.transaction.api.dto.PayChannelDTO;
import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;

import java.util.List;

/**
 * 支付渠道服务
 */
public interface PayChannelService {

    /**
     * 获取平台服务类型
     *
     * @return
     * @throws BusinessException
     */
    List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException;

    /**
     * 为APP绑定平台服务类型
     *
     * @param appId
     * @param platformChannelCode
     * @throws BusinessException
     */
    void bindPlatformChannelForApp(String appId, String platformChannelCode) throws BusinessException;


    /**
     * 应用是否已绑定某个服务类型
     *
     * @param appId
     * @param platformChannel
     * @return
     * @throws BusinessException
     */
    int queryAppBindPlatformChannel(String appId, String platformChannel) throws BusinessException;


    /**
     * 根据平台服务类型获取支付渠道列表
     *
     * @param platformChannelCode
     * @return
     * @throws BusinessException
     */
    List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) throws BusinessException;


    /**
     * 保存支付渠道参数
     *
     * @param payChannelParamDTO
     * @throws BusinessException
     */
    void savePayChannelParam(PayChannelParamDTO payChannelParamDTO) throws BusinessException;


    /**
     * 获取指定应用指定服务类型下所包含的原始支付渠道参数列表
     *
     * @param appId           应用id
     * @param platformChannel 服务类型
     * @return
     */
    List<PayChannelParamDTO> queryPayChannelParamByAppAndPlatform(String appId, String platformChannel) throws BusinessException;


    /**
     * 获取指定应用指定服务类型下所包含的某个原始支付参数
     *
     * @param appId
     * @param platformChannel
     * @param payChannel
     * @return
     * @throws BusinessException
     */
    PayChannelParamDTO queryParamByAppPlatformAndPayChannel(String appId, String platformChannel, String payChannel) throws BusinessException;

}
