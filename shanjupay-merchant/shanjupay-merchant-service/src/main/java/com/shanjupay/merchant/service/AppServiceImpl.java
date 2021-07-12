package com.shanjupay.merchant.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.RandomUuidUtil;
import com.shanjupay.merchant.api.AppService;
import com.shanjupay.merchant.api.dto.AppDTO;
import com.shanjupay.merchant.convert.AppConvert;
import com.shanjupay.merchant.entity.App;
import com.shanjupay.merchant.entity.Merchant;
import com.shanjupay.merchant.mapper.AppMapper;
import com.shanjupay.merchant.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * TODO
 *
 * @author chase
 * @date 2021/5/18 16:19
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException {
        //检查商户是否通过资质申请
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        if (!"2".equals(merchant.getAuditStatus())) {
            throw new BusinessException(CommonErrorCode.E_200003);
        }
        if (isExistAppName(appDTO.getAppName())) {
            throw new BusinessException(CommonErrorCode.E_200004);
        }
        //保存应用信息
        appDTO.setAppId(RandomUuidUtil.getUUID());
        appDTO.setMerchantId(merchant.getId());
        App entity = AppConvert.INSTANCE.dto2entity(appDTO);
        appMapper.insert(entity);
        return AppConvert.INSTANCE.entity2dto(entity);
    }


    @Override
    public List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException {
        List<App> apps = appMapper.selectList(new QueryWrapper<App>().lambda().eq(App::getMerchantId, merchantId));
        List<AppDTO> appDTOS = AppConvert.INSTANCE.listentity2dto(apps);
        return appDTOS;
    }


    @Override
    public AppDTO getAppById(String id) throws BusinessException {
        App app = appMapper.selectOne(new QueryWrapper<App>().lambda().eq(App::getAppId, id));
        AppDTO appDTO = AppConvert.INSTANCE.entity2dto(app);
        return appDTO;
    }


    /**
     * 检验应用名是否已存在
     *
     * @param appName
     * @return
     */
    private boolean isExistAppName(String appName) {
        Integer count = appMapper.selectCount(new QueryWrapper<App>().lambda().eq(App::getAppName, appName));
        return count > 0;
    }
}
